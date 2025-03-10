package com.krimo.event_indexer.payload;

import java.util.Set;

public record Event(
    Long event_id,
    String name,
    String banner,
    String description,
    String venue,
    Long startDateTime,
    Long endDateTime,
    String organizer,
    Set<String> tags,
    Status status) {

}
