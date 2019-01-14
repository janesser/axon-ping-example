package de.esserjan.edu.axon.distributed.sagas;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Duration;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
public class PingAggregate {

    @AggregateIdentifier
    public final UUID id;

    private final Multimap<String, Duration> durationsBySource =
            Multimaps.newMultimap(
                    Maps.newHashMap(),
                    Lists::newLinkedList);

    @CommandHandler
    public PingAggregate(PingCommand command) {
        this.id = command.getId();

        apply(new PingedEvent(command));
    }

    @EventHandler
    public void on(PingedEvent event) {
        log.info("got pinged " + event.getDestination() + " took " + event.getDuration() + ".");

        durationsBySource.put(event.getSource(), event.getDuration());
    }
}
