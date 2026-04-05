package main.java.com.xrusa.animals.services.impl;

import java.util.List;

import main.java.com.xrusa.animals.entities.AuditLog;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.AuditLogService;

public class AuditLogServiceImpl implements AuditLogService {

  private final FileAccess<AuditLog> auditLogReader;

  public AuditLogServiceImpl(FileAccess<AuditLog> auditLogReader) {
    this.auditLogReader = auditLogReader;
  }

  public List<AuditLog> getAllAuditLogs(Role role) {
    if (role == Role.ADMIN) return auditLogReader.read();
    throw new RuntimeException("No permission");
  }
}
