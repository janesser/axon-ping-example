package de.esserjan.edu.axon.distributed.sagas;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;
import java.util.UUID;

@Value
@RequiredArgsConstructor
public class PingCommand {

    @TargetAggregateIdentifier
    final UUID id = UUID.randomUUID();

    @RoutingKey
    final String destination;

    final String source;

    final Instant now = Instant.now();
}
