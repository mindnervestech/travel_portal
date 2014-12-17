package com.travelportal.domain.admin;

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
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelHealthAndSafety;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.InternalContacts;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.RoomType;

@Entity
@Table(name="specificMarkup")
public class SpecificMarkup {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int specificMarkupId;
	private long supplierCode;
	@OneToOne
	private AgentRegistration agentSpecific;
	@OneToOne
	private RateMeta rateSelected;
	private String specificSelected;
	private Double specificFlat;
	private Double specificPercent;
	
		
		

	public int getSpecificMarkupId() {
		return specificMarkupId;
	}

	public void setSpecificMarkupId(int specificMarkupId) {
		this.specificMarkupId = specificMarkupId;
	}

	public long getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}

	public AgentRegistration getAgentSpecific() {
		return agentSpecific;
	}

	public void setAgentSpecific(AgentRegistration agentSpecific) {
		this.agentSpecific = agentSpecific;
	}

	public RateMeta getRateSelected() {
		return rateSelected;
	}

	public void setRateSelected(RateMeta rateSelected) {
		this.rateSelected = rateSelected;
	}

	public String getSpecificSelected() {
		return specificSelected;
	}

	public void setSpecificSelected(String specificSelected) {
		this.specificSelected = specificSelected;
	}

	public Double getSpecificFlat() {
		return specificFlat;
	}

	public void setSpecificFlat(Double specificFlat) {
		this.specificFlat = specificFlat;
	}

	public Double getSpecificPercent() {
		return specificPercent;
	}

	public void setSpecificPercent(Double specificPercent) {
		this.specificPercent = specificPercent;
	}
	
public static SpecificMarkup findRateSupplier(AgentRegistration agentid, long supplier) {
    	
    	try
		{
		return (SpecificMarkup) JPA.em().createQuery("select c from SpecificMarkup c where c.supplierCode = ?1 and c.agentSpecific.agentCode = ?2").setParameter(1, supplier).setParameter(2, agentid).getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }

public static List getAllSpecific(long supplierCode) {
	Query query = JPA.em().createQuery("select h from SpecificMarkup h where h.supplierCode ="+supplierCode);
	return query.getResultList();
}

public static SpecificMarkup findBySpecificId(int specificId) {
	
	return (SpecificMarkup) JPA.em().createQuery("select c from SpecificMarkup c where c.specificMarkupId = ?1").setParameter(1, specificId).getSingleResult();
	
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
