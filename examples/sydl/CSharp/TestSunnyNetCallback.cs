
using SunnyNetlibray;
using SunnyNetlibray.Event;
using System.Diagnostics;

namespace SunnyTest
{
    public class TestSunnyNetCallback : SunnyNetlibray.Internal.SunnyNet
    {

        public void OnHttpCallback(HTTPEvent Conn)
        {

            switch (Conn.Type())
            {
                case HTTPEvent.EventType_HTTP_Request:
                    Debug.WriteLine("发起请求:" + Conn.URL() + " -> Post数据:" + Conn.Request().Body().String());
                    if (Conn.URL().Contains("11.11.11"))
                    {
                        //指定请求设置指定代理,有账号密码,超时 毫秒
                        Conn.Request().SetProxy("http://admin:123456@127.0.0.1:8888", 30 * 1000);
                        //指定请求设置指定代理,无账号密码,超时 毫秒
                        Conn.Request().SetProxy("http://127.0.0.1:8888", 30 * 1000);
                    }
                    break;
                case HTTPEvent.EventType_HTTP_Response:
                    Debug.WriteLine("请求完成:" + Conn.URL());
                    break;
                case HTTPEvent.EventType_HTTP_Error:
                    Debug.WriteLine("请求错误:" + Conn.URL() + " ->> " + Conn.Error());
                    break;
            }
        }


        public void OnTcpCallback(TCPEvent Conn)
        {
            //你可以记录保存 Conn.TheologyID() 唯一ID,使用以下函数,在回调函数以外的任意位置发送数据
            //SunnyNetlibray.Tools.TCPTools.SendMessage(SunnyNetlibray.Tools.TCPTools.SendToServer, Conn.TheologyID(), "tcp-SendToServerValue");
            //SunnyNetlibray.Tools.TCPTools.Close(Conn.TheologyID());
            switch (Conn.Type())
            {
                case TCPEvent.EventType_TCP_About:
                    Debug.WriteLine("TCP 即将连接:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr());
                    if (Conn.RemoteAddr().Contains("11.11.11"))
                    {
                        //指定请求设置指定代理,有账号密码,超时 毫秒
                        Conn.SetProxy("http://admin:123456@127.0.0.1:8888", 30 * 1000);
                        //指定请求设置指定代理,无账号密码,超时 毫秒
                        Conn.SetProxy("http://127.0.0.1:8888", 30 * 1000);
                    }
                    break;
                case TCPEvent.EventType_TCP_OK:
                    Debug.WriteLine("TCP 连接成功:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr());
                    break;
                case TCPEvent.EventType_TCP_Send:
                    Debug.WriteLine("TCP 发送消息:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr() + ",发送:" + Conn.Body().Length + " / byte");
                    break;
                case TCPEvent.EventType_TCP_Receive:
                    Debug.WriteLine("TCP 收到数据:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr() + ",接收:" + Conn.Body().Length + " / byte");
                    break;
                case TCPEvent.EventType_TCP_Close:
                    Debug.WriteLine("TCP 连接关闭:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr());
                    break;
            }
        }

        public void OnUdpCallback(UDPEvent Conn)
        {
            //你可以记录保存 Conn.TheologyID() 唯一ID,使用以下函数,在回调函数以外的任意位置发送数据
            //SunnyNetlibray.Tools.UDPTools.SendMessage(SunnyNetlibray.Tools.UDPTools.SendToServer, Conn.TheologyID(), "udp-SendToServerValue");
            switch (Conn.Type())
            {
                case UDPEvent.EventType_UDP_Send:
                    Debug.WriteLine("UDP 发送消息:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr() + ",发送:" + Conn.Body().Length + " / byte");
                    break;
                case UDPEvent.EventType_UDP_Receive:
                    Debug.WriteLine("UDP 收到数据:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr() + ",接收:" + Conn.Body().Length + " / byte");
                    break;
                case UDPEvent.EventType_UDP_Closed:
                    Debug.WriteLine("UDP 连接关闭:" + Conn.LocalAddr() + " -> " + Conn.RemoteAddr());
                    break;
            }
        }


        public void OnWebSocketCallback(WebSocketEvent Conn)
        {
            //你可以记录保存 Conn.TheologyID() 唯一ID,使用以下函数,在回调函数以外的任意位置发送数据、关闭会话
            //主动发送
            //SunnyNetlibray.Tools.WebSocketTools.SendMessage(SunnyNetlibray.Tools.WebSocketTools.SendToServer, Conn.TheologyID(), Const.WsMessageType.Text, "WebSocket-SendToServerValue");
            //关闭连接
            //SunnyNetlibray.Tools.WebSocketTools.Close(Conn.TheologyID());
            switch (Conn.Type())
            {
                case WebSocketEvent.EventType_Websocket_OK:
                    Debug.WriteLine("WebSocket 连接成功:" + Conn.URL());
                    break;
                case WebSocketEvent.EventType_Websocket_Send:
                    Debug.WriteLine("WebSocket 发送消息:" + Conn.URL() + " -> " + ",发送:" + Conn.Body().Length + " / byte  ->wsMeassageType:" + Conn.MessageType());
                    break;
                case WebSocketEvent.EventType_Websocket_Receive:
                    Debug.WriteLine("WebSocket 收到数据:" + Conn.URL() + " -> " + ",接收:" + Conn.Body().Length + " / byte ->wsMeassageType:" + Conn.MessageType());
                    break;
                case WebSocketEvent.EventType_Websocket_Close:
                    Debug.WriteLine("WebSocket 连接关闭:" + Conn.URL());
                    break;
            }
        }

        public void OnScriptCodeSaveCallback(long SunnyNetContext, SunnyNetlibray.Internal.EventValue scriptCode)
        {
            Debug.WriteLine(scriptCode.String() + "\r\n脚本编辑按下了保存按钮！");
        }

        public void OnScriptLogCallback(long SunnyNetContext, SunnyNetlibray.Internal.EventValue logInfo)
        {
            Debug.WriteLine(" 脚本日志：" + logInfo.String());
            Debug.WriteLine(" 脚本日志1：" + logInfo.Length);
        }
    }
}
