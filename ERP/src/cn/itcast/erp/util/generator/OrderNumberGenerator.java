package cn.itcast.erp.util.generator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumberGenerator {
	private static int idx = 1;
	private static byte[] buf = new byte[]{48,48,48,48,48,48,48,48,48,48,48,48,48,48,};
	public static final String getOrderNum(){
		//20150326 0000000 1
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String first = df.format(new Date());
		int len = 8;
		int numLen = idx++;
		int zeroLen = len - (numLen+"").length();
		//00000  长度是zeroLen
		String second = new String(buf,0,zeroLen);
		String finNum = first+ second + numLen;
		return Long.toHexString(new Long(finNum)).toUpperCase();
	}
	public static void main(String[] args) {
		System.out.println(getOrderNum());
		
		/*
		String s ="11";
		for(int i = 0;i<6;i++){
			s+="0";
		}
		System.out.println(s);
		*/
		
		/*
		//数据结构思想
		byte[] buf = new byte[]{48,48,48,48,48,48,48,};
		//String s ="11";
		//System.out.println(s);
		System.out.println(new String(buf,0,4));
		*/
	}
}
