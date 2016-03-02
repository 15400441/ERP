package cn.itcast.erp.invoice.order.web;

import java.util.List;

import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseAction;
import cn.itcast.erp.util.exception.AppException;

public class OrderAction extends BaseAction{
	public OrderModel om = new OrderModel();
	public OrderQueryModel oqm = new OrderQueryModel();

	private OrderEbi orderEbi;
	private SupplierEbi supplierEbi;
	private GoodsTypeEbi goodsTypeEbi;
	private GoodsEbi goodsEbi;
	private EmpEbi empEbi;
	private StoreEbi storeEbi;
	public void setStoreEbi(StoreEbi storeEbi) {
		this.storeEbi = storeEbi;
	}
	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}
	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}
	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}
	public void setOrderEbi(OrderEbi orderEbi) {
		this.orderEbi = orderEbi;
	}
	
	//----采购申请-----------------------------------------------
	
	//进入采购订单列表页
	public String buyList(){
		setDataTotal(orderEbi.getCount(oqm));
		List<OrderModel> orderList = orderEbi.getAll(oqm,pageNum,pageCount);
		put("orderList", orderList);
		return "buyList";
	}
	
	//进入采购订单页
	public String buyInput(){
		//加载具有商品类别的供应商信息，并且商品类别中要有商品
		List<SupplierModel> supplierList = supplierEbi.getAllUnionTwo();
		//第一个供应商对应的具有商品的类别数据
		List<GoodsTypeModel> gtmList = goodsTypeEbi.getAllUnionBySm(supplierList.get(0).getUuid());
		//商品
		List<GoodsModel> gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid());
		
		put("supplierList",supplierList);
		put("gtmList",gtmList);
		put("gmList",gmList);
		return "buyInput";
	}
	
	public Long[] goodsUuids;
	public Integer[] nums;
	public Double[] prices;
	//保存采购订单
	public String saveBuyOrder(){
		/*
		System.out.println(om.getSm().getUuid());
		System.out.println("======================");
		for(Long a :goodsUuids){
			System.out.println(a);
		}
		System.out.println("======================");
		for(Integer a :nums){
			System.out.println(a);
		}
		System.out.println("======================");
		for(Double a :prices){
			System.out.println(a);
		}
		*/
		orderEbi.saveBuyOrder(om,goodsUuids,nums,prices,getLogin());
		return "toBuyList";
	}
	
	//查询采购订单详情页
	public String buyOrderDetail(){
		om = orderEbi.get(om.getUuid());
		return "buyOrderDetail";
	}
	
	//----采购申请结束-----------------------------------------------
	
	
	
	
	
	
	//----采购审核-----------------------------------------------
	public String buyCheckList(){
		//订单种类的条件如何设置
		//设置订单种类类别条件为：采购 或 采购退货   （表现层？业务层？）业务层
		setDataTotal(orderEbi.getCountBuyCheckList(oqm));
		List<OrderModel> orderList = orderEbi.getAllBuyCheckList(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return "buyCheckList";
	}
	
	public String buyCheckInput(){
		om = orderEbi.get(om.getUuid());
		return "buyCheckInput";
	}
	
	public String buyCheckPass(){
		orderEbi.buyCheckPass(om.getUuid(),getLogin());
		return "toBuyCheckList";
	}
	
	public String buyCheckNoPass(){
		orderEbi.buyCheckNoPass(om.getUuid(),getLogin());
		return "toBuyCheckList";
	}
	
	//----采购审核结束-----------------------------------------------
	
	
	//----任务指派-----------------------------------------------
	
	public String taskList(){
		setDataTotal(orderEbi.getCountTaskList(oqm));
		List<OrderModel> orderList = orderEbi.getAllTaskList(oqm,pageNum,pageCount);
		put("orderList", orderList);
		return "taskList";
	}
	
	//去任务分配页面
	public String assignTask(){
		//获取当前登陆人所在部门所有员工信息
		List<EmpModel> empList = empEbi.getAllByDep(getLogin().getDm().getUuid());
		put("empList",empList);
		om = orderEbi.get(om.getUuid());
		return "assignTask";
	}
	
	//分派任务到任务人
	public String assignTaskToEmp(){
		//om.uuid,om.completer.uuid
		orderEbi.assignTaskToEmp(om.getUuid(),om.getCompleter());
		return "toTaskList";
	}
	
	//----任务指派结束-----------------------------------------------

	
	
	//----任务查询-----------------------------------------------
	
	public String tasks(){
		//将当前登陆人设置为跟单员
		oqm.setCompleter(getLogin());
		setDataTotal(orderEbi.getCountTasks(oqm));
		List<OrderModel> orderList = orderEbi.getAllTasks(oqm,pageNum,pageCount);
		put("orderList", orderList);
		return "tasks";
	}
	//个人任务明细查询
	public String taskDetail(){
		om = orderEbi.get(om.getUuid());
		return "taskDetail";
	}
	
	//完成任务
	public String endTask(){
		orderEbi.endTask(om.getUuid());
		return "toTasks";
	}
	
	//----任务查询结束-----------------------------------------------
	
	
	
	//----入库操作-----------------------------------------------
	public String inStoreList(){
		setDataTotal(orderEbi.getCountInStoreList(oqm));
		List<OrderModel> orderList = orderEbi.getAllInStoreList(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return "inStoreList";
	}
	
	//进入入库明细
	public String inStoreDetail(){
		//加载仓库信息（当前登陆人可以操作的仓库）
		List<StoreModel> storeList = storeEbi.getAllByEmp(getLogin().getUuid());
		put("storeList",storeList);
		om = orderEbi.get(om.getUuid());
		return "inStoreDetail";
	}
	
	//----入库操作结束-----------------------------------------------
	
	/*
	***********************************
	*						   		  *
	*				AJAX提交			  *
	*								  *
	***********************************
	*/
	public Long supplierUuid;
	public Long goodsTypeUuid;
	public Long goodsUuid;
	
	private List<GoodsTypeModel> gtmList;
	private List<GoodsModel> gmList;
	private GoodsModel gm;
	public GoodsModel getGm() {
		return gm;
	}
	public List<GoodsTypeModel> getGtmList() {
		return gtmList;
	}
	public List<GoodsModel> getGmList() {
		return gmList;
	}
	//ajax获取类别集合+商品集合
	public String ajaxGetGtmAndGm(){
		//类别集合
		gtmList = goodsTypeEbi.getAllUnionBySm(supplierUuid);
		//商品集合
		gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid());
		//第一个商品数据
		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}
	
	//ajax获取商品集合
	public String ajaxGetGm(){
		String[] uuidStrs = used.split(",");
		Long[] uuids = new Long[uuidStrs.length];
		for(int i = 0;i<uuidStrs.length;i++){
			uuids[i] = new Long(uuidStrs[i]);
		}
		
		gmList = goodsEbi.getAllByGtm(goodsTypeUuid,uuids);
		gm = gmList.get(0);
		return "ajaxGetGm";
	}
	
	//ajax获取商品信息
	public String ajaxGetOne(){
		gm = goodsEbi.get(goodsUuid);
		return "ajaxGetOne";
	}
	
	//public Long[] aa;
	
	public String used;			//1,2,3,格式->解析：数组，集合
	//ajax获取类别集合+商品集合(去重)
	public String ajaxGetGtmAndGm2(){
		//String[] ss = getRequest().getParameterValues("aa[]");
		//System.out.println(ss.length);
		/*构造网络延迟引发的BUG
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		//数据过滤的思想：
		//方式一：取出全部，删除重复	SQL语句完成取全部，通过JAVA删除重复
		//方式二：取时直接删除重复	SQL语句完成取最终数据（推荐）
		
		//后台使用的标准数据格式：数组，集合
		String[] uuidStrs = used.split(",");
		Long[] uuids = new Long[uuidStrs.length];
		//String[]->Long[]
		for(int i = 0;i<uuidStrs.length;i++){
			uuids[i] = new Long(uuidStrs[i]);
		}
		
		//类别集合
		//某个商品类别中的所有商品如果都使用完毕，则该类别必须被过滤掉
		gtmList = goodsTypeEbi.getAllUnionBySm(supplierUuid,uuids);
		//商品集合
		gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid(),uuids);
		//第一个商品数据
		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}

	public Long storeUuid;
	public Long odmUuid;
	public Integer num;
	private String msg ;
	public String getMsg() {
		return msg;
	}
	private OrderDetailModel odm;
	public OrderDetailModel getOdm() {
		return odm;
	}
	//异步入库商品
	public String ajaxInGoods(){
		//返回数据：剩余数量，总数量
		try {
			odm = orderEbi.inGoods(odmUuid,num,storeUuid,getLogin());
		} catch (AppException e) {
			msg = e.getMessage();
		}
		return "ajaxInGoods";
	}
	
}






