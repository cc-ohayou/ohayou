
	 var index_tab;
	layui.use('layer', function(){
   var layer= layui.layer;
   //为了layer.ext.js加载完毕再执行
 

  layer.msg('hello', {
 // icon: 1,
  time: 2000 //2秒关闭（如果不配置，默认是3秒）
}, function(){
  //do something
    // confirm();
    //swindow();
   //tab();
   //$("#layer-my-photos").show();
   //myphotos();
   //showphotos1();
	  }); 
	  
	  function confirm(){
	  	  layer.confirm('是否收藏该网页？', function(index){
  //do something
 
  layer.close(index);
  
  }); 
	  	
	  }
	  

//table示例
 function tab(){
 index_tab= layer.tab({
  area: ['600px', '300px'],
  tab: [{
    title: 'TAB1', 
    content: '内容1'
  }, {
    title: 'TAB2', 
    content: '内容2'
  }, {
    title: 'TAB3', 
    content: '内容3'
  }]
});  
 }
 
 layui.upload({
  url: '上传接口url'
  ,success: function(res){
    console.log(res); //上传成功返回值，必须为json格式
  }
});  
layui.use('upload', function(){
  layui.upload(options);
});
  


});  
  
  function showphotos1(){
  	 layer.photos({
    photos:'#layer-my-photos'
    //,shift: 5 //0-6的选择，指定弹出图片动画类型，默认随机
  });
  }
  
	  

	function smallwindow(){
	  	layer.close(index_tab);
	  	var index= layer.open({
  type: 1, //page层
  area: ['500px', '300px'],
  title: '收藏',
  shade: 0.5, //遮罩透明度
  moveType: 1, //拖拽风格，0是默认，1是传统拖动
  shift: 5, //0-6的动画形式，-1不开启
  content: '<input type=text style="width:200px;margin-left:30px;" placeholder="输入要收藏的网址吧">'//'<div style="padding:50px;">这是一个非常普通的页面层（type:1），传入了自定义的html</div>'
 // 值得注意的是layer/内容显示的颜色是跟body的color一致的 	
	  });
	  layer.msg('haha--', {
 // icon: 1,
  time: 2000 //2秒关闭（如果不配置，默认是3秒）
}, function(){
	  layer.close(index);
	 
	  });
	  }
	 
	 var headpath='img/head/h2.jpg';
	 function myphotos(){
	  	var index= layer.open({
  type: 1, //page层
  area: ['900px', '750px'],
  title: 'momo的相册',
  shade: 0.5, //遮罩透明度
  moveType: 1, //拖拽风格，0是默认，1是传统拖动
  shift: 5, //0-6的动画形式，-1不开启
  content:'<div id="mo-pho00"><div id="layer-my-photos" style="position:absolute;width:605px;border:2px dotted #DFCDA0;" class="layer-photos-demo"></div><div style="position:absolute;right:45px;top:10px;width:105px;border:2px dashed #DFCDA0;" id="momo-pho-head"></div> </div>'
  //'<div style="padding:50px;">这是一个非常普通的页面层（type:1），传入了自定义的html</div>'
 // 值得注意的是layer/内容显示的颜色是跟body的color一致的 	
	  });
/*	  $('#mo-pho00').append('<input type="file" id="uphead" value="upload">');
 * 
*/
	   $('#layer-my-photos').append('<img /*style="height:400px;width:600px;" */ layer-src="../img/momo_exam.jpg" src="img/momo_exam.jpg" alt="saber" >');

$('#layer-my-photos').append('<img  layer-src="../img/momo2.jpg" style="height:400px;width:600px;" src="img/momo2.jpg" alt="天依">');
	   $('#layer-my-photos').append('<img  /*style="height:1000px;width:600px;"*/ layer-src="img/ty112.jpg" src="img/ty112.jpg" alt="shlm" >');
	  $('#momo-pho-head').append('<img src="img/momo_head.jpg" id="head_img" style="margin:2px 2px 2px 2px;">');
	  showphotos1();
	 }
 // setTimeout('smallwindow()',4000);//setTimeOut这个函数只能钓到暴露在最外层的方法
	
	
	  		//layer.alert('allala');
	  		$(function(){
	  			
	  			 $('#momo-photo001').click(function(){
	 	         myphotos();
	 	
	             });
	             $("#momo-collec-div").mousemove(function(){
	
	              
	              $("#momo-collec-div").css("height","auto");
	              $('#collections').css("display","block");
	              $('#collections').children().css("display","block");
	               $('#momo-collec-div').css("background-color","#227097");
	         
                });
             $("#momo-collec-div").mouseout(function(){
 $('#collections').css("display","none");	
 $("#momo-collec-div").css("height","40px");//不能省略 也不能超出header的高度范围
	         $("#momo-collec-div").css("background","transparent");
               });	
	 
	  		});
	
	
function myrefresh()
{
       window.location.reload();
}




//setTimeout('myrefresh()',10000); //指定1秒刷新一次	
