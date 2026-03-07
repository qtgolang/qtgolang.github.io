# TCP回调示例 - Python
import time
from SunnyNet.Event import HTTPEvent, TCPEvent, UDPEvent, WebSocketEvent
from SunnyNet.SunnyNet import SunnyNet as Sunny

def __httpCallback__(Conn: HTTPEvent):
    if Conn.get_event_type() == Conn.EVENT_TYPE_REQUEST:
        print("发起请求：" + Conn.get_url())
        if Conn.get_url().find("11.22.33") != -1:
            Conn.get_request().set_proxy("http://admin:123456@127.0.0.1:8888", 30 * 1000)
            Conn.get_request().set_proxy("http://127.0.0.1:8888", 30 * 1000)
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_RESPONSE:
        print("请求完成：" + Conn.get_url())
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_ERROR:
        print("请求错误：" + Conn.get_url())

def __TcpCallback__(Conn: TCPEvent): pass
def __UDPCallback__(Conn: UDPEvent): pass
def __WebsocketCallback__(Conn: WebSocketEvent): pass
def __ScriptLogCallback__(LogInfo: str): pass
def __ScriptCodeCallback__(ScriptCode: str): pass

def main():
    port = 2025
    app = Sunny()
    app.set_port(port)
    app.set_callback(__httpCallback__, __TcpCallback__, __WebsocketCallback__, __UDPCallback__, __ScriptLogCallback__, __ScriptCodeCallback__)
    if not app.start():
        print("启动失败", app.error())
        return
    print("启动成功 端口:", port)
    while True:
        time.sleep(10)

if __name__ == '__main__':
    main()
