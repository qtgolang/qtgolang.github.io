# 导入自己的证书 - Python
# 使用自定义 CA 证书（.cer+.key 或 .p12）替代内置证书，需在 start() 之前设置。
import time
from SunnyNet.CertManager import CertManager
from SunnyNet.Event import HTTPEvent, TCPEvent, UDPEvent, WebSocketEvent
from SunnyNet.SunnyNet import SunnyNet as Sunny

def __httpCallback__(Conn: HTTPEvent):
    if Conn.get_event_type() == Conn.EVENT_TYPE_REQUEST:
        print("发起请求：" + Conn.get_url())
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
    app.set_callback(
        __httpCallback__, __TcpCallback__, __WebsocketCallback__,
        __UDPCallback__, __ScriptLogCallback__, __ScriptCodeCallback__
    )

    # 导入自己的证书：加载 X509 证书与私钥（密码可选，空传 ""）
    # 也可使用 cert.load_p12("ca.p12", "password") 加载 P12
    cert = CertManager()
    if cert.load_x509_cert("", "ca.cer", "ca.key"):
        app.set_cert(cert)  # 在启动前设置自定义 CA
    else:
        print("加载证书失败，将使用内置证书")

    if not app.start():
        print("启动失败", app.error())
        return
    print("启动成功 端口:", port)
    if not app.install_cert_to_system():
        print("安装证书到系统失败，请手动安装根证书")
    while True:
        time.sleep(10)

if __name__ == '__main__':
    main()
