package com.webspider.lanswebspider.jppwebspider.pojo;

import java.sql.Timestamp;

public class StockCodeProduct {
	private int id;
	private Timestamp create_time;
	private Timestamp update_time;
	private String stock_name="";		//股票名字
	private String stock_code="";		//股票代码
	private String today_open="";		//今开
	private String yesterday_close="";	//昨收	
	private String highest="";			//最高
	private String lowest="";			//最低
	private String limit_up="";			//涨停
	private String limit_down="";		//跌停
	private String change_rate="";		//换手率
	private String relative_rate="";  	//量比
	private String volume ="";			//成交量
	private String turnover="";			//成交额
	private String pe_ratio="";			//市盈率
	private String pb_ratio="";			//市净率
	private String total_value="";		//总市值
	private String current_value="";	//流通市值
	private String core_data="";		//核心数据
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	public String getStock_code() {
		return stock_code;
	}
	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}
	public String getYesterday_close() {
		return yesterday_close;
	}
	public void setYesterday_close(String yesterday_close) {
		this.yesterday_close = yesterday_close;
	}
	public String getHighest() {
		return highest;
	}
	public void setHighest(String highest) {
		this.highest = highest;
	}
	public String getLowest() {
		return lowest;
	}
	public void setLowest(String lowest) {
		this.lowest = lowest;
	}
	public String getLimit_up() {
		return limit_up;
	}
	public void setLimit_up(String limit_up) {
		this.limit_up = limit_up;
	}
	public String getLimit_down() {
		return limit_down;
	}
	public void setLimit_down(String limit_down) {
		this.limit_down = limit_down;
	}
	public String getChange_rate() {
		return change_rate;
	}
	public void setChange_rate(String change_rate) {
		this.change_rate = change_rate;
	}
	public String getRelative_rate() {
		return relative_rate;
	}
	public void setRelative_rate(String relative_rate) {
		this.relative_rate = relative_rate;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	public String getPe_ratio() {
		return pe_ratio;
	}
	public void setPe_ratio(String pe_ratio) {
		this.pe_ratio = pe_ratio;
	}
	public String getPb_ratio() {
		return pb_ratio;
	}
	public void setPb_ratio(String pb_ratio) {
		this.pb_ratio = pb_ratio;
	}
	public String getTotal_value() {
		return total_value;
	}
	public void setTotal_value(String total_value) {
		this.total_value = total_value;
	}
	public String getCurrent_value() {
		return current_value;
	}
	public void setCurrent_value(String current_value) {
		this.current_value = current_value;
	}
	public String getCore_data() {
		return core_data;
	}
	public void setCore_data(String core_data) {
		this.core_data = core_data;
	}
	public String getToday_open() {
		return today_open;
	}
	public void setToday_open(String today_open) {
		this.today_open = today_open;
	}
	@Override
	public String toString() {
		return "StockCodeProduct [id=" + id + ", create_time=" + create_time + ", update_time=" + update_time
				+ ", stock_name=" + stock_name + ", stock_code=" + stock_code + ", today_open=" + today_open
				+ ", yesterday_close=" + yesterday_close + ", highest=" + highest + ", lowest=" + lowest + ", limit_up="
				+ limit_up + ", limit_down=" + limit_down + ", change_rate=" + change_rate + ", relative_rate="
				+ relative_rate + ", volume=" + volume + ", turnover=" + turnover + ", pe_ratio=" + pe_ratio
				+ ", pb_ratio=" + pb_ratio + ", total_value=" + total_value + ", current_value=" + current_value
				+ ", core_data=" + core_data + "]";
	}
}
