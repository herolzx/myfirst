$(function() {
	if (error) {
		layer.alert(error);//弹出后端给的提示
	}
	//根据后台的性别 实现选中
	if (yy) {
		$(".ware-form>.sfyy :radio[value=" + yy + "]").prop("checked", true);
	}
	//用插件优化日期选择
	laydate.render({
		elem:"#start"
	});
	laydate.render({
		elem:"#end"
	});
	
});