package de.esserjan.edu.axon.distributed.sagas;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class PingQueryHandler {

    private final Multimap<String, Duration> pingDurations =
            Multimaps.newMultimap(
                    Maps.newHashMap(),
                    Lists::newLinkedList);

    @EventHandler
    public void on(PingedEvent event) {
        pingDurations.put(event.getEdgeId(), event.getDuration());

    }

    @QueryHandler
    public List<Duration> handle(PingStatsQuery query) {
        return (List<Duration>) pingDurations.get(query.getEdgeId());
    }
}
