## Overview

This is an example of how use the Java JMS api with ActiveMQ.

## Prereqs

- Install Java SDK
- Install [Maven](http://maven.apache.org/download.html) 
- Broker needs to be configured with the broker stats plugin:

<plugins>
    <statisticsBrokerPlugin/>
</plugins>

For more information on the Broker Statistics plugin see the [Broker Statistics Plugin Doc](http://activemq.apache.org/statisticsplugin.html)

## Building

Run:

    mvn install

## Running the Examples

To get Broker stats run:

    java -cp target/broker-stats-0.1-SNAPSHOT.jar example.BrokerStats


To get stats on a specific destination run:

    java -cp target/broker-stats-0.1-SNAPSHOT.jar example.DestinationStats

You can control to which server the examples try to connect to by
setting the following environment variables: 

* `ACTIVEMQ_HOST`
* `ACTIVEMQ_PORT`
* `ACTIVEMQ_USER`
* `ACTIVEMQ_PASSWORD`