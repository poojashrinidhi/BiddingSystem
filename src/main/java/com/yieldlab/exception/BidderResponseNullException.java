package com.yieldlab.exception;

/**
 * @author PoojaShankar
 */
public class BidderResponseNullException extends BiddingException {

	public BidderResponseNullException() {
		super("Failed to get response from the bidder");
	}

	public BidderResponseNullException(String arg0) {
		super(arg0);
	}

	public BidderResponseNullException(Throwable arg0) {
		super(arg0);
	}
}