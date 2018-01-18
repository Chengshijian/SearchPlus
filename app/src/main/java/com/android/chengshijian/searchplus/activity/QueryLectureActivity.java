package com.android.chengshijian.searchplus.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnItemClickListener;
import com.android.chengshijian.searchplus.listener.OnQueryLectureListener;
import com.android.chengshijian.searchplus.listener.OnQueryLectureTermListener;
import com.android.chengshijian.searchplus.model.Lecture;
import com.android.chengshijian.searchplus.model.LectureTermResult;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.LectureQueryUtil;
import com.android.chengshijian.searchplus.listener.OnSelectListener;
import com.android.chengshijian.searchplus.util.ProgressDialogUtil;
import com.android.chengshijian.searchplus.util.SelectUtil;
import com.android.chengshijian.searchplus.view.recyclerview.LectureAdapter;
import com.android.volley.VolleyError;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.List;

/**
 *
 * 查询讲座信息类
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public class QueryLectureActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private BoomMenuButton mSelectBmb;

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.lecture_rv);
    }

    @Override
    public void initData() {
        initCustomActionBar();
        //必须在自定义视图可见后才能findViewById否则报错！
        mSelectBmb = findViewById(R.id.action_bar_select_bmb);
        mProgressDialog = ProgressDialogUtil.getRequestDataDialog(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        transparentNavigationBar();
        queryTerms();
    }

    private void initCustomActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        LayoutInflater mInflater = LayoutInflater.from(this);
        View actionBar = mInflater.inflate(R.layout.custom_actionbar, null);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);//必须加这段代码，否则空指针异常
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0, 0);
        TextView textView = findViewById(R.id.title_text);
        textView.setText("讲座查询");
        setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_lecture;
    }

    private void queryTerms() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LectureQueryUtil.getLectureTerms(new OnQueryLectureTermListener() {
                    @Override
                    public void onSuccess(final LectureTermResult result) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                            }
                        });

                        prepareQuery(result);
                        initBoomMenuButton(result);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                showRetryDialogWhenRequestTerms();
                            }
                        });
                    }

                    @Override
                    public void onStartQuery() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.show();
                            }
                        });
                    }
                });
            }
        }).start();

    }

    private void showRetryDialogWhenRequestTerms() {
        new AlertDialog.Builder(QueryLectureActivity.this)
                .setTitle(R.string.hint)
                .setMessage("请求失败！是否重试？")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do request work here!
                        queryTerms();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }

    private void showRetryDialogWhenRequestLectures(final String year, String message) {
        new AlertDialog.Builder(QueryLectureActivity.this)
                .setTitle(R.string.hint)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        queryLectures(year);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.show();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }

    private void prepareQuery(final LectureTermResult result) {
        SelectUtil.select(new OnSelectListener() {
            @Override
            public void onFirstTerm() {
                queryLectures(result.getTermKeys().get(0));
            }

            @Override
            public void onNextTerm() {
                queryLectures(result.getTermKeys().get(1));
            }
        });
    }

    private void initBoomMenuButton(final LectureTermResult result) {
        //在这里进行初始化工作
        mSelectBmb.setButtonEnum(ButtonEnum.Ham);
        mSelectBmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        mSelectBmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);

        for (int i = 0; i < mSelectBmb.getPiecePlaceEnum().pieceNumber(); i++) {
            mSelectBmb.addBuilder(
                    new HamButton.Builder()
                            .pieceColor(Color.WHITE)
                            .normalText(result.getTermValues().get(i))
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressDialog.show();
                                        }
                                    });
                                    queryLectures(result.getTermKeys().get(index));
                                }
                            }));
        }
    }

    private void queryLectures(final String year) {
        LectureQueryUtil.getLectures(year, new OnQueryLectureListener() {
            @Override
            public void onFailed() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                        showRetryDialogWhenRequestLectures(year, "讲座信息获取失败！是否重试？");
                    }
                });

                DataUtil.addQueryLectureInfoToHistory("讲座信息获取失败！");
            }

            @Override
            public void onLectureSizeIsZero() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                        new AlertDialog.Builder(QueryLectureActivity.this)
                                .setTitle(R.string.hint)
                                .setMessage("未查询到本学期的讲座信息！")
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });

                DataUtil.addQueryLectureInfoToHistory("未查询到本学期的讲座信息！");

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                        showRetryDialogWhenRequestLectures(year, "服务器响应失败！是否重试？");
                    }
                });

                DataUtil.addQueryLectureInfoToHistory("服务器响应失败！");

            }

            @Override
            public void onStartQuery() {

            }

            @Override
            public void onSuccess(final List<Lecture> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                    }
                });

                DataUtil.addQueryLectureInfoToHistory(DataUtil.getStringByLectureList(result));

                mRecyclerView.setAdapter(new LectureAdapter(result, new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(QueryLectureActivity.this)
                                        .setTitle(R.string.hint)
                                        .setMessage(result.get(position).toString())
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            }
                        });

                }
                }));

            }
        });
    }
}
