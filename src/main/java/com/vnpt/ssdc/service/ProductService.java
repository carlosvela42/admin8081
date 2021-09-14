package com.vnpt.ssdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vnpt.ssdc.constant;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.dto.Users;

@Service
@Transactional
public class ProductService {
	
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	public List<Product> listAll() {
		List<Product> map = new ArrayList<Product>();
		String sql = "select USERS.EMAIL EMAIL, USERS.PHONE PHONE, USERS.MACHINE_ID MACHINE_ID, PACKAGES.ID PACKAGE_ID, PACKAGES.NAME PACKAGE_NAME, MAP.ID ID, MAP.IS_CANCEL IS_CANCEL, MAP.TOTAL_AMOUNT TOTAL_AMOUNT from MAP left join USERS on USERS.ID = MAP.USER_ID left join PACKAGES on PACKAGES.ID = MAP.PACKAGE_ID ";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName != null && !constant.ADMIN_EMAIL.equals(currentPrincipalName)) {
			sql += " WHERE USERS.EMAIL = '" + currentPrincipalName + "'";
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setEmail(rs.getString("EMAIL"));
				product.setPackageId(rs.getString("PACKAGE_ID"));
				product.setPackageName(rs.getString("PACKAGE_NAME"));
				product.setIsCancel(rs.getString("IS_CANCEL"));
				product.setTotalAmount(rs.getString("TOTAL_AMOUNT"));
				product.setPhone(rs.getString("PHONE"));
				product.setMachineId(rs.getString("MACHINE_ID"));
				product.setId(rs.getLong("ID"));	
				map.add(product);
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
	}
	
	public List<Product> listAll(Product productSearch) {
		List<Product> map = new ArrayList<Product>();
		String sql = "select USERS.EMAIL EMAIL, USERS.PHONE PHONE, USERS.MACHINE_ID MACHINE_ID, PACKAGES.ID PACKAGE_ID, PACKAGES.NAME PACKAGE_NAME, MAP.ID ID, MAP.IS_CANCEL IS_CANCEL, MAP.TOTAL_AMOUNT TOTAL_AMOUNT from MAP left join USERS on USERS.ID = MAP.USER_ID left join PACKAGES on PACKAGES.ID = MAP.PACKAGE_ID WHERE 1 = 1 ";
		if(productSearch.getEmail() != null && !"".equals(productSearch.getEmail())) {
			sql += " AND USERS.EMAIL = ?";
		}
		if(productSearch.getPackageId() != null && !"".equals(productSearch.getPackageId())) {
			sql += " AND PACKAGES.ID = ?";
		}
		if(productSearch.getId() != null) {
			sql += " AND MAP.ID = ?";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName != null && !constant.ADMIN_EMAIL.equals(currentPrincipalName)) {
			sql += " AND USERS.EMAIL = '" + currentPrincipalName + "'";
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			int i = 1;
			if(productSearch.getEmail() != null && !"".equals(productSearch.getEmail())) {
				pstm.setString(i, productSearch.getEmail());
				i++;
			}
			if(productSearch.getPackageId() != null && !"".equals(productSearch.getPackageId())) {
				pstm.setString(i, productSearch.getPackageId());
				i++;
			}
			if(productSearch.getId() != null) {
				pstm.setLong(i, productSearch.getId());
				i++;
			}
			;
			rs = pstm.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setEmail(rs.getString("EMAIL"));
				product.setPackageId(rs.getString("PACKAGE_ID"));
				product.setPackageName(rs.getString("PACKAGE_NAME"));
				product.setIsCancel(rs.getString("IS_CANCEL"));
				product.setTotalAmount(rs.getString("TOTAL_AMOUNT"));
				product.setPhone(rs.getString("PHONE"));
				product.setMachineId(rs.getString("MACHINE_ID"));
				product.setId(rs.getLong("ID"));
				map.add(product);
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
	}
	
	public List<Product> selectByEmail(String email) {
		String sql = "select * from MAP LEFT JOIN USERS on USERS.ID = MAP.USER_ID LEFT JOIN PACKAGES on PACKAGES.ID = MAP.PACKAGE_ID WHERE USERS.EMAIL = ? AND MAP.IS_CANCEL = 'N'";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Product> products = new ArrayList<Product>();
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, email);
			rs = pstm.executeQuery();
			while (rs.next()) {				
				Product product = new Product();
				product.setPackageName(rs.getString("NAME"));
				product.setPrice(rs.getString("PRICE"));
				Locale locale = new Locale("vi","VN");
				product.setPriceLocale(String.format(locale, "%,d", Integer.parseInt(rs.getString("PRICE"))) );
				Calendar c = Calendar.getInstance(); 
				c.setTime(rs.getDate("PAY_DATE")); 
				c.add(Calendar.DATE, Integer.parseInt(rs.getString("TIME")));
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String paydate = formatter.format(c.getTime());
				product.setPaydate(paydate);
				product.setMachineId(rs.getString("MACHINE_ID"));
				product.setPackageId(rs.getString("PACKAGE_ID"));
				product.setId(rs.getLong("ID"));
				products.add(product);
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return products;
	}
	
	public void updatePaymentLink(String paymentLink, String vaNumber, String invoiceNo) {
		String sql = "update PRODUCT set PAYMENT_LINK = ?, VA_NUMBER = ? WHERE INVOICE_NO = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, paymentLink);
			pstm.setString(2, vaNumber);
			pstm.setString(3, invoiceNo);
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void update(Users user) {
		String sql = "update USERS set PHONE = ?, EMAIL = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, user.getPhone());
			pstm.setString(2, user.getEmail());
			
//			if(user.getNewPassword() != null && !"".equals(user.getNewPassword())) {
//				pstm.setString(4, user.getNewPassword());
//			} else pstm.setString(4, user.getPassword());
			pstm.setLong(3, user.getId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void updateProduct(Product user) {
		String sql = "update MAP set PACKAGE_ID = ?, IS_CANCEL = ?, PAY_DATE = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, user.getPackageId());
			pstm.setString(2, user.getIsCancel());
			if("Y".equals(user.getIsCancel())) {
				pstm.setString(3, null);
			} else {
				pstm.setDate(3, (java.sql.Date) new Date());
				//doi goi cuoc neu gia han thi ko cap nhat, ngc lai cap nhat va goi sang epay
			}
			
			pstm.setLong(4, user.getId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public boolean changePass(Users user) {
		String sql = "update USERS set PASSWORD = ? WHERE EMAIL = ? AND PASSWORD = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, user.getNewPassword());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			pstm.setString(2, currentPrincipalName);
			pstm.setString(3, user.getOldPassword());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
			if(updateCount > 0) {
				res = true;
			}
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
		return res;
	}
	
	public boolean saveMachineId(Product product) {
		String sql = "update MAP set MACHINE_ID = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, product.getMachineId());			
			pstm.setString(2, product.getId() + "");

			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
			if(updateCount > 0) {
				res = true;
			}
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
		return res;
	}
	
	private void closeResource(Connection con, PreparedStatement pstm, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (pstm != null) {
				pstm.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {

		}
	}
	
}
