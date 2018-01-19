package com.android.chengshijian.searchplus;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.chengshijian.searchplus.activity.AboutActivity;
import com.android.chengshijian.searchplus.activity.AccountManageActivity;
import com.android.chengshijian.searchplus.activity.BaseActivity;
import com.android.chengshijian.searchplus.activity.FavoriteActivity;
import com.android.chengshijian.searchplus.activity.HistoryActivity;
import com.android.chengshijian.searchplus.activity.AddSchoolAccountActivity;
import com.android.chengshijian.searchplus.activity.LoginActivity;
import com.android.chengshijian.searchplus.activity.OneKeyAssessActivity;
import com.android.chengshijian.searchplus.activity.QueryGradeActivity;
import com.android.chengshijian.searchplus.activity.QueryGradeListActivity;
import com.android.chengshijian.searchplus.activity.QueryIndCreditActivity;
import com.android.chengshijian.searchplus.activity.QueryLectureActivity;
import com.android.chengshijian.searchplus.activity.QueryPersonalInfoActivity;
import com.android.chengshijian.searchplus.activity.SettingsActivity;
import com.android.chengshijian.searchplus.listener.OnCheckAccountValidateListener;
import com.android.chengshijian.searchplus.listener.OnCheckUpdateListener;
import com.android.chengshijian.searchplus.listener.OnPrepareQueryListener;
import com.android.chengshijian.searchplus.listener.OnQueryGradeListListener;
import com.android.chengshijian.searchplus.listener.OnQueryIndCreditListener;
import com.android.chengshijian.searchplus.model.GradeListResult;
import com.android.chengshijian.searchplus.model.IndCreditResult;
import com.android.chengshijian.searchplus.model.User;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.Version;
import com.android.chengshijian.searchplus.util.CheckUpdateUtil;
import com.android.chengshijian.searchplus.util.CreditQueryUtil;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.GradeQueryUtil;
import com.android.chengshijian.searchplus.util.LoginUtil;
import com.android.chengshijian.searchplus.util.ProgressDialogUtil;
import com.android.chengshijian.searchplus.util.QueryUtil;
import com.android.chengshijian.searchplus.util.SizeUtil;
import com.android.chengshijian.searchplus.util.TimeUtil;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.util.WebUtil;
import com.android.chengshijian.searchplus.view.IActivityView;
import com.android.volley.VolleyError;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import rx.functions.Action1;


/**
 * 主程序类
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IActivityView, View.OnClickListener, OnCheckAccountValidateListener, OnPrepareQueryListener, OnCheckUpdateListener {
    private RelativeLayout mGradeSearchRl;
    private RelativeLayout mGradeListRl;
    private RelativeLayout mOneKeyAssessRl;
    private RelativeLayout mIndCreditRl;
    private RelativeLayout mLectureRl;
    private RelativeLayout mPersonalRl;
    private BoomMenuButton mMenuButton;
    private ProgressDialog mProgressDialog;
    private TextView mAverageGpaTv;
    private TextView mTotalCreditTv;
    private boolean isHaveUpdateGpa = false;//是否更新了Gpa
    private boolean isHaveUpdateCredit = false;//是否更新了自主学分
    private boolean isShowUserName = false;//是否显示用户名
    private User mCurrentUser;//当前账户

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_login) {
            startActivity(LoginActivity.class);
        } else if (id == R.id.nav_account_manage) {
            startActivity(AccountManageActivity.class);
        } else if (id == R.id.nav_settings) {
            startActivity(SettingsActivity.class);
        } else if (id == R.id.nav_history_record) {
            startActivity(HistoryActivity.class);
        } else if (id == R.id.nav_favorite) {
            startActivity(FavoriteActivity.class);
        } else if (id == R.id.nav_about) {
            startActivity(AboutActivity.class);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void initView() {
        mGradeSearchRl = findViewById(R.id.grade_search);
        mMenuButton = findViewById(R.id.bmb);
        mOneKeyAssessRl = findViewById(R.id.one_key_assess);
        mGradeListRl = findViewById(R.id.grade_list);
        mIndCreditRl = findViewById(R.id.ind_credit);
        mLectureRl = findViewById(R.id.lecture);
        mPersonalRl = findViewById(R.id.personal_info);
        mAverageGpaTv = findViewById(R.id.average_gpa);
        mTotalCreditTv = findViewById(R.id.total_credit);
    }

    @Override
    public void initData() {
        mMenuButton.setNormalColor(getResources().getColor(R.color.white));
        for (int i = 0; i < mMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .normalImageRes(R.mipmap.ic_launcher_round)
                    .normalText("Butter Doesn't fly!");
            mMenuButton.addBuilder(builder);
        }
        initDrawerLayout();
        transparentNavigationBar();
        setDisplayHomeAsUpEnabled(false);
        initProgressDialog();
        initCurrentAccount();
        initPermission();
    }

    @Override
    public void initListener() {
        mGradeSearchRl.setOnClickListener(this);
        mOneKeyAssessRl.setOnClickListener(this);
        mGradeListRl.setOnClickListener(this);
        mIndCreditRl.setOnClickListener(this);
        mLectureRl.setOnClickListener(this);
        mPersonalRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            case R.id.grade_search:
                prepareQueryGrade();
                break;
            case R.id.one_key_assess:
                prepareOneKeyAssess();
                break;
            case R.id.grade_list:
                prepareQueryGradeList();
                break;
            case R.id.ind_credit:
                prepareQueryIndCredit();
                break;
            case R.id.lecture:
                prepareQueryLecture();
                break;
            case R.id.personal_info:
                prepareQueryPersonalInfo();
                break;
        }
    }

    private void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CheckUpdateUtil.checkUpdate(MainActivity.this);
            }
        }).start();

    }

    private void initPermission() {

        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            checkUpdate();
                        }
                    }
                });
    }

    private void initCurrentAccount() {
        mCurrentUser = DataUtil.getCurrentSchoolUser();
    }

    private void initProgressDialog() {
        mProgressDialog = ProgressDialogUtil.getInitDataDialog(this);
    }

    private void initDrawerLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //准备更新UI
    private void prepareUpdateUI() {
        QueryUtil.prepareQuery(LoginUtil.UPDATE_UI, this);
    }

    //准备查询成绩
    private void prepareQueryGrade() {
        QueryUtil.prepareQuery(LoginUtil.GRADE, this);
    }

    //准备一键评教
    private void prepareOneKeyAssess() {
        if (QueryUtil.isHaveAccount()) {
            startActivity(new Intent(this, OneKeyAssessActivity.class));
        } else {
            showInputDialog();
        }
    }

    //准备查询成绩单
    private void prepareQueryGradeList() {
        QueryUtil.prepareQuery(LoginUtil.GRADE_LIST, this);
    }

    //准备查询自主学分
    private void prepareQueryIndCredit() {
        QueryUtil.prepareQuery(LoginUtil.IND_CREDIT, this);
    }

    //准备查询讲座信息
    private void prepareQueryLecture() {
        QueryUtil.prepareQuery(LoginUtil.LECTURE, this);
    }

    //准备查询个人信息
    private void prepareQueryPersonalInfo() {
        QueryUtil.prepareQuery(LoginUtil.PERSONAL_INFO, this);
    }

    private void showInputDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("提示")
                .setMessage(R.string.input_account_password_hint)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, AddSchoolAccountActivity.class));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void startQueryGradeActivity() {
        startActivity(QueryGradeActivity.class);
    }

    private void startQueryGradeListActivity() {
        startActivity(QueryGradeListActivity.class);
    }

    private void startQueryIndCreditActivity() {
        startActivity(QueryIndCreditActivity.class);
    }

    private void startQueryLectureActivity() {
        startActivity(QueryLectureActivity.class);

    }

    private void startQueryPersonalInfoActivity() {
        startActivity(QueryPersonalInfoActivity.class);
    }


    //开启目标工作
    private void startTargetWork(int type) {
        if (type == LoginUtil.GRADE) {
            startQueryGradeActivity();
        } else if (type == LoginUtil.GRADE_LIST) {
            startQueryGradeListActivity();
        } else if (type == LoginUtil.IND_CREDIT) {
            startQueryIndCreditActivity();
        } else if (type == LoginUtil.UPDATE_UI) {
            startUpdateUI();
        } else if (type == LoginUtil.LECTURE) {
            startQueryLectureActivity();
        } else if (type == LoginUtil.PERSONAL_INFO) {
            startQueryPersonalInfoActivity();
        }
    }

    private void startUpdateUI() {
        requestTotalIndCredit();
        requestAverageGpa();
    }


    @Override
    public void onNeedInvalidate(int type) {
        if (type == LoginUtil.GRADE) {
            LoginUtil.isValidateAccount(LoginUtil.GRADE, this);
        } else if (type == LoginUtil.GRADE_LIST) {
            LoginUtil.isValidateAccount(LoginUtil.GRADE_LIST, this);
        } else if (type == LoginUtil.IND_CREDIT) {
            LoginUtil.isValidateAccount(LoginUtil.IND_CREDIT, this);
        } else if (type == LoginUtil.UPDATE_UI) {
            LoginUtil.isValidateAccount(LoginUtil.UPDATE_UI, this);
        } else if (type == LoginUtil.LECTURE) {
            LoginUtil.isValidateAccount(LoginUtil.LECTURE, this);
        } else if (type == LoginUtil.PERSONAL_INFO) {
            LoginUtil.isValidateAccount(LoginUtil.PERSONAL_INFO, this);
        }
    }

    @Override
    public void onStartCheck() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.show();
            }
        });
    }

    @Override
    public void onGetCookie(String cookie) {
        DataUtil.saveGradeCookieTime();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setMessage(getString(R.string.get_cookie_success));
            }
        });
    }

    @Override
    public void onCheckSuccess(int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setMessage(getString(R.string.init_success));
                mProgressDialog.dismiss();
            }
        });
        startTargetWork(type);
    }


    @Override
    public void onErrorResponse(final int type, final VolleyError volleyError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
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
                mProgressDialog.dismiss();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.hint)
                        .setMessage(R.string.address_closed)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
        });
    }

    @Override
    public void onLoginFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                ToastUtil.showShortToast("login failed！");
            }
        });

    }


    @Override
    public void onCanQuery(int type) {
        startTargetWork(type);
    }


    @Override
    public void onNotHaveAccount() {
        showInputDialog();
    }

    private void requestTotalIndCredit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreditQueryUtil.getIndCredits(new OnQueryIndCreditListener() {
                    @Override
                    public void onStartQuery() {

                    }

                    @Override
                    public void onRecordIs0() {
                        zero(mTotalCreditTv);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        unknown(mTotalCreditTv);
                        isHaveUpdateCredit = false;
                    }

                    @Override
                    public void onNotHaveCredit() {
                        zero(mTotalCreditTv);
                        isHaveUpdateCredit = true;
                    }

                    @Override
                    public void onSuccess(final Object result) {

                        updateCredit((IndCreditResult) result);
                        isHaveUpdateCredit = true;
                    }
                });
            }
        }).start();

    }

    private void updateCredit(final IndCreditResult result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTotalCreditTv.setText(result.getTotalCredit());

            }
        });
    }


    private void requestAverageGpa() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GradeQueryUtil.getGradeLists(new OnQueryGradeListListener() {
                    @Override
                    public void onStartQuery() {

                    }

                    @Override
                    public void onRecordIs0() {
                        zero(mAverageGpaTv);
                        notUpdateGpa();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        unknown(mAverageGpaTv);
                        notUpdateGpa();
                    }

                    @Override
                    public void onNotHaveAuthority() {
                        notHaveAuthority(mAverageGpaTv);
                        notUpdateGpa();
                    }

                    @Override
                    public void onSuccess(final Object result) {
                        updateGpa((GradeListResult) result);
                        updateTitle((GradeListResult) result);
                        haveUpdateGpa();
                        mCurrentUser.setName(((GradeListResult) result).getTitle().getName());
                    }
                });
            }
        }).start();
    }

    private void notUpdateGpa() {
        isHaveUpdateGpa = false;
        isShowUserName = false;
    }

    private void haveUpdateGpa() {
        isHaveUpdateGpa = true;
        isShowUserName = true;
    }

    private void updateGpa(final GradeListResult result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAverageGpaTv.setText(result.getTitle().getAverageGPA());
            }
        });
    }

    private void updateTitle(final GradeListResult result) {
        if (DataUtil.isShowUserName()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateActionBarTitle(result.getTitle().getName());
                }
            });

        }
    }

    private void updateActionBarTitle(String name) {
        if (TimeUtil.isMorning()) {
            setActionBarTitle("早上好，" + name);
        } else if (TimeUtil.isMidDay()) {
            setActionBarTitle("中午好，" + name);
        } else if (TimeUtil.isAfternoon()) {
            setActionBarTitle("下午好，" + name);
        } else if (TimeUtil.isDusk()) {
            setActionBarTitle("傍晚好，" + name);
        } else if (TimeUtil.isEvening()) {
            setActionBarTitle("晚上好，" + name);
        } else if (TimeUtil.isDaybreak()) {
            setActionBarTitle("凌晨好，" + name);
        } else if (TimeUtil.isDeepNight()) {
            setActionBarTitle("深夜好，" + name);
        } else if (TimeUtil.isEarlyMorning()) {
            setActionBarTitle("早晨好，" + name);
        }
    }

    private void unknown(final TextView view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("未知");
            }
        });
    }

    private void notHaveAuthority(final TextView view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("无权");
            }
        });
    }

    private void zero(final TextView view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("0");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = DataUtil.getCurrentSchoolUser();
        if (!user.getAccount().equals(mCurrentUser.getAccount())) {
            prepareUpdateUI();
            mCurrentUser = user;
        } else if (!isHaveUpdateCredit || !isHaveUpdateGpa) {
            prepareUpdateUI();
        }
        if (DataUtil.isShowUserName() && !isShowUserName && mCurrentUser.getName() != null) {
            updateActionBarTitle(mCurrentUser.getName());
        } else if (!DataUtil.isShowUserName() && isShowUserName) {
            setActionBarTitle(getString(R.string.app_name));
        }
    }

    @Override
    public void onSuccess(final Version result) {
        if (result.getVersionShort().compareTo(BuildConfig.VERSION_NAME) > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("发现新版本")
                            .setCancelable(true)
                            .setMessage(
                                    "发现新版本" + result.getVersionShort() + "是否更新?\n"
                                            + result.getChangelog()
                                            + "\n软件大小："
                                            + SizeUtil.B2M(
                                            result.getBinary()
                                                    .getFsize()
                                    )+"M"
                            ).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            WebUtil.openExternal(MainActivity.this,Constant.APP_URL);
                        }
                    }).setNegativeButton(R.string.cancel,null)
                    .show();
                }
            });
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        ToastUtil.showShortToast("检查更新失败！\n"+call.toString());
    }
}
