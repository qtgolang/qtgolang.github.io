const _main_Pages = `
  <div class="inner">
      <header id="headerH"> 
          <a class="logo"><strong style="color: red;">服务器维护不起了，只能用这种低成本的网站了</strong></br>网站域名有:(访问不了时,可切换尝试)</br>https://esunny.vip</br>https://www.esunny.vip</br>https://github.esunny.vip</br></br></br></br><h4 style="color: red;">重要提示：</br>如果使用谷歌浏览器下载文件，提示危险</br>请打开设置->隐私和安全->安全->不保护</br>然后重新下载即可，不清楚是什么原因</br>将文件上传到微云</br>使用谷歌浏览器下载，就不会提示危险！</br>但是微云下载太慢，还是蓝奏吧！</h4></br></br></br></br>Sunny网络中间件 <strong>（完整免费开源-功能介绍）QQ群:751406884 二群：545120699 三群：170902713</strong>
          </a>
      </header>
      <div class="content">
          <p>Sunny网络中间件 和 Fiddler 模块类似。 是可跨平台的网络分析组件</p>
          <p>可用于HTTP/HTTPS/WS/WSS/TCP/UDP网络分析 无内存泄漏，为二次开发量身制作。</p>
          <h3>SDK功能</h3>
          <p>支持 获取/修改 HTTP/HTTPS/WS/WSS/TCP/TLS-TCP/UDP 发送及返回数据</p>
          <p>支持 对 HTTP/HTTPS/WS/WSS 指定连接使用指定代理</p>
          <p>支持 对 HTTP/HTTPS/WS/WSS/TCP/TLS-TCP 链接重定向</p>
          <p>支持 gzip, deflate, br 解码</p>
          <p>支持 WS/WSS/TCP/TLS-TCP/UDP 主动发送数据</p>
      </div>
      <header id="header">
          <a class="logo"> Sunny服务器套件 <strong>（完整免费开源-功能介绍）QQ群:616787804</strong>
          </a>
      </header>
      <div class="content">
          <p>Suuny 服务器套件是基于Golang Gin 移植而来 拥有跨平台的特性</p>
          <p>Go 性能极其地快。其性能与 C 或 C++ 相似。 Go 一般比 Python 要快 30 倍左右</p>
          <p>Go最大的特色，天生的支持并发，可以充分的利用多核</p>
          <p>将Golang Gin 框架移植到易语言，乃至其他语言，都是不错的选择。</p>
          <h3>SDK功能</h3>
          <p>[Myqsl] [TCP/TLS-TCP服务端] [TCP/TLS-TCP客户端] [WS/WSS客户端]</p>
          <p>[存取键值表] [队列] [GoLock] [Websocket] [Session] [Redis] [Sqlite3]</p>
          <p>[日志功能] [创建生成二维码] [创建生成语音验证码] [创建生成图形中文验证码]</p>
          <p>[创建生成图形文本验证码] [创建生成图形算数验证码]</p>
      </div>
  </div>
  `;

document.getElementById('main').innerHTML = _main_Pages;

function sendGetRequest(url, callback) {
  try {
    var xhr = null;
    if (window.XMLHttpRequest) {
      xhr = new XMLHttpRequest();
    } else {  // 兼容 IE6-
      xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          callback(200, xhr.responseText);
        } else {
          callback(xhr.status, "");
        }
      }
    };
    xhr.open("GET", url, true);
    xhr.send();
  } catch (e) {
    callback(0, "");
  }
}
function replaceAll(str, searchChar, replaceChar) {
  return str.split(searchChar).join(replaceChar);
}
function NewErrorText(str, i, y) {
  if (i == undefined) {
    i = 13;
  }
  if (y == undefined) {
    y = 4;
  }
  s = "";
  for (let n = 0; n < y.length; i++) {
    s += "</br>";
  }
  s += "<h3>";
  for (let n = 0; n < i.length; i++) {
    s += "&nbsp;";
  }
  s += "网络请求失败，状态码：" + + str + "</h3>";
  return s;
}
function formatString(format, ...params) {
  return format.replace(/%s/g, function () {
    return params.shift();
  });
}
function Home() {
  //点击菜单->首页
  Loading();
  document.getElementById('main').innerHTML = _main_Pages;
}
function isCSSLoaded(cssPath) {
  var links = document.getElementsByTagName("link");
  for (var i = 0; i < links.length; i++) {
    if (links[i].href.indexOf(cssPath) != -1) {
      return true;
    }
  }
  return false;
}
function isScriptLoaded(jsPath) {
  var links = document.getElementsByTagName("script");
  for (var i = 0; i < links.length; i++) {
    if (links[i].src.indexOf(jsPath) != -1) {
      return true;
    }
  }
  return false;
}
async function loadCSS(cssPath) {
  return new Promise((resolve, reject) => {
    if (isCSSLoaded(cssPath)) {
      resolve();
    } else {
      var cssElement = document.createElement("link");
      cssElement.setAttribute("rel", "stylesheet");
      cssElement.setAttribute("type", "text/css");
      cssElement.setAttribute("href", cssPath);
      cssElement.onload = () => {
        resolve();
      };
      cssElement.onerror = () => {
        reject(new Error(`Load ${cssPath} failed.`));
      };
      var head = document.getElementsByTagName("head")[0];
      head.appendChild(cssElement);
    }
  });
}

async function loadScript(jsPath) {
  return new Promise((resolve, reject) => {
    if (isScriptLoaded(jsPath)) {
      resolve();
    } else {
      var script = document.createElement("script");
      script.type = "text/javascript";
      script.src = jsPath;
      script.onload = () => {
        resolve();
      };
      script.onerror = () => {
        reject(new Error(`Load ${jsPath} failed.`));
      };
      document.head.appendChild(script);
    }
  });
}

function SunntNet() {
  //点击菜单->Sunny网络中间件
  Loading();
  sendGetRequest("./SunnyNet/version.txt", function (code, result) {
    if (code != 200) {
      document.getElementById('main').innerHTML = NewErrorText(code);
      return;
    }
    let VersionList = replaceAll(result, "\r", "").split("\n");
    let template = "";
    for (let i = 0; i < VersionList.length; i++) {
      let List = VersionList[i].split("----");
      if (List.length != 3) {
        continue;
      }
      let Download = List[1].split("|");
      if (Download.length != 2) {
        continue;
      }
      let UpdateInfo = List[2].split("|");
      if (template == "") {
        template = "<div class=\"inner\"><header id=\"headerH\"><a class=\"logo\"><strong>Sunny网络中间件</strong> 版本 " + List[0] + "</a></header><div class=\"content\"><h3></h3><p>下载地址：<a  target=\"_blank\" href=\"" + Download[0] + "\">" + Download[0] + "</a></p><p>下载密码：" + Download[1] + "</p><p><a target=\"_blank\" href=\"https://github.com/qtgolang/SunnyNet\">GitHub 地址</a> ->> 可以帮我点点小星星</p><p><a target=\"_blank\" href=\"https://gitee.com/qtr/SunnyNet\">Gitee 地址</a> ->> 可以帮我点点小星星</p><h3 style=\"color: red;\">更新说明</h3>";
        for (let n = 0; n < UpdateInfo.length; n++) {
          template += "<p>" + UpdateInfo[n] + "</p>";
        }
        template += "</div><header id=\"header\"><a class=\"logo\"><strong>历史版本下载</strong></a></header><section><div class=\"features\">";
        continue;
      }
      template += "<article><div class=\"content\"><h3>版本 " + List[0] + "</h3><p>下载地址：<a target=\"_blank\" href=\"" + Download[0] + "\">" + Download[0] + "</a></p><p>下载密码：" + Download[1] + "</p><h3 style=\"color: red;\">更新说明</h3>";
      for (let n = 0; n < UpdateInfo.length; n++) {
        template += "<p>" + UpdateInfo[n] + "</p>";
      }
      template += "</div></article>";
    }
    template += "</div></section></div>";
    document.getElementById('main').innerHTML = template;
  });
}

function SunnyServerDocCode() {
  //点击菜单->Sunny服务器套件
  Loading();
  sendGetRequest("./SunnyServer/code.txt", function (code, result) {
    if (code != 200) {
      document.getElementById('main').innerHTML = NewErrorText(code);
      return;
    }
    let VersionList = replaceAll(result, "\r", "").split("\n");
    let template = "";
    for (let i = 0; i < VersionList.length; i++) {
      let List = VersionList[i].split("----");
      if (List.length != 2) {
        continue;
      } 
      if (template == "") {
        template = "<div class=\"inner\"><header id=\"headerH\"><a class=\"logo\"><strong>示例代码下载</strong></a></header><section><div class=\"features\">";
        continue;
      }
      template += "<article><div class=\"content\"><p>下载地址：<a target=\"_blank\" href=\"" + List[1] + "\">" +List[0] + "</a></p>";
     
      template += "</div></article>";
    }
    template += "</div></section></div>";
    document.getElementById('main').innerHTML = template;
  });
}
function SunnyServer() {
  //点击菜单->Sunny服务器套件
  Loading();
  sendGetRequest("./SunnyServer/version.txt", function (code, result) {
    if (code != 200) {
      document.getElementById('main').innerHTML = NewErrorText(code);
      return;
    }
    let VersionList = replaceAll(result, "\r", "").split("\n");
    let template = "";
    for (let i = 0; i < VersionList.length; i++) {
      let List = VersionList[i].split("----");
      if (List.length != 3) {
        continue;
      }
      let Download = List[1].split("|");
      if (Download.length != 2) {
        continue;
      }
      let UpdateInfo = List[2].split("|");
      if (template == "") {
        template = "<div class=\"inner\"><header id=\"headerH\"><a class=\"logo\"><strong>Sunny服务器套件</strong> 版本 " + List[0] + "</a></header><div class=\"content\"><h3></h3><p>下载地址：<a  target=\"_blank\" href=\"" + Download[0] + "\">" + Download[0] + "</a></p><p>下载密码：" + Download[1] + "</p><h3 style=\"color: red;\">更新说明</h3>";
        for (let n = 0; n < UpdateInfo.length; n++) {
          template += "<p>" + UpdateInfo[n] + "</p>";
        }
        template += "</div><header id=\"header\"><a class=\"logo\"><strong>历史版本下载</strong></a></header><section><div class=\"features\">";
        continue;
      }
      template += "<article><div class=\"content\"><h3>版本 " + List[0] + "</h3><p>下载地址：<a target=\"_blank\" href=\"" + Download[0] + "\">" + Download[0] + "</a></p><p>下载密码：" + Download[1] + "</p><h3 style=\"color: red;\">更新说明</h3>";
      for (let n = 0; n < UpdateInfo.length; n++) {
        template += "<p>" + UpdateInfo[n] + "</p>";
      }
      template += "</div></article>";
    }
    template += "</div></section></div>";
    document.getElementById('main').innerHTML = template;
  });
}
function SunntNetHook() {
  //点击菜单->Sunny网络中间件-抓包工具
  Loading();
  sendGetRequest("./SunnyNet/tools.txt", function (code, result) {
    if (code != 200) {
      document.getElementById('main').innerHTML = NewErrorText(code);
      return;
    }
    let VersionList = replaceAll(result, "\r", "").split("\n");
    let template = "";
    for (let i = 0; i < VersionList.length; i++) {
      let List = VersionList[i].split("----");
      if (List.length != 3) {
        continue;
      }
      let UpdateInfo = List[2].split("|");
      if (template == "") {
        template = "<div class=\"inner\"><header id=\"headerH\"><a class=\"logo\"><strong>Sunny网络中间件 抓包工具</strong> 版本 " + List[0] + "</a></header><div class=\"content\"><h3></h3><p>下载地址：<a target=\"_blank\" href=\"" + List[1] + "\">" + List[1] + "</a></p><h3 style=\"color: red;\">更新说明</h3>";
        for (let n = 0; n < UpdateInfo.length; n++) {
          template += "<p>" + UpdateInfo[n] + "</p>";
        }
        template += "</div><header id=\"header\"><a class=\"logo\"><strong>历史版本下载</strong></a></header><section><div class=\"features\">";
        continue;
      }
      template += "<article><div class=\"content\"><h3>版本 " + List[0] + "</h3><p>下载地址：<a target=\"_blank\" href=\"" + List[1] + "\">" + List[1] + "</a></p><h3 style=\"color: red;\">更新说明</h3>";
      for (let n = 0; n < UpdateInfo.length; n++) {
        template += "<p>" + UpdateInfo[n] + "</p>";
      }
      template += "</div></article>";
    }
    template += "</div></section></div>";
    document.getElementById('main').innerHTML = template;
  });
}
function ds() {
  //点击菜单->打赏
  var template = "<div class=\"inner\"><header id=\"headerH\"><a class=\"logo\"><strong>Sunny网络中间件(一群)：751406884</strong></a></header><header id=\"header\"><a class=\"logo\"><strong>Sunny网络中间件(二群)：545120699</strong></a></header><header id=\"header\"><a class=\"logo\"><strong>Sunny网络中间件(三群)：170902713</strong></a></header><header id=\"header\"><a class=\"logo\" href='https://pd.qq.com/s/cgzgyjj6u' ><strong> 点击加入《QQ频道》</strong></a></header>";
  template += "<header id=\"header\"><a class=\"logo\"><strong>Sunny服务器套件 - QQ群：616787804</strong></a></header>";
  template += "<img src=\"web/assets/img/zz.jpg\"></img>";
  template += "</div>";
  document.getElementById('main').innerHTML = template;
}
function Loading() {
  var template = "<div class=\"inner\"><header id=\"headerH\"><a class=\"logo\"><strong>加载中....</strong></a></header></div>";
  document.getElementById('main').innerHTML = template;
}

function Loaddocument(path, file,name) {
  if (file == "#" || file == "") {
    return;
  }
  setTimeout(() => {
    Loading();
  }, 0);
  setTimeout(() => {
    documentLoad(path, file,name)
  }, 0);
}
async function documentLoad(path, file,name) {
  if (file == "CertInstallDocument.html") {
    await loadCSS("web/assets/css/cer.css");
  } else {
    await loadScript("web/eHandle.js");
    await loadScript("web/ecode.js");
    await loadCSS("web/ecode.css");
  }
  sendGetRequest(path + file, async function (code, result) {
    if (code != 200) {
      document.getElementById('main').innerHTML = NewErrorText(code);
      return;
    }
    if (result.substring(0, 20).replace(/\s/g, '').startsWith(".版本")) {
      E_Handle_Code(name,result);
    } else {
      var template = "<div class=\"inner\"><div class=\"mycerClass\">" + result + "</div></div>";
      document.getElementById('main').innerHTML = template;
    }
    window.scrollTo(0, 0);
  });
}
sendGetRequest("./SunnyNet/document/list.txt", function (code, result) {
  if (code != 200) {
    document.getElementById('SunnyNetDoc').innerHTML = NewErrorText(code, 2, 0);
    return;
  }
  let VersionList = replaceAll(result, "\r", "").split("\n");
  let template = "";
  let tlist = [];
  for (let i = 0; i < VersionList.length; i++) {
    let List = replaceAll(VersionList[i], "\r", "").split("----");
    if (List.length != 3) {
      continue;
    }
    if (List[0]=="常见问题"){
      template += `<li><a href="javascript:Loaddocument('./SunnyNet/document/','` + List[2] + `','`+List[1]+`')"><h style="color: red;">` + List[0] + `</h></a></li>`;
    }else{
      template += `<li><a href="javascript:Loaddocument('./SunnyNet/document/','` + List[2] + `','`+List[1]+`')">` + List[0] + `</a></li>`;
    }
  
  }
  document.getElementById('SunnyNetDoc').innerHTML = template;
});
sendGetRequest("./SunnyServer/document/list.txt", function (code, result) {
  if (code != 200) {
    document.getElementById('SunnyServerDoc').innerHTML = NewErrorText(code, 2, 0);
    return;
  }
  let VersionList = replaceAll(result, "\r", "").split("\n");
  let template = "";
  let tlist = [];
  for (let i = 0; i < VersionList.length; i++) {
    let List = replaceAll(VersionList[i], "\r", "").split("----");
    if (List.length != 3) {
      continue;
    }
    template += `<li><a href="javascript:Loaddocument('./SunnyServer/document/','` + List[2] + `','`+List[1]+`')">` + List[0] + `</a></li>`;
  }
  document.getElementById('SunnyServerDoc').innerHTML = template;
});
