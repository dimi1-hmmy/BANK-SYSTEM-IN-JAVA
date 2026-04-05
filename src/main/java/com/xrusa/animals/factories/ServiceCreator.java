package main.java.com.xrusa.animals.factories;

@FunctionalInterface
public interface ServiceCreator<T> {
  T create(FileReaderFactory readerFactory);
}
