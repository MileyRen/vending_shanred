<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="<c:url value='/resources/js/channel.js'/>" type="text/javascript"></script>
<title>MachineOperater Info Page</title>
</head>
<body>
	<%@include file="../topmenu.jsp"%>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<ul class="breadcrumb">
					<li><a href="<c:url value="/home"/>">主页</a></li>
					<li><a href="<c:url value="/machine/machineInfo"/>">售货机查询列表</a></li>
					<li class="active">售货机信息</li>
				</ul>
			</div>
		</div>
		<!-- 显示内容 -->
		<div class="panel panel-default">
			<div class="panel-heading">售货机信息</div>
			<div class="panel-body">
				<table class="table" align="center">
					<tr>
						<th>铭牌号</th>
						<th>主板号</th>
						<th>是否分配</th>
						<th>售货机类型</th>
						<th>管理员</th>
						<th>售货机地址</th>
						<th>售货机组</th>
						<th>运营商</th>
						<th>操作者</th>
						<th>操作时间</th>
					</tr>
					<tr>
						<td>${machineOperater.machineInfo.machineName }</td>
						<td>${machineOperater.machineInfo.machinePannel }</td>
						<td>${machineOperater.machineAssign }</td>
						<td>${machineOperater.machineInfo.machineType.tModelName }</td>
						<td>${machineOperater.userId}</td>
						<td>${machineOperater.machineAddress }</td>
						<td>${machineOperater.groupInfo.groupName}</td>
						<td>${machineOperater.operFirmId}</td>
						<td>${machineOperater.operateId}</td>
						<td>${machineOperater.operateDate}</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				货道信息
			</div>
			<div class="panel-body">
				<table class="table">
					<tr>
						<th>货道编号</th>
						<th>存货量</th>
						<th>现存量</th>
						<th>新增量</th>
						<th>售货机名</th>
						<th>售货机主板号</th>
						<th>商品</th>
						<th>价格</th>
						<th>是否折扣</th>
						<th></th>
					</tr>
					<c:forEach items="${channelInfo}" var="ch">
						<tr>
							<td>${ch.channelNo}</td>
							<td>${ch.stockNum}</td>
							<td>${ch.stockNumNow}</td>
							<td>${ch.stockNumAdd}</td>
							<td>${ch.machineInfo.machineName}</td>
							<td>${ch.machineInfo.machinePannel}</td>
							<form class="form" id="channelWareForm">
							<td>
								<div class="form-group">
								<select name="wareId" class="form-control" style="width:150px;position:absolution">
									<option value="">---请选择商品---</option>
									<c:forEach items="${wares}" var="w">
										<option value="${w.wareId }">${w.wareName }</option>
									</c:forEach>
								</select>
							</div>
							</td>
							<td>
								<div class="form-group">
									<input type="text" name="price" class="form-control" placeholder="价格" style="width:80px;position:absolution">
								</div>
							</td>
							<td>
								<div class="form-group">
									<select name="isDiscount" class="form-control" style="width:60px;position:absolution">
										<option value="0">否</option>
										<option value="1">是</option>
									</select>
								</div>
							</td>
							<input type="hidden" name="channelId" value="${ch.channelId }">
							<input type="hidden" name="mOperaterId" value="${ch.machineOperater.mOperaterId }">
							<input type="hidden" name="userId" value="${user.userId}">
							</form>
							<td>
								<button type="button" class="btn btn-primary"
								onclick="addChannelWare()" >保存</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

<script type="text/javascript">
function addChannelWare(){
	$.ajax({
		url:'../channel/addChannelWare',
		type:"post",
		dataType:"text",
		data:$("#channelWareForm").serialize(),
		success: function(res){
			alert("");
		},
		error: function(){
			alert("获取数据失败");
		}
	});
}

</script>
</body>
</html>