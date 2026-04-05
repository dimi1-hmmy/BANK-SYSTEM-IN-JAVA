package main.java.com.xrusa.animals.filereaders.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.Statement;
import main.java.com.xrusa.animals.enums.TransactionType;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class StatementAccess extends FileAccess<Statement> {

  private final String filePath;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public StatementAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<Statement> read() {
    List<Statement> statements = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 6 || "statementId".equalsIgnoreCase(parts[0].trim())) continue;

      try {
        Statement statement = new Statement(
          parts[0].trim(),
          dateFormat.parse(parts[1].trim()),
          TransactionType.valueOf(parts[2].trim()),
          new BigDecimal(parts[3].trim()),
          new BigDecimal(parts[4].trim()),
          parts[5].trim()
        );
        statements.add(statement);
      } catch (Exception ignored) {}
    }
    return statements;
  }

  @Override
  public void saveOrReplace(Statement order){
    //do nothing
  }

}
