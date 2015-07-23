package com.travelportal.domain.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.travelportal.domain.HotelBookingDetails;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="booking_documents")
public class BookingDocument { 
	
		
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int doc_id;
	@Column(name="doc_path")
	private String docpath;
	@Column(name="doc_name")
	private String docname;
	@OneToOne
	private HotelBookingDetails hotelbooingId;
	
	
	public int getDoc_id() {
		return doc_id;
	}


	public void setDoc_id(int doc_id) {
		this.doc_id = doc_id;
	}


	public String getDocpath() {
		return docpath;
	}


	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}


	public String getDocname() {
		return docname;
	}


	public void setDocname(String docname) {
		this.docname = docname;
	}


	public HotelBookingDetails getHotelbooingId() {
		return hotelbooingId;
	}


	public void setHotelbooingId(HotelBookingDetails hotelbooingId) {
		this.hotelbooingId = hotelbooingId;
	}


	public static BookingDocument getImagPathById(int id) {
    	Query query = JPA.em(). createQuery("select p from BookingDocument p where p.doc_id = ?1");
		query.setParameter(1, id);
    	return (BookingDocument) query.getSingleResult();
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
