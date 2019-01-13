package de.esserjan.edu.axon.distributed.sagas;

import lombok.Value;

import java.time.Duration;
import java.time.Instant;

@Value
public class PingedEvent {

    final String source;
    final String destination;

    final Instant start;
    final Duration duration;

    public PingedEvent(PingCommand command) {
        this.source = command.getSource();
        this.destination = command.getDestination();

        this.start = command.getNow();
        this.duration = Duration.between(this.start, Instant.now());

    }


}
