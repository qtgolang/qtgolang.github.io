package com.example.sunnynet;

import com.SunnyNet.Internal.*;
import com.SunnyNet.api;

public class TestCallback_SunnyNet implements SunnyNetCallback {
    @Override
    public void onHTTPCallback(HTTPEvent Conn) {
        switch (Conn.Type()) {
            case Const.EventType_HTTP_Request:
                api.LogE("onHTTPCallback", "发起请求：" + Conn.URL());
                if (Conn.URL().contains("11.11.11")) {
                    Conn.Request().SetProxy("http://admin:123456@127.0.0.1:8888", 30 * 1000);
                    Conn.Request().SetProxy("http://127.0.0.1:8888", 30 * 1000);
                }
                break;
            case Const.EventType_HTTP_Response:
                api.LogE("onHTTPCallback", "请求完成：" + Conn.URL());
                break;
            case Const.EventType_HTTP_Error:
                api.LogE("onHTTPCallback", "请求错误：" + Conn.Error());
                break;
        }
    }

    @Override
    public void onWebSocketCallback(WebSocketEvent Conn) { }
    @Override
    public void onTCPCallback(TCPEvent Conn) { }
    @Override
    public void onUDPCallback(UDPEvent Conn) { }
    @Override
    public void onScriptLogCallback(long SunnyNetContext, String LogInfo) { }
    @Override
    public void onScriptCodeSaveCallback(long SunnyNetContext, String ScriptCode) { }
}
