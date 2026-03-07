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
                        Conn.Request().SetProxy("http://admin:123456@127.0.0.1:8888", 30 * 1000);
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

        public void OnTcpCallback(TCPEvent Conn) { }
        public void OnUdpCallback(UDPEvent Conn) { }
        public void OnWebSocketCallback(WebSocketEvent Conn) { }
        public void OnScriptCodeSaveCallback(long SunnyNetContext, SunnyNetlibray.Internal.EventValue scriptCode) { }
        public void OnScriptLogCallback(long SunnyNetContext, SunnyNetlibray.Internal.EventValue logInfo) { }
    }
}
