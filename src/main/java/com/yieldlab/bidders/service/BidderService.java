package com.yieldlab.bidders.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.yieldlab.exception.BidderResponseNullException;
import com.yieldlab.exception.BiddersNotFoundException;

/**
 * Service Class to interact between User Rest and the Bidders Rest. Posts the
 * BidRequests from the User to the Bidder.
 * 
 * @author PoojaShankar
 *
 */
@Service
public class BidderService {

	private Logger logger = LoggerFactory.getLogger(BidderService.class);
	private final String AUCTION_RESPONSE = "$price$";

	@Autowired
	private Bidder bidder;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Posts the BidRequests to the configured number of bidders. The BidResponses
	 * is processed and the highest bid Value result is returned. The response is
	 * ignored if it is null and will be analysed on others. Exception thrown when
	 * none of the bidders respond.
	 * 
	 * @param id
	 * @param requestParams
	 * @return
	 * @throws RestClientException
	 * @throws BiddersNotFoundException
	 * @throws URISyntaxException
	 * @throws BidderResponseNullException
	 */
	public String postRequest(String id, Map<String, String> requestParams)
			throws RestClientException, BiddersNotFoundException, URISyntaxException, BidderResponseNullException {

		BidRequest bidRequest = new BidRequest();
		bidRequest.setId(id);
		bidRequest.setAttributes(requestParams);

		if (CollectionUtils.isEmpty(bidder.getUrls())) {
			String errorMsg = "Failed to get atleast one bidders. Please add bidders in the property and try again";
			throw new BiddersNotFoundException(errorMsg);
		}

		logger.debug(String.format("Posting the bid request data of id : %s and attributes %s to all the bidders : %s",
				id, requestParams, bidder.getUrls()));

		List<BidResponse> bidResponses = new ArrayList<BidResponse>();
		for (String uri : bidder.getUrls()) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			URI url = null;
			try {
				url = new URI(uri);
			} catch (URISyntaxException e) {
				String errorMsg = "URISyntaxException occured for the uri : " + uri;
				logger.error(errorMsg);
				throw e;
			}

			ResponseEntity<BidResponse> response = null;

			response = restTemplate.postForEntity(url, bidRequest, BidResponse.class);

			if (response == null) {
				String errorMsg = String
						.format("Failed to get response from the bidder url : %s. Please make sure it is active", url);

				logger.error(errorMsg);
			} else {
				bidResponses.add(response.getBody());
			}
		}

		return processBidResponse(bidResponses);
	}

	private String processBidResponse(List<BidResponse> bidResponses) throws BidderResponseNullException {
		if (CollectionUtils.isEmpty(bidResponses)) {
			String errorMsg = "Bid Response from the bidders found to be empty";
			throw new BidderResponseNullException(errorMsg);
		}

		logger.info("List of bidResponses from bidders : " + bidResponses);
		BidResponse highestBidResponse = Collections.max(bidResponses, Comparator.comparing(s -> s.getBid()));
		String content = highestBidResponse.getContent();
		if (!StringUtils.isEmpty(content)) {
			String auctionResponse = content.replace(AUCTION_RESPONSE, highestBidResponse.getBid().toString());
			logger.info("Highest bidResponse : " + auctionResponse);
			return auctionResponse;
		}
		return null;
	}
}