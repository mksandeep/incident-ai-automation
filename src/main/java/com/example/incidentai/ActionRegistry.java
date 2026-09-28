package com.example.incidentai;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
class ActionRegistry {
    String execute(ActionPlan p) {
        return switch (p.actionType()) {
            case "RESTART_SERVICE" -> restart(p.parameters().get("service"));
            case "CLEAR_CACHE" ->
                "SIMULATED: cleared approved cache " + Objects.requireNonNull(p.parameters().get("cache"));
            default -> throw new IllegalArgumentException("Not allowlisted");
        };
    }

    private String restart(String s) {
        if (!Set.of("claims-api", "provider-api").contains(s))
            throw new SecurityException("Service not allowlisted");
        return "SIMULATED: restarted " + s;
    }
}