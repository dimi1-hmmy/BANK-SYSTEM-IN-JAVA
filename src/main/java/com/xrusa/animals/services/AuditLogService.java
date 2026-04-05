package main.java.com.xrusa.animals.services;

import java.util.List;

import main.java.com.xrusa.animals.entities.AuditLog;
import main.java.com.xrusa.animals.enums.Role;

public interface AuditLogService {

  List<AuditLog> getAllAuditLogs(Role role);

}
