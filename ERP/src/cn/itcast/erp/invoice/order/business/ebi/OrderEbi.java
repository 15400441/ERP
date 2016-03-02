package cn.itcast.erp.invoice.order.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface OrderEbi extends BaseEbi<OrderModel> {
	/**
	 * 保存采购订单
	 * @param om 订单数据模型（封装了对应的供应商uuid）
	 * @param goodsUuids 订单中商品的uuid数组
	 * @param nums	订单中的数量数组
	 * @param prices 订单中的单价数组
	 * @param creater 制单人 
	 */
	public void saveBuyOrder(OrderModel om, Long[] goodsUuids, Integer[] nums,Double[] prices, EmpModel creater);
	/**
	 * 获取所有采购审核相关订单
	 * @param oqm
	 * @param pageNum
	 * @param pageCount
	 * @return
	 */
	public List<OrderModel> getAllBuyCheckList(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount);
	public Integer getCountBuyCheckList(OrderQueryModel oqm);
	
	/**
	 * 采购审核通过
	 * @param uuid 被审核订单uuid
	 * @param creater 审核人
	 */
	public void buyCheckPass(Long uuid, EmpModel creater);
	public void buyCheckNoPass(Long uuid, EmpModel login);
	public List<OrderModel> getAllTaskList(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount);
	public Integer getCountTaskList(OrderQueryModel oqm);
	/**
	 * 分派任务到跟单人
	 * @param uuid 任务订单uuid
	 * @param completer 任务人
	 */
	public void assignTaskToEmp(Long uuid, EmpModel completer);
	public List<OrderModel> getAllTasks(OrderQueryModel oqm, Integer pageNum,
			Integer pageCount);
	public Integer getCountTasks(OrderQueryModel oqm);
	public void endTask(Long uuid);
	public List<OrderModel> getAllInStoreList(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount);
	public Integer getCountInStoreList(OrderQueryModel oqm);
	/**
	 * 入库商品
	 * @param odmUuid 商品所属订单明细uuid
	 * @param num 入库数量
	 * @param storeUuid 入库对应的仓库uuid
	 * @param oper 入库人
	 * @return 订单明细对象
	 */
	public OrderDetailModel inGoods(Long odmUuid, Integer num, Long storeUuid,EmpModel oper);

}