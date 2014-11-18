package com.travelportal.domain.allotment;

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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.City;
import com.travelportal.domain.rooms.RateMeta;

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
	@Column(name="allocation")
	private int Allocation;
	@Column(name="choose")
	private int choose;
	@Column(name="applyMarket")
	private String applyMarket;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<RateMeta> rate;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<City> cities;
	

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
		

	public List<RateMeta> getRate() {
		return rate;
	}

	public void setRate(List<RateMeta> rate) {
		this.rate = rate;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public String getApplyMarket() {
		return applyMarket;
	}

	public void setApplyMarket(String applyMarket) {
		this.applyMarket = applyMarket;
	}

	public static AllotmentMarket findById(int Code) {
		try
		{
		return (AllotmentMarket) JPA.em().createQuery("select c from AllotmentMarket c where c.allotmentMarketId = ?1").setParameter(1, Code).getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	
	public static AllotmentMarket findByTopid() {
		try
		{
		return (AllotmentMarket) JPA.em().createQuery("select c from AllotmentMarket c where c.allotmentMarketId = (select max(a.allotmentMarketId) from AllotmentMarket a)").getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
public static List<AllotmentMarket> getMarketById(int MarketId,List<Integer> rateid) {/*List<Integer> rateid*/
		
		//try
		//{
			Query q = JPA.em().createQuery("select c from AllotmentMarket c where c.allotmentMarketId = ?1");
			q.setParameter(1, MarketId);
			//q.setParameter(2, rateid);
		
		
			return q.getResultList();
	//	}
	//	catch(Exception ex){
	//		return null;
	//	}
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

	public static AllotmentMarket getAllotmentMarketById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
