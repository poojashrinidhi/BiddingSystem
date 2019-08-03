package com.yieldlab.bidders.service;

/**
 * Model Class to hold the Bid Response
 * 
 * @author PoojaShankar
 *
 */
public class BidResponse {

	private String id;
	private Integer bid;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("BidResponse [id=");
		stringBuilder.append(id);
		stringBuilder.append(", bid=");
		stringBuilder.append(bid);
		stringBuilder.append(", content=");
		stringBuilder.append(content);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}