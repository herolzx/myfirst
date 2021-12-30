$(function() {
	//后端是否有提示
	if (error) {
		layer.alert(error);//弹出后端给的提示
	}
	//优化日期
	laydate.render({
		elem: "#startRange",
		range: true

	});
	laydate.render({
		elem: "#end"
	});



	$(".paginate>span").click(function() {
		var me = $(this);
		if (me.is(".first")) {
			/*console.log("aaaa");*/
			$(".search-form [name=pageNo]").val(1);
		} else if (me.is(".prev")) {
			PageNo = parseInt(pageNo) - 1;
			if (isNaN(PageNo) || PageNo < 1) {
				PageNo = 1;
			}
			$(".search-form [name=pageNo]").val(PageNo);
		} else if (me.is(".next")) {
			PageNo = parseInt(pageNo) + 1;
			if (PageNo > +pages) {//注意pages是字符串
				PageNo = pages;
			}
			$(".search-form [name=pageNo]").val(PageNo);
		} else if (me.is(".last")) {

			$(".search-form [name=pageNo]").val(parseInt(pages));
		}
		//提交表单
		$(".search-form form").submit();
	});
	//数字页码点击事件
	$(".paginate>ul>li").click(function() {
		var p = $(this).children("a").text();
		$(".search-form [name=pageNo]").val(p);
		$(".search-form form").submit();
	});
	//页面大小下拉框改变事件
	$(".paginate>select").change(function() {
		var ps = $(this).val();
		$(".search-form [name=pageSize]").val(ps);
		$(".search-form form").submit();
	});
	//页面大小下拉框显示赋值
	$(".paginate>select").val(pageSize);


	//全选和取消全选：
	$("#checkall").click(function() {
		var checked = $(this).prop("checked");
		$("#tbl tr>td>:checkbox").prop("checked", checked);
	});


	//删除操作
	$("#del-btn").click(function() {
		var ids = [];
		var checked = $("#tbl tr>td>:checked").parent().next();//类数组。
		checked.each(function() {
			//表示压入选中当前内容 each表示对其中的每一个进行循环
			ids.push($(this).text());
		});



		//校验
		if (ids.length == 0) {
			layer.alert("请选择您要删除的数据");
		} else {
			layer.confirm("是否确认删除？", function(index) {
				if (index) {
					//给表单元素赋值
					//jion把一个数组里的内容用逗号拼接
					$(".delete-form [name=deleteIds]").val(ids.join(","));
					$(".delete-form form").submit();//提交表单
					//关闭对话框
					layer.close(index);
				}
			});
		}

	});
	//=============
	//============
	$(".ajax-del-btn").click(function() {
		//复选框的父元素的下一个兄弟，仅一个也就是Id
		var ids = [];
		var checked = $("#tbl tr>td>:checked").parent().next();//类数组

		/*第一种方法：
		for (var i = 0; i < checked.length; i++) {
			ids.push(checked.eq(i).text());
		}*/
		//第二种方法:
		checked.each(function() {
			//表示压入选中当前内容 each表示对其中的每一个进行循环
			ids.push($(this).text());
		});

		//console.log(ids);
		//校验
		if (ids.length == 0) {
			layer.alert("请选择您要删除的数据")
		} else {
			layer.confirm("是否确认删除？", function(index) {
				if (index) {
					//使用jquery发送ajax请求
					$.ajax({
						//请求地址：
						url: ctx + "/ware/",
						method: "post",
						//传的数据是一个对象，后端根据键名从域中取
						//后面的ids是一个数组，前面的键名省略了双引号
						data: {//data为传输的数据
							ids: ids,
						},
						//json为只有键值对的对象
						dataType: "json",
						traditional: true,
						success: function(resp) {
							//当请求成功之后的回调函数
							//resp可以自定义 代表着前端write时写进来的json对象
							if (resp.success) {
								layer.msg("成功删除" + resp.rows + "条记录");
								//方法一：刷新当前页面location.reload();
								//方法二：checked是选中的td,删除父元素tr
								checked.parent().remove();
							} else {
								//有参数就用erreo提示没有就用删除失败提示
								layer.alert(erreo || "删除失败");
							}
						}
					});
					//关闭对话框
					layer.close(index);
				}
			});
		}
	});

	//

	//添加操作
	$(".add-btn").click(function() {
		location.href = ctx + "/ware/add";//这里是get请求
	});
	//修改操作
	$(".edit-btn").click(function() {
		var ids = [];
		var checked = $("#tbl tr>td>:checked").parent().next();//类数组
		//第二种方法:
		checked.each(function() {
			//表示压入选中当前内容 each表示对其中的每一个进行循环
			ids.push($(this).text());
		});

		//console.log(ids);
		//校验
		if (ids.length == 0) {
			layer.alert("请选择您修改的信息");
		} else if (ids.length > 1) {
			layer.alert("一次只选择一条信息进行修改")
		} else {
			//var id=ids[0];
			var id = ids.pop();//取出来要修改的Id
			location.href = ctx + "/ware/edit?id=" + id;//这里是get请求 问号后面是get请求带给后端的参数
		}
	});


	//搜索按钮 点击搜索按钮的时候提交表单
	$(".search-btn").click(function() {
		$(".search-form form").submit();//提交表单
	});

});