// （抓HTTP/HTTPS）指定进程 - C#
// 按进程名或进程PID指定抓包目标，需在启动后加载驱动。
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
        // 指定进程：方式一按进程名，方式二按进程PID
        syNet.ProcessAddName("chrome.exe");
        syNet.ProcessAddName("notepad.exe");
        // syNet.ProcessAddPid(1234);  // 方式二：仅抓取指定 PID，按需取消注释并填入 PID
        if (syNet.Start())
        {
            // 指定进程场景需在启动后加载驱动（需管理员权限）
            // 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun
            if (!syNet.LoadDriver(2))
                Debug.WriteLine("加载驱动失败，请尝试以管理员运行");
            Debug.WriteLine("启动成功");
            if (syNet.IsScriptCodeSupported())
                Debug.Write("脚本代码管理页面：http://127.0.0.1:" + port + "/" + syNet.SetScriptPage(""));
        }
        else
        {
            Debug.WriteLine("启动失败 " + syNet.GetError());
        }
    }
}
