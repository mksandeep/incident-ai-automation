package com.example.incidentai;

import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.*;

@Service
class RunbookService {
    private final String text;

    RunbookService() throws Exception{text=new String(new ClassPathResource("runbook.md").getInputStream().readAllBytes(),StandardCharsets.UTF_8);}

String retrieve(String q){Set<String> t=tok(q);return Arrays.stream(text.split("(?m)(?=^## )")).sorted(Comparator.comparingInt((String s)->score(s,t)).reversed()).limit(3).collect(Collectors.joining("\n---\n"));} private int score(String s,Set<String> q){Set<String>d=tok(s);return(int)q.stream().filter(d::contains).count();}private Set<String>tok(String s){return Arrays.stream(Optional.ofNullable(s).orElse("").toLowerCase().split("[^a-z0-9]+" )).filter(x->x.length()>2).collect(Collectors.toSet());}}