package com.krimo.event_indexer.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record BrokerMessage(
    Long event_id,
    String name,
    String banner,
    String description,
    String venue,
    Long startDateTime,
    Long endDateTime,
    String organizer,
    Set<String> tags,
    Status status,
    @JsonProperty("__op") Character operation,
    @JsonProperty("__table") String sourceTable,
    @JsonProperty("__deleted") Boolean isDeleted) {
}
