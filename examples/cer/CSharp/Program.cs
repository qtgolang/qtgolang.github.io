// 导入自己的证书 - C#
// 使用自定义 CA 证书（.cer+.key 或 .p12）替代内置证书，需在 Start() 之前设置。
using System;
using System.Diagnostics;

class Program
{
    static void Main()
    {
        int port = 2025;
        var testCallBack = new TestSunnyNetCallback();
        var syNet = new SunnyNetlibray.SunnyNet();
        syNet.BindPort(port);
        syNet.BindCallback((SunnyNetlibray.Internal.SunnyNet)testCallBack);

        // 导入自己的证书：加载 X509 证书与私钥（密码可选）
        // 也可使用 LoadP12Certificate("ca.p12", "password") 加载 P12
        // 若 SDK 中证书类或方法名不同，请以官方文档为准
        var cert = new SunnyNetlibray.CertManager();
        if (cert.LoadX509Certificate("", "ca.cer", "ca.key"))
        {
            syNet.SetCustomCACertificate(cert);  // 在启动前设置自定义 CA
        }
        else
        {
            Debug.WriteLine("加载证书失败，将使用内置证书");
        }

        if (syNet.Start())
        {
            Debug.WriteLine("启动成功");
            string err;
            if (!syNet.InstallCertificate(out err))
                Debug.WriteLine("安装证书到系统失败，请手动安装根证书: " + err);
            if (syNet.IsScriptCodeSupported())
                Debug.Write("脚本管理页面：http://127.0.0.1:" + port + "/" + syNet.SetScriptPage(""));
        }
        else
        {
            Debug.WriteLine("启动失败 " + syNet.GetError());
        }
    }
}
