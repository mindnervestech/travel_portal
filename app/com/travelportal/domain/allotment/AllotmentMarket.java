package com.travelportal.domain.allotment;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
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
	@Column(name="stopAllocation")
	private int stopAllocation;
	@Column(name="stopChoose")
	private int stopChoose;
	@Column(name="stopPeriod")
	private String stopPeriod;
	@Column(name="fromDate")
	private Date fromDate;
	@Column(name="toDate")
	private Date toDate;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<RateMeta> rate;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Country> country;
	

	
	
	public List<Country> getCountry() {
		return country;
	}

	public void setCountry(List<Country> country) {
		this.country = country;
	}

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
	
	public String getApplyMarket() {
		return applyMarket;
	}

	public void setApplyMarket(String applyMarket) {
		this.applyMarket = applyMarket;
	}

	public int getStopAllocation() {
		return stopAllocation;
	}

	public void setStopAllocation(int stopAllocation) {
		this.stopAllocation = stopAllocation;
	}

	public int getStopChoose() {
		return stopChoose;
	}

	public void setStopChoose(int stopChoose) {
		this.stopChoose = stopChoose;
	}

	public String getStopPeriod() {
		return stopPeriod;
	}

	public void setStopPeriod(String stopPeriod) {
		this.stopPeriod = stopPeriod;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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
	
	//return (AllotmentMarket) JPA.em().createNativeQuery("select * from allotmentmarket_rate_meta where rate_rate_id = '"+Code+"'").getSingleResult();
	
	public static AllotmentMarket getOneMarket(Long Code) {
	
				List<Object[]> list =JPA.em().createNativeQuery("select * from allotmentmarket am,allotmentmarket_rate_meta arm where arm.AllotmentMarket_allotmentMarket_Id = am.allotmentMarket_Id and arm.rate_rate_id = '"+Code+"'").getResultList();
				AllotmentMarket am = new AllotmentMarket();
				for(Object[] o :list) {
					am.setAllocation(Integer.parseInt(o[1].toString()));
					am.setAllotmentMarketId(Integer.parseInt(o[0].toString()));
					am.setChoose(Integer.parseInt(o[2].toString()));
					if(o[3] != null){
					am.setPeriod(o[3].toString());
					}
					if(o[4] != null){		
					am.setSpecifyAllot(o[4].toString());
					}
					if(o[5] != null){
					am.setApplyMarket(o[5].toString());
					}
				}
				
				return am;
	
	   }
	
	public static List<AllotmentMarket> getCityWiseMarket(int cityId) {
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<AllotmentMarket > result = new ArrayList<AllotmentMarket>();
		List<Object[]> list =JPA.em().createNativeQuery("select * from allotmentmarket am,allotmentmarket_city arm,allotmentmarket_rate_meta allmark,rate_meta rm where arm.AllotmentMarket_allotmentMarket_Id = am.allotmentMarket_Id and am.allotmentMarket_Id = allmark.AllotmentMarket_allotmentMarket_Id  and arm.cities_city_code = "+cityId+" and allmark.AllotmentMarket_allotmentMarket_Id=am.allotmentMarket_Id").getResultList();
		Map<Long,Integer> map = new HashMap<Long,Integer>(); 
		for(Object[] o :list) {
			Integer index = map.get(Long.parseLong(o[0].toString()));
			if(index == null) {
				AllotmentMarket am = new AllotmentMarket();
				am.setAllocation(Integer.parseInt(o[1].toString()));
				am.setAllotmentMarketId(Integer.parseInt(o[0].toString()));
				am.setChoose(Integer.parseInt(o[2].toString()));
				if(o[3] != null){
					am.setPeriod(o[3].toString());
				}
				if(o[4] != null){		
					am.setSpecifyAllot(o[4].toString());
				}
				if(o[5] != null){
					am.setApplyMarket(o[5].toString());
				}
				if(o[14] != null) {
					am.rate = new ArrayList<RateMeta>();
					RateMeta em  = new RateMeta();
					em.setId(Long.parseLong(o[14].toString()));
					em.setCurrency(o[16].toString());
					try {
						em.setFromDate(format.parse(o[17].toString()));
						em.setToDate(format.parse(o[19].toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					em.setRateName(o[18].toString());
					em.setSupplierCode(Long.parseLong(o[21].toString()));
					am.rate.add(em);
				}
				result.add(am);
				map.put(Long.parseLong(o[0].toString()), result.size()-1);
			} else {
				AllotmentMarket am = result.get(index);
				RateMeta em  = new RateMeta();
				em.setId(Long.parseLong(o[14].toString()));
				//em.setCurrency(o[11].toString());
				am.rate.add(em);
			}
			
		}
		
		return result;

}
	//List<AllotmentMarket> list =JPA.em().createNativeQuery("select * from allotmentmarket am,allotmentmarket_city arm,allotmentmarket_rate_meta allmark where arm.AllotmentMarket_allotmentMarket_Id = am.allotmentMarket_Id and am.allotmentMarket_Id = allmark.AllotmentMarket_allotmentMarket_Id and arm.cities_city_code = '"+cityId+"'",AllotmentMarket.class).getResultList();
	
	
	public static AllotmentMarket findByTopid() {
		try
		{
		return (AllotmentMarket) JPA.em().createQuery("select c from AllotmentMarket c where c.allotmentMarketId = (select max(a.allotmentMarketId) from AllotmentMarket a)").getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	
public static List<AllotmentMarket> getMarketById(int MarketId,List<Integer> rateid) {
		
	
			Query q = JPA.em().createQuery("select c from AllotmentMarket c where c.allotmentMarketId = ?1");
			q.setParameter(1, MarketId);
		
			return q.getResultList();
	
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
