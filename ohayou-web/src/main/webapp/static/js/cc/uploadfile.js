var layer;
var element;
var context=getContextPath();
function getContextPath(){
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result =window.location.host;
    // alert(result);
    return result;
}

layui.use(['layer', 'form'], function(){
    layer=layui.layer
        ,form = layui.form();
    // layer.msg('Hello World');


});
//注意进度条依赖 element 模块，否则无法进行正常渲染和功能性操作
layui.use('element', function(){
    var $ = layui.jquery
        ,element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
    $('.site-demo-active').on('click', function(){
        var othis = $(this), type = $(this).data('type');
        active[type] ? active[type].call(this, othis) : '';
    });
});
layui.use(['upload', 'layer'], function(){
    layui.upload({
        url: 'upload.do'
        ,before: function(input){
            layer.msg('文件上传中');
        },success:function(res){
            layer.msg(res.msg);
        }
    });



});

function uploadFile(obj){

    layer.msg("lalal");

    layui.use('upload', function() {
        layui.upload({
            url: context+'/linnai/test/upload.do'
            , before: function(input){
                layer.msg('文件上传中');
            },success: function (res) {
                layer.msg(res.msg); //上传成功返回值，必须为json格式
            }
        });
    });

}
$(function(){

//alert("hello cc");

    $(".a-upload").on("change","input[type='file']",function(){
        var filePath=$(this).val();
        if(filePath.indexOf("jpg")!=-1 || filePath.indexOf("png")!=-1){
            $(".fileerrorTip").html("").hide();
            var arr=filePath.split('\\');
            var fileName=arr[arr.length-1];
            $(".showFileName").html(fileName);
        }else{
            $(".showFileName").html("");
            $(".fileerrorTip").html("您未上传文件，或者您上传文件类型有误！").show();
            return false
        }
    })
    $(".b-upload").on("change","input[type='file']",function(){
        var filePath=$(this).val();
        if(filePath.indexOf("jpg")!=-1 || filePath.indexOf("png")!=-1||filePath.indexOf("gif")){
            $(".fileerrorTip").html("").hide();
            var arr=filePath.split('\\');
            var fileName=arr[arr.length-1];
            $(".showFileName").html(fileName);
        }else{
            $(".showFileName").html("");
            $(".fileerrorTip").html("您未上传文件，或者您上传文件类型有误！").show();
            return false
        }
    });



    $(".b-upload").on("click",function () {
        var fileSelect = document.getElementById('fileSelect');
        var formData = new FormData();
        var files=fileSelect.files;
        if(files==null||files.length==0){
            layer.msg("请至少选中一个文件oo");
        }
        for(var i=0;i<files.length;i++){
            formData.append("file",files[i],files[i].name);
            // 检查文件类型
            /*  if (!file.type.match('image.*')) {
             continue;
             }*/
        }
        //layer.msg("cc");
        $.ajax({
            url: context+'/linnai/test/uploadFiles.do',
            type: 'POST',
            cache: false,
            data:formData,
            /**
             * 必须false才会避开jQuery对 formdata 的默认处理
             * XMLHttpRequest会对 formdata 进行正确的处理
             */
            processData: false,
            /**
             *必须false才会自动加上正确的Content-Type
             */
            contentType:false,
            dataType:"json",
        }).success(function(res) {
            var files=res.fileMess;
            layer.alert(res.status);
            if(files!=null&&files.length>0) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    layer.alert(file.name + ",url:" + file.url);
                }
            }
            //layer.msg(res.msg);

        }).error(function(data) {
            layer.alert(data.status);
        });
    });




});

