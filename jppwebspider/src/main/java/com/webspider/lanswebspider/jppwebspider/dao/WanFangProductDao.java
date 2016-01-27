package com.webspider.lanswebspider.jppwebspider.dao;

import java.util.List;

import com.webspider.lanswebspider.jppwebspider.pojo.WanFangProduct;

public interface WanFangProductDao {
	//public List<CqVipProduct> getCqVipProduct();
	public void addWanFangProduct(WanFangProduct Product);
	public void updateWanFangProduct();
	public void deleteWanFangProduct();
	public WanFangProduct getWanFangProduct(int id);
}
