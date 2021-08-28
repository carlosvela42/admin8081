package com.vnpt.ssdc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Packages {
	
	private Long id;
	
	private String name;
	
	private String price;
	
	private String category;
	
	private String time;
	
	private String code;
	
	private String template;

	public Packages() {
	}



	public Packages(Long id, String name, String price, String category, String time, String code, String template) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.time = time;
		this.code = code;
		this.template = template;
	}



	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getTemplate() {
		return template;
	}



	public void setTemplate(String template) {
		this.template = template;
	}

}
