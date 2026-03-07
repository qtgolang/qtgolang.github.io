// （抓HTTP/HTTPS）指定进程 - Go
// 按进程名或进程PID指定抓包目标，需在启动后加载驱动。
package main

import (
	"fmt"
	"log"
	"strings"
	"time"

	"github.com/qtgolang/SunnyNet/SunnyNet"
	"github.com/qtgolang/SunnyNet/public"
)

var Sunny = SunnyNet.NewSunny()

func main() {
	Sunny.SetPort(2025)
	Sunny.SetGoCallback(HttpCallback, nil, nil, nil)
	// 指定进程：方式一按进程名，方式二按进程PID
	Sunny.ProcessAddName("chrome.exe")
	Sunny.ProcessAddName("notepad.exe")
	// Sunny.ProcessAddPid(1234)  // 方式二：仅抓取指定 PID，按需取消注释并填入 PID
	if Sunny.Start() {
		// 指定进程场景需在启动后加载驱动（需管理员权限）
		// 驱动类型: 0=Proxifier, 1=NFAPI, 2=Tun
		if !Sunny.OpenDrive(2) {
			log.Println("加载驱动失败，请尝试以管理员运行")
		}
		log.Println("启动成功")
	} else {
		log.Println("启动失败", Sunny.GetError())
	}
	time.Sleep(100 * time.Second)
}

func HttpCallback(Conn SunnyNet.ConnHTTP) {
	switch Conn.Type() {
	case public.HttpSendRequest:
		fmt.Println("发起请求:", Conn.URL())
		if strings.Contains(Conn.URL(), "baidu.com") {
			Conn.SetAgent("http://127.0.0.1:8888", 30*1000)
		}
		return
	case public.HttpResponseOK:
		fmt.Println("请求完成:", Conn.URL())
		return
	case public.HttpRequestFail:
		fmt.Println("请求错误:", Conn.URL(), Conn.Error())
		return
	}
}
