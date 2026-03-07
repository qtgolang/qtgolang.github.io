/*
 * 导入自己的证书 - C/C++
 * 使用自定义 CA 证书（.cer + .key 或 .p12）替代内置证书，需在 Start() 之前设置。
 */
#include <iostream>
#include <windows.h>
#include "SunnyNet.h"
#include "HTTPClient.h"
#include "CertManager.h"
#include <thread>
#include <chrono>

SunnyNet Sunny;

void http_callback_Func(HTTPEvent Conn) {
	switch (Conn.Type()) {
	case Conn.EventType_Request:
		printf("发起请求：%s\n", Conn.URL().c_str());
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
	int port = 2025;
	Sunny.BindCallbacks(&http_callback_Func, &tcp_callback_Func, &ws_callback_Func, &udp_callback_Func, &script_log_callback_Func, &script_svae_callback_Func);
	Sunny.BindPort(port);

	// 导入自己的证书：加载 X509 证书与私钥（密码可选，空则传 ""）
	// 也可使用 LoadP12Certificate("path/to/ca.p12", "password") 加载 P12
	CertManager cert;
	if (cert.LoadX509Certificate("", "ca.cer", "ca.key")) {
		Sunny.SetCert(cert);  // 在启动前设置自定义 CA
	} else {
		printf("加载证书失败，将使用内置证书\n");
	}

	if (Sunny.Start()) {
		std::cout << "启动成功 端口:" << port << "\n";
		// 将根证书安装到系统信任区，便于浏览器/系统信任
		if (!Sunny.InstallCertificate()) {
			printf("安装证书到系统失败，请手动安装根证书\n");
		}
	} else {
		std::cout << "启动失败 " << Sunny.GetError().c_str() << "\n";
	}
	std::this_thread::sleep_for(std::chrono::seconds(10000));
	return 0;
}
