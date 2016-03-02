<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function(){
		//为id=commit绑定点击事件
		$("#commit").click(function(){
			//提交form
			//$("#mainForm").submit();
			//$("form").submit();
			//$("form:eq(0)").submit();
			$("form:first").submit();
		});
		
		$("[name='dm.name']").blur(function(){
			var nameStr = $(this).val();
			if(nameStr.length > 12 || nameStr.length < 6){
				//非法
				$span = $(this).next();
				$span.html("×");
				$span.removeClass("ok");
				$span.addClass("error");
				//$span.toggleClass("error");
				//$span = $('<span style="color:red;font-weight:bold">×</span>');
				//$(this).after($span);
				
			}else{
				//合法
				$span = $(this).next();
				$span.html("√");
				$span.removeClass("error");
				$span.addClass("ok");
				//$span.toggleClass("ok");
				//$span = $('<span style="color:#4a4;font-weight:bold">√</span>');
				//$(this).after($span);
			}
		});
	});
</script>
<style>
	.ok{
		color:#4a4;
		font-weight:bold;
	}
	.error{
		color:red;
		font-weight:bold;
	}
</style>
	<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">部门管理</span>
		</div>
	</div>
	<div class="content-text">
		<div class="square-order">
			<s:form action="dep_save" method="post" id="mainForm">
			<s:hidden name="dm.uuid"/>
  			<div style="border:1px solid #cecece;">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				  <tr bgcolor="#FFFFFF">
				    <td>&nbsp;</td>
				  </tr>
				</table>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">部门名称</td>
				      <td width="32%">
				      	<s:textfield name="dm.name" size="25"/><span></span>
				      </td>
				      <td width="18%" align="center">电话</td>
				      <td width="32%">
				      	<s:textfield name="dm.tele" size="25"/>
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td colspan="4">&nbsp;</td>
				    </tr>
				</table>
				
			</div>
			<div class="order-botton">
				<div style="margin:1px auto auto 1px;">
					<table width="100%"  border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td>
					    	<a href="javascript:void(0)" id="commit"><img src="images/order_tuo.gif" border="0" /></a>
					    </td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					  </tr>
					</table>
				</div>
			</div>
			</s:form>
		</div><!--"square-order"end-->
	</div><!--"content-text"end-->
	<div class="content-bbg"><img src="images/content_bbg.jpg" /></div>
</div>
