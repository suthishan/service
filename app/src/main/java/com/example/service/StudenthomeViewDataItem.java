package com.example.service;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudenthomeViewDataItem implements Serializable {

	@SerializedName("Collegename")
	public String college_name;

	@SerializedName("Department")
	public String department;

	@SerializedName("Eventame")
	public String event_name;

	@SerializedName("Eventdate")
	public String event_date;

	@SerializedName("Lastreg")
	public String registration_date;


	@SerializedName("City")
	public String team_size;
}