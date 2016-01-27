package com.webspider.lanswebspider.jppwebspider.pojo;

import java.sql.Timestamp;

public class CqVipProduct{
	private int id;
	private Timestamp create_time;
	private Timestamp update_time;
	private String article_title="";
	private String article_info="";
	private String article_tags="";
	private String article_url="";
	
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_info() {
		return article_info;
	}
	public void setArticle_info(String article_info) {
		this.article_info = article_info;
	}
	public String getArticle_tags() {
		return article_tags;
	}
	public void setArticle_tags(String article_tags) {
		this.article_tags = article_tags;
	}
	public String getArticle_url() {
		return article_url;
	}
	public void setArticle_url(String article_url) {
		this.article_url = article_url;
	}
	@Override
	public String toString() {
		return "CqVipProduct [id=" + id + ", create_time=" + create_time
				+ ", update_time=" + update_time + ", article_title="
				+ article_title + ", article_info=" + article_info
				+ ", article_tags=" + article_tags + ", article_url="
				+ article_url + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
