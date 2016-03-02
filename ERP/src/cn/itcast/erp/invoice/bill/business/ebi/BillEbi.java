package cn.itcast.erp.invoice.bill.business.ebi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.itcast.erp.invoice.bill.vo.BillQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;

public interface BillEbi {

	public List<Object[]> getBuyBill(BillQueryModel bqm);

	public List<OrderDetailModel> getBillDetail(BillQueryModel bqm);

	public void writePieToStream(OutputStream os,BillQueryModel bqm) throws IOException;
	/**
	 * 将Excel报表文件写入流中
	 * @return 保存有报表文件的输入流对象
	 * @throws Exception
	 */
	public InputStream writeExcelToStream(BillQueryModel bqm) throws Exception;

}