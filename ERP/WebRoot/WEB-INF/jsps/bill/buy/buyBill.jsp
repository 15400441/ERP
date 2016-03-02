<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
<script type="text/javascript">
/*
	$(function() {
		$("#query").click(function() {
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
		$(".ajaxMsg").live("click",function(){
			$(".ajaxMsg").empty();
		});
		$(".info").click(function(){
			var jsonParam = {"bqm.goodsUuid":$(this).attr("value")};
			jsonParam["bqm.time"]= $("[name='bqm.time']").val();
			jsonParam["bqm.time2"]= $("[name='bqm.time2']").val();
			jsonParam["bqm.type"]= $("[name='bqm.type']").val();
			$tt = $(this).parent().parent();
				//将制定标记tr删除
				$('.ajaxMsg').empty();
				
				//每个tr对象都带有一个class="ajaxMsg",用于后期操作删除标记
				
				//动态添加一个tr行,用于做标题栏
				//创建tr组件
				
				var $trHead = $("<tr align='center' class='ajaxMsg' style='background:url(images/table_bg.gif) repeat-x;'></tr>");
				var $td1 = $("<td height='30'>订单号</td>");
				$trHead.append($td1);
				var $td2 = $("<td>订单时间</td>");
				$trHead.append($td2);
				var $td3 = $("<td>数量</td>");
				$trHead.append($td3);
				var $td4 = $("<td>单价</td>");
				$trHead.append($td4);
				var $td5 = $("<td>合计</td>");
				$trHead.append($td5);
				$tt.after($trHead);
				$tt=$trHead;
				
				//--------------------------------------------------------------------------
				var sum = 0;
				for(i = 0;i<3;i++){
					var $tr = $("<tr align='center' class='ajaxMsg'></tr>");
					//共计5列
					var $td1 = $("<td height='30'>1238987412</td>");
					$tr.append($td1);
					var $td2 = $("<td>2014-01-01</td>");
					$tr.append($td2);
					var $td3 = $("<td>"+i+"</td>");
					$tr.append($td3);
					var $td4 = $("<td align='right'>100.00&nbsp;元</td>");
					$tr.append($td4);
					var $td5 = $("<td align='right'>100.00&nbsp;元</td>");
					$tr.append($td5);
					
					$tt.after($tr);
					$tt=$tr;
					sum = sum + 100;
				}
				//--------------------------------------------------------------------------
				
				
				var $trFoot = $("<tr align='center' class='ajaxMsg'></tr>");
				var $td1 = $("<td align='right' colspan='4' height='30'>总计：</td>");
				$trFoot.append($td1);
				var $td2 = $("<td align='right'>"+intToFloat(sum)+"&nbsp;元</td>");
				$trFoot.append($td2);
				$tt.after($trFoot);
				$tt=$trHead;
				
		});
		function intToFloat(val){
			return new Number(val).toFixed(2);
		}
		
	});
*/
	$(function(){
		$("[commitFlag='true']").change(function(){
			$("form:first").submit();
		});
		$("#query").click(function() {
			$("form:first").submit();
		});
		$(".info").click(function(){
			var $nowTr = $(this).parent().parent();
			
			//需求：查询指定商品的明细信息报表
			//获取数据，动态行展示
			var gmUuid = $(this).attr("gm");
			var type = $("[name='bqm.type']").val();
			var supplierUuid = $("[name='bqm.supplierUuid']").val();
			var jsonParam = {};
			jsonParam["bqm.goodsUuid"] = gmUuid;
			jsonParam["bqm.type"] = type;
			jsonParam["bqm.supplierUuid"] = supplierUuid;
			$.post("bill_ajaxGetBillDetail.action",jsonParam,function(data){
				$(".ajaxMsg").remove();
				//data中包含 5个内容  其中三个是直接包含的属性，2个是包含在另一个对象中
				$headTr = $('<tr align="center" style="background:url(images/table_bg.gif) repeat-x;" class="ajaxMsg"><td height="30">订单号</td><td>订单时间</td><td>数量</td><td>单价</td><td>合计</td></tr>');
				$nowTr.after($headTr);
				$nowTr = $headTr; 
				
				//数据区
				var sum = 0.0;
				var odmList = data;
				for(var i = 0 ;i<odmList.length;i++){
					var odm = odmList[i];		//三个属性和一个对象(om)
					var om = odm.om;			//两个属性
					$dataTr = $('<tr align="center" class="ajaxMsg"></tr>');
					
					$td1 = $('<td height="30">'+om.orderNum+'</td>');
					$dataTr.append($td1);
					
					$td2 = $('<td>'+om.createTimeView+'</td>');
					$dataTr.append($td2);
					
					$td3 = $('<td>'+odm.num+'</td>');
					$dataTr.append($td3);
					
					$td4 = $('<td align="right">'+odm.priceView+' 元</td>');
					$dataTr.append($td4);
					
					$td5 = $('<td align="right">'+odm.totalPriceView+' 元</td>');
					$dataTr.append($td5);
					
					$nowTr.after($dataTr);
					$nowTr = $dataTr; 
					
					sum += odm.totalPriceView*1;
				}
				
				$tailTr = $('<tr align="center" class="ajaxMsg"><td height="30" align="right" colspan="4">总计：</td><td align="right">'+intToFloat(sum)+' 元</td></tr>');
				$nowTr.after($tailTr);
			});
		});
		$(".ajaxMsg").live("click",function(){
			$(".ajaxMsg").remove();	
		});
		
		function intToFloat(val){
			return new Number(val).toFixed(2);
		}
	});
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">进货报表</span>
		</div>
	</div>
	<div class="content-text">
		<s:form action="bill_buyBill" method="post">
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td width="70" height="30">报表类别:</td>
						<td width="140">
							<input type="radio" name="all" checked="checked">商品名称
						</td>
						<td width="70">订单类别:</td>
						<td width="190">
							<s:select commitFlag="true" name="bqm.type" list="@cn.itcast.erp.invoice.order.vo.OrderModel@buyTypeMap" headerKey="-1" headerValue="---请-选-择---"></s:select>
						</td>
						<td width="70">开始日期:</td>
						<td width="190">
							<input type="text" size="18" onfocus="c.showMoreDay=false;c.show(this);" />
						<td ><a id="query"> <img
								src="images/can_b_01.gif" border="0" /> </a></td>
					</tr>
					<tr>
						<td height="30">&nbsp;</td>
						<td>
							<input type="radio" name="all">销售人员
						</td>
						<td>厂商名称:</td>
						<td>
							<s:select commitFlag="true" name="bqm.supplierUuid" cssClass="kuan" list="supplierList" listKey="uuid" listValue="name" headerKey="-1" headerValue="---请-选-择---"></s:select>
						</td>
						<td>结束日期:</td>
						<td width="190">
							<input type="text" size="18" onfocus="c.showMoreDay=false;c.show(this);" />
						<td>
							<a href="bill_downloadExcelBill.action?bqm.type=${param['bqm.type']}&bqm.supplierUuid=${param['bqm.supplierUuid']}">
								<img src="images/can_b_03.gif" border="0" />
							</a>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<table width="70%" border="1" cellpadding="0" cellspacing="0" style="float:left;">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td colspan="2" width="49%" height="30">商品名称</td>
						<td colspan="2" width="28%">总数量</td>
						<td width="23%">详情</td>
					</tr>
					<s:iterator value="billList" var="obj">
						<tr align="center" bgcolor="#FFFFFF">
							<td colspan="2" width="30%" height="30">${obj[0].name}</td>
							<td colspan="2">${obj[1]}</td>
							<td>
								<a href="javascript:void(0)" class="xiu info" gm="${obj[0].uuid}">
									详情
								</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<div style="float:right;"> 
					<a href="bill_getPieBill.action?bqm.type=${param['bqm.type']}&bqm.supplierUuid=${param['bqm.supplierUuid']}">
						<img id="pei" src="bill_getPieBill.action?bqm.type=${param['bqm.type']}&bqm.supplierUuid=${param['bqm.supplierUuid']}" width="228px" height="180px">
					</a>
				</div>
			</div>
		</s:form>
	</div>
	<div class="content-bbg"></div>
</div>
