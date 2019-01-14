package de.esserjan.edu.axon.distributed.sagas;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class PingStatsQuery {

    private String edgeId;
}
