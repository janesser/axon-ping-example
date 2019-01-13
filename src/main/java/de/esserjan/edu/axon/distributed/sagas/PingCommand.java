package de.esserjan.edu.axon.distributed.sagas;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

@Value
@RequiredArgsConstructor
public class PingCommand {

    @TargetAggregateIdentifier
    @RoutingKey
    final String destination;

    final String source;

    final Instant now = Instant.now();

}
