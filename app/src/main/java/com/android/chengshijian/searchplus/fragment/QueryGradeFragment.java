package com.android.chengshijian.searchplus.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.activity.OneKeyAssessActivity;
import com.android.chengshijian.searchplus.listener.OnQueryGradeListener;
import com.android.chengshijian.searchplus.listener.OnQueryGradeTermListener;
import com.android.chengshijian.searchplus.model.Grade;
import com.android.chengshijian.searchplus.model.GradeResult;
import com.android.chengshijian.searchplus.model.User;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.GradeQueryUtil;
import com.android.chengshijian.searchplus.listener.OnSelectListener;
import com.android.chengshijian.searchplus.util.ProgressDialogUtil;
import com.android.chengshijian.searchplus.util.SelectUtil;
import com.android.chengshijian.searchplus.util.ShareUtils;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.view.recyclerview.GradeAdapter;
import com.android.volley.VolleyError;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by ChengShiJian on 2018/1/9.
 * <p>
 * 查询成绩的Fragment类
 */

public class QueryGradeFragment extends BaseFragment implements OnSelectListener, OnQueryGradeListener {

    private AppCompatSpinner mTermSpn;
    private int mMaxTerm = 0;//最大学期初始化为0
    private TextView mNameTv;
    private TextView mAccountTv;
    private TextView mGradeTv;
    private RecyclerView mExamARv;//考试课A考
    private RecyclerView mTestARv;//考查课A考
    private CardView mExamACv;//考试课A考
    private CardView mTestACv;//考查课A考
    private RecyclerView mExamBRv;//考试课A考
    private RecyclerView mTestBRv;//考查课A考
    private CardView mExamBCv;//考试课A考
    private CardView mTestBCv;//考查课A考
    private RecyclerView mExamRRv;//考试课A考
    private RecyclerView mTestRRv;//考查课A考
    private CardView mExamRCv;//考试课A考
    private CardView mTestRCv;//考查课A考
    private ProgressDialog mProgressDialog;
    private User mUser;
    private List<Grade> mExamsA;
    private List<Grade> mTestsA;
    private List<Grade> mExamsB;
    private List<Grade> mTestsB;
    private List<Grade> mExamsR;
    private List<Grade> mTestsR;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_grade;
    }

    @Override
    public void initView() {
        mTermSpn = (AppCompatSpinner) findViewById(R.id.select_term_spinner);
        mNameTv = (TextView) findViewById(R.id.name);
        mAccountTv = (TextView) findViewById(R.id.account);
        mGradeTv = (TextView) findViewById(R.id.grade);
        mExamARv = (RecyclerView) findViewById(R.id.exam_a_rcv);
        mTestARv = (RecyclerView) findViewById(R.id.test_a_rcv);
        mExamACv = (CardView) findViewById(R.id.exam_a_subject_card_view);
        mTestACv = (CardView) findViewById(R.id.test_a_subject_card_view);
        mExamBRv = (RecyclerView) findViewById(R.id.exam_b_rcv);
        mTestBRv = (RecyclerView) findViewById(R.id.test_b_rcv);
        mExamBCv = (CardView) findViewById(R.id.exam_b_subject_card_view);
        mTestBCv = (CardView) findViewById(R.id.test_b_subject_card_view);
        mExamRRv = (RecyclerView) findViewById(R.id.exam_r_rcv);
        mTestRRv = (RecyclerView) findViewById(R.id.test_r_rcv);
        mExamRCv = (CardView) findViewById(R.id.exam_r_subject_card_view);
        mTestRCv = (CardView) findViewById(R.id.test_r_subject_card_view);
    }

    @Override
    public void initData() {
        setBackArrowEnabled(true);
        transparentNavigationBar();
        setToolbarTitle("");
        initSpinner();
        initRecyclerView();
        initProgressDialog();
    }

    private void initProgressDialog() {
        mProgressDialog = ProgressDialogUtil.getRequestDataDialog(getActivity());
    }

    private void initRecyclerView() {
        mExamARv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTestARv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExamBRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTestBRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExamRRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTestRRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExamARv.setNestedScrollingEnabled(false);
        mTestARv.setNestedScrollingEnabled(false);
        mExamBRv.setNestedScrollingEnabled(false);
        mTestBRv.setNestedScrollingEnabled(false);
        mExamRRv.setNestedScrollingEnabled(false);
        mTestRRv.setNestedScrollingEnabled(false);
    }

    @Override
    public void initListener() {

        mTermSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                queryGrade(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void queryGrade(final int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GradeQueryUtil.getGrades(String.valueOf(mMaxTerm - i), QueryGradeFragment.this);
            }
        }).start();
    }

    private void update(List<Grade> examsA, List<Grade> testsA, List<Grade> examsB, List<Grade> testsB, List<Grade> examsR, List<Grade> testsR) {
        updateExamAUI(examsA);
        updateTestAUI(testsA);
        updateExamBUI(examsB);
        updateTestBUI(testsB);
        updateExamRUI(examsR);
        updateTestRUI(testsR);
    }

    private void updateExamAUI(List<Grade> exams) {
        if (exams.size() > 0) {
            mExamACv.setVisibility(View.VISIBLE);
            mExamARv.setAdapter(new GradeAdapter(exams));
        } else {
            mExamACv.setVisibility(View.GONE);
        }
    }

    private void updateTestAUI(List<Grade> tests) {
        if (tests.size() > 0) {
            mTestACv.setVisibility(View.VISIBLE);
            mTestARv.setAdapter(new GradeAdapter(tests));
        } else {
            mTestACv.setVisibility(View.GONE);
        }
    }

    private void updateExamRUI(List<Grade> exams) {
        if (exams.size() > 0) {
            mExamRCv.setVisibility(View.VISIBLE);
            mExamRRv.setAdapter(new GradeAdapter(exams));
        } else {
            mExamRCv.setVisibility(View.GONE);
        }
    }

    private void updateTestRUI(List<Grade> tests) {
        if (tests.size() > 0) {
            mTestRCv.setVisibility(View.VISIBLE);
            mTestRRv.setAdapter(new GradeAdapter(tests));
        } else {
            mTestRCv.setVisibility(View.GONE);
        }
    }

    private void updateExamBUI(List<Grade> exams) {
        if (exams.size() > 0) {
            mExamBCv.setVisibility(View.VISIBLE);
            mExamBRv.setAdapter(new GradeAdapter(exams));
        } else {
            mExamBCv.setVisibility(View.GONE);
        }
    }

    private void updateTestBUI(List<Grade> tests) {
        if (tests.size() > 0) {
            mTestBCv.setVisibility(View.VISIBLE);
            mTestBRv.setAdapter(new GradeAdapter(tests));
        } else {
            mTestBCv.setVisibility(View.GONE);
        }
    }

    private void initSpinner() {
        requestTerms();
    }

    private void requestTerms() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GradeQueryUtil.getTerms(new OnQueryGradeTermListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(R.string.hint)
                                        .setMessage("获取学期数据失败！是否重试？")
                                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                requestTerms();
                                            }
                                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();

                                DataUtil.addQueryGradeInfoToHistory("获取学期数据失败！");
                            }
                        });
                    }

                    @Override
                    public void onStartQuery() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.show();
                                mProgressDialog.setMessage("获取学期数据中...");
                            }
                        });

                    }

                    @Override
                    public void onResponse(List<String> terms, int maxTerm) {
                        ArrayAdapter<String> termAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item_white, terms);
                        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mMaxTerm = maxTerm;
                        mTermSpn.setAdapter(termAdapter);
                        SelectUtil.select(QueryGradeFragment.this);
                    }
                });
            }
        }).start();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share:
                new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean result) {
                        if (result) {
                            ShareUtils.shareText(getActivity(), getSharedData());
                        }
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData(User user, List<Grade> examsA, List<Grade> testsA, List<Grade> examsB, List<Grade> testsB, List<Grade> examsR, List<Grade> testsR) {
        mUser = user;
        mExamsA = examsA;
        mTestsA = testsA;
        mExamsB = examsB;
        mTestsB = testsB;
        mExamsR = examsR;
        mTestsR = testsR;
        DataUtil.addQueryGradeInfoToHistory(getSharedData());
    }

    private String getSharedData() {
        StringBuffer buffer = new StringBuffer();

        /**
         *
         * 这里要判断一下是否为空，万一用户在没有获取到任何信息就分享了那
         *
         */
        if (mUser != null) {
            buffer.append("姓名：").append(mUser.getName()).append("\n学号：").append(mUser.getAccount()).append("\n");
        }
        if (
                mExamsA != null
                        && mTestsA != null
                        && mExamsB != null
                        && mTestsB != null
                        && mExamsR != null
                        && mTestsR != null
                ) {
            append(buffer, mExamsA);
            append(buffer, mTestsA);
            append(buffer, mExamsB);
            append(buffer, mTestsB);
            append(buffer, mExamsR);
            append(buffer, mTestsR);
        }
        return buffer.toString();
    }

    private void append(StringBuffer buffer, List<Grade> data) {
        for (Grade grade : data) {
            buffer.append("科目：").append(grade.getName()).append(",期末成绩：").append(grade.getEndOfTermGrade()).append(",总评成绩：").append(grade.getTotalGrade()).append("\n");
        }
    }

    @Override
    public boolean isHaveToolbar() {
        return true;
    }

    @Override
    public void onFirstTerm() {
        mTermSpn.setSelection(0);
    }

    @Override
    public void onNextTerm() {
        mTermSpn.setSelection(1);
    }

    @Override
    public void onSuccess(Object result) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });
        GradeResult gradeResult = (GradeResult) result;
        final User user = gradeResult.getUser();
        List<Grade> examsA = gradeResult.getExamAGrades();
        List<Grade> testsA = gradeResult.getTestAGrades();
        List<Grade> examsB = gradeResult.getExamBGrades();
        List<Grade> testsB = gradeResult.getTestBGrades();
        List<Grade> examsR = gradeResult.getExamRGrades();
        List<Grade> testsR = gradeResult.getTestRGrades();
        final String averageGrade = gradeResult.getAverageGrade();

        if (!examsA.get(0).getTotalGrade().equals("未考评")) {
            setData(user, examsA, testsA, examsB, testsB, examsR, testsR);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNameTv.setText(user.getName());
                    mAccountTv.setText(getString(R.string.account) + user.getAccount());
                    mGradeTv.setText(getString(R.string.grade) + averageGrade);
                }
            });
            update(examsA, testsA, examsB, testsB, examsR, testsR);
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.hint)
                            .setMessage(R.string.is_need_assess)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getActivity(), OneKeyAssessActivity.class));
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    getActivity().finish();
                                }
                            })
                            .show();
                }
            });
        }
    }

    @Override
    public void onStartQuery() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setMessage(getString(R.string.is_get_data));
            }
        });
    }

    @Override
    public void onRecordIs0() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.hint)
                        .setCancelable(false)
                        .setMessage(R.string.not_have_your_grade)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
        });
        DataUtil.addQueryGradeInfoToHistory(getString(R.string.not_have_your_grade));
    }

    @Override
    public void onErrorResponse(final VolleyError volleyError) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                DataUtil.addQueryGradeInfoToHistory("查询时出错，出错信息：" + DataUtil.volleyErrorToString(volleyError));
                ToastUtil.showLongToast(DataUtil.volleyErrorToString(volleyError));
            }
        });
    }
}
