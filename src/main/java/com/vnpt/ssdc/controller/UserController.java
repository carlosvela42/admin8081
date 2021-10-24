/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vnpt.ssdc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vnpt.ssdc.dto.Code;
import com.vnpt.ssdc.dto.Packages;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.dto.Users;
import com.vnpt.ssdc.service.CodeService;
import com.vnpt.ssdc.service.PackagesService;
import com.vnpt.ssdc.service.ProductService;
import com.vnpt.ssdc.service.UserService;

/**
 *
 * @author kiendt
 */
@Controller
public class UserController {
	@Autowired
	private ProductService service;
	@Autowired
	private PackagesService packagesService;
	@Autowired
	private UserService userService;
	@Autowired
	private CodeService codeService;
    
    @GetMapping("/user")
    public ModelAndView user() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	ModelAndView mav = new ModelAndView("user");
		
		List<Product> users = service.listAll();
		Product product = new Product();
		mav.addObject("users", users);
		mav.addObject("product", product);
		return mav;
    }
    
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView savePackage(@ModelAttribute("user") Product user) {
    	service.updateProduct(user);
		
		ModelAndView mav = new ModelAndView("user");
		List<Product> users = service.listAll();
		Product product = new Product();
		mav.addObject("users", users);
		mav.addObject("product", product);
		return mav;
	}
    
    @RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public ModelAndView changePass(@ModelAttribute("user") Users user) {
    	boolean res = service.changePass(user);
		
    	ModelAndView mav = new ModelAndView("home");
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
		Users users = userService.selectByEmail(currentPrincipalName);
		List<Product> products = service.selectByEmail(currentPrincipalName);
		List<Code> code = codeService.listAll();
		if(res) {
			users.setCheckPass("1");
		} else {
			users.setCheckPass("0");
		}
		mav.addObject("user", users);
		mav.addObject("code", code);
		mav.addObject("products", products);
		return mav;
	}
    
    @RequestMapping(value = "/saveMachineId", method = RequestMethod.POST)
	public ModelAndView saveMachineId(@ModelAttribute("abc") Product product) {
    	service.saveMachineId(product);
		
    	ModelAndView mav = new ModelAndView("home");
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
		Users users = userService.selectByEmail(currentPrincipalName);
		List<Product> products = service.selectByEmail(currentPrincipalName);
		List<Code> code = codeService.listAll();
		users.setCheckPass("3");
		mav.addObject("user", users);
		mav.addObject("code", code);
		mav.addObject("products", products);
		return mav;
	}
    
    @RequestMapping("/deletetPackage")
	public ModelAndView deletetPackage(@ModelAttribute("user") Users user) {
    	packagesService.delete(user.getId());
    	packagesService.updatePayment(user.getId());
    	ModelAndView mav = new ModelAndView("home");
    	List<Packages> listPackages = packagesService.listAll();
		mav.addObject("packages", listPackages);
		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
		Users users = userService.selectByEmail(currentPrincipalName);
		List<Product> products = service.selectByEmail(currentPrincipalName);
		List<Code> code = codeService.listAll();
		users.setCheckPass("4");
		mav.addObject("user", users);
		mav.addObject("code", code);
		mav.addObject("products", products);
		return mav;		
	}
    
    @RequestMapping("/editUser")
	public ModelAndView editPackage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("edit_user");
		Product productSearch = new Product();
		productSearch.setId(Long.parseLong(request.getParameter("id")));
		List<Product> user = service.listAll(productSearch);
		if(user.size() > 0) {
			mav.addObject("user", user.get(0));
		}
		else mav.addObject("user", new Product());
		
		return mav;
	}
    
    @RequestMapping("/searchUser")
	public ModelAndView searchUser(@ModelAttribute("packages") Product product) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	ModelAndView mav = new ModelAndView("user");
		
		List<Product> users = service.listAll(product);
		mav.addObject("users", users);
		mav.addObject("product", product);
		return mav;	
	}
    
    @RequestMapping("/excel")
	public ModelAndView excel(@ModelAttribute("packages") Product product) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	ModelAndView mav = new ModelAndView("user");
		
		List<Product> users = service.listAll(product);
		mav.addObject("users", users);
		mav.addObject("product", product);
		return mav;	
	}
    
    @RequestMapping("/sendMail")
	public ModelAndView sendMail(HttpServletRequest request) throws UnsupportedEncodingException {    	
    	String requestDecode = URLDecoder.decode(request.getQueryString(), "UTF-8");
    	Map<String, String> requestMap = getQueryMap(requestDecode);
    	String content = requestMap.get("content");
    	String subject = requestMap.get("subject");
    	String listRec = requestMap.get("listRec");
    	
    	userService.sendEmail(content, subject, listRec);
    	
    	ModelAndView mav = new ModelAndView("user");
		Product product = new Product();
		List<Product> users = service.listAll(product);
		mav.addObject("users", users);
		mav.addObject("product", product);	
		return mav;	
	}
    
    public static Map<String, String> getQueryMap(String query) {  
	    String[] params = query.split("&amp;");  
	    Map<String, String> map = new HashMap<String, String>();

	    for (String param : params) {  
	        String name = param.split("=")[0];
	        String value = "";
	        if(param.split("=").length > 1) {
	        	value = param.split("=")[1]; 
	        }
	        
	        map.put(name, value);  
	    }  
	    return map;  
	}
}
