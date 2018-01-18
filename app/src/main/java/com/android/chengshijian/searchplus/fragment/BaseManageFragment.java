package com.android.chengshijian.searchplus.fragment;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnCheckAccountValidateListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.LoginUtil;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.view.IBaseManageFragmentView;
import com.android.volley.VolleyError;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.Collections;
import java.util.List;

/**
 * 账号管理的类
 *
 * 学号管理和App账号管理都继承自它
 *
 * Created by ChengShiJian on 2018/1/17.
 */

public abstract class BaseManageFragment<T> extends BaseFragment implements View.OnClickListener, OnCheckAccountValidateListener ,IBaseManageFragmentView{
    private SwipeMenuRecyclerView mRecyclerView;
    private FloatingActionButton mAddFb;
    private int mClickedPosition;
    protected RecyclerView.Adapter mAdapter;
    protected List<T> mList;

    @Override
    public boolean isHaveToolbar() {
        return false;
    }

    /**
     * 获取集合（从本地数据库中获取的数据）
     *
     * 学号管理中泛型传的是@link User
     *
     * App账号管理中泛型传的是@link App
     *
     * @return
     */
    public abstract List<T> getList();

    @Override
    public void initView() {
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.account_manage_rcv);
        mAddFb = (FloatingActionButton) findViewById(R.id.add);
    }

    @Override
    public void initData() {
        transparentNavigationBar();
        setDisplayHomeAsUpEnabled(true);
        setToolbarTitle("");
        mRecyclerView.setOnItemStateChangedListener(new OnItemStateChangedListener() {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {
                    // 拖拽的时候背景就透明了，这里我们可以添加一个特殊背景。
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.accent));
                } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {

                } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
                    // 在手松开的时候还原背景。
                    ViewCompat.setBackground(viewHolder.itemView, ContextCompat.getDrawable(getActivity(), R.color.back_color));
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(getResources().getColor(R.color.back_color), 0, 14));
        initSwipeMenuItemClickListener();
        initSwipeMenuCreator();
        mRecyclerView.setAdapter(mAdapter);
        initOnItemMoveListener();
        mRecyclerView.setItemViewSwipeEnabled(false);
        mRecyclerView.setLongPressDragEnabled(true);

    }

    private void initSwipeMenuItemClickListener() {
        mRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                if (menuPosition == 0) {
                    if (mList.size() > 0) {
                        if(adapterPosition==onGetSelectedPos()) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(R.string.hint)
                                    .setMessage("不能删除当前正在使用的账号！")
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.ok, null)
                                    .show();
                        } else  {
                            onAddToHistory(adapterPosition);
                            onDelete(adapterPosition);
                        }
                    } else {

                    }
                }
            }
        });
    }

    private void initSwipeMenuCreator() {
        mRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = 250;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                            .setBackground(R.color.colorAccent)
                            .setImage(R.drawable.ic_delete_24dp)
                            .setWeight(11)
                            .setText("删除")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
                }
            }
        });
    }

    private void initOnItemMoveListener() {
        mRecyclerView.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                // 不同的ViewType不能拖拽换位置。
                if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

                // 真实的Position：通过ViewHolder拿到的position都需要减掉HeadView的数量。
                int fromPosition = srcHolder.getAdapterPosition() - mRecyclerView.getHeaderItemCount();
                int toPosition = targetHolder.getAdapterPosition() - mRecyclerView.getHeaderItemCount();

                Collections.swap(mList, fromPosition, toPosition);
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            }
        });
    }


    private void initRecyclerView() {
        mList=getList();
        mClickedPosition = onGetPosition();//点击的位置默认为当前账号的位置
        mAdapter=onGetAdapter(mClickedPosition);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void initListener() {
        mAddFb.setOnClickListener(this);
        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                int preClickedPos=onGetSelectedPos();
                if (preClickedPos != position) {
                    onSaveAccountToSharedPreferences(position);
                    onClickedUpdateUI(position,position);//此方法来自IBaseManageFragmentView接口
                    mAdapter.notifyItemChanged(preClickedPos);//将原来的视图也一起更新
                    LoginUtil.isValidateAccount(LoginUtil.ACCOUNT_MANAGE, BaseManageFragment.this);
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_manage_school_account;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                onAddAccount();
                break;
        }
    }


    public void addToHistory(String account) {
        DataUtil.addDeleteAccountToHistory(account);
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 在生命周期onResume调用
         *
         * onResume方法在用用户能看到App的Ui前调用
         *
         * 当用户按home键离开App
         *
         * 然后又回到App时也会调用
         *
         */
        initRecyclerView();
    }

    @Override
    public void onStartCheck() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(R.string.is_checking);
            }
        });
    }

    @Override
    public void onGetCookie(String cookie) {
        DataUtil.saveGradeCookieTime();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(R.string.get_cookie_success);
            }
        });
    }

    @Override
    public void onCheckSuccess(int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(R.string.switch_success);
            }
        });

    }

    @Override
    public void onErrorResponse(final int type, final VolleyError volleyError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == Constant.ID_CODE) {
                    ToastUtil.showShortToast(getString(R.string.error_in_lode_image) + "\n" + volleyError.getCause());
                } else if (type == Constant.LOGIN) {
                    ToastUtil.showShortToast(getString(R.string.error_in_login) + "\n" + volleyError.getCause());
                }
            }
        });
    }

    @Override
    public void onAddressClosed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(R.string.address_closed);
            }
        });
    }

    @Override
    public void onLoginFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast("login failed!");
            }
        });
    }
}
