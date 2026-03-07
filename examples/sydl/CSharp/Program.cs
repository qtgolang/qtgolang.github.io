// SunnyNet 设置上游代理示例 - C#
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
        // 设置全局代理,有账号密码
        syNet.SetGlobalProxy("http://admin:123456@127.0.0.1:8888");
        // 设置全局代理,无账号密码,超时 毫秒
        syNet.SetGlobalProxy("http://127.0.0.1:8888", 30 * 1000);
        bool b = syNet.Start();
        if (b)
        {
            Debug.WriteLine("启动成功");
            if (syNet.IsScriptCodeSupported())
                Debug.Write("脚本代码管理页面：http://127.0.0.1:" + port + "/" + syNet.SetScriptPage(""));
            else
                Debug.Write("当前SDK是Mini 版本不支持脚本代码");
        }
        else
        {
            Debug.WriteLine("启动失败");
            Debug.WriteLine(syNet.GetError());
        }
    }
}
