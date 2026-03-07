// 导入自己的证书 - Go
// 使用自定义 CA 证书（.p12 或 X509 .cer+.key）替代内置证书，需在 Start() 之前设置。
package main

import (
	"log"
	"time"

	"github.com/qtgolang/SunnyNet/SunnyNet"
)

var Sunny = SunnyNet.NewSunny()

func main() {
	Sunny.SetPort(2025)
	// 导入自己的证书：加载 P12（或使用 LoadX509Certificate 加载 .cer+.key）
	cert := SunnyNet.NewCertManager()
	if ok := cert.LoadP12Certificate("ca.p12", "password"); ok {
		Sunny.SetCert(certx.Context())  // 在启动前设置自定义 CA
	} else {
		log.Println("加载证书失败，将使用内置证书")
	}

	if Sunny.Start() {
		log.Println("启动成功")
		if !Sunny.InstallCertificate() {
			log.Println("安装证书到系统失败，请手动安装根证书")
		}
	} else {
		log.Println("启动失败", Sunny.GetError())
	}
	time.Sleep(100 * time.Second)
}
