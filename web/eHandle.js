
function E_Handle_Code(name,resultCode) { 
    var codes= Ecode.create();//创建
    var template = `<div class="inner"><div ><div class="ecode" id="test" desc="`+name+`" style="float: right;clear: none;width: 100%;margin: 0px;height: 102%"></div></div></div>`;
    document.getElementById('main').innerHTML =template ;
    EcodeSetCode(document.getElementById('test'),resultCode);
    codes.trans();//绘制
}
