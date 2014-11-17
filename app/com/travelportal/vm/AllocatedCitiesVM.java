package com.travelportal.vm;

public class AllocatedCitiesVM {

	public boolean multiSelectGroup;
	public String name;
	public boolean ticked;
	
	public boolean isMultiSelectGroup() {
		return multiSelectGroup;
	}
	public void setMultiSelectGroup(boolean multiSelectGroup) {
		this.multiSelectGroup = multiSelectGroup;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isTicked() {
		return ticked;
	}
	public void setTicked(boolean ticked) {
		this.ticked = ticked;
	}
	
	
}

