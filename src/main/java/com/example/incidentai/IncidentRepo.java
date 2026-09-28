package com.example.incidentai;
import org.springframework.data.jpa.repository.*; import jakarta.persistence.LockModeType; import java.util.*;
public interface IncidentRepo extends JpaRepository<IncidentJob,Long>{boolean existsByIncidentSysId(String id); @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select j from IncidentJob j where j.token=:t") Optional<IncidentJob> lock(String t);}