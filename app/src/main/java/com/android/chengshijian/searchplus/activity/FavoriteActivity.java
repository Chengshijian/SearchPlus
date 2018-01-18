package com.android.chengshijian.searchplus.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.model.CloudFavorite;
import com.android.chengshijian.searchplus.model.Favorite;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.ProgressDialogUtil;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.view.recyclerview.FavoriteAdapter;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

/**
 * 收藏类
 * <p>
 * 它继承自MultiOperateActivity
 * <p>
 * Created by ChengShiJian on 2018/1/15.
 */

public class FavoriteActivity extends MultiOperateActivity<Favorite> implements View.OnClickListener, FABProgressListener {

    private FavoriteAdapter mAdapter;
    protected FABProgressCircle mProgressCircle;
    protected FloatingActionButton mFab;
    private ProgressDialog mProgressDialog;

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected int getRecyclerResId() {
        return R.id.favorite_rv;
    }

    @Override
    public void initData() {
        super.initData();
        mProgressDialog = ProgressDialogUtil.getRequestDataDialog(this);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_favorite;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("我的收藏");
        mList = DataSupport.findAll(Favorite.class);
        mAdapter = new FavoriteAdapter(mList);
        initSwipeMenuItemClickListener();
        initSwipeMenuCreator();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemViewSwipeEnabled(false);
        mRecyclerView.setLongPressDragEnabled(true);
    }

    @Override
    public void initView() {
        super.initView();
        mProgressCircle = findViewById(R.id.upload);
        mFab = findViewById(R.id.fab);
    }

    private void initSwipeMenuItemClickListener() {
        mRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                if (menuPosition == 0) {
                    mAdapter.delete(adapterPosition);
                } else {
                    //to do your work!
                }

            }
        });
    }

    private void initSwipeMenuCreator() {
        mRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

                int width = 200;

                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(FavoriteActivity.this)
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

    @Override
    public void initListener() {
        super.initListener();
        mFab.setOnClickListener(this);
        mProgressCircle.attachListener(this);
    }

    @Override
    public void onClick(View view) {
        if (DataUtil.isCurrentAppUserExist()) {
            mProgressCircle.show();
            saveDataToCloud();
        } else {
            showLoginDialog();
        }
    }

    private void showLoginDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.hint)
                .setMessage("您还没有登录App账号，是否登录？")
                .setCancelable(true)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(LoginActivity.class);
                    }
                }).setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void saveDataToCloud() {
        List<BmobObject> cloudHistories = getCloudHistories();
        upLoadToCloud(cloudHistories);
    }

    private void upLoadToCloud(List<BmobObject> cloudHistories) {
        new BmobBatch()
                .insertBatch(cloudHistories)
                .doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {
                        if (e == null) {
                            for (int i = 0; i < list.size(); i++) {
                                BatchResult result = list.get(i);
                                BmobException ex = result.getError();
                                if (ex == null) {
                                    com.orhanobut.logger.Logger.i("第" + i + "个数据添加成功！" + list.get(i).toString());
                                } else {
                                    com.orhanobut.logger.Logger.i("第" + i + "个数据添加失败！" + ex.getErrorCode());
                                }
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressCircle.beginFinalAnimation();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressCircle.hide();
                                }
                            });

                            ToastUtil.showShortToast("上传失败！");
                            com.orhanobut.logger.Logger.i("数据添加失败！" + e.getErrorCode() + "," + e.getMessage());
                        }
                    }
                });
    }


    private void downloadDataFromCloud() {
        if (DataUtil.isCurrentAppUserExist()) {
            mProgressDialog.show();
            download();
        } else {
            showLoginDialog();
        }
    }

    private void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<CloudFavorite> query = new BmobQuery<>();
                query.addWhereEqualTo("mAccount", DataUtil.getCurrentAppUser().getAccount());
                query.findObjects(new FindListener<CloudFavorite>() {
                    @Override
                    public void done(List<CloudFavorite> list, final BmobException e) {
                        if (e == null) {
                            for (CloudFavorite cloudFavorite : list) {

                                Favorite f = cloudFavorite.getFavorite();
                                Favorite favorite = new Favorite();
                                favorite.setTime(f.getTime());
                                favorite.setOperate(f.getOperate());
                                favorite.setResult(f.getResult());
                                favorite.setFavoriteTime(f.getFavoriteTime());
                                favorite.save();
                            }

                            mList = DataSupport.findAll(Favorite.class);
                            mAdapter.setData(mList);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressDialog.dismiss();
                                    ToastUtil.showShortToast("下载数据成功！");
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressDialog.dismiss();
                                    ToastUtil.showShortToast("下载数据失败！" + e.getMessage());
                                }
                            });

                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 获取ClodHistory集合
     *
     * @return List<BmobObject>对象
     */
    @NonNull
    private List<BmobObject> getCloudHistories() {
        List<BmobObject> cloudHistories = new ArrayList<>();
        List<Favorite> favorites = DataSupport.findAll(Favorite.class);
        for (Favorite f : favorites) {
            CloudFavorite cf = new CloudFavorite();
            cf.setFavorite(f);
            cf.setAccount(DataUtil.getCurrentAppUser().getAccount());
            cloudHistories.add(cf);
        }
        return cloudHistories;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cloud, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                downloadDataFromCloud();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFABProgressAnimationEnd() {
        ToastUtil.showShortToast("上传完成！");
    }
}
