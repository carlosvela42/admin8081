package com.vnpt.ssdc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
	
	private Long id;
	
	private String email;
	
	private String phone;
	
	private String packageId;
	
	private String packageName;

	private String code;
	
	private String invoiceNo;
	
	private String vaNumber;
	
	private String paymentLink;
	
	private String isCancel;
	
	private String price;
	
	private String machineId;
	
	private String totalAmount;
	
	private String paydate;

	public Product() {
	}

	

	public Product(Long id, String email, String phone, String packageId, String packageName, String code,
			String invoiceNo, String vaNumber, String paymentLink, String isCancel, String price, String machineId,
			String totalAmount, String paydate) {
		super();
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.packageId = packageId;
		this.packageName = packageName;
		this.code = code;
		this.invoiceNo = invoiceNo;
		this.vaNumber = vaNumber;
		this.paymentLink = paymentLink;
		this.isCancel = isCancel;
		this.price = price;
		this.machineId = machineId;
		this.totalAmount = totalAmount;
		this.paydate = paydate;
	}



	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVaNumber() {
		return vaNumber;
	}

	public void setVaNumber(String vaNumber) {
		this.vaNumber = vaNumber;
	}

	public String getPaymentLink() {
		return paymentLink;
	}

	public void setPaymentLink(String paymentLink) {
		this.paymentLink = paymentLink;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}



	public String getPaydate() {
		return paydate;
	}



	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	
}
