package com.itheima.jd.dao;

import java.util.List;

import com.itheima.jd.entity.ProductModel;

public interface JdDao {

	// 通过四个条件查询
		public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name,
				String price,String sort) throws Exception ;
}
