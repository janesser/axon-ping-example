package de.esserjan.edu.axon.distributed.rest;

import de.esserjan.edu.axon.distributed.query.PingStatsQuery;
import de.esserjan.edu.axon.distributed.query.PingStatsQueryResult;
import de.esserjan.edu.axon.distributed.sagas.PingCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;

@Controller
public class PingController {

    @Value("${axon.hub.clientId}")
    String clientId;

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    QueryGateway queryGateway;

    @RequestMapping("/subscribe")
    @ResponseBody
    public String subscribe() {
        SubscriptionQueryResult<Object, Object> result = queryGateway.subscriptionQuery("query", Object.class, Object.class);

        return result.toString();
    }

    @RequestMapping("/ping/{destination}")
    @ResponseBody
    public String ping(@PathVariable String destination) {
        commandGateway.send(new PingCommand(clientId, destination));
        return "ping emitted for " + destination;
    }

    @RequestMapping("/ping/{destination}/stats")
    @ResponseBody
    public String pingStats(@PathVariable String destination) throws ExecutionException, InterruptedException {
        // FIXME AXONIQ-2000 struggle with event sequence number zero
        PingStatsQueryResult result = queryGateway.query(
                new PingStatsQuery(clientId, destination),
                PingStatsQueryResult.class
        ).get();
        return result.toString();
    }
}
