
public class Main {
    static SunnyNetCallback callback = new TestCallback_SunnyNet();
    static SunnyNet sunny = new SunnyNet();

    public static void main(String[] args) {
        TestSunnyNet();
    }

    public static void TestSunnyNet() {
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

    }

}
