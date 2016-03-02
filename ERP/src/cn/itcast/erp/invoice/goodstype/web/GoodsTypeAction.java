package cn.itcast.erp.invoice.goodstype.web;

import java.util.List;

import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeQueryModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseAction;

public class GoodsTypeAction extends BaseAction{
	public GoodsTypeModel gm = new GoodsTypeModel();
	public GoodsTypeQueryModel gqm = new GoodsTypeQueryModel();

	private GoodsTypeEbi goodsTypeEbi;
	private SupplierEbi supplierEbi;
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}
	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}

	//分页列表
	public String list(){
		setDataTotal(goodsTypeEbi.getCount(gqm));
		List<GoodsTypeModel> goodsTypeList = goodsTypeEbi.getAll(gqm,pageNum,pageCount);
		put("goodsTypeList", goodsTypeList);
		return LIST;
	}

	//跳转到添加/修改页
	public String input(){
		//加载供应商的集合
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);	
		if(gm.getUuid() != null){
			gm = goodsTypeEbi.get(gm.getUuid());
		}
		return INPUT;
	}

	//新增/修改
	public String save(){
		if(gm.getUuid() == null){
			goodsTypeEbi.save(gm);
		}else{
			goodsTypeEbi.update(gm);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		goodsTypeEbi.delete(gm);
		return TO_LIST;
	}

	//----AJAX-----------------------------
	//----AJAX-----------------------------
	//----AJAX-----------------------------
	//----AJAX-----------------------------
	//----AJAX-----------------------------
	
	
	public String getAbc(){
		return "def";
	}
	public Integer getId(){
		return 123;
	}
	
	public Long supplierUuid;
	private List<GoodsTypeModel> gtmList;
	public List<GoodsTypeModel> getGtmList() {
		return gtmList;
	}
	public String ajaxGetBySm(){
		gtmList = goodsTypeEbi.getAllBySm(supplierUuid);
		return "ajaxGetBySm";
	}
}