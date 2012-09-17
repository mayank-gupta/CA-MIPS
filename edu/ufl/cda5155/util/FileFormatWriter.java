/**
 * 
 */
package edu.ufl.cda5155.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Mayank Gupta
 *
 */
public class FileFormatWriter {

  private BufferedWriter disasseblyWriter;
  private BufferedWriter simulatorWriter;


  public FileFormatWriter(String sFileName) throws IOException {
    disasseblyWriter = new BufferedWriter(new FileWriter("disassembly.txt"));
    simulatorWriter = new BufferedWriter(new FileWriter(sFileName));
  }

  public void writeDisassembly(String str) throws IOException {
    disasseblyWriter.write(str);
  }

  public void writeSimulator(String str) throws IOException {
    simulatorWriter.write(str);
  }

  public void closeDisassemblyQuietly() {
    try {
      disasseblyWriter.close();
    } catch (IOException e) {
      // sshhhhhh......
    }
  }

  public void closeSimulatedQuietly() {
    try {
      simulatorWriter.close();
    } catch (IOException e) {
      // sshhhhhh......
    }
  }

}
