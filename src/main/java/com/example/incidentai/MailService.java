package com.example.incidentai;
import org.springframework.mail.*;import org.springframework.mail.javamail.*;import org.springframework.stereotype.Service;import org.springframework.beans.factory.annotation.Value;
@Service class MailService{private final JavaMailSender m;private final String base,from,to;MailService(JavaMailSender m,@Value("${app.approval.base-url}")String b,@Value("${app.approval.from}")String f,@Value("${app.approval.to}")String t){this.m=m;base=b;from=f;to=t;}void send(IncidentJob j){String u=base+"/api/approvals/"+j.token;SimpleMailMessage x=new SimpleMailMessage();x.setFrom(from);x.setTo(to);x.setSubject("Approval required: "+j.number);x.setText("Subject: "+j.subject+"
Plan: "+j.planJson+"
Approve: "+u+"/approve
Decline: "+u+"/decline");m.send(x);}}