package com.vnpt.ssdc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Code {
	
	private Long id;
	
	private String name;
	
	private String isactive;
	
	private String email;
	
	private String price;
	
	private String usecount;
	
	private String startDate;
	
	private String endDate;

	public Code() {
	}


	public Code(Long id, String name, String isactive, String email, String price) {
		super();
		this.id = id;
		this.name = name;
		this.isactive = isactive;
		this.email = email;
		this.price = price;
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

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getUsecount() {
		return usecount;
	}


	public void setUsecount(String usecount) {
		this.usecount = usecount;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}
