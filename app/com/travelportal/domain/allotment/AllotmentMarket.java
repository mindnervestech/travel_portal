package com.travelportal.domain.allotment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="AllotmentMarket")
public class AllotmentMarket {
	
	@Column(name="allotmentMarket_Id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int allotmentMarketId;
	@Column(name="period")
	private String period;
	@Column(name="specifyAllot")
	private String specifyAllot;
	@Column(name="Allocation")
	private int Allocation;
	@Column(name="choose")
	private int choose;
	
	
	
	

	public int getAllotmentMarketId() {
		return allotmentMarketId;
	}

	public void setAllotmentMarketId(int allotmentMarketId) {
		this.allotmentMarketId = allotmentMarketId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getSpecifyAllot() {
		return specifyAllot;
	}

	public void setSpecifyAllot(String specifyAllot) {
		this.specifyAllot = specifyAllot;
	}

	public int getAllocation() {
		return Allocation;
	}

	public void setAllocation(int allocation) {
		Allocation = allocation;
	}

	public int getChoose() {
		return choose;
	}

	public void setChoose(int choose) {
		this.choose = choose;
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
