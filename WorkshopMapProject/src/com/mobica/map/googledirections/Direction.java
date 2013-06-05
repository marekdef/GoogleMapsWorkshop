package com.mobica.map.googledirections;

import com.google.gson.annotations.SerializedName;

public class Direction {

	@SerializedName("text")
	private String text;
	
	@SerializedName("value")
	private Integer value;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
