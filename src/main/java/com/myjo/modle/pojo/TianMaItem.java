package com.myjo.modle.pojo;

import com.opencsv.bean.CsvBindByPosition;

//商品货号	货源	中国尺码	外国尺码	品牌	市场价	库存数量	类别	小类	性别	季节	折扣
public class TianMaItem {

	@CsvBindByPosition(position = 0)
	private String itemId;

	@CsvBindByPosition(position = 1)
	private String supplier;

	@CsvBindByPosition(position = 2)
	private String  chineseSize;

	public void setForeignSize(String foreignSize) {
		this.foreignSize = foreignSize;
	}

	public void setChineseSize(String chineseSize) {
		this.chineseSize = chineseSize;
	}

	public TianMaItem(String itemId, String supplier, String chineseSize, String foreignSize, String brandSize,
			Double marketPric, Integer inventoryNum, Character category, String smallCategory, Character sex,
			String season, Integer discount) {
		super();
		this.itemId = itemId;
		this.supplier = supplier;
		this.chineseSize = chineseSize;
		this.foreignSize = foreignSize;
		this.brandSize = brandSize;
		this.marketPric = marketPric;
		this.inventoryNum = inventoryNum;
		this.category = category;
		this.smallCategory = smallCategory;
		this.sex = sex;
		this.season = season;
		this.discount = discount;
	}

	@CsvBindByPosition(position = 3)
	private String foreignSize;
	@CsvBindByPosition(position = 4)
	private String brandSize;

	@CsvBindByPosition(position = 5)
	private Double marketPric;

	@CsvBindByPosition(position = 6)
	private Integer inventoryNum;

	@CsvBindByPosition(position = 7)
	private Character category;

	@CsvBindByPosition(position = 8)
	private String smallCategory;
	@CsvBindByPosition(position = 9)
	private Character sex;
	@CsvBindByPosition(position = 10)
	private String season;
	@CsvBindByPosition(position = 11)
	private Integer discount;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getBrandSize() {
		return brandSize;
	}

	public void setBrandSize(String brandSize) {
		this.brandSize = brandSize;
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

	public Character getCategory() {
		return category;
	}

	public void setCategory(Character category) {
		this.category = category;
	}

	public String getSmallCategory() {
		return smallCategory;
	}

	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "TianMaItem [itemId=" + itemId + ", supplier=" + supplier + ", chineseSize=" + chineseSize
				+ ", foreignSize=" + foreignSize + ", brandSize=" + brandSize + ", marketPric=" + marketPric
				+ ", inventoryNum=" + inventoryNum + ", category=" + category + ", smallCategory=" + smallCategory
				+ ", sex=" + sex + ", season=" + season + ", discount=" + discount + "]";
	}

	public TianMaItem() {
		super();
	}


}
