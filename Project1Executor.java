import static edu.ufl.cda5155.util.InstructionUtil.binaryToDecimal;
import static edu.ufl.cda5155.util.InstructionUtil.prependChar;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Memory;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.functionalunit.ALUBFunctionalUnit;
import edu.ufl.cda5155.functionalunit.ALUFunctionalUnit;
import edu.ufl.cda5155.functionalunit.FunctionalUnit;
import edu.ufl.cda5155.functionalunit.IFFunctionalUnit;
import edu.ufl.cda5155.functionalunit.IssueFunctionalUnit;
import edu.ufl.cda5155.functionalunit.MEMFunctionalUnit;
import edu.ufl.cda5155.functionalunit.WBFunctionalUnit;
import edu.ufl.cda5155.translator.Category1InstructionTranslator;
import edu.ufl.cda5155.translator.Category2InstructionTranslator;
import edu.ufl.cda5155.translator.Translator;
import edu.ufl.cda5155.util.Constants;
import edu.ufl.cda5155.util.EntityManager;
import edu.ufl.cda5155.util.FileFormatWriter;
import edu.ufl.cda5155.util.FileReader;
import edu.ufl.cda5155.util.ProgramCounter;


public class Project1Executor {

  public Project1Executor() {

  }

  public void execute(String iFileName, String oFileName) throws IOException {
    FileReader reader = new FileReader(iFileName);
    Translator category1InstructionTranslator = new Category1InstructionTranslator();
    Translator category2InstructionTranslator = new Category2InstructionTranslator();
    String instructionStr = null;
    Instruction instr = null;
    int count = 60;
    Map<Integer, Instruction> instructionToExecute = new LinkedHashMap<Integer, Instruction>();
    while((instructionStr = reader.nextRecord()) != null) {
      count += 4;
      instructionStr = new StringBuilder(instructionStr.trim()).reverse().toString();
      if(category1InstructionTranslator.doesSatisfy(instructionStr)) {
	instr = category1InstructionTranslator.translate(instructionStr);
      } else if(category2InstructionTranslator.doesSatisfy(instructionStr)){
	instr = category2InstructionTranslator.translate(instructionStr);
      } else {
	System.out.println(instructionStr);
	throw new IllegalArgumentException("Instruction not valid or problem with code");
      }
      instr.setRawInstruction(instructionStr);
      instructionToExecute.put(count, instr);
      if(instr.getOperator().equals(Operator.BREAK)) {
	break;
      }
    }

    GlobalData globalRegisters = new GlobalData(null, getMemoryValues(reader), count + 4);
    EntityManager em = new EntityManager(globalRegisters, instructionToExecute);
    EntityManager.setEntityManager(em);
    //		FormatBuilder.getBuilder().buildDisassemblyFormattedData(instructionToExecute, globalRegisters);
    FileFormatWriter writer = new FileFormatWriter(oFileName);
    ProgramCounter pc = EntityManager.getInstance().getProgramCounter();
    pc.setInstructionAddress(Constants.BASE_ADDRESS);
    //Instruction executeInst = instructionToExecute.get(Constants.BASE_ADDRESS);
    FunctionalUnit ifFu = new IFFunctionalUnit(1);
    FunctionalUnit issueFu = new IssueFunctionalUnit(1);
    FunctionalUnit aluFu = new ALUFunctionalUnit(1);
    FunctionalUnit alubFu = new ALUBFunctionalUnit(2);
    FunctionalUnit wbFu = new WBFunctionalUnit(1);
    FunctionalUnit memFu = new MEMFunctionalUnit(1);
    boolean isComp = false;
    do {
      ifFu.execute();
      issueFu.execute();
      aluFu.execute();
      alubFu.execute();
      memFu.execute();
      wbFu.execute();
      writer.writeSimulator("--------------------\n");
      writer.writeSimulator("Cycle:" + em.getProgramCounter().getCycle() + "\n\n");
      writer.writeSimulator("IF Unit:\n");
      writer.writeSimulator("\tWaiting Instruction: ");
      writer.writeSimulator(((em.getWaitingInstructions().isEmpty() ? "" : em.getWaitingInstructions().get(0).getStringRepresentation())));
      writer.writeSimulator("\n\tExecuted Instruction: ");
      writer.writeSimulator(((em.getExecutedInstructions().isEmpty() ? "" : em.getExecutedInstructions().get(0).getStringRepresentation())));
      writer.writeSimulator("\nPre-Issue Buffer:\n");
      writer.writeSimulator(print(em.getPreIssueBuffer(), -1));
      writer.writeSimulator("Pre-ALU Queue:\n");
      writer.writeSimulator(print(em.getPreALUBuffer(), -1));
      writer.writeSimulator("Post-ALU Buffer:");
      writer.writeSimulator(printPost(em.getPostALUBuffer(), 1));
      writer.writeSimulator("Pre-ALUB Queue:\n");
      writer.writeSimulator(print(em.getPreALU_BBuffer(), -1));
      writer.writeSimulator("Post-ALUB Buffer:");
      writer.writeSimulator(printPost(em.getPostALU_BBuffer(), 1));
      writer.writeSimulator("Pre-MEM Queue:\n");
      writer.writeSimulator(print(em.getPreMEMBuffer(), -1));
      writer.writeSimulator("Post-MEM Buffer:");
      writer.writeSimulator(printPost(em.getPostMEMBuffer(), 1));
      em.getProgramCounter().nextCycle();
      writer.writeSimulator("\nRegisters\n");
      writer.writeSimulator(printReg(em.getGlobalData()));
      writer.writeSimulator("\nData\n");
      writer.writeSimulator(printMem(em.getGlobalData()));
      if(ifFu.isFinished()) {
	writer.closeSimulatedQuietly();
	isComp = true;
	//				System.exit(0);
      }
    } while(!isComp && (!isComplete()));
    writer.closeSimulatedQuietly();
    //		FormatBuilder.getBuilder().buildSimulatedFormattedData(executeInst, globalRegisters, cycle++, pc.getInstructionAddress());
    //		FormatBuilder.getBuilder().closeAll();
    //		reader.closeQuietly();

  }

  private String printMem(GlobalData globalData) {
    int c = 0;
    StringBuilder sb = new StringBuilder();
    for(Entry<Integer, Memory> mem: globalData.getAllMemory().entrySet()) {
      if(c%8 == 0) {
	sb.append(mem.getKey() + ":\t");
      }
      sb.append(mem.getValue().getValue() + "\t");
      c++;
      if(c%8 == 0) {
	sb.append("\n");
      }
    }
    return sb.toString();
  }


  private String printReg(GlobalData globalData) {
    int c = 0;
    StringBuilder sb = new StringBuilder();
    for(Register reg: globalData.getAllRegisters()) {
      if(c%8 == 0) {
	sb.append("R" + prependChar(""+c, 2, '0') + ":\t");
      }
      sb.append(reg.getValue() + "\t");
      c++;
      if(c%8 == 0) {
	sb.append("\n");
      }
    }
    return sb.toString();
  }

  private int[] getMemoryValues(FileReader reader) throws IOException {
    int [] regValues = new int[Constants.NO_OF_REGISTERS];
    for(int i=0;i<Constants.NO_OF_MEM_LOCATIONS;i++) {
      String nextRecord = reader.nextRecord();
      if(nextRecord != null) {
	regValues[i] = binaryToDecimal(nextRecord);
      }
    }
    return regValues;
  }

  private boolean isComplete() {
    EntityManager em = EntityManager.getInstance();
    return !em.isALUBFunctionalUnitBusy() && 
      em.getPostALU_BBuffer().isEmpty() && 
      em.getPostALUBuffer().isEmpty() && 
      em.getPostMEMBuffer().isEmpty() &&
      em.getPreALU_BBuffer().isEmpty() &&
      em.getPreALUBuffer().isEmpty() &&
      em.getPreIssueBuffer().isEmpty() &&
      em.getPreMEMBuffer().isEmpty() &&
      em.getWaitingInstructions().isEmpty() &&
      em.getExecutedInstructions().isEmpty();
  }


  private String print(Buffer b, int num) {
    StringBuilder sb = new StringBuilder();
    int i=0;
    for(BufferEntry entry : b.getBufferEntries()) {
      sb.append("\tEntry " + i++ + ": [" + entry.getInstruction().getStringRepresentation() + "]\n" );
    }
    for(int j=0;j<b.available();j++) {
      sb.append("\tEntry " + i++ + ": \n");
    }
    return sb.toString();
  }

  private String printPost(Buffer b, int num) {
    StringBuilder sb = new StringBuilder();
    sb.append(b.isEmpty() ? "\n" : "[" + b.getData(0).getInstruction().getStringRepresentation() + "]\n");
    return sb.toString();
  }
}

