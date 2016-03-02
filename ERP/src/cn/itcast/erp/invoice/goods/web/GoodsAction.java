package cn.itcast.erp.invoice.goods.web;

import java.util.List;

import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goods.vo.GoodsQueryModel;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseAction;

public class GoodsAction extends BaseAction{
	public GoodsModel gm = new GoodsModel();
	public GoodsQueryModel gqm = new GoodsQueryModel();

	private GoodsEbi goodsEbi;
	private SupplierEbi supplierEbi;
	private GoodsTypeEbi goodsTypeEbi;
	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}
	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}

	//分页列表
	public String list(){
		//加载供应商的数据集合
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);
		setDataTotal(goodsEbi.getCount(gqm));
		List<GoodsModel> goodsList = goodsEbi.getAll(gqm,pageNum,pageCount);
		put("goodsList", goodsList);
		return LIST;
	}

	
	//跳转到添加/修改页
	public String input(){
		//加载具有商品类别的供应商数据集合
		List<SupplierModel> supplierList = supplierEbi.getAllUnionGtm();
		put("supplierList",supplierList);
		//加载供应商数据
		Long supplierUuid = null;
		List<GoodsTypeModel> gtmList = null;
		if(gm.getUuid() != null){
			gm = goodsEbi.get(gm.getUuid());
			//修改的商品加载类别信息必须是对应的厂商的类别信息
			supplierUuid = gm.getGtm().getSm().getUuid();
		}else{
			supplierUuid = supplierList.get(0).getUuid();
		}
		gtmList = goodsTypeEbi.getAllBySm(supplierUuid);
		put("gtmList",gtmList);
		return INPUT;
	}

	//新增/修改
	public String save(){
		if(gm.getUuid() == null){
			goodsEbi.save(gm);
		}else{
			goodsEbi.update(gm);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		goodsEbi.delete(gm);
		return TO_LIST;
	}

}