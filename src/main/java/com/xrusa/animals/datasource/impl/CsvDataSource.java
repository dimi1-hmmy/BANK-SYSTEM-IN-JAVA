package main.java.com.xrusa.animals.datasource.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;

public class CsvDataSource implements DataSource {

  private static CsvDataSource instance;

  private CsvDataSource() {
  }

  public static synchronized CsvDataSource getInstance() {
    if (instance == null) {
      instance = new CsvDataSource();
    }
    return instance;
  }


  @Override
  public List<String[]> readAll(String filePath) {
    List<String[]> rows = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty()) {
          rows.add(line.split(","));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("CSV read error", e);
    }
    return rows;
  }


  @Override
  public void writeOrReplace(String filePath, String key, String[] newRow) {
    List<String[]> rows = readAll(filePath);
    List<String[]> result = new ArrayList<>();
    boolean replaced = false;
    String[] header = rows.isEmpty() ? null : rows.get(0);
    if (header != null) {
      result.add(header);
    }
    for (int i = 1; i < rows.size(); i++) {
      String[] row = rows.get(i);
      if (row.length > 0 && row[0].trim().equals(key)) {
        result.add(newRow);
        replaced = true;
      } else {
        result.add(row);
      }
    }
    if (!replaced) {
      result.add(newRow);
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
      for (String[] row : result) {
        bw.write(String.join(",", row));
        bw.newLine();
      }
    } catch (IOException e) {
      throw new RuntimeException("CSV write error", e);
    }
  }

}
