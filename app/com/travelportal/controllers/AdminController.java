package com.travelportal.controllers;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import play.Play;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.MarketPolicyTypes;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.HotelRegistrationVM;

public class AdminController extends Controller {
	
	final static String username=Play.application().configuration().getString("supportUser");
    final static String password=Play.application().configuration().getString("supportPassword");

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
	public static Result getBlockUsers() {
		List<HotelRegistration> list = HotelRegistration.getAllBlockUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result approveUser(Long id,String email,Long supplierCode, String perferHotel) {
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
			hotelProfile.setHoteBrands(register.getHotelBrand());
			hotelProfile.setChainHotel(register.getChainHotel());
		} else {
			hotelProfile.setPartOfChain("false");
		}
		
		hotelProfile.setHotelEmailAddr(register.getEmail());
		hotelProfile.setMarketPolicyType(MarketPolicyTypes.getMarketPolicyByName(register.getPolicy()));
		hotelProfile.setPassword(register.getPassword());
		hotelProfile.setStartRatings(HotelStarRatings.getHotelRatingsByName(register.getStarRating()));
		hotelProfile.setLaws(register.isLaws());
		hotelProfile.setZipCode(register.getZipcode());
		if(perferHotel.equals("true")){
			hotelProfile.setPerfer("perfer");
		}else{
			hotelProfile.setPerfer("test_first");
		}
		
		hotelProfile.setStatus("APPROVED");
		hotelProfile.save();
		
		
		}
		
	//	final String username=Play.application().configuration().getString("username");
     //   final String password=Play.application().configuration().getString("password");
		
		
		  Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", "smtp.checkinrooms.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
	        try
			{
				//MimeBodyPart attachPart = new MimeBodyPart();
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username,"CheckInRooms"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(email));
				message.setSubject("Approved User");
				Multipart multipart = new MimeMultipart();
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart = new MimeBodyPart();
				
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute" );
				ve.setProperty("runtime.log.logsystem.log4j.logger","clientService");
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.init();
			
				
		        Template t = ve.getTemplate("/public/emailTemplet/approvedNewSuppMail.vm"); 
		        VelocityContext context = new VelocityContext();
		        context.put("hotelEmail", register.getEmail());
		        context.put("supplierCode", register.getSupplierCode());
		        context.put("password", register.getPassword());
		        
		        
		        StringWriter writer = new StringWriter();
		        t.merge( context, writer );
		        String content = writer.toString(); 
				
				messageBodyPart.setContent(content, "text/html");
				multipart.addBodyPart(messageBodyPart);
				
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("Sent test message successfully....");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			} 
	
	 			
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
	public static Result perferUser(Long id) {
		
		HotelProfile hotelp = HotelProfile.findById(id);
		
		hotelp.setPerfer("perfer");
		hotelp.merge();
		
		return ok();
	}
	
	
	@Transactional
	public static Result pendingUser(Long id) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("PENDING");
		register.merge();
		return ok();
	}
	
	@Transactional
	public static Result blockUser(Long id) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("BLOCK");
		register.merge();
		
		HotelProfile hotelp = HotelProfile.findById(Integer.parseInt(register.getSupplierCode()));
		
		if(hotelp == null){			
			hotelp.setStatus("BLOCK");
			hotelp.merge();
		}
		
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
			AgentRegistrationVM agentRegisVM = new AgentRegistrationVM(hotel);
			vm.add(agentRegisVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	
	@Transactional
	public static Result creditLimitUpdate(String agentId, String creditLimit){
		
		AgentRegistration aRegistration = AgentRegistration.getAgentCode(agentId);
		if(aRegistration.getCreditLimit() == null && aRegistration.getAvailableLimit() == null){
			aRegistration.setCreditLimit(0d);
			aRegistration.setAvailableLimit(0d);
		}
		Double cLimit = aRegistration.getCreditLimit() + Double.parseDouble(creditLimit);
		Double aLimit = aRegistration.getAvailableLimit() + Double.parseDouble(creditLimit);
		aRegistration.setCreditLimit(cLimit);
		aRegistration.setAvailableLimit(aLimit);
		aRegistration.merge();
		return ok();
		
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
	public static Result getBlockAgent() {
		List<AgentRegistration> list = AgentRegistration.getAllBlockAgent();
		List<AgentRegistrationVM> vm = new ArrayList<>();
		for(AgentRegistration hotel : list) {
			AgentRegistrationVM agentRegisVM = new AgentRegistrationVM(hotel);
			vm.add(agentRegisVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	
	@Transactional
	public static Result approveAgent(Long id,String email,Long agentCode,Long creditLimit) {
		AgentRegistration register = AgentRegistration.findById(id);
		register.setStatus("APPROVED");
		register.setCreditLimit(creditLimit.doubleValue());
		register.setAvailableLimit(creditLimit.doubleValue());
		register.merge();
        
        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.checkinrooms.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
        try
		{
			//MimeBodyPart attachPart = new MimeBodyPart();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username,"CheckInRooms"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("Approved Agent");
			Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart = new MimeBodyPart();
			
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute" );
			ve.setProperty("runtime.log.logsystem.log4j.logger","clientService");
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
		
			
	        Template t = ve.getTemplate("/public/emailTemplet/approvedNewAgentMail.vm"); 
	        VelocityContext context = new VelocityContext();
	        context.put("agentEmail", register.getEmailAddr());
	        context.put("agentCode", register.getAgentCode());
	        context.put("password", register.getPassword());
	        
	        StringWriter writer = new StringWriter();
	        t.merge( context, writer );
	        String content = writer.toString(); 
			
			messageBodyPart.setContent(content, "text/html");
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Sent test message successfully....");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	 			
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
	
	
	@Transactional
	public static Result blockresultAgent(Long id) {
		AgentRegistration register = AgentRegistration.findById(id);
		register.setStatus("BLOCK");
		register.merge();
		return ok();
	}
	
}
