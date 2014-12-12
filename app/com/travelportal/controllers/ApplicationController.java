package com.travelportal.controllers;

import java.security.acl.Permission;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;
import javax.persistence.OneToOne;

import com.travelportal.domain.AdminUser;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HearAboutUs;
import com.travelportal.domain.HotelBrands;
import com.travelportal.domain.HotelChain;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.NatureOfBusiness;
import com.travelportal.domain.Permissions;
import com.travelportal.domain.Salutation;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.RateWrapper;
import com.travelportal.vm.AgentRegisVM;
import com.travelportal.vm.HotelSignUpVM;
import com.travelportal.vm.RoomtypeVM;

import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Array;
import views.html.home;

public class ApplicationController extends Controller{

	@Transactional
	public static Result login() {
		System.out.println("SESSION VALUE   "+session().get("SUPPLIER"));
		final String value = session().get("SUPPLIER");
        if (value == null) {
        	return ok(views.html.login.render(" "));
        }
        return ok(views.html.login.render(" "));
        //return ok(home.render("Home Page", Long.parseLong(session().get("SUPPLIER"))));
	}
	
	@Transactional
	public static Result agentlogin() {
		return ok();
	}
	
	@Transactional
	public static Result adminLogin() {
		final String value = session().get("NAME");
        if (value == null) {
        	return ok(views.html.adminLogin.render());
        }
		
        return ok(views.html.adminHome.render());
		
	}
	

	
	@Transactional
	public static Result doAdminLogin() {
		DynamicForm form = DynamicForm.form().bindFromRequest();
		try {
			AdminUser adminUser = AdminUser.doLogin(form.get("name"),form.get("pass"));
			
			if(adminUser != null) {
				session().put("NAME", adminUser.getUserName());
				return ok(views.html.adminHome.render());
			}
		
		} catch(NoResultException e) { }
		System.out.println("SESSION VALUE   "+session().get("NAME"));
		return ok(views.html.adminLogin.render());
	}
	
	@Transactional
	public static Result doAgentLogin() {
		/*DynamicForm form = DynamicForm.form().bindFromRequest();
		try {
			AgentRegistration agentUser = AgentRegistration.doLogin(form.get("code"),form.get("loginId"),form.get("pass"));
			
			if(agentUser != null) {
				session().put("AGENT", String.valueOf(agentUser.getId()));
				long code = agentUser.getId();
				return ok(agentHome.render("Home Page", code));
			}
		
		} catch(NoResultException e) { }
		System.out.println("SESSION VALUE   "+session().get("AGENT"));
		return ok(views.html.agentLogin.render());*/
		
		return ok();
	
	}
	
	@Transactional
	public static Result doSupplierLogin() {
		DynamicForm form = DynamicForm.form().bindFromRequest();
		System.out.println("SESSION VALUE   "+session().get("SUPPLIER"));
		try {
			HotelRegistration user = HotelRegistration.doLogin(form.get("email"),form.get("code"),form.get("pass"),form.get("type"));
			System.out.println(user.getHotelAddress());
			if(user != null) {
				session().put("SUPPLIER", user.getSupplierCode());
				long code = Long.parseLong(user.getSupplierCode());
				HashMap<String , String> permission = new HashMap<>();
		        List<Permissions> permissions = Permissions.getPermission();
		    
		        for(Permissions permissions2 : permissions){
		        	permission.put(permissions2.getName() , String.valueOf(user.getPermissions().contains(permissions2)));
		        }
		        System.out.println(permission);
				return ok(home.render("Home Page", code , Json.stringify(Json.toJson(permission))));  //
			}
		
		} catch(NoResultException e) { }
		System.out.println("SESSION VALUE   "+session().get("SUPPLIER"));
		return ok(views.html.login.render("Invalid Credentials"));
	}
	//

	@Transactional(readOnly=false)
	public static Result findSupplier() {
		DynamicForm form = DynamicForm.form().bindFromRequest();
		System.out.println("-----------------");
		System.out.println(form.get("supplierCode"));
		HotelRegistration user = HotelRegistration.findSupplier(form.get("supplierCode"),form.get("hotelNm"));
		System.out.println(user.getSupplierCode());
		if(user != null) {
			//session().put("SUPPLIER", user.getSupplierCode());
			long code = Long.parseLong(user.getSupplierCode());
			return ok(views.html.adminHome.render());
			//return ok(home.render("Home Page", code));
		}
		return ok(views.html.adminHome.render());
		
	}
	
	@Transactional
	public static Result adminLogout() {
		System.out.println("SESSION VALUE   "+session().get("NAME"));
		session().clear();
		return ok(views.html.adminLogin.render());
	}	
	
	@Transactional
	public static Result supplierLogout() {
		System.out.println("SESSION VALUE   "+session().get("SUPPLIER"));
		session().clear();
		return ok(views.html.login.render(" "));
	}	
	
	
	@Transactional
	public static Result getAgentSignUpForm() {
	
		List<Country> countries = Country.getCountries();
		List<String> countryList = new ArrayList<>();
		for(Country country: countries) {
			countryList.add(country.getCountryName());
		}
		
		List<Salutation> salutations = Salutation.getsalutation();
		List<String> salList = new ArrayList<>();
		for(Salutation salutation: salutations){
			salList.add(salutation.getSalutationValue());
		}
		
		List<HearAboutUs> hearAboutUs = HearAboutUs.gethearAboutUs();
		List<String> hearlist = new ArrayList<>();
		for(HearAboutUs heaUs: hearAboutUs){
			hearlist.add(heaUs.getHearAboutUs());
		}
			
		List<NatureOfBusiness> natureOfBusinesses = NatureOfBusiness.getNatureOfBusiness();
		List<String> natureList = new ArrayList<>();
		for(NatureOfBusiness natBusiness:natureOfBusinesses){
			natureList.add(natBusiness.getNatureofbusiness());
		}
		List<Currency> currencies = Currency.getCurrency();
		List<String> currencyList = new ArrayList<>();
		for(Currency currency : currencies) {
			currencyList.add(currency.getCurrencyName());
		}
		
		
		return ok(views.html.agentsignup.render(countryList,salList,hearlist,natureList,currencyList));
	}
	
	@Transactional
	public static Result getSignUpForm() {
		
		List<Country> countries = Country.getCountries();
		List<String> countryList = new ArrayList<>();
		for(Country country: countries) {
			countryList.add(country.getCountryName());
		}
		
		List<HotelChain> hotelChains = HotelChain.getChainHotels();
		List<String> chainList = new ArrayList<>();
		for(HotelChain hotel: hotelChains) {
			chainList.add(hotel.getChainHotelName());
		}
		
		List<HotelBrands> brands = HotelBrands.gethotelbrand();
		List<String> brandList = new ArrayList<>();
		for(HotelBrands hotel: brands) {
			brandList.add(hotel.getBrandName());
		}
		
		List<Currency> currencies = Currency.getCurrency();
		List<String> currencyList = new ArrayList<>();
		for(Currency currency : currencies) {
			currencyList.add(currency.getCurrencyName());
		}
		
		List<HotelStarRatings> ratings = HotelStarRatings.gethotelStarratings();
		List<String> ratingList = new ArrayList<>();
		for(HotelStarRatings rating: ratings) {
			ratingList.add(rating.getstarRatingTxt());
		}
		
		return ok(views.html.signup.render(countryList, chainList, brandList, currencyList, ratingList));
	}
	
	@Transactional
	public static Result getCityOfCountry() {
		DynamicForm bindedForm = DynamicForm.form().bindFromRequest();
		System.out.println(bindedForm.get("country"));
		String countryName = bindedForm.get("country");
		List<String> cities = new ArrayList<>();
		
			Country country = Country.getCountryByName(countryName);
			cities = City.getCitiesByCountry(country.getCountryCode());
			
		return ok(Json.toJson(cities));
	}
	
	
	@Transactional
	public static Result agentRegister() {
		Form<AgentRegisVM> agForm= Form.form(AgentRegisVM.class).bindFromRequest();
		AgentRegisVM agentVm = agForm.get();
		
		AgentRegistration aRegistration = new AgentRegistration();
		
		aRegistration.setCountry(Country.getCountryByName(agentVm.country));
		aRegistration.setCity(City.getCitiByName(agentVm.city));
		aRegistration.setTitle(Salutation.getSalutationByName(agentVm.title));
		aRegistration.setFirstName(agentVm.firstName);
		aRegistration.setLastName(agentVm.lastName);
		aRegistration.setBusiness(NatureOfBusiness.getNatureOfBusinessByName(agentVm.business));
		aRegistration.setHear(HearAboutUs.getHearAboutUsByName(agentVm.hear));
		aRegistration.setPosition(agentVm.Position);
		aRegistration.setCompanyName(agentVm.companyName);
		aRegistration.setCompanyAddress(agentVm.companyAddress);
		aRegistration.setPostalCode(agentVm.postalCode);
		aRegistration.setPaymentMethod(agentVm.paymentMethod);
		aRegistration.setFinanceEmailAddr(agentVm.financeEmailAddr);
		aRegistration.setEmailAddr(agentVm.EmailAddr);
		aRegistration.setLoginId(agentVm.loginId);
		aRegistration.setDirectCode(agentVm.directCode);
		aRegistration.setDirectTelNo(agentVm.directTelNo);
		aRegistration.setFaxCode(agentVm.faxCode);
		aRegistration.setFaxTelNo(agentVm.faxTelNo);
		aRegistration.setWebSite(agentVm.webSite);
		aRegistration.setCurrency(Currency.getCurrencyByName(agentVm.currency));
		aRegistration.setAgree(agentVm.agree);
		aRegistration.setPassword(agentVm.password);
		aRegistration.setCommission(agentVm.commission);
		aRegistration.setReceiveNet(agentVm.receiveNet);
		
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100000);
		aRegistration.setAgentCode(randomInt);
		aRegistration.setStatus("PENDING");
				
	
		aRegistration.save();
		return ok(views.html.agentLogin.render());
		
	}
	
	@Transactional
	public static Result doRegister() {
		DateFormat format = new SimpleDateFormat("MMddyyyy");
		Form<HotelSignUpVM> HotelForm = Form.form(HotelSignUpVM.class).bindFromRequest();
		HotelSignUpVM hotelSignUpVM = HotelForm.get();

		HotelRegistration register = new HotelRegistration();
		
		register.setCountry(Country.getCountryByName(hotelSignUpVM.country));
		register.setCity(City.getCitiByName(hotelSignUpVM.city));
		register.setCurrency(Currency.getCurrencyByName(hotelSignUpVM.currency));
		register.setHotelAddress(hotelSignUpVM.hotelAddress);
		
		register.setHotelName(hotelSignUpVM.hotelName);
		register.setLaws(Boolean.parseBoolean(hotelSignUpVM.laws));
		register.setPartOfChain(Boolean.parseBoolean(hotelSignUpVM.partOfChain));
		if(hotelSignUpVM.partOfChain.equals("true")) {
			register.setHotelBrand(hotelSignUpVM.hotelBrand);
			register.setChainHotel(hotelSignUpVM.chainHotel);
		}
		register.setPassword(hotelSignUpVM.password);
		register.setPolicy(hotelSignUpVM.policy);
		register.setStarRating(hotelSignUpVM.starRating);
		register.setSupplierName(hotelSignUpVM.supplierName);
		register.setZipcode(hotelSignUpVM.zipcode);
		/*Date date = new Date();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10000);
	register.setSupplierCode(format.format(date).concat(Integer.toString(randomInt)));*/
			
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100000);
		register.setSupplierCode(Integer.toString(randomInt));
		register.setEmail(hotelSignUpVM.getEmail());
		register.setStatus("PENDING");
		register.setSupplierType("Accomodation");
		
		register.save();
		
		return ok(views.html.signupMessage.render());
	}
	
}
