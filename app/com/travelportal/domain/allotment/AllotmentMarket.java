package com.travelportal.domain.allotment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.rooms.RateMeta;

@Entity
@Table(name="allotmentmarket")
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
	
	@OneToOne
	private RateMeta rate;
	
	//@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	//private List<RateMeta> rate;
	//@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //public List<Country> country;
	

	
	
	/*public List<Country> getCountry() {
		return country;
	}

	public void setCountry(List<Country> country) {
		this.country = country;
	}*/

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
		
/*
	public List<RateMeta> getRate() {
		return rate;
	}

	public void setRate(List<RateMeta> rate) {
		this.rate = rate;
	}*/
	
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
	
	
	

	public RateMeta getRate() {
		return rate;
	}

	public void setRate(RateMeta rate) {
		this.rate = rate;
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
	
	
	public static AllotmentMarket getOneMarket(Long Code) {
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				List<Object[]> list =JPA.em().createNativeQuery("select am.allotmentMarket_Id,am.allocation,am.choose,am.period,am.specifyAllot,am.applyMarket,am.stopAllocation,am.stopChoose,am.stopPeriod,am.fromDate,am.toDate from allotmentmarket am where am.rate_rate_id = '"+Code+"'").getResultList();
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
					if(o[6]!=null){
						am.setStopAllocation(Integer.parseInt(o[6].toString()));
					}
					if(o[7]!=null){
						am.setStopChoose(Integer.parseInt(o[7].toString()));
					}
					if(o[8]!=null){
						am.setStopPeriod(o[7].toString());
					}
					if(o[9]!=null){
						try {
							am.setFromDate(format.parse(o[9].toString()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(o[10]!=null){
						try {
							am.setToDate(format.parse(o[10].toString()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				return am;
	
	   }
	
	
	public static AllotmentMarket findByRateId(Long id) {
		try {
			Query query = JPA.em().createQuery(
					"Select c from AllotmentMarket c where c.rate.id = ?1");
			query.setParameter(1, id);
			return (AllotmentMarket) query.getSingleResult();
		} catch (Exception ex) {
			return null;
		}

	}
	
	public static AllotmentMarket getnationalitywiseMark(int allotId,int nationalityId) {
		
		
				List<Object[]> list =JPA.em().createNativeQuery("select am.allotmentMarket_Id,am.allocation,am.choose from allotmentmarket am,allotmentmarket_country ac where am.allotmentMarket_Id = ac.AllotmentMarket_allotmentMarket_Id and am.allotmentMarket_Id = '"+allotId+"' and ac.country_country_code ='"+nationalityId+"'").getResultList();
				AllotmentMarket am = new AllotmentMarket();
				System.out.println(list);
				if(!list.isEmpty()){
					
					for(Object[] o :list) {
						am.setAllocation(Integer.parseInt(o[1].toString()));
						am.setAllotmentMarketId(Integer.parseInt(o[0].toString()));
						am.setChoose(Integer.parseInt(o[2].toString()));
					}
				
					return am;
				}else{
					
					return null;
				}
	
	   }
	
	public static int deleteAllotmentM(long code) {
		Query q = JPA.em().createNativeQuery("delete from allotmentmarket where rate_rate_id = '"+code+"'");
		return q.executeUpdate();
	}
	
	public static List<AllotmentMarket> getCityWiseMarket(int cityId) {
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<AllotmentMarket> result = new ArrayList<AllotmentMarket>();
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

				result.add(am);
				map.put(Long.parseLong(o[0].toString()), result.size()-1);
			} else {
				AllotmentMarket am = result.get(index);
				RateMeta em  = new RateMeta();
				em.setId(Long.parseLong(o[14].toString()));
				//em.setCurrency(o[11].toString());
				//am.rate.add(em);
			}
			
		}
		
		return result;

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
