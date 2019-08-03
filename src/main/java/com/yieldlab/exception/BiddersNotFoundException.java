package com.yieldlab.exception;

/**
 * @author PoojaShankar
 */
public class BiddersNotFoundException extends BiddingException {

	public BiddersNotFoundException() {
		super("No Bidders found");
	}

	public BiddersNotFoundException(String arg0) {
		super(arg0);
	}

	public BiddersNotFoundException(Throwable arg0) {
		super(arg0);
	}
}