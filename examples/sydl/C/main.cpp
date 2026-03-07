#include <iostream>
#include <windows.h>   
#include "SunnyNet.h"  
#include "HTTPClient.h"  
#include <thread>
#include "Tools.h" 

SunnyNet Sunny;
void ws_callback_Func(WebSocketEvent Conn) {

	//你可以将 Conn.UniqueID() 结果储存,在回调函数以外的任何地方,使用以下方法,发送数据，关闭连接
	//WebSocketTools tools = WebSocketTools();
	//tools.Close(Conn.UniqueID());
	//tools.Send(tools.ToClient, Conn.UniqueID(), tools.WsMessage_Text, Bytes("123456"));

	switch (Conn.Type())
	{
	case Conn.EventType_OK://连接成功
		printf("WebSocket 连接成功：%s\n", Conn.URL().c_str()); 
		break;
	case Conn.EventType_Send://客户端发送数据
		printf("WebSocket 客户端发送数据：%s\n", Conn.URL().c_str());
		break;
	case Conn.EventType_Receive://客户端收到数据
		printf("WebSocket 客户端收到数据：%s\n", Conn.URL().c_str());
		break;
	case Conn.EventType_Close://连接已关闭
		printf("WebSocket 连接已关闭：%s\n", Conn.URL().c_str());
		break;
	default:
		break;
	}  
}

void udp_callback_Func(UDPEvent Conn) {

	//你可以将 Conn.UniqueID() 结果储存,在回调函数以外的任何地方,使用以下方法,发送数据
	//UDPTools tools = UDPTools();
	//tools.Send(tools.ToClient, Conn.UniqueID(), Bytes("123456")); 
	switch (Conn.Type())
	{
	case Conn.EventType_Send://客户端发送数据
		printf("UDP 客户端发送数据：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		break;
	case Conn.EventType_Receive://客户端收到数据
		printf("UDP 客户端收到数据：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		break;
	case Conn.EventType_Close://连接已关闭
		printf("UDP 连接已关闭：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		break;
	default:
		break;
	}
}
 
void tcp_callback_Func(TCPEvent Conn) { 
	return;
	//你可以将 Conn.UniqueID() 结果储存,在回调函数以外的任何地方,使用以下方法,发送数据，关闭连接
	//TCPTools tools =TCPTools();
	//tools.Close(Conn.UniqueID());
	//tools.Send(tools.ToClient, Conn.UniqueID(), Bytes("123456"));

	switch (Conn.Type())
	{
	case Conn.EventType_About: //即将连接
		printf("TCP 即将连接：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		if (Conn.RemoteAddress().find("baidu.com") != string::npos) {
			//设置全局代理，有账号密码
			Conn.SetProxy("http://admin:123456@127.0.0.1:8888", 30 * 1000);
			//设置全局代理，无账号密码,超时毫秒
			Conn.SetProxy("http://127.0.0.1:8888", 30 * 1000);
		}
		break;
	case Conn.EventType_OK://连接成功
		printf("TCP 连接成功：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		break;
	case Conn.EventType_Send://客户端发送数据
		printf("TCP 客户端发送数据：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		break;
	case Conn.EventType_Receive://客户端收到数据
		printf("TCP 客户端收到数据：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		break;
	case Conn.EventType_Close://连接已关闭
		printf("TCP 连接已关闭：%s -> %s\n", Conn.LocalAddress().c_str(), Conn.RemoteAddress().c_str());
		break;
	default:
		break;
	}
}
 
void http_callback_Func(HTTPEvent Conn) {
	switch (Conn.Type())
	{
	case Conn.EventType_Request://发起请求
		printf("发起请求：%s\n", Conn.URL().c_str());
		if (Conn.URL().find("baidu.com") != string::npos) { 
			//设置全局代理，有账号密码
			Conn.Request().SetProxy("http://admin:123456@127.0.0.1:8888",30*1000);
			//设置全局代理，无账号密码,超时毫秒
			Conn.Request().SetProxy("http://127.0.0.1:8888", 30 * 1000);
		}
		Conn.Request().SetHTTP2Config(HTTP2_Fingerprint_Config_Chrome_103_105);
		break;
	case Conn.EventType_Response://请求完成
		printf("请求完成：%s\n", Conn.URL().c_str());
		break;
	case Conn.EventType_Error://请求错误
		printf("请求错误：%s\n", Conn.URL().c_str());
		break;
	default:
		break;
	}
}

void script_log_callback_Func(IntPtr SunnyNetContext, string logInfo) {
	printf("脚本日志输出:%s\n", logInfo.c_str());
}
void script_svae_callback_Func(IntPtr SunnyNetContext, string code) {
	//请注意编码，输出的code 是UTF8的编码
	printf("%s\n脚本页面按下了保存按钮\n", code.c_str());
}

 
int main()
{
	Sunny.BindCallbacks(&http_callback_Func, &tcp_callback_Func, &ws_callback_Func, &udp_callback_Func, &script_log_callback_Func, &script_svae_callback_Func);
	int port = 2025;
	Sunny.BindPort(port);
	Sunny.SetOutRouterIP("");
	//设置全局代理，有账号密码
	Sunny.SetGlobalProxy("http://admin:123456@127.0.0.1:8888");
	//设置全局代理，无账号密码,超时毫秒
	Sunny.SetGlobalProxy("http://127.0.0.1:8888",30*1000);

	if (Sunny.Start()) {
		// 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun
		if (!Sunny.Processes_LoadDriver(2)) {
			printf("加载驱动失败\n");
		}
		if (Sunny.IsScriptCodeSupported()) {
			printf("当前DLL支持脚本代码\n");
			//设置上一次的脚本代码
			//Sunny.SetScriptCode("");
			printf("当前脚本管理页面:http://127.0.0.1:%d/%s\n", port, Sunny.SetScriptPage("").c_str());
		}
		else {
			printf("当前DLL是Mini版本,不支持脚本代码\n");
		}

		//Sunny.Processes_LoadDriver(true);
		//Sunny.Processes_AllProcesses(true, false);
		std::cout << "启动[成功] 运行端口:"<< port <<"  DLL版本：" << GetSunnyNetVersion().c_str() << "\n";
	}
	else {
		std::cout << "启动[失败]" << Sunny.GetError().c_str() << "\n";
	}
	std::this_thread::sleep_for(std::chrono::seconds(10000));
}
