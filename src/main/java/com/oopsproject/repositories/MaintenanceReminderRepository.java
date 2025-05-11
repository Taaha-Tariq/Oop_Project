package com.oopsproject.repositories;

import com.oopsproject.models.MaintenanceReminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceReminderRepository extends JpaRepository<MaintenanceReminder, Integer> {    
}
