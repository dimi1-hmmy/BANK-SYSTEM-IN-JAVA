package main.java.com.xrusa.animals.services.impl;

import java.util.List;

import main.java.com.xrusa.animals.entities.Statement;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.StatementService;

public class StatementServiceImpl implements StatementService {

  private final FileAccess<Statement> statementReader;

  public StatementServiceImpl(FileAccess<Statement> statementReader) {
    this.statementReader = statementReader;
  }

  @Override
  public List<Statement> getAllStatements() {
    return statementReader.read();
  }
}
