var layer;
layui.use(['layer', 'form'], function(){
    layer=layui.layer
        ,form = layui.form();
});
$(function(){
    $("#but").on("click",function () {
        var table=$("#tableName").val();
        if(table==null||(table.replace(/(^s*)|(s*$)/g, "").length==0)){
            layer.alert("table name can't be empty!");
            return;
        }
        var tab={
            tableName:table
        };
	    var context=getContextPath();
        $.ajax({
            url: context+'/linnai/test/bnnDemo',
            type: 'post',
            cache: false,
            data:tab,
            dataType:"json",
        }).success(function(res) {
//                   layer.msg(res.result)
//                    alert(res);
            layer.alert(res.result);
        }).error(function(data) {
            alert(data.status);
        });

    });
	$("#redirect_test").on("click",function () {
       var context=getContextPath();
       $.ajax({
            url: context+'/linnai/test/mvtest',
            type: 'post',
            cache: false,
            dataType:"json",
        }).success(function(res) {
        //    layer.alert(res.result);
        }).error(function(data) {
           var d=data;
           (JSON.stringify(data.responseText));
           // var url=data.Object("cc");
           // window.location.href=url;
        });


	});

    var backGroundUrl="static/images/bg/zddoubble.png";
    $(document.body).css("backgroundImage","url("+backGroundUrl+")");
    // $(document.body).css("background-color","green");

});
 //  $(document.body).on("load",function(){
		//设置账户后可根据  存储的信息返回对应的服务器url   实现背景图的个性更换  
		//根据登录人session信息获取对应的背景图文件夹路径    上传的文件 文件夹路径 采用简单的hash算法确定 
		//多来几层 高低位参与运算 把整hash均匀 参考HashMap计算方法     登录前统一使用登录背景  页面检测登录状态和cookies session一旦存在 和cookies对的上则直接自动切换背景
		//cookies(未过期)和密码对不上则不注册session信息 提示重新登录 登录成功重新生成cookies和session
		//cookies过期直接提示登录
     //   var backGroundUrl="static/images/a2.png";
     //  $(document.body).css("backgroundImage","url("+backGroundUrl+")");

        // $("body").attr("style","background:url('static/jpg/a2.png') no-repeat;width:100%;height:100%;");
   // });
function getContextPath(){
    // var pathName = window.location.href;
    // var index = pathName.substr(1).indexOf("/");
    var result = window.location.host;
    //if(index==-1){
    //  result+="cc";
    //}
    //alert(window.location.host);
    return "http://"+result;
}
function init(){
   /* var backGroundUrl="static/images/a2.png";
   $(document.body).css("backgroundImage","url("+backGroundUrl+")");
*/
    // document.body.style.backgroundColor="green";
    // document.body.style.backgroundImage="url(/"+backGroundUrl+")";
}


