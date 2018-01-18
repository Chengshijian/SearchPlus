package com.android.chengshijian.searchplus.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnQueryGradeListListener;
import com.android.chengshijian.searchplus.model.GradeList;
import com.android.chengshijian.searchplus.model.GradeListResult;
import com.android.chengshijian.searchplus.model.GradeListTitle;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.GradeQueryUtil;
import com.android.chengshijian.searchplus.util.ProgressDialogUtil;
import com.android.chengshijian.searchplus.util.ShareUtils;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.view.recyclerview.GradeListAdapter;
import com.android.volley.VolleyError;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.functions.Action1;

/**
 *
 * 查询成绩单类
 *
 * Created by ChengShiJian on 2018/1/12.
 */

public class QueryGradeListActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private GradeListResult mResult;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_grade_list;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.grade_list_rv);

    }

    @Override
    public void initData() {
        setDisplayHomeAsUpEnabled(true);
        transparentNavigationBar();
        setActionBarTitle(R.string.grade_list);
        initProgressDialog();
        initRecyclerView();
        requestGradeList();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initProgressDialog() {
        mProgressDialog = ProgressDialogUtil.getRequestDataDialog(this);
    }

    private void requestGradeList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GradeQueryUtil.getGradeLists(new OnQueryGradeListListener() {
                    @Override
                    public void onStartQuery() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.show();
                            }
                        });
                    }

                    @Override
                    public void onRecordIs0() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                new AlertDialog.Builder(QueryGradeListActivity.this)
                                        .setTitle(R.string.hint)
                                        .setMessage(R.string.not_have_your_grade)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).show();
                            }
                        });

                        DataUtil.addQueryGradeListInfoToHistory(getString(R.string.not_have_your_grade));
                    }

                    @Override
                    public void onErrorResponse(final VolleyError volleyError) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                ToastUtil.showLongToast(DataUtil.volleyErrorToString(volleyError));
                            }
                        });
                        DataUtil.addQueryGradeListInfoToHistory(DataUtil.volleyErrorToString(volleyError));

                    }

                    @Override
                    public void onNotHaveAuthority() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                new AlertDialog.Builder(QueryGradeListActivity.this)
                                        .setTitle(R.string.hint)
                                        .setMessage(R.string.function_closed)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).show();
                            }
                        });
                        DataUtil.addQueryGradeListInfoToHistory(getString(R.string.function_closed));

                    }

                    @Override
                    public void onSuccess(final Object result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                mResult = (GradeListResult) result;
                                mRecyclerView.setAdapter(new GradeListAdapter(mResult));
                            }
                        });

                        DataUtil.addQueryGradeListInfoToHistory(getSharedData());
                    }
                });
            }
        }).start();
    }

    private void share() {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean result) {
                if (result) {
                    DataUtil.addShareQueryGradeListInfoToHistory(getSharedData());
                    ShareUtils.shareText(QueryGradeListActivity.this, getSharedData());
                }
            }
        });
    }

    /**
     * 获得分享成绩的String字符串
     *
     * @return String对象
     *
     */
    private String getSharedData() {
        List<GradeList> gradeLists = mResult.getGradeLists();
        GradeListTitle title = mResult.getTitle();
        StringBuilder builder = new StringBuilder();
        builder
                .append("学号：")
                .append(title.getAccount())
                .append("\n姓名：")
                .append(title.getName())
                .append("\n入学年：")
                .append(title.getEnterTime())
                .append("\n培养层次：")
                .append(title.getDevelopLevel())
                .append("\n院系：")
                .append(title.getDepart())
                .append("\n专业：")
                .append(title.getMajor())
                .append("\n班级：")
                .append(title.getClassB())
                .append("\n总绩点：")
                .append(title.getTotalGPA())
                .append("\n平均绩点：")
                .append(title.getAverageGPA())
                .append("\n");

        for (GradeList gradeList : gradeLists) {
            builder
                    .append("学年学期：")
                    .append(gradeList.getTime())
                    .append("，课程名：")
                    .append(gradeList.getCourseName())
                    .append("，开课模式：")
                    .append(gradeList.getCourseModel())
                    .append(",学时：")
                    .append(gradeList.getCourseTime())
                    .append("，学分：")
                    .append(gradeList.getCredit())
                    .append("，成绩：")
                    .append(gradeList.getGrade())
                    .append("，绩点：")
                    .append(gradeList.getGPA())
                    .append("\n");
        }
        return builder.toString();
    }

    @Override
    public void initListener() {

    }
}
