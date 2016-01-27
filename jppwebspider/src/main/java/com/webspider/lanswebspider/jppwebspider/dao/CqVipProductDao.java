package com.webspider.lanswebspider.jppwebspider.dao;

import java.util.List;

import com.webspider.lanswebspider.jppwebspider.pojo.CqVipProduct;


public interface CqVipProductDao {
	//public List<CqVipProduct> getCqVipProduct();
	public void addCqVipProduct(CqVipProduct Product);
	public void updateCqVipProduct();
	public void deleteCqVipProduct();
	public CqVipProduct getCqVipProduct(int id);
}
