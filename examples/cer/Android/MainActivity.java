// 导入自己的证书 - Android
// 使用自定义 CA 证书（.cer+.key 或 .p12）替代内置证书，需在 Start() 之前设置。
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

        // 导入自己的证书：加载 X509 证书与私钥（密码可选）
        // 若 SDK 中 CertManager/SetCustomCA/InstallCertificate 名称不同，请以官方文档为准
        com.SunnyNet.CertManager cert = new com.SunnyNet.CertManager();
        if (cert.loadX509Certificate("", "ca.cer", "ca.key")) {
            sunny.SetCert(cert);  // 在启动前设置自定义 CA
        } else {
            api.LogE("Main", "加载证书失败，将使用内置证书");
        }

        if (sunny.Start()) {
            api.LogE("SunnyNet", "启动成功");
            if (!sunny.InstallCertificate()) {
                api.LogE("SunnyNet", "安装证书到系统失败，请手动安装根证书");
            }
            if (sunny.IsScriptCodeSupported())
                api.LogE("SunnyNet", "脚本管理页面:http://127.0.0.1:" + port + "/" + sunny.SetScriptPage(""));
        }
        sunny.ProcessAddName("com.tencent.mm");
        // 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun（安卓一般为 2）
        sunny.OpenDrive(2);
    }
}
