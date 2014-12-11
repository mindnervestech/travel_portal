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
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.RoomType;

@Entity
@Table(name="batchMarkup")
public class BatchMarkup {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int batchMarkupId;
	private long supplier;
/*	@OneToOne
	private HotelRegistration supplier;*/
	@OneToOne
	private AgentRegistration agent;
	private String selected;
	private Double flat;
	private Double percent;
	
		
	public int getBatchMarkupId() {
		return batchMarkupId;
	}

	public void setBatchMarkupId(int batchMarkupId) {
		this.batchMarkupId = batchMarkupId;
	}

	
	public long getSupplier() {
		return supplier;
	}

	public void setSupplier(long supplier) {
		this.supplier = supplier;
	}

	public AgentRegistration getAgent() {
		return agent;
	}

	public void setAgent(AgentRegistration agent) {
		this.agent = agent;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Double getFlat() {
		return flat;
	}

	public void setFlat(Double flat) {
		this.flat = flat;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}
	
	
public static BatchMarkup findAgentSupplier(long agentid, long supplier) {
    	
    	try
		{
		return (BatchMarkup) JPA.em().createQuery("select c from BatchMarkup c where c.supplier = ?1 and c.agent.id = ?2").setParameter(1, supplier).setParameter(2, agentid).getSingleResult();
		
		//return (SpecificMarkup) JPA.em().createQuery("select c from SpecificMarkup c where c.supplierCode = ?1 and c.agentSpecific.id = ?3 and c.rateSelected.id = ?2").setParameter(1, supplier).setParameter(2, rateid).setParameter(3, agentid).getSingleResult();
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
