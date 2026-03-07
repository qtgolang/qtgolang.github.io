import json
import time

from SunnyNet import WebsocketTools, tools
from SunnyNet.CertManager import CertManager
from SunnyNet.Event import HTTPEvent, TCPEvent, UDPEvent, WebSocketEvent
from SunnyNet.HTTPClient import SunnyHTTPClient
from SunnyNet.Queue import Queue
from SunnyNet.SunnyNet import Version
from SunnyNet.SunnyNet import SunnyNet as Sunny

print("SunnyNet DLL版本：" + Version())

#
#  2025-04-13 (by:秦天)
#
#  请先下载 Python SDK文件


def __ScriptLogCallback__(LogInfo: str):
    print("脚本代码日志输出", LogInfo)


def __ScriptCodeCallback__(ScriptCode: str):
    print(ScriptCode, "在脚本代码处按下了保存代码按钮")


def __httpCallback__(Conn: HTTPEvent):
    if Conn.get_event_type() == Conn.EVENT_TYPE_REQUEST:
        Conn.get_request().remove_compression_mark()
        if Conn.get_url().find("11.22.33") != -1:
            # 给指定连接设置代理,有账号密码,设置超时（毫秒）
            Conn.get_request().set_proxy("http://admin:123456@127.0.0.1:8888", 30 * 1000)
            # 给指定连接设置代理,无账号密码,设置超时（毫秒）
            Conn.get_request().set_proxy("http://127.0.0.1:8888", 30 * 1000)
        else:
            pass
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_RESPONSE:
        ss = "请求完成：" + Conn.get_url() + " 响应长度:" + str(Conn.get_response().body_length())
        try:
            ss += " 响应内容：" + Conn.get_response().body_auto_str()
        except:
            ss += " -->> {响应内容:转字符串失败}请确认这是一个正常的字符串,你可以获取 使用 BodyAuto 函数 手动查看字节码,是否加密了？或者这是一张图片？"
        print(ss)
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_ERROR:
        print("请求客户端IP：" + Conn.get_client_ip() + "|" + Conn.get_request().get_header("Meddyid"))
        return


def __TcpCallback__(Conn: TCPEvent):
    """你可以将 Conn.get_theology_id() 储存 起来 在回调函数之外的任意代码位置 调用 SunnyNet.TCPTools.SendMessage() 发送数据 或  SunnyNet.TCPTools.Close() 关闭会话 """


    if Conn.get_event_type() == Conn.EVENT_TYPE_ABOUT:
        print("TCP即将连接：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr())
        if Conn.get_remote_addr().find("11.22.33") != -1:
            # 给指定连接设置代理,有账号密码,设置超时（毫秒）
            Conn.set_proxy("http://admin:123456@127.0.0.1:8888", 30 * 1000)
            # 给指定连接设置代理,无账号密码,设置超时（毫秒）
            Conn.set_proxy("http://127.0.0.1:8888", 30 * 1000)
        else:
            pass
        return
    if Conn.get_event_type() == Conn.EVENT_TYPE_OK:
        print("TCP连接成功：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr())
        return
    if Conn.get_event_type() == Conn.EVENT_TYPE_SEND:
        print("TCP发送数据：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr(), len(Conn.get_body()))
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_RECEIVE:
        print("TCP接收数据：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr(), len(Conn.get_body()))
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_CLOSE:
        print("TCP连接关闭：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr())
        return


def __UDPCallback__(Conn: UDPEvent):
    """你可以将 Conn.get_theology_id() 储存 起来 在回调函数之外的任意代码位置 调用 SunnyNet.UDPTools.SendMessage() 发送数据  """
    if Conn.get_event_type() == Conn.EVENT_TYPE_SEND:
        print("UDP发送数据：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr(), len(Conn.get_body()))
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_RECEIVE:
        print("UDP接收数据：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr(), len(Conn.get_body()))
        return
    elif Conn.get_event_type() == Conn.EVENT_TYPE_CLOSED:
        print("UDP连接关闭：" + Conn.get_local_addr() + "->" + Conn.get_remote_addr())
        return


def __WebsocketCallback__(Conn: WebSocketEvent):
    """你可以将 Conn.get_theology_id() 储存 起来 在回调函数之外的任意代码位置 调用 SunnyNet.WebsocketTools.SendMessage() 发送数据   或  SunnyNet.WebsocketTools.Close() 关闭会话 """
    if Conn.get_event_type() == Conn.EVENT_TYPE_CONNECTION_SUCCESS:
        print("Websocket 连接成功：" + Conn.get_url())
        return
    if Conn.get_event_type() == Conn.EVENT_TYPE_SEND:
        print("Websocket 发送数据：" + Conn.get_url(), len(Conn.get_body()))
        return
    if Conn.get_event_type() == Conn.EVENT_TYPE_RECEIVE:
        print("Websocket 收到数据：" + Conn.get_url(), len(Conn.get_body()))
        return
    if Conn.get_event_type() == Conn.EVENT_TYPE_CLOSE:
        print("Websocket 连接关闭：" + Conn.get_url())
        return


def TestSunnyNet():
    """ 测试SunnyNet 网络中间件 """
    port=2025
    app = Sunny()  # 创建一个 SunnyNet 应用实例
    app.set_port(port)  # 设置网络服务的端口为 2025
    app.install_cert_to_system()  # 将证书安装到系统中，以便进行安全通信

    # 设置全局上游代理,有账号密码,设置超时（毫秒）
    app.set_proxy("http://admin:123456@127.0.0.1:8888",30*1000)
    # 设置全局上游代理,无账号密码,设置超时（毫秒）
    app.set_proxy("http://127.0.0.1:8888",30*1000)

    # app.cancel_ie_proxy()  # 取消 IE代理
    # app.set_ie_proxy()  # 设置 IE代理

    app.set_callback(  # 设置回调函数，以处理不同的网络事件
        __httpCallback__,
        __TcpCallback__,
        __WebsocketCallback__,
        __UDPCallback__,
        __ScriptLogCallback__,
        __ScriptCodeCallback__
    )

    if not app.start():  # 尝试启动应用
        print("启动失败")  # 如果启动失败，打印错误信息
        print(app.error())  # 打印具体的错误信息
        exit(0)  # 退出程序
    else:
        if app.is_script_code_supported():
            print("当前脚本管理页面:http://127.0.0.1:"+str(port)+"/"+app.set_script_page(""))
        else:
            print("当前脚本是Mini版本不支持脚本代码")

        # 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun
        if not app.open_drive(2):  # 尝试打开驱动，需要以管理员模式运行
            raise Exception(
                "加载驱动失败，进程代理不可用(注意，需要管理员权限（请检查），NFAPI驱动win7请安装 KB3033929 补丁)")  # 如果加载驱动失败，抛出异常并提供详细提示
        else:
            app.process_all(True, False)  # 开始处理所有网络请求，参数为 True 和 False 表示某些处理选项
            print("正在运行 0.0.0.0:2025")  # 打印当前运行状态，表明服务正在监听 0.0.0.0:2025

    while True:  # 进入无限循环，保持程序运行
        time.sleep(10)  # 每隔 10 秒钟休眠一次，避免 CPU 占用过高


if __name__ == '__main__':
    TestSunnyNet()
