package com.android.chengshijian.searchplus.util;

import android.support.annotation.NonNull;

import com.android.chengshijian.searchplus.model.App;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.Favorite;
import com.android.chengshijian.searchplus.model.History;
import com.android.chengshijian.searchplus.model.Lecture;
import com.android.chengshijian.searchplus.model.User;
import com.android.volley.VolleyError;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 数据操作类
 *
 * 主要用来操作数据
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public class DataUtil {

    //设置重新登录时间间隔
    public static void setSelectDuration(String duration) {
        SharedPreferencesHelper.putStringValue(Constant.SELECT_DURATION, duration);
    }

    //获取重新登录时间间隔
    public static String getSelectDuration() {
        return SharedPreferencesHelper.getStringValue(Constant.SELECT_DURATION);
    }

    //是否显示用户名
    public static boolean isShowUserName() {
        return SharedPreferencesHelper.getBooleanValue(Constant.SHOW_USER_NAME);
    }

    //设置是否显示用户名
    public static void setShowUserName(boolean isShow) {
        SharedPreferencesHelper.putBooleanValue(Constant.SHOW_USER_NAME, isShow);
    }

    //是否保存用户数据
    public static boolean isSaveData() {
        return SharedPreferencesHelper.getBooleanValue(Constant.SAVE_DATA);
    }

    //设置是否保存用户数据
    public static void setSaveData(boolean isSave) {
        SharedPreferencesHelper.putBooleanValue(Constant.SAVE_DATA, isSave);
    }

    //清空当前用户的学号在SharedPreferences中的数据
    public static void clearCurrentUserSchoolAccountDataInSharedPreferences() {
        SharedPreferencesHelper.putStringValue(Constant.SCHOOL_ACCOUNT, Constant.NONE);
        SharedPreferencesHelper.putStringValue(Constant.SCHOOL_PASSWORD, Constant.NONE);
    }

    //清空当前用户的App账号在SharedPreferences中的数据
    public static void clearCurrentUserAppAccountDataInSharedPreferences() {
        SharedPreferencesHelper.putStringValue(Constant.APP_ACCOUNT, Constant.NONE);
        SharedPreferencesHelper.putStringValue(Constant.APP_PASSWORD, Constant.NONE);
    }

    //清空用户数据(所有)
    public static void clearUserData() {
        clearCurrentUserSchoolAccountDataInSharedPreferences();
        clearCurrentUserAppAccountDataInSharedPreferences();
        DataSupport.deleteAll(User.class);
        DataSupport.deleteAll(App.class);
        DataSupport.deleteAll(History.class);
        DataSupport.deleteAll(Favorite.class);
    }


    public static Map<String, String> getIndCreditParamsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("op", "");
        return map;
    }

    public static Map<String, String> getGradeCookieMap() {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.COOKIE, SharedPreferencesHelper.getStringValue(Constant.COOKIE));
        return map;
    }

    public static Map<String, String> getAssessCookieMap() {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.COOKIE, SharedPreferencesHelper.getStringValue(Constant.COOKIE_ASSESS));
        return map;
    }

    public static String getAssessCookie() {
        return SharedPreferencesHelper.getStringValue(Constant.COOKIE_ASSESS);
    }

    //保存账号密码信息
    public static void saveSchoolAccountToSharedPreferences(String account, String password) {
        SharedPreferencesHelper.putStringValue(Constant.SCHOOL_ACCOUNT, account);
        SharedPreferencesHelper.putStringValue(Constant.SCHOOL_PASSWORD, password);
    }

    //获取当前学校用户
    public static User getCurrentSchoolUser() {
        return new User(SharedPreferencesHelper.getStringValue(Constant.SCHOOL_ACCOUNT), SharedPreferencesHelper.getStringValue(Constant.SCHOOL_PASSWORD));
    }

    public static boolean isCurrentAppUserExist(){
        return !getCurrentAppUser().getAccount().equals(Constant.NONE);
    }

    //获取当前App用户
    public static User getCurrentAppUser() {
        return new User(SharedPreferencesHelper.getStringValue(Constant.APP_ACCOUNT), SharedPreferencesHelper.getStringValue(Constant.APP_PASSWORD));
    }

    //保存Cookie获取时的时间
    public static void saveGradeCookieTime() {
        SharedPreferencesHelper.putStringValue(Constant.COOKIE_GET_TIME, String.valueOf(Calendar.getInstance().getTimeInMillis()));
    }

    public static void addDeleteAccountToHistory(String account) {
        History history = getHistoryWithTime();
        history.setOperate("在账号管理页面删除了“"+account+"”的账号信息");
        history.setResult("删除数据成功！");
        history.save();
    }

    public static String getCurrentFormatTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
        return format.format(new Date());
    }

    public static void addAddedAccountToHistory(String account) {
        History history = getHistoryWithTime();
        history.setOperate("在添加账号页面添加了“"+account+"”的账号信息");
        history.setResult("添加数据成功！");
        history.save();
    }

    @NonNull
    private static History getHistoryWithTime() {
        History history=new History();
        history.setTime(getCurrentFormatTime());
        return history;
    }

    public static void addOneKeyAssessInfoToHistory(String text) {
        History history = getHistoryWithTime();
        history.setOperate("对账号“"+DataUtil.getCurrentSchoolUser().getAccount()+"”进行一键评教");
        history.setResult(text);
        history.save();
    }

    public static void addQueryGradeInfoToHistory(String text) {
        History history = getHistoryWithTime();
        history.setOperate("查询“"+DataUtil.getCurrentSchoolUser().getAccount()+"”的成绩信息");
        history.setResult(text);
        history.save();
    }

    public static void addQueryGradeListInfoToHistory(String text) {
        History history = getHistoryWithTime();
        history.setOperate("查询“"+DataUtil.getCurrentSchoolUser().getAccount()+"”的成绩单信息");
        history.setResult(text);
        history.save();
    }

    public static void addShareQueryGradeListInfoToHistory(String text) {
        History history = getHistoryWithTime();
        history.setOperate("分享“"+DataUtil.getCurrentSchoolUser().getAccount()+"”的成绩单信息");
        history.setResult(text);
        history.save();
    }

    public static void addIndCreditInfoToHistory(String text) {
        History history = getHistoryWithTime();
        history.setOperate("查询“"+DataUtil.getCurrentSchoolUser().getAccount()+"”的自主学分信息");
        history.setResult(text);
        history.save();
    }

    public static void addQueryLectureInfoToHistory(String text) {
        History history = getHistoryWithTime();
        history.setOperate("查询讲座信息");
        history.setResult(text);
        history.save();
    }

    public static String getStringByLectureList(List<Lecture> result){
        StringBuilder builder=new StringBuilder();
        for (Lecture lecture:result){
            builder.append(lecture.toString());
        }

        return builder.toString();
    }


    public static void addQueryPersonalInfoToHistory(String text) {
        History history = getHistoryWithTime();
        history.setOperate("查询“"+DataUtil.getCurrentSchoolUser().getAccount()+"”的个人信息");
        history.setResult(text);
        history.save();
    }

    public static String volleyErrorToString(VolleyError volleyError){
        return volleyError.getCause()
                +"\n"+volleyError.getCause()
                +"\n"+volleyError.getMessage()
                +"\n"+volleyError.getSuppressed()
                +"\n"+volleyError.getLocalizedMessage();
    }

    public static void addToFavorite(History history) {
        Favorite favorite=new Favorite();
        favorite.setFavoriteTime(getCurrentFormatTime());
        favorite.setHistory(history);
        favorite.save();
    }

    //截取15个字符，并加“...”
    public static String subString(String s){
        return s.length()>=15?s.substring(0,15)
                +"...":s
                +"...";
    }

    //随机截取20-30个字符，并加“...”
    public static String randomSubString(String s){
        return s.length()>=20?s.substring(0,20+(int) (Math.random()*10))
                +"...":s
                +"...";
    }

    public static void saveAppAccountToSharedPreferences(String account,String password){
        SharedPreferencesHelper.putStringValue(Constant.APP_ACCOUNT,account);
        SharedPreferencesHelper.putStringValue(Constant.APP_PASSWORD,password);
    }
}
