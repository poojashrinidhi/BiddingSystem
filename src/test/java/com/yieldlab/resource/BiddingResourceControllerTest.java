package com.yieldlab.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.yieldlab.bidders.service.BidderService;
import com.yieldlab.exception.BidderResponseNullException;
import com.yieldlab.exception.BiddersNotFoundException;

/**
 * @author PoojaShankar
 * 
 * Code Coverage : 100% (Eclemma)
 */
public class BiddingResourceControllerTest {
	
	@Mock
	private BidderService bidderService;
	
	@InjectMocks
    private BiddingResourceController biddingResourceController = new BiddingResourceController();
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void getHighestBidValueSuccess() throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(bidderService.postRequest(any(), any())).thenReturn("a:600");
		ResponseEntity<String> response = biddingResourceController.getHighestBidValue(any(), any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void getHighestBidValueBiddersNotFoundException() throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(bidderService.postRequest(any(), any())).thenThrow(new BiddersNotFoundException());
		ResponseEntity<String> response = biddingResourceController.getHighestBidValue(any(), any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void getHighestBidValueURISyntaxException() throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(bidderService.postRequest(any(), any())).thenThrow(new URISyntaxException("", ""));
		ResponseEntity<String> response = biddingResourceController.getHighestBidValue(any(), any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void getHighestBidValueRestClientException() throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(bidderService.postRequest(any(), any())).thenThrow(new RestClientException("excepytion"));
		ResponseEntity<String> response = biddingResourceController.getHighestBidValue(any(), any());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	
	@Test
	public void getHighestBidValueBiddersResponseException() throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(bidderService.postRequest(any(), any())).thenThrow(new BidderResponseNullException());
		ResponseEntity<String> response = biddingResourceController.getHighestBidValue(any(), any());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	
	@Test
	public void getHighestBidValueBiddersNullPointerException() throws RestClientException, BiddersNotFoundException, BidderResponseNullException, URISyntaxException {
		when(bidderService.postRequest(any(), any())).thenThrow(new NullPointerException());
		ResponseEntity<String> response = biddingResourceController.getHighestBidValue(any(), any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}