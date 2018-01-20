SearchPlus
==

开源让编程更快乐

Introduction
-

    本开源项目是根据我大二寒假期间（2017年1月）做的一个教务方面的软件，今年寒假期间（2018年1月）进行重构并进行重新设计而成，代码已进行了优化。
    依托Bmob后端云实现收藏模块的数据上传与更新。其他教务方面的数据是通过Jsoup框架解析辽宁石油化工大学的html网页源码获得。
    apk文件已托管到fir.im免费应用内测托管平台,实现了自动检测软件更新。项目中用到了很多github上优秀的开源框架，非常感谢开源的作者们。
    
How to get the test account?
--

    LoginUtil.java文件里有一个getValidSchoolAccount()方法可以获得2001届至2017届未修改账号密码的学生的学号并将结果保存至手机根目录的account.txt文     件中（这个方法会将所有可能的账号逐个尝试登录，成功则写入account.txt文件，时间较长），由于隐私问题这个文件我没有上传，利用里面的账号即可登录（账号和密码相同），要想获得这些账号，可通过调用以下代码获得：
 
       new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            LoginUtil.getValidSchoolAccount();
                        }
                    }
                });
                
    
Screenshots
-

<div >
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133620.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133624.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-150124.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133630.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133636.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133640.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133646.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133651.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133655.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133706.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-134009.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133713.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133717.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133727.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133749.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133757.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-150931.png" width="250">
 <img src="https://github.com/Chengshijian/SearchPlus/blob/master/screenshots/Screenshot_20180119-133805.png" width="250">
</div>

Download the apk file
--

[SearchPlus.apk](http://fir.im/SearchPlus)

Thanks to the open source project
--

* [BoomMenu](https://github.com/Nightonke/BoomMenu)（一个非常棒的浮动菜单）
* [logger](https://github.com/orhanobut/logger)（漂亮的日志打印工具）
* [RxPermissions](https://github.com/tbruyelle/RxPermissions)（一款非常棒的运行时权限授权库）
* [CircleProgress](https://github.com/lzyzsd/CircleProgress)（一款非常棒的环形进度条）
* [SwipeRecyclerView](https://github.com/yanzhenjie/SwipeRecyclerView)（一个对谷歌官方RecyclerView拓展很棒的库）
* [LitePal](https://github.com/LitePalFramework/LitePal)（郭霖郭大神的SQLite数据库操纵框架）
* [FABProgressCircle](https://github.com/JorgeCastilloPrz/FABProgressCircle)（包裹谷歌官方FloatingActionButton的一款炫酷的进度条）
* [okhttputils](https://github.com/hongyangAndroid/okhttputils)（一款非常棒的对okhttp封装的库）
* [okhttp](https://github.com/square/okhttp)（出色的网络请求框架）

About author
--
* [CSDN博客](http://blog.csdn.net/chengshijian2015)
* [微博](https://weibo.com/u/1843700133)

License
--

>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>
>   http://www.apache.org/licenses/LICENSE-2.0
>
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.


