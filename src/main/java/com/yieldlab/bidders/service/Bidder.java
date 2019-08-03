package com.yieldlab.bidders.service;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author PoojaShankar
 */
@Component
@ConfigurationProperties("bidders")
public class Bidder {

	private List<String> urls;

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Bidder [urls=");
		stringBuilder.append(urls);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}