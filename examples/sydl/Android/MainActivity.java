// SunnyNet HTTP 请求示例 - Android
package com.example.sunnynet;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    static SunnyNetCallback callback = new TestCallback_SunnyNet();
    static SunnyNet sunny = new SunnyNet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int port = 2025;
        //设置端口
        sunny.SetPort(port);
        //开启随机TLS Ja3
        sunny.RandomJa3(true);

        //设置回调
        sunny.SetCallback(callback);
        //设置全局上游代理,有账号密码
        sunny.SetGlobalProxy("http://admin:123456@127.0.0.1:8888");
        //设置全局上游代理,无账号密码
        sunny.SetGlobalProxy("http://127.0.0.1:8888");
        //设置全局上游代理,无账号密码,设置超时（毫秒）
        sunny.SetGlobalProxy("http://127.0.0.1:8888",30*1000);

        //启动
        if (sunny.Start()) {
            if (sunny.IsScriptCodeSupported()) {
                api.LogE("SunnyNet", "当前脚本管理页面:http://127.0.0.1:" + port + "/" + sunny.SetScriptPage(""));
            } else {
                api.LogE("SunnyNet", "当前SDK是Mini版本不支持脚本代码");
            }
        }
        /*
            请在 AndroidManifest.xml 中加入：
            <!-- 下面这个权限：允许网络通信（无论是否走 VPN）。 -->
            <uses-permission android:name="android.permission.INTERNET"/>
            <!-- 下面这个权限：从 Android 8.0 (Oreo) 起，VPN 服务需要前台运行。 -->
            <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
            <!-- 下面这个权限：告诉系统：这是一个 VPN 服务：最关键的权限，必须显式声明 -->
            <uses-permission android:name="android.permission.BIND_VPN_SERVICE"/>

            <application
                ......
                <activity
                    android:name="com.SunnyNet.tun.VpnAuth"
                    android:exported="true"
                    android:theme="@android:style/Theme.Translucent.NoTitleBar"
                    android:launchMode="singleTop"
                    android:noHistory="true"
                    android:excludeFromRecents="true">
                </activity>
                <service
                    android:name="com.SunnyNet.tun.VpnService"
                    android:permission="android.permission.BIND_VPN_SERVICE"
                    android:exported="true" />
            </application>
        */
        // sunny.MustTcp(true);
        // 捕获指定APP,不能设置为程序自身包名,需再 OpenDrive 之前调用
        sunny.ProcessAddName("com.tencent.mm");
        sunny.ProcessAddName("com.hwk.httptest");
        sunny.ProcessAddName("com.disney.shanghaidisneyland_goo");

        //捕获全部APP(除本进程之外的所有APP),需再 OpenDrive 之前调用
        //sunny.ProcessAllName(true,false);
        //开启Tun进程代理
        // 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun（安卓只能是 2）
        sunny.OpenDrive(2);  
    }
}
