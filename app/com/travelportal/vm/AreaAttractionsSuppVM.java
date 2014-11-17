package com.travelportal.vm;

import java.util.List;

public class AreaAttractionsSuppVM {
	
	
	public long supplierCode;
	public List<AreaAttractionsVM> areaInfo;
	
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
