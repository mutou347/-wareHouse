package com.myjo.modle.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myjo.modle.httpClint.AccessMethod;
import com.myjo.modle.pojo.TianMaItem;
import com.opencsv.CSVReader;
/**
 * 对csv进行操作
 * @author hh
 *
 */
public class MargeCSV {
	 private static final Logger LOGGER = LoggerFactory.getLogger(AccessMethod.class);
	@SuppressWarnings("deprecation")
	@Test
	/**
	 * 货源去重
	 * @param file	传入文件的地址
	 */
	public Map<String, TianMaItem> supplierToHeavy(File file) {
		CSVReader reader=null;
		InputStreamReader in=null;
		try {
			//解析csv文件
			in = new InputStreamReader(new FileInputStream(file), Charset.forName("gbk"));
			reader = new CSVReader(in, ',');
			//读取所有的csv文件
			List<String[]> allRecords = reader.readAll();
			
			List<TianMaItem>itemAL=new ArrayList<>();
			Set set=new TreeSet();
			//遍历所有的行
			for (int i = 0; i < allRecords.size(); i++) {
				//去掉标题列
				if (i == 0) {
					continue;
				}
				//取得每一行
				TianMaItem item = new TianMaItem();
				String[] records = allRecords.get(i);
				//便利列,得到每一行的属性
				for (int j = 0; j < records.length; j++) {
					item.setItemId(records[0]);
					item.setSupplier(records[1]);
					item.setChineseSize(records[2]);
					item.setForeignSize(records[3]);
					item.setBrand(records[4]);
					if(!records[5].equals("null")) {
						item.setMarketPric(Double.parseDouble(records[5]));
					}else {
						//LOGGER.info("天马仓库错误，出现代理价为null！！！！！！"+records[0]);
						set.add(records[0]);
						item.setMarketPric(0.0);
					}
					if(!records[6].equals("null")) {
						item.setInventoryNum(Integer.parseInt(records[6]));
					}else {
						item.setInventoryNum(0);
					}
					if(records[7].length()>0) {
						item.setCategory(records[7]);
					}else {
						item.setCategory("");
					}
					item.setSmallCategory(records[8]);
					if(records[9].length()>0) {
						item.setSex(records[9]);
					}else {
						item.setSex("");
					}
					item.setSeason(records[10]);
					item.setDiscount(Integer.getInteger(records[11]));
					//将商品信息添加到ArrayList中。
					itemAL.add(item);
				}
			}
			LOGGER.info("天马家共出现："+set+"个，代理价为null的错误");
			Map<String,TianMaItem>TMIMap=new HashMap<>();
			//System.out.println(itemAL);
			for(int i=0;i<itemAL.size();i++) {
				TianMaItem tmi = itemAL.get(i);
				TMIMap.put(tmi.getSupplier(), tmi);
			}
			return TMIMap;
		}catch(Exception e) {
			LOGGER.error("csv文件解析失败！");
			e.printStackTrace();
		}finally {
			//资源释放
			try {
				reader.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.error("csv文件解析失败！");
		return null;
	}
}
