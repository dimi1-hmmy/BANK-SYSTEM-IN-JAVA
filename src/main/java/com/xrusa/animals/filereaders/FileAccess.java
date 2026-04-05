package main.java.com.xrusa.animals.filereaders;

import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;

public abstract class FileAccess<T> {

  protected DataSource datasource;

  protected FileAccess(DataSource datasource) {
    this.datasource = datasource;
  }

  public abstract List<T> read();

  public abstract void saveOrReplace(T entity);

}
