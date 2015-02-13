package com.travelportal.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelBrands;
import com.travelportal.domain.HotelChain;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.MarketPolicyTypes;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.vm.AgentRegisVM;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.HotelRegistrationVM;

import play.Play;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class AdminController extends Controller {

	@Transactional
	public static Result getPendingUsers() {
		List<HotelRegistration> list = HotelRegistration.getAllPendingUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result getApprovedUsers() {
		List<HotelRegistration> list = HotelRegistration.getAllApprovedUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result getRejectedUsers() {
		List<HotelRegistration> list = HotelRegistration.getAllRejectedUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result approveUser(Long id,String email,Long supplierCode) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("APPROVED");
		register.merge();
		
		
		HotelProfile hotelp = HotelProfile.findById(supplierCode);
		
		if(hotelp == null){			
		
			
		HotelProfile hotelProfile = new HotelProfile();
		
		hotelProfile.setSupplier_code(Long.parseLong(register.getSupplierCode()));
		hotelProfile.setHotelName(register.getHotelName());
		hotelProfile.setSupplierName(register.getSupplierName());
		hotelProfile.setAddress(register.getHotelAddress());
		hotelProfile.setCountry(register.getCountry());
		hotelProfile.setCurrency(register.getCurrency());
		hotelProfile.setCity(register.getCity());
		if(register.isPartOfChain()) {
			hotelProfile.setPartOfChain("true");
			hotelProfile.setHoteBrands(HotelBrands.getHotelBrandsByName(register.getHotelBrand()));
			hotelProfile.setChainHotel(HotelChain.getHotelChainByName(register.getChainHotel()));
		} else {
			hotelProfile.setPartOfChain("false");
		}
		
		hotelProfile.setHotelEmailAddr(register.getEmail());
		hotelProfile.setMarketPolicyType(MarketPolicyTypes.getMarketPolicyByName(register.getPolicy()));
		hotelProfile.setPassword(register.getPassword());
		hotelProfile.setStartRatings(HotelStarRatings.getHotelRatingsByName(register.getStarRating()));
		hotelProfile.setLaws(register.isLaws());
		hotelProfile.setZipCode(register.getZipcode());
		hotelProfile.save();
		
		
		}
		
		
	/*	final String username=Play.application().configuration().getString("username");
	        final String password=Play.application().configuration().getString("password");
	        
	 		Properties props = new Properties();
	 		props.put("mail.smtp.auth", "true");
	 		props.put("mail.smtp.starttls.enable", "true");
	 		props.put("mail.smtp.host", "smtp.gmail.com");
	 		props.put("mail.smtp.port", "587");
	  
	 		Session session = Session.getInstance(props,
	 		  new javax.mail.Authenticator() {
	 			protected PasswordAuthentication getPasswordAuthentication() {
	 				return new PasswordAuthentication(username, password);
	 			}
	 		  });
	  
	 		try{
	 		   
	  			Message feedback = new MimeMessage(session);
	  			feedback.setFrom(new InternetAddress(username));
	  			feedback.setRecipients(Message.RecipientType.TO,
	  			InternetAddress.parse(email));
	  			feedback.setSubject("You Approved For travel_portal");	  			
	  			 BodyPart messageBodyPart = new MimeBodyPart();	  	       
	  	         messageBodyPart.setText("You Approved For travel_portal \n Your Supplier Code : "+supplierCode);	  	    
	  	         Multipart multipart = new MimeMultipart();	  	    
	  	         multipart.addBodyPart(messageBodyPart);	            
	  	         feedback.setContent(multipart);
	  		     Transport.send(feedback);
	       		} catch (MessagingException e) {
	  			  throw new RuntimeException(e);
	  		}*/
	 		
	 			
		return ok();
	}
	
	@Transactional
	public static Result rejectUser(Long id) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("REJECTED");
		register.merge();
		return ok();
	}
	
	@Transactional
	public static Result pendingUser(Long id) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("PENDING");
		register.merge();
		return ok();
	}
	
	/*---------------------------------Agent-----------------------------------------*/
	
	
	@Transactional
	public static Result getPendingAgent() {
		List<AgentRegistration> list = AgentRegistration.getAllPendingAgent();
		List<AgentRegistrationVM> vm = new ArrayList<>();
		for(AgentRegistration hotel : list) {
			AgentRegistrationVM agentRegisVM = new AgentRegistrationVM(hotel);
			vm.add(agentRegisVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result getApprovedAgent() {
		List<AgentRegistration> list = AgentRegistration.getApprovedAgent();
		List<AgentRegistrationVM> vm = new ArrayList<>();
		for(AgentRegistration hotel : list) {
			System.out.println("9090909090909090909");
			AgentRegistrationVM agentRegisVM = new AgentRegistrationVM(hotel);
			vm.add(agentRegisVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result getRejectedAgent() {
		List<AgentRegistration> list = AgentRegistration.getAllRejectedAgent();
		List<AgentRegistrationVM> vm = new ArrayList<>();
		for(AgentRegistration hotel : list) {
			AgentRegistrationVM agentRegisVM = new AgentRegistrationVM(hotel);
			vm.add(agentRegisVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	
	@Transactional
	public static Result approveAgent(Long id,String email,Long agentCode) {
		AgentRegistration register = AgentRegistration.findById(id);
		register.setStatus("APPROVED");
		register.merge();
		
		
		/*HotelProfile hotelp = HotelProfile.findById(supplierCode);
		
		if(hotelp == null){			
		
			
		HotelProfile hotelProfile = new HotelProfile();
		
		hotelProfile.setSupplier_code(Long.parseLong(register.getSupplierCode()));
		hotelProfile.setHotelName(register.getHotelName());
		hotelProfile.setSupplierName(register.getSupplierName());
		hotelProfile.setAddress(register.getHotelAddress());
		hotelProfile.setCountry(register.getCountry());
		hotelProfile.setCurrency(register.getCurrency());
		hotelProfile.setCity(register.getCity());
		if(register.isPartOfChain()) {
			hotelProfile.setPartOfChain("true");
			hotelProfile.setHoteBrands(HotelBrands.getHotelBrandsByName(register.getHotelBrand()));
			hotelProfile.setChainHotel(HotelChain.getHotelChainByName(register.getChainHotel()));
		} else {
			hotelProfile.setPartOfChain("false");
		}
		
		hotelProfile.setHotelEmailAddr(register.getEmail());
		hotelProfile.setMarketPolicyType(MarketPolicyTypes.getMarketPolicyByName(register.getPolicy()));
		hotelProfile.setPassword(register.getPassword());
		hotelProfile.setStartRatings(HotelStarRatings.getHotelRatingsByName(register.getStarRating()));
		hotelProfile.setLaws(register.isLaws());
		hotelProfile.setZipCode(register.getZipcode());
		hotelProfile.save();
		
		
		}
		*/
		
		/*final String username=Play.application().configuration().getString("username");
	        final String password=Play.application().configuration().getString("password");
	        
	 		Properties props = new Properties();
	 		props.put("mail.smtp.auth", "true");
	 		props.put("mail.smtp.starttls.enable", "true");
	 		props.put("mail.smtp.host", "smtp.gmail.com");
	 		props.put("mail.smtp.port", "587");
	  
	 		Session session = Session.getInstance(props,
	 		  new javax.mail.Authenticator() {
	 			protected PasswordAuthentication getPasswordAuthentication() {
	 				return new PasswordAuthentication(username, password);
	 			}
	 		  });
	  
	 		try{
	 		   
	  			Message feedback = new MimeMessage(session);
	  			feedback.setFrom(new InternetAddress(username));
	  			feedback.setRecipients(Message.RecipientType.TO,
	  			InternetAddress.parse(email));
	  			feedback.setSubject("You Approved For travel_portal");	  			
	  			 BodyPart messageBodyPart = new MimeBodyPart();	  	       
	  	         messageBodyPart.setText("You Approved For travel_portal \n Your Agent code : "+agentCode);	  	    
	  	         Multipart multipart = new MimeMultipart();	  	    
	  	         multipart.addBodyPart(messageBodyPart);	            
	  	         feedback.setContent(multipart);
	  		     Transport.send(feedback);
	       		} catch (MessagingException e) {
	  			  throw new RuntimeException(e);
	  		}*/
	 		
	 			
		return ok();
	}
	
	@Transactional
	public static Result rejectAgent(Long id) {
		AgentRegistration register = AgentRegistration.findById(id);
		register.setStatus("REJECTED");
		register.merge();
		return ok();
	}
	
	@Transactional
	public static Result pendingAgent(Long id) {
		AgentRegistration register = AgentRegistration.findById(id);
		register.setStatus("PENDING");
		register.merge();
		return ok();
	}
	
}
