package com.example.incidentai;

import java.util.*;

record SnowIncident(String sys_id, String number, String short_description, String description) {
}

record SnowResponse(List<SnowIncident> result) {
}

record ActionPlan(String actionType, Map<String, String> parameters, String rationale, List<String> runbookSteps,
        double confidence) {
}