// （抓HTTP/HTTPS）指定进程 - Java
// 按进程名或进程PID指定抓包目标，需在启动后加载驱动。
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
        // 指定进程：方式一按进程名，方式二按进程PID
        sunny.ProcessAddName("chrome.exe");
        sunny.ProcessAddName("notepad.exe");
        // sunny.ProcessAddPid(1234);  // 方式二：仅抓取指定 PID，按需取消注释并填入 PID
        if (sunny.Start()) {
            // 指定进程场景需在启动后加载驱动（需管理员权限）
            // 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun
            if (!sunny.OpenDrive(2)) {
                api.LogE("SunnyNet", "加载驱动失败，请尝试以管理员运行");
            }
            if (sunny.IsScriptCodeSupported())
                api.LogE("SunnyNet", "脚本管理页面:http://127.0.0.1:" + port + "/" + sunny.SetScriptPage(""));
        }
    }
}
