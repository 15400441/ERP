<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
<script type="text/javascript">
	$(function(){
		//全选
		$("#all").click(function(){
			//点击后，下面//name=resUuids组件状态切换成全选按钮的状态
			//$("[name=resUuids]").attr("checked","checked");
			
			//获取全选按钮的状态
			//js语法中：false,FALSE,"false","FALSE"表示false，其他全为true
			//alert($(this).attr("checked"));
			$("[name=resUuids]").attr("checked",$(this).attr("checked") == "checked");
		});
		//反选
		$("#reverse").click(function(){
			//需求：点击后，要求name=resUuids组件的状态进行反向切换，选中->未选中，未选中->选中，
			//$("[name=resUuids]").attr("checked",!($("[name=resUuids]").attr("checked") == "checked"));
			//$("[name=resUuids]")表示属性名name的值是resUuids的所有组件
			//$("[name=resUuids]").attr("???"),如果选择的内容为一个，则直接出值，如果选择的内容为多个，则出的值为第一个组件的值
			//alert($("[name=resUuids]").attr("checked"));
			//需求：每个组件应该获取自己的状态,切换，换状态
			//对每个组件单独执行对应的操作
			$("[name=resUuids]").each(function(){
				//alert($(this).attr("checked"));
				$(this).attr("checked",!($(this).attr("checked") == "checked"));
			});
			checkAll();
		});
		//绑定选中组件
		$("[name=resUuids]").click(function(){
			//需求：点击以后，获取所有组件的状态，如果大家都选中，则全选选中，否则全选取消
			checkAll();
		});
		function checkAll(){
			var flag = true;
			$("[name=resUuids]").each(function(){
				var flags = $(this).attr("checked") == "checked";
				flag = flag && flags;
			});
			$("#all").attr("checked",flag);
		}
	});
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">角色管理</span>
		</div>
	</div>
	<div class="content-text">
		<div class="square-order">
			<s:form action="role_save" method="post">
			<s:hidden name="rm.uuid"/>
  			<div style="border:1px solid #cecece;">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				  <tr bgcolor="#FFFFFF">
				    <td>&nbsp;</td>
				  </tr>
				</table>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">角色名称</td>
				      <td width="32%">
				      	<s:textfield name="rm.name" size="25"/>
				      </td>
				      <td width="18%" align="center">角色编码</td>
				      <td width="32%">
				      	<s:textfield name="rm.code" size="25"/>
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td colspan="4">&nbsp;</td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">资源名称</td>
				      <td width="82%" colspan="3">
				      	<input type="checkbox" id="all">全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      	<input type="checkbox" id="reverse">反选
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">&nbsp;</td>
				      <td width="82%" colspan="3">
				      	<s:checkboxlist name="resUuids" list="resList" listKey="uuid" listValue="name"></s:checkboxlist>
				      </td>
				    </tr>
				     <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">菜单名称</td>
				      <td width="82%" colspan="3">
				      	<input type="checkbox" id="all2">全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      	<input type="checkbox" id="reverse2">反选
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">&nbsp;</td>
				      <td width="82%" colspan="3">
				      	<s:checkboxlist name="menuUuids" list="menuList" listKey="uuid" listValue="name"></s:checkboxlist>
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
					    	<a href="javascript:document.forms[0].submit()"><img src="images/order_tuo.gif" border="0" /></a>
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
