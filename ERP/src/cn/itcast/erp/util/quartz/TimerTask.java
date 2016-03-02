package cn.itcast.erp.util.quartz;

import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.util.format.FormatUtil;

public class TimerTask {
	private GoodsEbi goodsEbi;
	private MailSender mailSender;
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}

	public void goodsUseNumUpdate(){
		/*
		update
			tbl_goods g
		set
			useNum = 
				(
					select
						count(uuid)
					from
						tbl_orderdetail
					where
						goodsUuid = g.uuid
				)
		*/
		goodsEbi.useNumUpdate();
	}
	
	//库存预警
	public void storeWarn(){
		//需求：
		//查询哪些商品现在处于报警阶段
		/*
		select
			g.name,
			sum(sd.num) > g.maxNum,
			sum(sd.num) < g.minNum
		from
			tbl_storedetail sd,
			tbl_goods g
		where
			sd.goodsUuid = g.uuid
		group by
			goodsUuid
		*/
		//根据查询结果发送email
		//1.测试email发送
		SimpleMailMessage smm = new SimpleMailMessage();
		//126.smtp,from必须与配置的权限用户相同
		smm.setFrom("itcast0228@126.com");
		//设置接受人
		smm.setTo("abc@qq.com");
		//设置发送时间
		smm.setSentDate(new Date());
		//设置主题
		smm.setSubject("库存预警信息["+FormatUtil.formatDateTime(System.currentTimeMillis())+"]");
		//设置正文
		StringBuilder sbf = new StringBuilder();
		List<Object[]> warnList = goodsEbi.getWarnList();
		for(Object[] objs:warnList){
			BigInteger maxFlag = (BigInteger) objs[1];
			if(maxFlag.intValue() == 1 ){
				String name = objs[0].toString();
				sbf.append("商品【");
				sbf.append(name);
				sbf.append("】超过库存最大值，请停止补货！\r\n");
				continue;
			}
			BigInteger minFlag = (BigInteger) objs[2];
			if(minFlag.intValue() == 1 ){
				String name = objs[0].toString();
				sbf.append("商品【");
				sbf.append(name);
				sbf.append("】低于库存最小值，请及时补货！\r\n");
				continue;
			}
		}
		//2.将数据加入email
		smm.setText(sbf.toString());
		mailSender.send(smm);
	}
	
}
