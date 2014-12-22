package com.travelportal.domain.allotment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelHealthAndSafety;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.RoomType;

@Entity
@Table(name="Allotment")
public class Allotment {
	@Column(name="allotment_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int allotmentId;
	@Column(name="supplierCode")
	private Long supplierCode;
	@Column(name="formDate")
	private Date formDate;
	@Column(name="toDate")
	private Date toDate;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelRoomTypes roomId;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Currency currencyId;
	
	/*@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<RateMeta> rate;*/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AllotmentMarket> allotmentmarket;
	
	
	public int getAllotmentId() {
		return allotmentId;
	}

	public void setAllotmentId(int allotmentId) {
		this.allotmentId = allotmentId;
	}

	public Long getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}

	

	public Date getFormDate() {
		return formDate;
	}

	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Currency getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Currency currencyId) {
		this.currencyId = currencyId;
	}
	public HotelRoomTypes getRoomId() {
		return roomId;
	}

	public void setRoomId(HotelRoomTypes roomId) {
		this.roomId = roomId;
	}

	/*public List<RateMeta> getRate() {
		return rate;
	}

	public void setRate(List<RateMeta> rate) {
		this.rate = rate;
	}*/

	public List<AllotmentMarket> getAllotmentmarket() {
		return allotmentmarket;
	}

	public void setAllotmentmarket(List<AllotmentMarket> allotmentmarket) {
		this.allotmentmarket = allotmentmarket;
	}
	
	public void addAllotmentmarket(AllotmentMarket allotmentmarket) {
		if(this.allotmentmarket == null){
			this.allotmentmarket = new ArrayList<>();
		}
		if(!this.allotmentmarket.contains(allotmentmarket))
		this.allotmentmarket.add(allotmentmarket);
	}
	
	
	

	public static Allotment findById(Long supplierCode) {
		try
		{
		return (Allotment) JPA.em().createQuery("select c from Allotment c where c.supplierCode = ?1").setParameter(1, supplierCode).getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	public static Allotment getRateById(Long supplierCode,Date formDate,Date toDate,String currId,Long roomId) {/*List<Integer> rateid*/
		
	/*Calendar c = Calendar.getInstance();
	c.setTime(formDate);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	formDate = c.getTime();
	c.setTime(toDate);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	toDate = c.getTime();
	System.out.println("|||||||");
	*/	System.out.println(formDate);
		System.out.println(toDate);
		
		
		try
		{
			Query q = JPA.em().createQuery("select c from Allotment c where c.supplierCode = ?1 and c.formDate = ?2 and c.toDate = ?3 and c.currencyId.currencyName = ?4 and c.roomId.roomId = ?5");
			q.setParameter(1, supplierCode);
			q.setParameter(2, formDate);
			q.setParameter(3, toDate);
			q.setParameter(4, currId);
			q.setParameter(5, roomId);
			return(Allotment) q.getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
   }

	
	public static Allotment allotmentfindById(int Code) {
		try
		{
		return (Allotment) JPA.em().createQuery("select c from Allotment c where c.allotmentId = ?1").setParameter(1, Code).getSingleResult();
		}
		catch(Exception ex){
			return null;
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
