package TestDemo.SunnyNet;


import com.SunnyNet.Internal.*;
import com.SunnyNet.api;
import com.SunnyNet.tools;

import java.util.HashMap;

/**
 * 构造回调函数,所有的回调函数事件在 本类中处理 你可以自己写一个类 只需要实现 com.SunnyNet.Internal.SunnyNetCallback 接口。
 */
public class TestCallback_SunnyNet implements SunnyNetCallback {
    /**
     * HTTP 请求的回调方法。
     *
     * @param Conn 事件对象
     */
    @Override
    public void onHTTPCallback(HTTPEvent Conn) {
        switch (Conn.Type()) {
            case Const.EventType_HTTP_Request:      //发起请求
                api.LogE("onHTTPCallback", "发起请求：" + Conn.URL());
                //删除压缩标记
                Conn.Request().RemoveCompressionMark();
                //设置HTTP2指纹,你可以根据模板随机一些值来达到随机HTTP2指纹的效果
                Conn.Request().SetH2Config(Const.HTTP2_Fingerprint_Config_Chrome_103_105);
                if (!Conn.Method().equalsIgnoreCase("GET")) {
                    api.LogE("onHTTPCallback", "POST提交内容：" + Conn.Request().BodyToUTF8());
                }
                break;
            case Const.EventType_HTTP_Response:      //请求完成
                //api.LogE("onHTTPCallback", "响应内容：" + Conn.Response().BodyAutoUTF8());
                break;
            case Const.EventType_HTTP_Error:      //请求错误
                api.LogE("onHTTPCallback", "请求错误：" + Conn.Error());
                break;
        }
    }

    /**
     * WebSocket 请求的回调方法。
     *
     * @param Conn 事件对象
     */
    @Override
    public void onWebSocketCallback(WebSocketEvent Conn) {
        //你可以记录保存 Conn.TheologyID() 唯一ID,使用以下函数,在回调函数以外的任意位置发送数据
        //WebsocketTools.SendMessage()

        //获取请求时的协议头
        HashMap<String, String[]> headerMap = Conn.Headers();
        for (String key : headerMap.keySet()) {
            String[] values = headerMap.get(key);
            //api.LogE("SunnyNetCallback", "SunnyNetCallback onWebSocketCallback:提交的Headers ->> " + key + ":" + String.join(";", values));
        }
        switch (Conn.Type()) {
            case Const.EventType_Websocket_OK:
                api.LogE("onWebSocketCallback", "Websocket 连接成功");
                break;
            case Const.EventType_Websocket_Close:
                api.LogE("onWebSocketCallback", "Websocket 连接关闭");
                break;
            case Const.EventType_Websocket_Receive:
                api.LogE("onWebSocketCallback", "Websocket 收到消息:" + tools.bytesToHex(Conn.Body()));
                break;
            case Const.EventType_Websocket_Send:
                api.LogE("onWebSocketCallback", "Websocket 发送消息:" + tools.bytesToHex(Conn.Body()));
                break;
        }


    }

    /**
     * TCP 请求的回调方法。
     *
     * @param Conn 事件对象
     */
    @Override
    public void onTCPCallback(TCPEvent Conn) {

        //你可以记录保存 Conn.TheologyID() 唯一ID,使用以下函数,在回调函数以外的任意位置发送数据
        //TCPTools.SendMessage()

        switch (Conn.Type()) {
            case Const.EventType_TCP_About://即将连接
                api.LogE("onTCPCallback", "TCPEvent 即将连接:" + Conn.RemoteAddr());
                
                if (Conn.RemoteAddr().contains("11.22.33")){
                    //对指定请求设置代理,有账号密码,设置超时（毫秒）
                    Conn.SetProxy("http://admin:123456@127.0.0.1:8888",30*1000);
                    //对指定请求设置代理,无账号密码,设置超时（毫秒）
                    Conn.SetProxy("http://127.0.0.1:8888",30*1000);
                }

                //Conn.SetProxy();//设置代理
                //Conn.Redirect();//重定向到新的地址
                break;
            case Const.EventType_TCP_OK: //连接成功
                api.LogE("onTCPCallback", "TCPEvent " + Conn.LocalAddr() + "->" + Conn.RemoteAddr() + "连接成功");
                break;
            case Const.EventType_TCP_Send: //发送数据
                api.LogE("onTCPCallback", "TCPEvent " + Conn.LocalAddr() + "->" + Conn.RemoteAddr() + " 发送数据:" + tools.bytesToHex(Conn.Body()));
                break;
            case Const.EventType_TCP_Receive: //接收数据
                api.LogE("onTCPCallback", "TCPEvent " + Conn.LocalAddr() + "->" + Conn.RemoteAddr() + " 接收数据:" + tools.bytesToHex(Conn.Body()));
                break;
            case Const.EventType_TCP_Close: //连接关闭
                api.LogE("onTCPCallback", "TCPEvent " + Conn.LocalAddr() + "->" + Conn.RemoteAddr() + "连接关闭");
                break;
        }

    }

    /**
     * UDP 请求的回调方法。
     *
     * @param Conn 事件对象
     */
    @Override
    public void onUDPCallback(UDPEvent Conn) {

        //你可以记录保存 Conn.TheologyID() 唯一ID,使用以下函数,在回调函数以外的任意位置发送数据
        //UDPTools.SendMessage()

        switch (Conn.Type()+100) {
            case Const.EventType_UDP_Send: //发送数据
                api.LogE("onUDPCallback", "UDPEvent " + Conn.LocalAddr() + "->" + Conn.RemoteAddr() + " 发送数据:" + tools.bytesToHex(Conn.Body()));
                break;
            case Const.EventType_UDP_Receive: //接收数据
                api.LogE("onUDPCallback", "UDPEvent " + Conn.LocalAddr() + "->" + Conn.RemoteAddr() + " 接收数据:" + tools.bytesToHex(Conn.Body()));
                break;
            case Const.EventType_UDP_Closed: //连接关闭
                api.LogE("onUDPCallback", "UDPEvent " + Conn.LocalAddr() + "->" + Conn.RemoteAddr() + "连接关闭");
                break;
        }
    }

    /**
     * 脚本日志输出回调函数
     *
     * @param LogInfo 日志信息
     */
    @Override
    public void onScriptLogCallback(long SunnyNetContext, String LogInfo) {
        //脚本代码中打印的日志信息
        api.LogE("onScriptLogCallback", "ScriptCode Log -> " + LogInfo);
    }

    /**
     * 脚本代码保存
     *
     * @param ScriptCode 脚本代码
     */
    @Override
    public void onScriptCodeSaveCallback(long SunnyNetContext, String ScriptCode) {
        //当在脚本页面按下保存操作后,并且加载成功后，这里会得到页面中的脚本代码
        //你可以将脚本代码保存到文件,下次打开软件,在启动SunnyNet 时,使用命令  sunny.SetScriptCode( code ); 来载入脚本代码

    }
}
