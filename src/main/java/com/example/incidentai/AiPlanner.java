package com.example.incidentai;

import com.fasterxml.jackson.databind.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.*;

@Service
class AiPlanner {
    private final WebClient w = WebClient.create();
    private final RunbookService r;
    private final ObjectMapper om;
    private final String ep, key, dep, ver;AiPlanner(RunbookService r,ObjectMapper om,@Value("${app.ai.endpoint}")String ep,@Value("${app.ai.key}")String key,@Value("${app.ai.deployment}")String dep,@Value("${app.ai.version}")String ver){this.r=r;this.om=om;this.ep=ep;this.key=key;this.dep=dep;this.ver=ver;}

PlanResult plan(SnowIncident i)throws Exception{String ev=r.retrieve(i.short_description()+" "+i.description());String sys="Use ONLY the runbook. Return strict JSON: actionType, parameters, rationale, runbookSteps, confidence. Allowed actions: RESTART_SERVICE,CLEAR_CACHE,NO_ACTION. Never invent commands; insufficient evidence means NO_ACTION.";var body=Map.of("messages",List.of(Map.of("role","system","content",sys),Map.of("role","user","content","INCIDENT: "+i.short_description()+" "+i.description()+"
RUNBOOK:
"+ev)),"temperature",0,"response_format",Map.of("type","json_object"));JsonNode n=w.post().uri(ep+"/openai/deployments/"+dep+"/chat/completions?api-version="+ver).header("api-key",key).bodyValue(body).retrieve().bodyToMono(JsonNode.class).block();String raw=n.at("/choices/0/message/content").asText();return new PlanResult(om.readValue(raw,ActionPlan.class),raw,ev);}record PlanResult(ActionPlan plan,String raw,String evidence){}}