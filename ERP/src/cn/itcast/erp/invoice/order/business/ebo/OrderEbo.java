package cn.itcast.erp.invoice.order.business.ebo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.dao.dao.OrderDao;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderdetail.dao.dao.OrderDetailDao;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.storedetail.dao.dao.StoreDetailDao;
import cn.itcast.erp.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.erp.invoice.storeoper.dao.dao.StoreOperDao;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.erp.util.base.BaseQueryModel;
import cn.itcast.erp.util.exception.AppException;
import cn.itcast.erp.util.generator.OrderNumberGenerator;

public class OrderEbo implements OrderEbi{
	private OrderDao orderDao;
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public void save(OrderModel om) {
		orderDao.save(om);
	}

	public List<OrderModel> getAll() {
		return orderDao.getAll();
	}

	public OrderModel get(Long uuid) {
		return orderDao.get(uuid);
	}

	public void update(OrderModel om) {
		orderDao.update(om);
	}

	public void delete(OrderModel om) {
		orderDao.delete(om);
	}

	public List<OrderModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return orderDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return orderDao.getCount(qm);
	}
	//1-5  1.5  4-4.5
	public void saveBuyOrder(OrderModel om, Long[] goodsUuids, Integer[] nums,Double[] prices, EmpModel creater) {
		//将订单数据与明细数据全部保存到数据库中
		//给一个订单号：不能重复
		//201503264000000006
		//201503274000000001 - >Long ->HEX
		om.setOrderNum(OrderNumberGenerator.getOrderNum());
		//创建时间：当前时间
		om.setCreateTime(System.currentTimeMillis());
		//订单类别:采购
		om.setOrderType(OrderModel.ORDER_ORDERTYPE_OF_BUY);
		//订单状态：未审核
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK);
		//制单人：当前系统登录人
		om.setCreater(creater);
		
		Integer totalNum = 0;
		Double totalPrice = 0.0d;
		//缺少订单明细
		Set<OrderDetailModel> odms = new HashSet<OrderDetailModel>();
		//讲数组中的数据取出来，转换成集合可以接受的数据类型，并添加到集合中
		for (int i = 0; i < goodsUuids.length; i++) {
			OrderDetailModel odm = new OrderDetailModel();
			//明细数量
			odm.setNum(nums[i]);
			//明细剩余数量=明细数量
			odm.setSurplus(nums[i]);
			//明细单价
			odm.setPrice(prices[i]);
			//明细货物
			GoodsModel gm = new GoodsModel();
			gm.setUuid(goodsUuids[i]);
			odm.setGm(gm);
			odm.setOm(om);
			odms.add(odm);
			//orderDetailDao.save(odm);
			totalNum += nums[i];
			totalPrice += nums[i] * prices[i];
		}
		//利用级联添加，必须在关系上配置cascasd="save-update"
		om.setOdms(odms);
		
		//设置货物总量:循环中累加
		om.setTotalNum(totalNum);
		//设置订单总额:循环中累加
		om.setTotalPrice(totalPrice);
		orderDao.save(om);
	}
	private Integer[] buyCheckOrderTypes = new Integer[]{
			OrderModel.ORDER_ORDERTYPE_OF_BUY,	
			OrderModel.ORDER_ORDERTYPE_OF_RETURN_BUY
			};
	public List<OrderModel> getAllBuyCheckList(OrderQueryModel oqm,Integer pageNum, Integer pageCount) {
		//添加业务条件：订单类别限制在采购与采购退货间
		//传递参数是多个，用于作为同一个条件，后台SQL中使用 in 操作 （数组或集合）
		oqm.setOrderTypes(buyCheckOrderTypes);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public Integer getCountBuyCheckList(OrderQueryModel oqm) {
		oqm.setOrderTypes(buyCheckOrderTypes);
		return orderDao.getCount(oqm);
	}

	public void buyCheckPass(Long uuid, EmpModel checker) {
		//通过订单OID使用快照更新状态
		OrderModel temp = orderDao.get(uuid);
		
		//逻辑校验：（重要）
		/*
		if(不能正常进行操作){
			//提示用户非法操作
		}
		*/
		//目前的状态值不足，应该包含采购未审核与采购退货未审核
		if(!temp.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK)){
			throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
		}
		//修改状态，审核人，审核时间
		//设置订单的状态为采购审核通过
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS);
		//审核人为当前登陆人
		temp.setChecker(checker);
		//审核时间当前系统时间
		temp.setCheckTime(System.currentTimeMillis());
	}

	public void buyCheckNoPass(Long uuid, EmpModel checker) {
		//通过订单OID使用快照更新状态
		OrderModel temp = orderDao.get(uuid);
		if(!temp.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK)){
			throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
		}
		//修改状态，审核人，审核时间
		//设置订单的状态为采购审核驳回
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_CHECK_NO_PASS);
		//审核人为当前登陆人
		temp.setChecker(checker);
		//审核时间当前系统时间
		temp.setCheckTime(System.currentTimeMillis());
	}
	
	private Integer[] taskListType = new Integer[]{
		//16个状态
		OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS,
		OrderModel.ORDER_TYPE_OF_BUY_BUYYING,
		OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,
		OrderModel.ORDER_TYPE_OF_BUY_COMPLETE,
		//OrderModel.ORDER_TYPE_OF_SALE_CHECK_PASS,
		//OrderModel.ORDER_TYPE_OF_RETURN_BUY_CHECK_PASS,
		//OrderModel.ORDER_TYPE_OF_RETURN_SALE_CHECK_PASS,
		};
	public List<OrderModel> getAllTaskList(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount) {
		//将要查询的条件设置在oqm对象中，然后传递到后台，通用查询完成操作
		//设置的条件未：订单状态在 采购审核通过，销售审核通过，采购退货审核通过，销售退货审核通过
		oqm.setTypes(taskListType);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public Integer getCountTaskList(OrderQueryModel oqm) {
		oqm.setTypes(taskListType);
		return orderDao.getCount(oqm);
	}

	public void assignTaskToEmp(Long uuid, EmpModel completer) {
		//快照更新
		OrderModel temp = orderDao.get(uuid);
		//错误的，正确的应该是在4个状态中
		if(!temp.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS)){
			throw new AppException("悟空，你又调皮了！");
		}
		//快照更新跟单人
		temp.setCompleter(completer);
		//状态
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_BUYYING);
	}
	private Integer[] taskTypes = new Integer[]{
		OrderModel.ORDER_TYPE_OF_BUY_BUYYING,
		OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,
		OrderModel.ORDER_TYPE_OF_BUY_COMPLETE,
		//还有9个，一共12个状态
		};
	public List<OrderModel> getAllTasks(OrderQueryModel oqm, Integer pageNum,Integer pageCount) {
		//任务必须是当前登陆人的任务(表现层：最佳实现应该是在业务层)
		//任务必须状态满足条件:12个
		oqm.setTypes(taskTypes);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public Integer getCountTasks(OrderQueryModel oqm) {
		oqm.setTypes(taskTypes);
		return orderDao.getCount(oqm);
	}

	public void endTask(Long uuid) {
		//快照
		OrderModel temp = orderDao.get(uuid);
		
		//校验当前登陆人是否与跟单人一致
		if(!temp.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_BUYYING)){
			throw new AppException("你懂得！");
		}
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_IN_STORE);
	}

	private Integer[] inStoreTypes = new Integer[]{
		OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,	
		//OrderModel.ORDER_TYPE_OF_RETURN_SALE_IN_STORE,	
		};
	public List<OrderModel> getAllInStoreList(OrderQueryModel oqm,Integer pageNum, Integer pageCount) {
		//为oqm对象设置条件：状态处于入库中显示
		oqm.setTypes(inStoreTypes);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public Integer getCountInStoreList(OrderQueryModel oqm) {
		oqm.setTypes(inStoreTypes);
		return orderDao.getCount(oqm);
	}
	
	//业务层注入数据层（组合业务逻辑）
	private OrderDetailDao orderDetailDao;
	private StoreDetailDao storeDetailDao;
	private StoreOperDao storeOperDao;
	public void setStoreOperDao(StoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
	}

	public void setStoreDetailDao(StoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
	}
	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	//入库(4.5)
	public OrderDetailModel inGoods(Long odmUuid, Integer num, Long storeUuid, EmpModel oper) {
		OrderDetailModel odm = orderDetailDao.get(odmUuid);
		OrderModel om = odm.getOm();
		//逻辑校验
		//1.订单的状态判断
		if(!om.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_IN_STORE)){
			throw new AppException("不要调皮！");
		}
		if(num > odm.getSurplus()){
			throw new AppException("对不起，入库数量不能大于订单数量！");
		}
		
		//1.订单的明细数量修改  原始数量-本次入库数量
		//快照更新
		
		odm.setSurplus(odm.getSurplus() - num);
		
		//组织处的参数在使用时，兼顾后面的使用范围
		GoodsModel gm = odm.getGm();
		
		StoreModel sm = new StoreModel();
		sm.setUuid(storeUuid);
		
		//2.库存明细数量发生变化  原始数量+本次入库数量
		//快照
		StoreDetailModel sdm = storeDetailDao.getBySmAndGm(storeUuid,gm.getUuid());
		//判断对应仓库中是否有对应商品
		if(sdm == null){
			//如果没有，创建新记录
			sdm = new StoreDetailModel();
			//设置数量
			sdm.setNum(num);
			//设置仓库
			sdm.setSm(sm);
			//设置货物
			sdm.setGm(gm);
			storeDetailDao.save(sdm);
		}else{
			//如果有，快照更新
			sdm.setNum(sdm.getNum() + num);
		}
		
		//3.记录入库操作流程数据
		StoreOperModel som = new StoreOperModel();
		//操作时间：系统时间
		som.setOperTime(System.currentTimeMillis());
		//操作数量
		som.setNum(num);
		//操作类别
		som.setType(StoreOperModel.STOREOPER_TYPE_OF_IN);
		//操作人
		som.setEm(oper);
		//操作的仓库
		som.setSm(sm);
		//对应的明细
		som.setOdm(odm);
		//save
		storeOperDao.save(som);
		
		//4.判定订单是否入库完毕，修改其状态和完成时间
		//某个订单入库完毕，一个订单中包含有若干个明细，如果某一个明细没有入库完毕，不算入库完毕
		//入库完毕：一个订单中的所有的明细都入库完毕，他们的surplus = 0
		//求当前订单的所有明细入库剩余量的和
		Integer sum = 0;
		for(OrderDetailModel temp : om.getOdms()){
			sum += temp.getSurplus();
		}
		//入库全部完毕
		if(sum == 0){
			//设置订单状态为：结单
			om.setType(OrderModel.ORDER_TYPE_OF_BUY_COMPLETE);
			//设置结束时间
			om.setEndTime(System.currentTimeMillis());
		}
		return odm;
	}

}










