// SunnyNet 设置上游代理示例 - Go
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
	Sunny.SetPort(2026)
	// 设置全局代理无账号密码,超时毫秒
	Sunny.SetGlobalProxy("socks://192.168.31.1:4321", 30000)
	// 设置全局代理有账号密码,超时毫秒
	Sunny.SetGlobalProxy("socks://admin:123456@192.168.31.1:4321", 30000)
	Sunny.SetGoCallback(HttpCallback, TcpCallback, nil, nil)
	if Sunny.Start() {
		log.Println("启动成功")
	} else {
		log.Println("启动失败", Sunny.GetError())
	}
	time.Sleep(100 * time.Second)
}

func HttpCallback(Conn SunnyNet.ConnHTTP) {
	switch Conn.Type() {
	case public.HttpSendRequest: // 发起请求
		if strings.Contains(Conn.URL(), "https://qew.tlb.com:33656/") {
			Conn.SetAgent("socks://admin:123456@192.168.31.1:4321", 30*1000)
			Conn.SetAgent("socks://192.168.31.1:4321", 30*1000)
		}
		return
	case public.HttpResponseOK: // 请求完成
		if strings.Contains(Conn.URL(), "https://qqnew.tlbb.qq.com:33656/") {
			fmt.Println("请求完成", Conn.URL(), Conn.GetResponseProto(), Conn.GetResponseHeader())
		}
		return
	case public.HttpRequestFail: // 请求错误
		fmt.Println("请求错误", time.Now(), Conn.URL(), Conn.Error())
		return
	}
}

func TcpCallback(Conn SunnyNet.ConnTCP) {
	switch Conn.Type() {
	case public.SunnyNetMsgTypeTCPAboutToConnect: // 即将连接
		log.Println("TCP 即将连接到:", Conn.LocalAddress(), "->", Conn.RemoteAddress())
		if strings.Contains(Conn.RemoteAddress(), "11.22.33") {
			Conn.SetAgent("socks://admin:123456@192.168.31.1:4321", 30*1000)
			Conn.SetAgent("socks://192.168.31.1:4321", 30*1000)
		}
		return
	case public.SunnyNetMsgTypeTCPConnectOK:
		return
	case public.SunnyNetMsgTypeTCPClose:
		return
	case public.SunnyNetMsgTypeTCPClientSend:
		return
	case public.SunnyNetMsgTypeTCPClientReceive:
		return
	default:
		return
	}
}