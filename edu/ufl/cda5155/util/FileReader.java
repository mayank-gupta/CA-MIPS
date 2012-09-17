package edu.ufl.cda5155.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileReader {

  private final BufferedReader fileReader;

  public FileReader(String fileName) throws FileNotFoundException {
    super();
    this.fileReader = new BufferedReader(new java.io.FileReader(fileName));
  }

  public FileReader(File file) throws FileNotFoundException {
    super();
    this.fileReader = new BufferedReader(new java.io.FileReader(file));
  }

  public String nextRecord() throws IOException {
    String str = fileReader.readLine();
    return  str != null ? str.trim() : "";
  }

  public void close() throws IOException {
    fileReader.close();
  }

  public void closeQuietly() {
    try {
      close();
    } catch (IOException e) {
      // eat exception
    }
  }
}
