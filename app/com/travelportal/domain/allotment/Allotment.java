package com.travelportal.domain.allotment;

import java.util.ArrayList;
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
import com.travelportal.domain.Rate;
import com.travelportal.domain.rooms.HotelRoomTypes;
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
	@Column(name="DatePeriodId")
	private int DatePeriodId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelRoomTypes roomId;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Currency currencyId;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Rate> rate;
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

	
	public int getDatePeriodId() {
		return DatePeriodId;
	}

	public void setDatePeriodId(int datePeriodId) {
		DatePeriodId = datePeriodId;
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

	public List<Rate> getRate() {
		return rate;
	}

	public void setRate(List<Rate> rate) {
		this.rate = rate;
	}

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
