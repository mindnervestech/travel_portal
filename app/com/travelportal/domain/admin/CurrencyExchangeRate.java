package com.travelportal.domain.admin;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.Currency;
import com.travelportal.domain.agent.AgentRegistration;

@Entity
@Table(name="currency_exchange_rate")
public class CurrencyExchangeRate {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String currencyName;
    private Double currencyRate;
	@OneToOne
	private Currency currId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public Currency getCurrId() {
		return currId;
	}

	public void setCurrId(Currency currId) {
		this.currId = currId;
	}
	
	
	public static List<CurrencyExchangeRate> findCurrencyRate(Integer currId) {
		
		try
		{
		return (List<CurrencyExchangeRate>) JPA.em().createQuery("select c from CurrencyExchangeRate c where c.currId.id = ?1").setParameter(1, currId).getResultList();
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
