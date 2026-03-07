/*
 * WebSocket回调函数示例 - C/C++
 */
#include <iostream>
#include <windows.h>
#include "SunnyNet.h"
#include "HTTPClient.h"
#include <thread>
#include <chrono>

SunnyNet Sunny;

void ws_callback_Func(WebSocketEvent Conn) {
	switch (Conn.Type()) {
	case Conn.EventType_OK:
		printf("WebSocket 连接成功：%s\n", Conn.URL().c_str());
		break;
	case Conn.EventType_Send:
		printf("WebSocket 客户端发送：%s\n", Conn.URL().c_str());
		break;
	case Conn.EventType_Receive:
		printf("WebSocket 客户端收到：%s\n", Conn.URL().c_str());
		break;
	case Conn.EventType_Close:
		printf("WebSocket 连接关闭：%s\n", Conn.URL().c_str());
		break;
	default:
		break;
	}
}

void http_callback_Func(HTTPEvent Conn) { return; }
void tcp_callback_Func(TCPEvent Conn) { return; }
void udp_callback_Func(UDPEvent Conn) { return; }
void script_log_callback_Func(IntPtr SunnyNetContext, std::string logInfo) { }
void script_svae_callback_Func(IntPtr SunnyNetContext, std::string code) { }

int main() {
	Sunny.BindCallbacks(&http_callback_Func, &tcp_callback_Func, &ws_callback_Func, &udp_callback_Func, &script_log_callback_Func, &script_svae_callback_Func);
	int port = 2025;
	Sunny.BindPort(port);
	if (Sunny.Start()) {
		std::cout << "启动成功 端口:" << port << "\n";
	} else {
		std::cout << "启动失败 " << Sunny.GetError().c_str() << "\n";
	}
	std::this_thread::sleep_for(std::chrono::seconds(10000));
	return 0;
}
