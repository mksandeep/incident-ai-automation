package com.example.incidentai;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.*;

@Component
class SnowClient {
    private final WebClient w;
    private final String group;

    SnowClient(@Value("${app.snow.url}") String u, @Value("${app.snow.user}") String n,
            @Value("${app.snow.password}") String p, @Value("${app.snow.group}") String g) {
        group = g;
        w = WebClient.builder().baseUrl(u).defaultHeaders(h -> h.setBasicAuth(n, p)).build();
    }

    List<SnowIncident> get() {
        var r = w.get().uri(b -> b.path("/api/now/table/incident")
                .queryParam("sysparm_query", "active=true^assignment_group.name=" + group + "^ORDERBYsys_created_on")
                .queryParam("sysparm_fields", "sys_id,number,short_description,description")
                .queryParam("sysparm_limit", 100).build()).retrieve().bodyToMono(SnowResponse.class).block();
        return r == null || r.result() == null ? List.of() : r.result();
    }

    void note(String id, String note) {
        w.patch().uri("/api/now/table/incident/{id}", id).bodyValue(Map.of("work_notes", note)).retrieve()
                .toBodilessEntity().block();
    }
}