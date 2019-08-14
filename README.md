The task
========

Instructions To Run
-------------------
Update application.properties with the biddersUrl.<br />
mvn clean install<br />
java -jar target/BidderProject-0.0.1-SNAPSHOT.jar<br />
Run Test with bidder servers.<br />

Build a bidding system behaving as following:

For every incoming request as described in [1], send out bid requests as described in [2] to a configurable number of bidders[5]. Responses from these bidders as described in [3] must be processed. The highest bidder wins, and payload is sent out as described in [4].

Code should be production-quality. Please stay with commonly known frameworks for easier reviewing and explaining afterwards.

### [1] Incoming Requests

An incoming request is of the following format:

```
http://localhost:8080/[id]?[key=value,...]
```

### [2] Bid Requests

A bid request is a POST request with the following JSON body format:

```json
{
	“id”: $id,
	“attributes” : {
		“$key”: “$value”,
		…
	}
}
```

### [3] Bid Response

The response will contain details of the bid:

```json
{
	"id" : $id,
	"bid": bid,
	"content":"the string to deliver as a response"
}
```

### [4] Auction Response

The response for the auction must be the `content` property of the winning bid, with some tags that can be mentioned in the content.

For now, only `$price$` must be supported, denoting the final price of the bid.

### [5] Configuration

* bidders: a comma-separated list of URLs denoting bidder endpoints


Test Setup
==========

To start the test environment, use the script `test-setup.sh` on Linux or run the following docker commands one after the other:

```sh
Docker commands
```

This will set up three bidders on localhost, opening ports 8081, 8082 and 8083.

Running the test
================

To run the test, execute `run-test.sh` as a shell script. The script expects your application to run on port 8080. 

You need `curl` and `diff` installed for this script to work. 
