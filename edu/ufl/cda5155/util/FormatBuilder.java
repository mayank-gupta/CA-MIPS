/**
 * 
 */
package edu.ufl.cda5155.util;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;

/**
 * @author Mayank Gupta
 *
 */
public class FormatBuilder {

  private FileFormatWriter writer; 
  private static FormatBuilder singleton;


  public FormatBuilder() throws IOException {
    writer = new FileFormatWriter("simulation.txt");
  }

  public static FormatBuilder getBuilder() throws IOException {
    if(singleton == null) {
      singleton = new FormatBuilder();
    } 
    return singleton;
  }

  public void buildDisassemblyFormattedData(Map<Integer, Instruction> instructions, GlobalData data) throws IOException {
    int count = Constants.BASE_ADDRESS;
    for(Entry<Integer, Instruction> entry: instructions.entrySet()) {
      count += 4;
      Instruction instruction = entry.getValue();
      writer.writeDisassembly(new StringBuilder(InstructionUtil.preformatInstruction(instruction.getRawInstruction(), ' ')).reverse().toString());
      writer.writeDisassembly("\t");
      writer.writeDisassembly("" + entry.getKey());
      writer.writeDisassembly("\t\t");
      writer.writeDisassembly(instruction.getStringRepresentation());
      writer.writeDisassembly("\n");
    }

    for(int i=0;i<data.getAllMemory().size();i++) {
      int memValue = data.getMemoryValue(i);
      writer.writeDisassembly(InstructionUtil.prependChar(InstructionUtil.decimalToBinary(memValue), 32, '0'));
      writer.writeDisassembly("\t");
      writer.writeDisassembly("" + count);
      writer.writeDisassembly("\t\t");
      writer.writeDisassembly(""+memValue);
      writer.writeDisassembly("\n");
      count += 4;
    }


    //		writer.closeDisassemblyQuietly();
  }

  public void buildSimulatedFormattedData(Instruction instruction, GlobalData data, int cycle, int pc) throws IOException {
    for(int i=0;i<20;i++) {
      writer.writeSimulator("-");
    }
    writer.writeSimulator("\n");
    writer.writeSimulator("Cycle:" + cycle);
    writer.writeSimulator("\t");
    writer.writeSimulator("" + pc);
    writer.writeSimulator("\t");
    writer.writeSimulator(instruction.getStringRepresentation());
    writer.writeSimulator("\n");
    writer.writeSimulator("\n");
    writer.writeSimulator("Registers");
    writer.writeSimulator("\n");
    writer.writeSimulator("R00:\t");
    for(int i=0;i<data.getAllRegisters().length;i++) {
      if(i%16 == 0 && i != 0) {
	writer.writeSimulator("\n");
	writer.writeSimulator("R16:\t");
      }
      writer.writeSimulator(""+data.getRegisterValue(i) + "\t");
    }
    writer.writeSimulator("\n");
    writer.writeSimulator("\n");
    writer.writeSimulator("Data");
    for(int i=0;i<data.getAllMemory().size();i++) {
      if(i % 8 == 0) {
	writer.writeSimulator("\n");
	writer.writeSimulator("" + (data.getBaseMemLocation() + i*4) + ":");
      }
      writer.writeSimulator("\t" + data.getMemoryValue(i));
    }
    writer.writeSimulator("\n");
    writer.writeSimulator("\n");
  }

  public void closeAll() {
    writer.closeDisassemblyQuietly();
    writer.closeSimulatedQuietly();
  }
}
