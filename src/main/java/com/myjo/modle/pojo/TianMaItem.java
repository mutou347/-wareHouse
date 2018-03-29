package com.myjo.modle.pojo;

import java.time.LocalDateTime;

//商品货号	货源	中国尺码	外国尺码	品牌	市场价	库存数量	类别	小类	性别	季节	折扣
public class TianMaItem {
	//商品货号
	private String itemId;
	//货源ID
    private Integer wareHouseID;
	//货源
	private String supplier;
	//中国尺码
	private String  chineseSize;
	//外国尺码
	private String foreignSize;
	//品牌
	private String brand;
	//市场价
	private Double marketPric;
	//库存数量
	private Integer inventoryNum;
	//类别
	private String category;
	//小类
	private String smallCategory;
	//性别
	private String sex;
	//季节
	private String season;
	//折扣
	private Integer discount;
	//配货率
	private Integer pickRate;
	//配货的有效期
	private Integer pickRateTime;
	//发货时间
	private String PickDate;
	//库存更新时间
	private LocalDateTime updateTime;
	
	public Integer getPickRateTime() {
		return pickRateTime;
	}
	public void setPickRateTime(Integer pickRateTime) {
		this.pickRateTime = pickRateTime;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Integer getWareHouseID() {
		return wareHouseID;
	}
	public void setWareHouseID(Integer wareHouseID) {
		this.wareHouseID = wareHouseID;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getChineseSize() {
		return chineseSize;
	}
	public void setChineseSize(String chineseSize) {
		this.chineseSize = chineseSize;
	}
	public String getForeignSize() {
		return foreignSize;
	}
	public void setForeignSize(String foreignSize) {
		this.foreignSize = foreignSize;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getMarketPric() {
		return marketPric;
	}
	public void setMarketPric(Double marketPric) {
		this.marketPric = marketPric;
	}
	public Integer getInventoryNum() {
		return inventoryNum;
	}
	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSmallCategory() {
		return smallCategory;
	}
	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getPickRate() {
		return pickRate;
	}
	public void setPickRate(Integer pickRate) {
		this.pickRate = pickRate;
	}
	public String getPickDate() {
		return PickDate;
	}
	public void setPickDate(String pickDate) {
		PickDate = pickDate;
	}
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	
	public TianMaItem(String itemId, Integer wareHouseID, String supplier, String chineseSize, String foreignSize,
			String brand, Double marketPric, Integer inventoryNum, String category, String smallCategory,
			String sex, String season, Integer discount, Integer pickRate, Integer pickRateTime, String pickDate,
			LocalDateTime updateTime) {
		super();
		this.itemId = itemId;
		this.wareHouseID = wareHouseID;
		this.supplier = supplier;
		this.chineseSize = chineseSize;
		this.foreignSize = foreignSize;
		this.brand = brand;
		this.marketPric = marketPric;
		this.inventoryNum = inventoryNum;
		this.category = category;
		this.smallCategory = smallCategory;
		this.sex = sex;
		this.season = season;
		this.discount = discount;
		this.pickRate = pickRate;
		this.pickRateTime = pickRateTime;
		PickDate = pickDate;
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "TianMaItem [itemId=" + itemId + ", wareHouseID=" + wareHouseID + ", supplier=" + supplier
				+ ", chineseSize=" + chineseSize + ", foreignSize=" + foreignSize + ", brand=" + brand + ", marketPric="
				+ marketPric + ", inventoryNum=" + inventoryNum + ", category=" + category + ", smallCategory="
				+ smallCategory + ", sex=" + sex + ", season=" + season + ", discount=" + discount + ", pickRate="
				+ pickRate + ", pickRateTime=" + pickRateTime + ", PickDate=" + PickDate + ", updateTime=" + updateTime
				+ "]";
	}
	public TianMaItem() {
		super();
	}

}