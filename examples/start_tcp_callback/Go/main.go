// TCP回调示例 - Go
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
	if Sunny.Start() {
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
