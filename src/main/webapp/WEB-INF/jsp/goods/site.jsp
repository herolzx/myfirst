<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<base href="${ctx }/">
<meta charset="UTF-8">
<title>仓库管理系统</title>

<link rel="stylesheet" type="text/css" href="assets/site/list.css">
<script type="text/javascript" language="javascript"
	src="assets/public/lib/jquery/jquery-3.6.0.min.js"></script>

<!-- 引入layer弹出 -->
<script type="text/javascript" language="javascript"
	src="assets/public/lib/layer/layer.js"></script>
<script type="text/javascript" language="javascript"
	src="assets/public/lib/laydate/laydate.js"></script>

<script type="text/javascript">
	var pageNo = "${pageNo}";//当前页
	var pages = "${pages}";//总页数
	var pageSize = "${pi.pageSize}";//一页显示多少行数据
	var error = "${error}";//页面错误信息
	var ctx = "${ctx}";//应用上下文
	var size = "${sites.size()}";
</script>

<script type="text/javascript" language="javascript"
	src="assets/site/list.js"></script>


</head>
<body>
	<div class="container">
	<h1 align="center" style="margin: 0">京区大洋百货仓库</h1>
	
	<!--表单区  -->
	<div class="search-form"  >
		<form action="ware/list" method="post">
			<fieldset>
				<legend align="center">查询区</legend>
				<div>
					<label for="id">入库号：</label><input type="text" name="id" id="id"
						value="${ssb.id }">
				</div>
				<div>
					<label for="goodsId">商品编号：</label><input type="text" name="goodsId"
						id="goodsId" value="${ssb.goodsId }">
				</div>
				<div>
					<label for="names">联系人：</label><input type="text" name="names"
						id="names" value="${ssb.names }">
				</div>
				<div>
					<label for="phone">联系电话：</label><input type="text" name="phone"
						id="phone" value="${ssb.phone }">
				</div>
				<div>
					<label for="startRange">存储日期：</label><input type="text"
						name="startRange" id="startRange" autocomplete="off"
						value="${ssb.startRange }">
				</div>

			</fieldset>

			<!-- 页码 -->
			<input type="hidden" name="pageNo">
			<!--  每页显示多少条数据-->
			<input type="hidden" name="pageSize" value="${pi.pageSize }">
		</form>
	</div>
		<!--  按钮操作区-->
		<div class="op">
			<ul>
				<li><a class="" href="ware/list">首页</a></li>
				<li><a class="add-btn" href="javascript:void(0)">添加</a></li>
				<li><a class="edit-btn" href="javascript:void(0)">修改</a></li>
				<li><a class="search-btn" href="javascript:void(0)">查询</a></li>
				<!-- <li><a href="javascript:void(0)">重置</a></li> -->
				<!-- <li><a id="del-btn" href="javascript:void(0)">删除</a></li> -->
				<li><a class="ajax-del-btn" href="javascript:void(0)"
					style="background-color: #FF6600;">删除</a></li>
			</ul>
		</div>

<!-- 删除表单 -->
		<div class="delete-form">
			<form action="ware/delete" method="post">
				<input type="hidden" name="deleteIds" />

			</form>
		</div>

		<!--数据显示区  -->
		<div class="data">
			<table id="tbl" align="center">
				<thead>
					<tr style="height: 20px">
						<th><input id="checkall" type="checkbox"></th>
						<th>入库号</th>
						<th>商品编号</th>
						<th>商品名称</th>
						<th>数量/箱</th>
						<th>入库时间</th>
						<th>出库时间</th>
						<th>联系人</th>
						<th>联系电话</th>
						<th>是否预约</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${sites }" var="sit">
						<tr>
							<td><input type="checkbox"></td>
							<td>${sit.id }</td>
							<td>${sit.goodsId }</td>
							<td>${sit.name }</td>
							<td>${sit.numb }</td>
							<td>${sit.start }</td>
							<td>${sit.end }</td>
							<td>${sit.names }</td>
							<td>${sit.phone }</td>
							<td>${sit.yy }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 页码区 -->
		<div class="paginate">
			<span class="first"> <a href="javascript:void(0)">首页</a></span> <span
				class="prev"><a href="javascript:void(0)">上一页</a></span>
			<!-- 1 2 3 4 5 区 -->
			<ul>
				<c:forEach begin="${pi.navItemStart }" end="${pi.navItemEnd }"
					var="p">

					<c:choose>
						<c:when test="${p==pageNo }">
							<li class="current"><a href="javascript:void(0)">${p }</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="javascript:void(0)">${p }</a></li>
						</c:otherwise>
					</c:choose>

				</c:forEach>
			</ul>
			<span class="next"><a href="javascript:void(0)">下一页</a></span> <span
				class="last"><a href="javascript:void(0)">尾页</a></span>
			<!-- 页面大小下拉框 -->
			<select>
				<option selected="selected" value="3">3</option>
				<option value="2">2</option>
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="40">40</option>
			</select>
		</div>
	</div>



	
</body>
</html>