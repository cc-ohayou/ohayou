layui.use('layer', function(){
   var layer= layui.layer; 
});
var index_addCollect;
$(function(){
	
	$('#addCollectSign').mousemove(function(){
		
			$('#addCollectSign').css("color","#FF65DB");

	});
	

$('#addCollectSign').mouseout(function(){
		$('#addCollectSign').css("color","#FFFFFF");

});
	
	
	$('#addCollect').click (function(){
	$('#webName').val('');
	$('#webAddress').val('');
	index_addCollect=layer.open({
    type: 1, //page层
    area: ['500px', '300px'],
    title: 'momo的收藏',
    shade: 0.5, //遮罩透明度
    moveType: 1, //拖拽风格，0是默认，1是传统拖动
    shift: 4, //0-6的动画形式，-1不开启
    content:$('#addCollect-div-open')
// 值得注意的是layer/内容显示的颜色是跟body的color一致的
  });

	});
	
	//验证用户
	$('#c2').click(function(){
		var username=$('#username').val();
		var password=$('#password').val();
		if(username=='momo'&&password=='1994'){
			$('#momologin').hide();
			$('#momo-selections-div').show();
		layer.msg('欢迎亲爱的momo小主！可以查看收藏和相册了哦^_^',{time:3000})
		
		}
		else
		  layer.msg('阿勒，貌似输入了错误的用户名或密码，再想想吧',{time:1000})
		
	});
	
	

});
function addCollectSure(obj){
		
		var webName=$('#webName').val();
		var webAddress=$('#webAddress').val();
		//layer.msg(webName+",,,"+webAddress,{time:2000});
		$('#collections').append('<li><a href="'+webAddress+'" class="white" target="_blank">'+webName+'</a></li>');
        layer.alert('收藏成功la 快去momo的收藏看看吧^_^！');	
        closeAddCollectOpen();
}

function closeAddCollectOpen(){
	
	layer.close(index_addCollect);
}
function drawboard(){
	 location.href = "/filesaver/demo/index.xhtml";
}

function read_excel(){

    var filePath="C:\Users\Administrator\Desktop\collect.xlsx"; //要读取的xls
    var sheet_id=1; //读取第2个表
    var row_start=2; //从第3行开始读取
    var tempStr='';
    try{
        var oXL = new ActiveXObject("Excel.application"); //创建Excel.Application对象
    }catch(err)
    {
        alert(err);
    }
    var oWB = oXL.Workbooks.open(filePath);
    oWB.worksheets(sheet_id).select();
    var oSheet = oWB.ActiveSheet;
    var colcount=oXL.Worksheets(sheet_id).UsedRange.Cells.Rows.Count ;

    for(var i=row_start;i<=colcount;i++){
        if (typeof(oSheet.Cells(i,3).value)=='date'){ //处理第8列部分单元格内容是日期格式时的读取问题
            d= new Date(oSheet.Cells(i,3).value);
            temp_time=d.getFullYear()+"-"+(d.getMonth() + 1)+"-"+d.getDate();
        }
        else
            temp_time=$.trim(oSheet.Cells(i,7).value.toString());
        tempStr+=($.trim(oSheet.Cells(i,1).value)+" "+$.trim(oSheet.Cells(i,2).value)+" "+temp_time+"\n");
        //读取第2、4、6、8列内容
    }

    return tempStr; //返回
    oXL.Quit();
    CollectGarbage();
}



