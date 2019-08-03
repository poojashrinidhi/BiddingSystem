package com.yieldlab.resource;

import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.yieldlab.bidders.service.BidderService;
import com.yieldlab.exception.BidderResponseNullException;
import com.yieldlab.exception.BiddersNotFoundException;

/**
 * Reception of the User Rest Interface
 * 
 * @author PoojaShankar
 *
 */
@RestController
public class BiddingResourceController {

	private Logger logger = LoggerFactory.getLogger(BiddingResourceController.class);

	@Autowired
	private BidderService bidderService;

	/**
	 * 
	 * @param id
	 * @param requestParams
	 * @return information of the Highest bid value in the format
	 *         HighestBidder:price
	 * 
	 *         Sample Url : "http://localhost:8080/1?a=5&b=2"
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getHighestBidValue(@PathVariable String id,
			@RequestParam Map<String, String> requestParams) {

		String response = null;
		try {
			response = bidderService.postRequest(id, requestParams);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (BiddersNotFoundException | URISyntaxException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} catch (RestClientException | BidderResponseNullException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(response, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}