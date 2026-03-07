// HTTP回调示例 - C#
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
        if (syNet.Start())
        {
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
