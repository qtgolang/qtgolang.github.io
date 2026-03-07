// WebSocket回调函数示例 - Android
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
        if (sunny.Start()) {
            if (sunny.IsScriptCodeSupported())
                api.LogE("SunnyNet", "脚本管理页面:http://127.0.0.1:" + port + "/" + sunny.SetScriptPage(""));
        }
        sunny.ProcessAddName("com.tencent.mm");
        // 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun（安卓一般为 2）
        sunny.OpenDrive(2);
    }
}
