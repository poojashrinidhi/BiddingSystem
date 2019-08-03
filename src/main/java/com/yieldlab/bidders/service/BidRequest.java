package com.yieldlab.bidders.service;

import java.util.Map;

/**
 * Model Class to hold the BidRequest
 * 
 * @author PoojaShankar
 *
 */
public class BidRequest {

	private String id;
	private Map<String, String> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("BidRequest [id=");
		stringBuilder.append(id);
		stringBuilder.append(", attributes=");
		stringBuilder.append(attributes);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}