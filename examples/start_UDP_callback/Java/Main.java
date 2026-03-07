// UDP回调示例 - Java
public class Main {
    static SunnyNetCallback callback = new TestCallback_SunnyNet();
    static SunnyNet sunny = new SunnyNet();

    public static void main(String[] args) {
        TestSunnyNet();
    }

    public static void TestSunnyNet() {
        int port = 2025;
        sunny.SetPort(port);
        sunny.SetCallback(callback);
        if (sunny.Start()) {
            if (sunny.IsScriptCodeSupported())
                api.LogE("SunnyNet", "脚本管理页面:http://127.0.0.1:" + port + "/" + sunny.SetScriptPage(""));
        }
    }
}
