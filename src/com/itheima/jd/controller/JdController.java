package com.itheima.jd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.jd.entity.ProductModel;
import com.itheima.jd.service.JdService;

/**
 * 商品管理
 * @author 被风追逐的人
 *
 */
@Controller
public class JdController {
	
	@Autowired
	private JdService jdService;
	
	@RequestMapping(value = "list.action")
	public String list(String queryString,String catalog_name,String price,
			String sort,Model model) throws Exception {
		//通过四个条件查询
		List<ProductModel> productModels = jdService.selectProductModelListByQuery(queryString, catalog_name, price, sort);
		model.addAttribute("productModels", productModels);
		//用于回显
		model.addAttribute("queryString", queryString);
		model.addAttribute("catalog_name", catalog_name);
		model.addAttribute("price", price);
		model.addAttribute("sort", sort);
		return "product_list";
	}
}
