// （抓HTTP/HTTPS）指定进程 - Android
// 按进程名或进程PID指定抓包目标，需在启动后加载驱动。
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
        sunny.SetPort(port);
        sunny.SetCallback(callback);
        // 指定进程： 
        sunny.ProcessAddName("com.tencent.mm");
        sunny.ProcessAddName("com.example.app");
        // 安卓环境下 指定PID 无效 只能使用指定包名 或使用全部进程
        //sunny.ProcessAllName(true,false);
        
        if (sunny.Start()) {
            if (sunny.IsScriptCodeSupported())
                api.LogE("SunnyNet", "脚本管理页面:http://127.0.0.1:" + port + "/" + sunny.SetScriptPage(""));
        }
        // 指定进程场景需在启动后加载驱动（安卓为 OpenDrive）
        // 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun（安卓一般为 2）
        sunny.OpenDrive(2);
    }
}
