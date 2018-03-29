package com.myjo.modle.httpClint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.myjo.modle.pojo.TianMaItem;
import com.myjo.modle.util.OcDateTimeUtils;

/**
 * 爬取货源信息
 * @author hh
 *
 */
public class SupplyInfo {
	 private static final Logger LOGGER = LoggerFactory.getLogger(SupplyInfo.class);
	    long loo;
	/**
	 * 根据货号取到货源信息
	 * @param ArticleNo 
	 * @return 
	 */
	 @Test
	public Map<String,TianMaItem> getSearchByArticleno(Map<String,TianMaItem>itemMap) {
        //取出itemMap中的货号list
        List<String>list=new ArrayList<>();
        itemMap.forEach((key,value)->{
        	String articleno = value.getItemId();
        	list.add(articleno);
        });
        //遍历所有的所有的货号
        for(int i=0;i<list.size();i++) {
        	try {
    		    String jsonstr = "";
    			DownTianma tianma=new DownTianma(); 
    	        String[] JSessionId=tianma.getJSessionId();
    	        
    	        LOGGER.info("查询第"+i+"个货号:"+list.get(i));
    	        
    			HttpResponse<String> response = Unirest.post("http://www.tianmasport.com/ms/order/searchByArticleno.do")
    									.header("Host", "www.tianmasport.com")
    									.header("Connection", "keep-alive")
    									.header("Accept", "*/*")
    									.header("Origin", "http://www.tianmasport.com")
    									.header("X-Requested-With", "XMLHttpRequest")
    									.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
    									.header("Referer", "http://www.tianmasport.com/ms/order/quickOrder.shtml")
    									.header("Accept-Encoding", "gzip, deflate")
    									.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4")
    									.header("Cookie", "JSESSIONID="+JSessionId[1])
    									.field("articleno", list.get(i))
    									.asString();
    	        int code = response.getStatus();
    	        LOGGER.debug("http-status:" + code);
    	        LOGGER.debug("http-status-text:" + response.getStatusText());
    	        if(code==200) {
    	            String rt = response.getBody();
    	            if(rt.indexOf("没有类似货号的商品!")>-1){
    	            	LOGGER.info("货号出现异常错误:"+list.get(i));
    	            	continue;
    	            }
    	            Document doc = Jsoup.parse(rt);
    	            Element script = doc.select("script").get(1);
    	            String data = script.data();
    	            String bstr = "var data = $.parseJSON('";
    	            int  bstr_index  = data.indexOf(bstr);
    	            int  estr_index  = data.indexOf("');",bstr_index);
    	            jsonstr = data.substring(bstr_index+bstr.length(),estr_index);
    	            
    	            JsonElement jsonElement=new JsonParser().parse(jsonstr);
    	            JsonArray jsonArray = jsonElement.getAsJsonObject().get("rows").getAsJsonArray();

    	            String dd1 ; //配货率
    	            String dd2 ; //发货时效
    	        
    	            for(int j=0;j<jsonArray.size();j++){
    	            	JsonElement jsonIndex = jsonArray.get(j);
    	            	dd1=StringUtils.substringBeforeLast(jsonIndex.getAsJsonObject().get("pickRate").getAsString(), "%");
    	            	dd2=StringUtils.substringAfterLast(jsonIndex.getAsJsonObject().get("pickRate").getAsString(),"发货时效:");
    	            	String warehouseName=jsonIndex.getAsJsonObject().get("wareHouseName").getAsString();
    	            	if(itemMap.containsKey(warehouseName)) {
    	            		TianMaItem item = itemMap.get(warehouseName);
    	            		//添加配货率
    	            		item.setPickRate(Integer.parseInt(dd1.replaceAll("配货率：","")));
    	            		Integer pickDateIndex = Integer.valueOf(jsonIndex.getAsJsonObject().get("pick_date").getAsString());
    	            		switch(pickDateIndex) {
    	            			case 0:
    	            				item.setPickDate("周一至周五"); break;
    	            			case 1:
    	            				item.setPickDate("周一到周六"); break;
    	            			case 2:
    	            				item.setPickDate("周一到周日"); break;
    	            			default:
    	            				item.setPickDate("");
    	            		}
    		            	//添加仓库的ID
    		            	item.setWareHouseID(Integer.parseInt(jsonIndex.getAsJsonObject().get("wareHouseID").getAsString()));
    		            	//添加配货时效
    		            	item.setPickRateTime(Integer.parseInt(dd2.replaceAll("小时","")));
    		            	//添加更新时间
    		            	item.setUpdateTime(OcDateTimeUtils.string2LocalDateTime(jsonIndex.getAsJsonObject().get("updateTime").getAsString()));
    		            	//更改map中存储的值
    		            	itemMap.replace(warehouseName, item);
    	            	}
    	            }
    	        }
    		} catch (UnirestException e) {
    			 LOGGER.error("获取map信息出错！！");
    			e.printStackTrace();
    		}
        }
        LOGGER.info("获取完整的map信息");
		return itemMap;
	}


	
}
