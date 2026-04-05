package main.java.com.xrusa.animals.entities;

import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.enums.Action;

import java.util.Date;

public class AuditLog {

    private String logId;
    private Date timestamp;
    private Role actorType;
    private String actorId;
    private Action action;
    private String details;

    public AuditLog(String logId, Date timestamp, Role actorType,
        String actorId, Action action, String details) {
      this.logId = logId;
      this.timestamp = timestamp;
      this.actorType = actorType;
      this.actorId = actorId;
      this.action = action;
      this.details = details;
    }

public String getLogId() {
return logId;
}

public void setLogId(String logId) {
this.logId = logId;
}

public Date getTimestamp() {
return timestamp;
}

public void setTimestamp(Date timestamp) {
this.timestamp = timestamp;
}

public Role getActorType() {
return actorType;
}

public void setActorType(Role actorType) {
this.actorType = actorType;
}

public String getActorId() {
return actorId;
}

public void setActorId(String actorId) {
this.actorId = actorId;
}

public Action getAction() {
return action;
}

public void setAction(Action action) {
this.action = action;
}

public String getDetails() {
return details;
}

public void setDetails(String details) {
this.details = details;
}
}
