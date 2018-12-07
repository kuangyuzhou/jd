package com.itheima.jd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.jd.dao.JdDao;
import com.itheima.jd.entity.ProductModel;
import com.itheima.jd.service.JdService;

@Service
public class JdServiceImpl implements JdService {
	
	@Autowired
	private JdDao jdDao;
	
	@Override
	public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name, String price,
			String sort) throws Exception {
		List<ProductModel> list = jdDao.selectProductModelListByQuery(queryString, catalog_name, price, sort);
		return list;
	}
	
	
}
