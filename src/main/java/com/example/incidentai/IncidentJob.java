package com.example.incidentai;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "incidentSysId"))
public class IncidentJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Version
    public long version;
    @Column(nullable = false)
    public String incidentSysId;
    public String number;
    @Lob
    public String subject, issue, planJson, evidence, result;
    public double confidence;
    public String status;
    @Column(unique = true)
    public String token;
    public String decidedBy;
    public Instant createdAt, decidedAt, processedAt;
}