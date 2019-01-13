package de.esserjan.edu.axon.distributed.sagas;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import de.esserjan.edu.axon.distributed.query.PingStatsQuery;
import de.esserjan.edu.axon.distributed.query.PingStatsQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.SequenceNumber;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Duration;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
public class PingAggregate {

    @AggregateIdentifier
    public final String destination;

    private final Multimap<String, Duration> durationsBySource =
            Multimaps.newMultimap(
                    Maps.newHashMap(),
                    Lists::newLinkedList);

    @CommandHandler
    public PingAggregate(PingCommand command) {
        this.destination = command.getDestination();

        apply(new PingedEvent(command));
    }

    @EventHandler
    public void on(PingedEvent event, @SequenceNumber long seqNr) {
        log.info("got pinged " + event.getDestination() + " took " + event.getDuration() + " seqNr " + seqNr + ".");

        durationsBySource.put(event.getSource(), event.getDuration());
    }

    @QueryHandler
    public PingStatsQueryResult handle(PingStatsQuery query) {
        return new PingStatsQueryResult(
                query.getSource(),
                query.getDestination(),
                durationsBySource.get(query.getSource())
        );
    }
}
