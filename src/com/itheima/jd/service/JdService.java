package com.itheima.jd.service;

import java.util.List;

import com.itheima.jd.entity.ProductModel;

public interface JdService {
	// 通过四个条件查询
		public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name,
				String price,String sort) throws Exception ;
}
