package com.rays.pro4.Bean;

import java.util.Date;

public class RouteBean extends BaseBean{

	private String name;
	private Date startDate;
	private int allowedSpeed;
	private String vehicleId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getAllowedSpeed() {
		return allowedSpeed;
	}

	public void setAllowedSpeed(int allowedSpeed) {
		this.allowedSpeed = allowedSpeed;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	public String getkey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return startDate + "";
	}

}
