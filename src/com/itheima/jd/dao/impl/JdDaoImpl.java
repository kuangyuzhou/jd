package com.itheima.jd.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itheima.jd.dao.JdDao;
import com.itheima.jd.entity.ProductModel;

@Repository
public class JdDaoImpl implements JdDao {

	// SolrJ
	@Autowired
	private SolrServer solrServer;

	// 通过四个条件查询
	public List<ProductModel> selectProductModelListByQuery(String queryString, String catalog_name,
			String price,String sort) throws Exception {

		// 查询
		SolrQuery solrQuery = new SolrQuery();
		// 关键词
		// solrQuery.set("q", "*:*");
		// solrQuery.set("q", "product_name:台灯");
		solrQuery.setQuery(queryString);
		// 过滤条件
		if(catalog_name != null && !"".equals(catalog_name)) {
			solrQuery.set("fq", "product_catalog_name:" + catalog_name);
		}
		if(price != null && !"".equals(price)) {
			//0-9 10-19 ... 50-*
			String[] p = price.split("-");
			solrQuery.set("fq", "product_price:["+ p[0] +" TO "+ p[1] +"]");
		}
		// 价格排序
		if("1".equals(sort)) {
			solrQuery.addSort("product_price", ORDER.desc);
		}else {
			solrQuery.addSort("product_price", ORDER.asc);
		}
		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(16);
		// 默认域
		solrQuery.set("df", "product_keywords");
		// 只查询指定域
		solrQuery.set("fl", "id,product_name,product_price,product_picture");
		// 高亮
		// 打开高亮开关
		solrQuery.setHighlight(true);
		// 指定高亮域
		solrQuery.addHighlightField("product_name");
		// 前缀
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		// 后缀
		solrQuery.setHighlightSimplePost("</span>");

		// 执行查询
		QueryResponse response = solrServer.query(solrQuery);
		// 文档结果集
		SolrDocumentList docs = response.getResults();

		// 高亮属于另一个容器
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// 1 Map K id V Map
		// 2 Map K 域名 V List
		// List list.get[0]
		// 总条数
		long numFound = docs.getNumFound();
		
		List<ProductModel> productModels = new ArrayList<>();
		
		for (SolrDocument doc : docs) {
			
			ProductModel productModel = new ProductModel();
			productModel.setPid((String) doc.get("id"));
			productModel.setPrice((float) doc.get("product_price"));
			productModel.setPicture((String) doc.get("product_picture"));
			
			//设置高亮
			Map<String, List<String>> map = highlighting.get((String) doc.get("id"));
			List<String> list = map.get("product_name");
			
			
			productModel.setName(list.get(0));
			
			productModels.add(productModel);
		}

		return productModels;
	}
}
