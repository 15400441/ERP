package cn.itcast.erp.invoice.storeoper.vo;

import java.util.HashMap;
import java.util.Map;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.util.format.FormatUtil;

public class StoreOperModel {
	public static final Integer STOREOPER_TYPE_OF_IN = 1;
	public static final Integer STOREOPER_TYPE_OF_OUT = 2;
	
	public static final String STOREOPER_TYPE_OF_IN_VIEW ="入库";
	public static final String STOREOPER_TYPE_OF_OUT_VIEW ="出库";
	
	public static final Map<Integer, String> typeMap = new HashMap<Integer, String>();
	static{
		typeMap.put(STOREOPER_TYPE_OF_IN, STOREOPER_TYPE_OF_IN_VIEW);
		typeMap.put(STOREOPER_TYPE_OF_OUT, STOREOPER_TYPE_OF_OUT_VIEW);
	}
	
	private Long uuid;
	
	private Integer num;
	
	private Long operTime;
	private Integer type;
	
	private String operTimeView;
	private String typeView;
	
	private OrderDetailModel odm;
	private EmpModel em;
	private StoreModel sm;
	
	public StoreModel getSm() {
		return sm;
	}
	public void setSm(StoreModel sm) {
		this.sm = sm;
	}
	public String getOperTimeView() {
		return operTimeView;
	}
	public String getTypeView() {
		return typeView;
	}
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getOperTime() {
		return operTime;
	}
	public void setOperTime(Long operTime) {
		this.operTime = operTime;
		this.operTimeView = FormatUtil.formatDateTime(operTime);
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
		this.typeView = typeMap.get(type);
	}
	public OrderDetailModel getOdm() {
		return odm;
	}
	public void setOdm(OrderDetailModel odm) {
		this.odm = odm;
	}
	public EmpModel getEm() {
		return em;
	}
	public void setEm(EmpModel em) {
		this.em = em;
	}
	
	
}
