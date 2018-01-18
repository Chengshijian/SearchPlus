package com.android.chengshijian.searchplus.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnAssessListener;
import com.android.chengshijian.searchplus.model.Assess;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.LnpuAssessUtil;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.List;

/**
 *
 * 一键评教类
 *
 * Created by ChengShiJian on 2018/1/11.
 */

public class OneKeyAssessActivity extends BaseActivity implements OnClickListener, OnAssessListener {
    private DonutProgress mProgress;
    private TextView mContentTv;
    private FloatingActionButton mOneKeyAssessFb;
    private StringBuffer mAssessContent = new StringBuffer();
    private int mNowTeacherNum = 0;
    private int mTotalTeacherNum = 0;

    @Override
    public void initListener() {
        mOneKeyAssessFb.setOnClickListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_one_key_assess;
    }

    @Override
    public void initView() {
        mProgress = findViewById(R.id.progress);
        mContentTv = findViewById(R.id.content);
        mOneKeyAssessFb = findViewById(R.id.one_key);
    }

    @Override
    public void initData() {
        mProgress.setMax(100);
        setActionBarTitle(R.string.one_key_assess);
        setDisplayHomeAsUpEnabled(true);
        transparentNavigationBar();
        removeElevation();
    }

    /**
     * 去除ActionBar的阴影
     *
     */
    private void removeElevation() {
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_key:
                oneKeyAssess();
                break;
        }
    }

    private void oneKeyAssess() {
        /**
         *
         * 开启一个线程开始评教
         *
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                LnpuAssessUtil.oneKeyAsess(OneKeyAssessActivity.this);
            }
        }).start();

    }

    @Override
    public void onLoginDataOutOfTime() {

        /**
         *
         * 调用runOnUiThread方法，在UI线程（主线程上）更新UI
         *
         */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast("超时！");
            }
        });

    }

    @Override
    public void onAssessTeacherDataReceived(final List<Assess> infos) {
        if (infos.size() != 0) {
            mNowTeacherNum = infos.size();
            mTotalTeacherNum = mNowTeacherNum;
            final StringBuilder builder = getReceivedTeacherDataMsg(infos);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAssessContent
                            .append("共获得")
                            .append(infos.size())
                            .append("位教师信息...\n")
                            .append("分别为：")
                            .append(builder)
                            .append("\n")
                            .append("系统将会为上述教师自动评分...\n")
                            .append("-------------------------------------------------------\n");
                    mContentTv.setText(mAssessContent.toString());
                }
            });

        } else {
            DataUtil.addOneKeyAssessInfoToHistory("对不起系统暂未开放，请等到开放时再试，谢谢配合！");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mContentTv.setText("对不起系统暂未开放，请等到开放时再试，谢谢配合！");
                }
            });

        }
    }

    @NonNull
    private StringBuilder getReceivedTeacherDataMsg(List<Assess> infos) {
        final StringBuilder builder = new StringBuilder();
        for (Assess assess : infos) {
            builder.append(assess.getName()).append("  ");
        }
        return builder;
    }

    @Override
    public void onAssessTeacherDataError(Class volleyErrorClass) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAssessContent.append("获取教师数据时出错！\n");
                mContentTv.setText(mAssessContent.toString());
            }
        });
        DataUtil.addOneKeyAssessInfoToHistory("获取评教基础数据时出错！");
    }

    @Override
    public void onAssessSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(100);
                mAssessContent.append("恭喜您，评教完成！\n");
                mContentTv.setText(mAssessContent.toString());
            }
        });
        DataUtil.addOneKeyAssessInfoToHistory("获取评教基础数据时出错！");
    }

    @Override
    public void onAssessBaseDataError(Class volleyErrorClass) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAssessContent.append("获取评教基础数据时出错！\n");
                mContentTv.setText(mAssessContent.toString());
            }
        });
        DataUtil.addOneKeyAssessInfoToHistory("获取评教基础数据时出错！");
    }

    @Override
    public void onStartAssessForTeacher(final Assess assess) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAssessContent.append("正在给").append(assess.getName()).append("老师评分...\n");
                mContentTv.setText(mAssessContent.toString());
            }
        });
        DataUtil.addOneKeyAssessInfoToHistory("正在给"+assess.getName()+"老师评分...");
    }

    @Override
    public void onAssessForTeacherFinished(String resultHtml, final Assess assess) {
        mNowTeacherNum--;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress((int) (100 - ((float) mNowTeacherNum / mTotalTeacherNum) * 100));
                mAssessContent.append("给" + assess.getName() + "老师评分完成！\n");
                mContentTv.setText(mAssessContent.toString());
            }
        });
    }
}