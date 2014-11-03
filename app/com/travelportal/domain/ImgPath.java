package com.travelportal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="image_path")
public class ImgPath { //Seed table.
	@Column(name="img_path_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int imgpathId;
	@Column(name="img_path")
	private String imgpath;
	@Column(name="doc_name")
	private String docname;
	@Column(name="date_Time")
	private Date datetime;
	
	
	
	public int getImgpathId() {
		return imgpathId;
	}

	public void setImgpathId(int imgpathId) {
		this.imgpathId = imgpathId;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	
	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
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
