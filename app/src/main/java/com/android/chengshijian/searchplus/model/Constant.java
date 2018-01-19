package com.android.chengshijian.searchplus.model;

/**
 *
 * 常量类
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public class Constant {
    public static final int LOGIN = 0;//请求登录
    public static final int ID_CODE = 1;//请求验证码
    public static final String SCHOOL_ACCOUNT = "account";
    public static final String SCHOOL_PASSWORD = "password";
    public static final String APP_ACCOUNT = "app_account";
    public static final String APP_PASSWORD = "app_password";
    public static final String NONE = "none";
    //查询成绩和保存Cookie时用
    public static final String COOKIE = "cookie";
    //评教时用
    public static final String COOKIE_ASSESS = "cookie_assess";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String LOGIN_SUCCESS = "网络综合平台";
    public static final String ADDRESS_CLOSED = "关闭的连接";
    public static final String RECORD_IS_ZERO="共有记录数0";
    public static final String HAVE_PUBLISHED="已发布";
    public static final String SHOW_USER_NAME="show_user_name";
    public static final String SAVE_DATA ="not_save_data";
    public static final String SELECT_DURATION="select_duration";
    public static final String LONG_BLANK="               ";
    public static final String NOT_HAVE_AUTHORITY="用户已登录但没该操作权限  如需继续操作请联系管理员并重试";
    //项目github地址
    public static final String PROJECT_URL="https://github.com/Chengshijian/SearchPlus";
    //cookie获取时的时间
    public static final String COOKIE_GET_TIME = "cookie_get_time";
    //
    public static final String API_TOKEN="api_token";
    //
    public static final String APP_ID="f2bb0a382cdd77598f1896f96c887df2";
    //检查更新的网址
    public static final String UPDATE_URL="http://api.fir.im/apps/latest/5a60aba4ca87a862e07da2ec";
    //
    public static final String TERM_QUERY_URL = "http://202.118.120.84:" + port() + "/ACTIONQUERYSTUDENTSCORE.APPPROCESS";
    //
    public static final String LOGIN_URL = "http://202.118.120.84:" + port() + "/ACTIONLOGON.APPPROCESS";
    //
    public static final String APP_URL="http://www.fir.im/SearchPlus";
    public static final String ASSESS_MAIN_URL = "http://202.118.120.84/ACTIONLOGON.APPPROCESS";//评教主入口
    //评教教师信息入口
    public static final String ASSESS_TEACHER_INFO_URL = "http://202.118.120.84/ACTIONJSCHOSEAPPRAISECONTENT.APPPROCESS?SeriesID=";
    //开始为教师评教URL
    public static final String START_ASSESS_FOR_TEACHER_URL = "http://202.118.120.84/ACTIONJSSHOWCONTENTRESULT.APPPROCESS?SeriesID=";
    //
    public static final String PAPER_ID = "&PaperID=";
    //
    public static final String TASK_ID = "&TaskID=";
    //
    public static final String TEACHER_NO = "&TeacherNO=";
    //最终评教URL
    public static final String ASSESS_URL = "http://202.118.120.84/ACTIONJSUPDATECONTENTRESULT.APPPROCESS";
    //查询成绩结果
    public static final String USER_NOT_LOGIN_IN = "用户未登录或者登录已过期  如需继续操作请重新登录";
    //成绩单查询URL
    public static final String GRADE_LIST_URL = "http://202.118.120.84:" + port() + "/ACTIONQUERYGRADUATESCHOOLREPORTBYSELF.APPPROCESS";
    //自主学分查询URL
    public static final String INDEPENDENT_CREDIT_URL = "http://202.118.120.84:" + port() + "/ACTIONZZSHOW.APPPROCESS";
    //公布讲座查询URL
    public static final String LECTURE_PUBLISHED_URL = "http://202.118.120.84:" + port() + "/ACTIONJZSHOWSTU.APPPROCESS?op=1";
    //个人信息URL
    public static final String PERSONAL_INFO_URL = "http://202.118.120.84:" + port() + "/ACTIONFINDSTUDENTINFO.APPPROCESS?mode=1&showMsg=";
    public static final String QUERY_GRADES_RESULT_REQUEST_URL = "http://202.118.120.84:" + port() + "/ACTIONQUERYSTUDENTSCORE.APPPROCESS";
    //验证码
    public static final String IDENTIFYING_CODE_REQUEST_URL = "http://202.118.120.84:" + port() + "/ACTIONVALIDATERANDOMPICTURE.APPPROCESS";

    private static String port() {
        return "7001";
    }
}
