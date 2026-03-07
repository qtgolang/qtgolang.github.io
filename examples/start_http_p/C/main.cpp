/*
 * （抓HTTP/HTTPS）指定进程 - C/C++
 * 按进程名或进程PID指定抓包目标，需在启动后加载驱动。
 */
#include <iostream>
#include <windows.h>
#include "SunnyNet.h"
#include "HTTPClient.h"
#include <thread>
#include <chrono>
#include "Tools.h"

SunnyNet Sunny;

void http_callback_Func(HTTPEvent Conn) {
	switch (Conn.Type()) {
	case Conn.EventType_Request:
		printf("发起请求：%s\n", Conn.URL().c_str());
		if (Conn.URL().find("baidu.com") != std::string::npos) {
			Conn.Request().SetProxy("http://admin:123456@127.0.0.1:8888", 30 * 1000);
			Conn.Request().SetProxy("http://127.0.0.1:8888", 30 * 1000);
		}
		Conn.Request().SetHTTP2Config(HTTP2_Fingerprint_Config_Chrome_103_105);
		break;
	case Conn.EventType_Response:
		printf("请求完成：%s\n", Conn.URL().c_str());
		break;
	case Conn.EventType_Error:
		printf("请求错误：%s\n", Conn.URL().c_str());
		break;
	default:
		break;
	}
}

void tcp_callback_Func(TCPEvent Conn) { return; }
void ws_callback_Func(WebSocketEvent Conn) { return; }
void udp_callback_Func(UDPEvent Conn) { return; }
void script_log_callback_Func(IntPtr SunnyNetContext, std::string logInfo) { }
void script_svae_callback_Func(IntPtr SunnyNetContext, std::string code) { }

int main() {
	Sunny.BindCallbacks(&http_callback_Func, &tcp_callback_Func, &ws_callback_Func, &udp_callback_Func, &script_log_callback_Func, &script_svae_callback_Func);
	int port = 2025;
	Sunny.BindPort(port);
	// 指定进程：方式一按进程名，方式二按进程PID
	Sunny.Processes_AddName("chrome.exe");
	Sunny.Processes_AddName("notepad.exe");
	// Sunny.Processes_AddPid(1234);  // 方式二：仅抓取指定 PID 的流量，按需取消注释并填入 PID
	if (Sunny.Start()) {
		// 指定进程场景需在启动后加载驱动，否则无法按进程过滤（需管理员权限）
		// 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun
		if (!Sunny.Processes_LoadDriver(2)) {
			printf("加载驱动失败，请尝试以管理员运行\n");
		}
		std::cout << "启动成功 端口:" << port << " 已指定进程过滤\n";
	} else {
		std::cout << "启动失败 " << Sunny.GetError().c_str() << "\n";
	}
	std::this_thread::sleep_for(std::chrono::seconds(10000));
	return 0;
}
