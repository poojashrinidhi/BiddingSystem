package com.yieldlab.bidders.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.yieldlab.exception.BidderResponseNullException;
import com.yieldlab.exception.BiddersNotFoundException;

/**
 * @author PoojaShankar
 * 
 * Code Coverage : 91.1% (Eclemma)
 */
@SuppressWarnings("unchecked")
public class BidderServiceTest {

	@Mock
	private Bidder bidder;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private BidderService biddingService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPostRequestSuccess()
			throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(restTemplate.postForEntity(any(), any(), any())).thenReturn(getBidder1Response(), getBidder2Response());
		when(bidder.getUrls()).thenReturn(getBidderUrls());
		String response = biddingService.postRequest("id", getMap());
		assertTrue(response.equalsIgnoreCase("b:1000"));
	}

	@Test(expected = BiddersNotFoundException.class)
	public void testPostRequestWithNoBidders()
			throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(restTemplate.postForEntity(any(), any(), any())).thenReturn(getBidder1Response(), getBidder2Response());
		when(bidder.getUrls()).thenReturn(null);
		biddingService.postRequest("id", getMap());
	}

	@Test(expected = URISyntaxException.class)
	public void testPostRequestWithInvalidUrl()
			throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(restTemplate.postForEntity(any(), any(), any())).thenReturn(getBidder1Response(), getBidder2Response());
		when(bidder.getUrls()).thenReturn(Arrays.asList("wrong url"));
		biddingService.postRequest("id", getMap());
	}

	@Test(expected = BidderResponseNullException.class)
	public void testPostRequestNullBidResponse()
			throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(restTemplate.postForEntity(any(), any(), any())).thenReturn(null, null);
		when(bidder.getUrls()).thenReturn(getBidderUrls());
		biddingService.postRequest("id", getMap());
	}

	@Test
	public void testPostRequestEmptyBidResponseContent()
			throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(restTemplate.postForEntity(any(), any(), any())).thenReturn(getBidder1Response(),
				getBidder2EmptyContentResponse());
		when(bidder.getUrls()).thenReturn(getBidderUrls());
		assertNull(biddingService.postRequest("id", getMap()));
	}

	public List<String> getBidderUrls() {
		return Arrays.asList("http://localhost:8081", "http://localhost:8082");
	}

	public ResponseEntity<Object> getBidder1Response() {
		BidResponse response = new BidResponse();
		response.setBid(1);
		response.setContent("a:$price$");
		response.setBid(750);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getBidder2Response() {
		BidResponse response = new BidResponse();
		response.setBid(1);
		response.setContent("b:$price$");
		response.setBid(1000);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getBidder2EmptyContentResponse() {
		BidResponse response = new BidResponse();
		response.setBid(1);
		response.setBid(1000);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	public Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "100");
		return map;
	}
}