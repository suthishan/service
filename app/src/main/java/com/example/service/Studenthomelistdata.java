package com.example.service;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Studenthomelistdata implements Serializable {

	@SerializedName("view_data")
	public List<StudenthomeViewDataItem> viewData;
}