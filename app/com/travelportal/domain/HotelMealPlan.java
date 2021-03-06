package com.travelportal.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.rooms.ChildPolicies;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;

@Entity
@Table(name="hotel_meal_plan")
public class HotelMealPlan { //supplier specific records...
	/*@JoinColumn(name="supplier_code")
	@OneToOne
	private SupplierCode supplier_code;*/
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	private MealType mealType;
	@OneToMany
	private List<ChildPolicies> child;
	@Column(name="meal_plan_nm")
	private String mealPlanNm;
	@Column(name="restaurants")
	private String restaurants;
	@Column(name="from_period")
	private Date fromPeriod;
	@Column(name="to_period")
	private Date toPeriod;
	@Column(name="rate")
	private Double rate;
	@Column(name="guest_type")
	private String guestType; //adult or child...
	@Column(name="tax_included")
	private String taxIncluded; //flag to show whether the taz is included or not in rate given.
	public String getTaxIncluded() {
		return taxIncluded;
	}

	@Column(name="age_criteria")
	private String ageCriteria;
	private long supplierCode;
	@Column(name="taxvalue")
	private Double taxvalue;
	@Column(name="taxtype")
	private String taxtype;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	/**
	 * @return the mealPlanNm
	 */
	public String getMealPlanNm() {
		return mealPlanNm;
	}
	/**
	 * @param mealPlanNm the mealPlanNm to set
	 */
	public void setMealPlanNm(String mealPlanNm) {
		this.mealPlanNm = mealPlanNm;
	}
	public Double getTaxvalue() {
		return taxvalue;
	}
	public void setTaxvalue(Double taxvalue) {
		this.taxvalue = taxvalue;
	}
	
	
	public String getTaxtype() {
		return taxtype;
	}
	public void setTaxtype(String taxtype) {
		this.taxtype = taxtype;
	}
	public String isTaxIncluded() {
		return taxIncluded;
	}
	public void setTaxIncluded(String taxIncluded) {
		this.taxIncluded = taxIncluded;
	}
	/**
	 * @return the fromPeriod
	 */
	public Date getFromPeriod() {
		return fromPeriod;
	}
	/**
	 * @param fromPeriod the fromPeriod to set
	 */
	public void setFromPeriod(Date fromPeriod) {
		this.fromPeriod = fromPeriod;
	}
	/**
	 * @return the toPeriod
	 */
	public Date getToPeriod() {
		return toPeriod;
	}
	/**
	 * @param toPeriod the toPeriod to set
	 */
	public void setToPeriod(Date toPeriod) {
		this.toPeriod = toPeriod;
	}
	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/**
	 * @return the guestType
	 */
	public String getGuestType() {
		return guestType;
	}
	/**
	 * @param guestType the guestType to set
	 */
	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}
	/**
	 * @return the ageCriteria
	 */
	public String getAgeCriteria() {
		return ageCriteria;
	}
	/**
	 * @param ageCriteria the ageCriteria to set
	 */
	public void setAgeCriteria(String ageCriteria) {
		this.ageCriteria = ageCriteria;
	}
	/**
	 * @return the mealType
	 */
	public MealType getMealType() {
		return mealType;
	}
	/**
	 * @param mealType the mealType to set
	 */
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	
	public String getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(String restaurants) {
		this.restaurants = restaurants;
	}
	public List<ChildPolicies> getChild() {
		return child;
	}
	public void setChild(List<ChildPolicies> child) {
		this.child = child;
	}
	public void addChild(ChildPolicies child) {
		if(this.child == null){
			this.child = new ArrayList<>();
		}
		if(!this.child.contains(child))
		this.child.add(child);
	}
	public static List<HotelMealPlan> getmealtype(long supplierCode) {
		return JPA.em().createQuery("select c from HotelMealPlan c where c.supplierCode = ?1").setParameter(1, supplierCode).getResultList();
	}
	
	//findById
	 public static HotelMealPlan findById(int id) {
	    	Query query = JPA.em().createQuery("Select a from HotelMealPlan a where a.id = ?1");
			query.setParameter(1, id);
	    	return (HotelMealPlan) query.getSingleResult();
	    }
	 
	 
	
	public static HotelMealPlan getHotelMealPlanIdByCode(int code) {
		return (HotelMealPlan) JPA.em().createQuery("select c from HotelMealPlan c where id = ?1").setParameter(1, code).getSingleResult();
	}
	
	public static int getHotelMealCompulsory(int code,Date date) {
		
		try{
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
		 String SDate = null;
		
			SDate = format.format(date);
		 
			Integer mealId;
			return mealId =(Integer) JPA.em().createNativeQuery("select hmp.id from hotel_meal_plan hmp where '"+SDate+"'  between hmp.from_period and hmp.to_period and id = '"+code+"'").getSingleResult();
			
		//return (HotelMealPlan) JPA.em().createQuery("select c from HotelMealPlan c where ?2 between toPeriod and fromPeriod and id = ?1").setParameter(1, code).setParameter(2, SDate).getSingleResult();
		}
		catch(Exception ex){
			return 0;
		}
		
	
	}

	
	@Transactional
    public void save() {
		JPA.em().persist(this);
        JPA.em().flush();     
    }
      
    @Transactional
    public void delete() {
        JPA.em().remove(this);
    }
    
    @Transactional
    public void merge() {
        JPA.em().merge(this);
    }
    
    @Transactional
    public void refresh() {
        JPA.em().refresh(this);
    }
}
