package com.travelportal.vm;

import java.util.Date;
import java.util.List;

public class AreaAttractionsSuppVM {
	
	
	private long supplierCode;
	private List<AreaAttractionsVM> areaInfo;
	
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public List<AreaAttractionsVM> getAreaInfo() {
		return areaInfo;
	}
	public void setAreaInfo(List<AreaAttractionsVM> areaInfo) {
		this.areaInfo = areaInfo;
	}
	
	
	
}
