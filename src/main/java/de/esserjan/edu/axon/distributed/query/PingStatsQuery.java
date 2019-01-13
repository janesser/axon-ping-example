package de.esserjan.edu.axon.distributed.query;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
@RequiredArgsConstructor
public class PingStatsQuery {

    @TargetAggregateIdentifier
    private final String source;
    private final String destination;
}
