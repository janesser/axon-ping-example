package de.esserjan.edu.axon.distributed.query;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.time.Duration;
import java.util.Collection;

@Value
@AllArgsConstructor
@ToString
public class PingStatsQueryResult {

    private final String source;
    private final String destination;
    private final Collection<Duration> durations;
}
