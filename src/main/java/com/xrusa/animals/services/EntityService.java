package main.java.com.xrusa.animals.services;

import java.util.List;

public interface EntityService<T> {
  List<T> getAll();
}
