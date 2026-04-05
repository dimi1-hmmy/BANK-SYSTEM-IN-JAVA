package main.java.com.xrusa.animals.datasource;

import java.util.List;

public interface DataSource {

  List<String[]> readAll(String filePath);

  void writeOrReplace(String filePath, String key, String[] newRow);

}
