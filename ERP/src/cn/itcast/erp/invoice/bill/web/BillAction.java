package cn.itcast.erp.invoice.bill.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.itcast.erp.invoice.bill.business.ebi.BillEbi;
import cn.itcast.erp.invoice.bill.vo.BillQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseAction;
import cn.itcast.erp.util.format.FormatUtil;

public class BillAction extends BaseAction{
	public BillQueryModel bqm = new BillQueryModel();

	private BillEbi billEbi;
	private SupplierEbi supplierEbi;
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public void setBillEbi(BillEbi billEbi) {
		this.billEbi = billEbi;
	}

	public String buyBill(){
		//加载供应商信息
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);
		/*
		select
			g.uuid,
			g.name,
			sum(od.num)
		from
			tbl_orderdetail od,
			tbl_goods g
		where
			g.uuid = goodsUuid
		group by
			g.uuid
		*/
		List<Object[]> billList = billEbi.getBuyBill(bqm);
		/*
		for(Object[] objs:billList){
			System.out.println(objs[0]);
			System.out.println(objs[1]);
		}
		*/
		put("billList",billList);
		return "buyBill";
	}
	
	//获取饼状报表
	public void getPieBill() throws IOException{
		//原始：服务器帮我们找到文件，IO读取内容，送出
		//需求：需要手动的读取文件内容，将其IO出去
		OutputStream os = getResponse().getOutputStream();
		//找到图片文件，将其内容写入到OS对象中
		billEbi.writePieToStream(os,bqm);
		os.flush();
		//return null;//NONE
	}
	private InputStream downloadExcelStream;
	public InputStream getDownloadExcelStream() {
		return downloadExcelStream;
	}

	private String xlsName;
	public String getXlsName() throws UnsupportedEncodingException {
		//现在程序中是UTF-8,页面接收ISO8859-1
		String name = "采购报表["+FormatUtil.formatDate(System.currentTimeMillis())+"].xls";
		return new String(name.getBytes("utf8"),"iso8859-1");
	}

	//下载Excel报表
	public String downloadExcelBill() throws Exception{
		//下载：后台action提供一个流，将数据写入到流中，通过配置，设置对应的流
		//将文件写入到流中
		downloadExcelStream = billEbi.writeExcelToStream(bqm);
		return "downloadExcelBill";
	}
	
	//----AJAX----------------------------------------
	private List<OrderDetailModel> odmList;
	public List<OrderDetailModel> getOdmList() {
		return odmList;
	}

	public String ajaxGetBillDetail(){
		//获取数据，条件在bqm中
		//订单号	订单时间	数量	单价	合计
		//om     om     odm odm odm   odm->om
		odmList = billEbi.getBillDetail(bqm);
		return "ajaxGetBillDetail";
	}
	
	
}










