<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
<script type="text/javascript">
/*
	$(function() {
		$("#all").click(function() {
			$("[name=roles]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
		});
		$("#reverse").click(function() {
			$("[name=roles]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });
		});
		$("#supplier").change(function(){
			$.post("goodsTypeAction_getAll.action",{"gm.supplier.uuid":$(this).val()},function(data){
				$("#goodsType").empty();
				for(var i = 0;i<data.gtList.length;i++){
					var goodsType = data.gtList[i];
					var $option = $("<option value='"+goodsType.uuid+"'>"+goodsType.goodsTypeName+"</option>");	//创建option对象(jQuery格式)
					$("#goodsType").append($option);				//将option对象添加到select组件中
				}
			});
		});
	});
*/
	$(function(){
		//变更供应商
		//改变供应商后发送ajax请求，将要获取的对应供应商的商品类别信息查询出来送回页面，显示
		//1.页面组件绑定change事件（页面：完成）
		//2.发送ajax请求，传递供应商的uuid到后台(页面：参数获取完成，发送请求完成)
		//3.后台接受到参数后，根据参数查询对应的商品类别信息（Java,action:调用数据层完成数据查询)
		//4.将查询的数据返回到页面上（重点）
		//问题：java中查询的数据是java对象,页面上数据接收方js/jquery无法接收java对象数据
		//解决方案：必须找一种能够在java对象与jquery语法间进行数据共享的数据格式：json
		//4.1java中需要将数据转换成json（struts2提供了一个struts2-json-plugin.jar包，专用于完成该操作）
		//4.1.1对要返回的数据提供相应的get方法
		//4.1.2设置对应请求操作的返回result的type=json
		//4.1.3设置对应action声明所在的package继承json-default，extends="json-default"
		//数据将以json的格式返回{"abc":"def"}，get方法的方法名将成为返回数据的json属性名，get方法的返回值将作为返回数据对应属性的属性值
		//返回数据：{"abc":"def","id":123}，每个get方法将对应返回数据中的一个属性
		//无论后台返回的数据时什么格式，都将被自动转化成json数据
		//基本数据类型+封装类类型+String：值
		//类（自定义）：json数据
		//集合数据:json数组数据
		//4.2页面通过回调方法的第一个参数接收返回的数据，该数据是一个json数据
		//5.将页面接收的数据展示出来
		
		$("#supplier").change(function(){
			//获取参数
			var supplierUuid = $(this).val();
			//发送请求：
			//$.get("abc.action?aa=1");
			//$.post("abc.action",{"aa":1});
			//$.post(请求的地址url,请求的参数,请求完成后的回调方法);
			$.post("goodsType_ajaxGetBySm.action",{"supplierUuid":supplierUuid},function(data){
				ajaxCheck(data);
				//清除原始select中的所有选项
				$("#goodsType").empty();
				//data中包含有1个属性gtmList
				var gtmList = data.gtmList;
				for(var i = 0;i<gtmList.length ;i++){
					var gtm = gtmList[i];
					//将取到的数据转换成select组件中的选项
					//alert(gtm.uuid+","+gtm.name);
					$op = $('<option value="'+gtm.uuid+'">'+gtm.name+'</option>');
					//将option添加到select中
					$("#goodsType").append($op);
				}
			});
		});
	});
	function ajaxCheck(data){
		var len = data.length;
		if(len != undefined){
			top.document.location="index.jsp";
		}
	}
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">商品管理</span>
		</div>
	</div>
	<div class="content-text">
		<div class="square-order">
			<s:form action="goods_save" method="post">
			<s:hidden name="gm.uuid"/>
  			<div style="border:1px solid #cecece;">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				  <tr bgcolor="#FFFFFF">
				    <td>&nbsp;</td>
				  </tr>
				</table>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">供&nbsp;应&nbsp;商</td>
				      <td width="32%">
				      	<s:select name="gm.gtm.sm.uuid" id="supplier" list="supplierList" listKey="uuid" listValue="name" cssStyle="width:190px"></s:select>
				      </td>
				      <td width="18%"align="center">商品类别</td>
				      <td width="32%">
				     	<s:select name="gm.gtm.uuid" id="goodsType" list="gtmList" listKey="uuid" listValue="name" cssStyle="width:190px"></s:select>
					  </td>
				    </tr>
				    <tr bgcolor="#FFFFFF">
					  <td colspan="4">&nbsp;</td>
					</tr>
				    <tr  bgcolor="#FFFFFF">
				      <td align="center">商品名称</td>
				      <td>
				      	<s:textfield name="gm.name" size="25"/>
				      </td>
				      <td  align="center">产&nbsp;&nbsp;&nbsp;&nbsp;地</td>
				      <td >
				      	<s:textfield name="gm.origin" size="25"/>
				      </td>
				    </tr>
				     <tr bgcolor="#FFFFFF">
					  <td colspan="4">&nbsp;</td>
					</tr>
				    <tr  bgcolor="#FFFFFF">
				      <td height="30" align="center">生产厂家</td>
				      <td>
				      	<s:textfield name="gm.producer" size="25"/>
				      <td align="center">单&nbsp;&nbsp;&nbsp;&nbsp;位</td>
				      <td>
				      	<s:textfield name="gm.unit" size="25"/>
					  </td>
				     </tr>
				    <tr bgcolor="#FFFFFF">
					  <td colspan="4">&nbsp;</td>
					</tr>
				    <tr  bgcolor="#FFFFFF">
				      <td height="30" align="center">进货单价</td>
				      <td>
				      	<s:textfield name="gm.inPrice" size="25"/>
					  </td>
				      <td align="center">销售单价</td>
				      <td>
				      	<s:textfield name="gm.outPrice" size="25"/>
					  </td>
				    </tr>
				    <tr bgcolor="#FFFFFF">
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
