
//	$('#ssi-upload2').ssi_uploader({url:'#',preview:false,allowed:['jpg','gif','txt','png','pdf']});
//有几处对ssi_uploader的调用 就会去初始化 $.fn.ssi_uploader = function (opts) {几次
    $('#ssi-upload').ssi_uploader({url:context+'/linnai/test/uploadFilesSSI',maxFileSize:6,allowed:['jpg','gif','txt','png','pdf']});
    $('#ssi-upload3').ssi_uploader({url:'#',dropZone:false,allowed:['jpg','gif','txt','png','pdf']});
