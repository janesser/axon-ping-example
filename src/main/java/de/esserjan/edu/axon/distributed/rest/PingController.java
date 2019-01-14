package de.esserjan.edu.axon.distributed.rest;

import de.esserjan.edu.axon.distributed.sagas.PingCommand;
import de.esserjan.edu.axon.distributed.sagas.PingStatsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller
public class PingController {

    @Value("${axon.hub.clientId}")
    String clientId;

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    QueryGateway queryGateway;

    @RequestMapping("/ping/{destination}")
    @ResponseBody
    public String ping(@PathVariable String destination) {
        commandGateway.sendAndWait(new PingCommand(clientId, destination));
        return "ping emitted for " + destination;
    }

    @RequestMapping("/ping/{destination}/stats")
    @ResponseBody
    public String pingStats(@PathVariable String destination) throws ExecutionException, InterruptedException {
        final String edgeId = clientId + "_" + destination;
        CompletableFuture<List<Duration>> result =
                queryGateway.query(
                        new PingStatsQuery(edgeId),
                        ResponseTypes.multipleInstancesOf(Duration.class));

        return "ping stats for " + edgeId + ": " + result.get().toString();
    }

}
