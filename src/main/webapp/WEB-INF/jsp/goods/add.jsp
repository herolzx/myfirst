<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 上下文 -->
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>

<head>
<base href="${ctx }/">
<meta charset="UTF-8">
<title>添加学生</title>
<link rel="stylesheet" type="text/css" href="assets/site/add.css">

<script type="text/javascript" language="javascript"
	src="assets/public/lib/jquery/jquery-3.6.0.min.js"></script>

<!-- 引入layer弹出 -->
<script type="text/javascript" language="javascript"
	src="assets/public/lib/layer/layer.js"></script>
<!--引入选择日期  -->
<script type="text/javascript" language="javascript"
	src="assets/public/lib/laydate/laydate.js"></script>

<!-- 接收后端传过来的数据 -->
<script type="text/javascript">
	var error = "${error}";
	var yy = "${params['yy'][0]}";//是否预约是选择按钮，需要通过js来设置用户已经选择的值，
	//避免用户出错时再次选择
</script>
<!-- 引入自己的js -->
<script type="text/javascript" language="javascript"
	src="assets/site/add.js"></script>
</head>
<!-- 身体部分 -->
<body>
	<div class="container">
		<fieldset>
			<legend align="center">添加商品</legend>
			<form class="ware-return" action="ware/list" method="post">
				<div>
					<label></label>
					<button type="submit">返回列表</button>
				</div>
			</form>
			<form class="ware-form" action="ware/add" method="post">
				<div>
					<label for="goodsId">商品编号:</label> <input type="text"
						name="goodsId" id="goodsId" placeholder="请输入商品编号"
						value="${params['goodsId'] [0]}">
				</div>
				<div>
					<label for="name">商品名称:</label> <input type="text" name="name"
						id="name" placeholder="请输入商品名称" value="${params['name'] [0]}">
				</div>
				<div>
					<label for="numb">商品数量:</label> <input type="text" name="numb"
						id="numb" placeholder="请输入商品数量" value="${params['numb'] [0]}">
				</div>
				<div>
					<label for="start">入库日期:</label> <input type="text" name="start"
						id="start" placeholder="请选择入库日期" value="${params['start'] [0]}"
						autocomplete="off">
				</div>
				<div>
					<label for="end">出库日期:</label> <input type="text" name="end"
						id="end" placeholder="请选择出库日期" value="${params['end'] [0]}"
						autocomplete="off">
				</div>
				<div>
					<label for="names">联系人:</label> <input type="text" name="names"
						id="names" placeholder="请输入联系人" value="${params['names'] [0]}">
				</div>
				<div>
					<label for="phone">联系电话:</label> <input type="text" name="phone"
						id="phone" placeholder="请输入联系电话" value="${params['phone'] [0]}">
				</div>
				<div class="sfyy">
					<label for="yy">是否预约:</label>
					<div>
						<input type="radio" id="s" name="yy" value="是" checked="checked">
						<label for="s">是</label>
					</div>
					<div>
						<input type="radio" id="f" name="yy" value="否"> <label
							for="f">否</label>
					</div>
				</div>
				<div>
					<label></label>
					<button type="submit">提交</button>
					<button type="reset">重置</button>
				</div>
			</form>
		</fieldset>
	</div>
</body>
</html>