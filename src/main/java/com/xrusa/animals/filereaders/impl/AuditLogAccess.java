package main.java.com.xrusa.animals.filereaders.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.AuditLog;
import main.java.com.xrusa.animals.enums.Action;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class AuditLogAccess extends FileAccess<AuditLog> {

  private final String filePath;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private List<AuditLog> cache;

  public AuditLogAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<AuditLog> read() {
    if(cache != null) {
      return cache;
    }
    List<AuditLog> logs = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 6 || "logId".equalsIgnoreCase(parts[0].trim())) continue;

      try {
        AuditLog log = new AuditLog(
          parts[0].trim(),
          dateFormat.parse(parts[1].trim()),
          Role.valueOf(parts[2].trim()),
          parts[3].trim(),
          Action.valueOf(parts[4].trim()),
          parts[5].trim()
        );
        logs.add(log);
      } catch (Exception ignored) {}
    }
    cache = logs;
    return cache;
  }

  @Override
  public void saveOrReplace(AuditLog log) {
    datasource.writeOrReplace(
      filePath,
      log.getLogId(),
      toRow(log)
    );
    reload();
  }

  private String[] toRow(AuditLog log) {
    return new String[] {
      log.getLogId(),
      dateFormat.format(log.getTimestamp()),
      log.getActorType().name(),
      log.getActorId(),
      log.getAction().name(),
      log.getDetails()
    };
  }

  public void reload() {
    cache = null;
  }
}
