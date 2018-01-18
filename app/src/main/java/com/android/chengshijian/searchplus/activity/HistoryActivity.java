package com.android.chengshijian.searchplus.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.model.Favorite;
import com.android.chengshijian.searchplus.model.History;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.view.recyclerview.HistoryAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import org.litepal.crud.DataSupport;

import java.util.Collections;


/**
 *
 * 历史记录类
 *
 * 继承自MultiOperateActivity
 *
 * Created by ChengShiJian on 2018/1/15.
 */

public class HistoryActivity extends MultiOperateActivity<History> {

    private HistoryAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_history;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("历史记录");
        mList = DataSupport.findAll(History.class);
        mAdapter = new HistoryAdapter(mList);
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
                    mAdapter.delete(adapterPosition);
                } else {
                    if (DataSupport.where("mTime=?", mList.get(adapterPosition).getTime()).find(Favorite.class).size() > 0) {
                        ToastUtil.showShortToast("您已收藏，无需再次收藏");

                    } else {
                        DataUtil.addToFavorite(mList.get(adapterPosition));
                        ToastUtil.showShortToast("收藏成功！");
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
                    SwipeMenuItem deleteItem = new SwipeMenuItem(HistoryActivity.this)
                            .setBackground(R.color.colorAccent)
                            .setImage(R.drawable.ic_delete_24dp)
                            .setWeight(11)
                            .setText("删除")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                    SwipeMenuItem closeItem = new SwipeMenuItem(HistoryActivity.this)
                            .setBackground(R.color.accent)
                            .setImage(R.drawable.ic_favorite_24dp)
                            .setText("收藏")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。
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
                int adapterPosition = srcHolder.getAdapterPosition();
                int position = adapterPosition - mRecyclerView.getHeaderItemCount();

                // 普通Item。
                mList.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(HistoryActivity.this, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected int getRecyclerResId() {
        return R.id.history_rv;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}
