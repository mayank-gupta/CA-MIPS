/**
 * 
 */
package edu.ufl.cda5155.util;

import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.domain.Register;

/**
 * @author Mayank Gupta
 *
 */
public final class DependencyManager {

  private DependencyManager() {
    // no instantations
  }

  private static int[] localRegisters = new int[Constants.NO_OF_REGISTERS];

  public static boolean checkDependency(Instruction ins, GlobalData data) {
    boolean status = false;
    if(ins.getRd() != null) {
      status = data.getRegister(ins.getRd().getId()).isUsed();
    }

    if(ins.getRs() != null && !status) {
      status = data.getRegister(ins.getRs().getId()).isUsed();
    }

    if(ins.getRt() != null && !status) {
      status = data.getRegister(ins.getRt().getId()).isUsed();
    }

    return status;
  }

  public static boolean verifyLocalDependency(Buffer buffer, Instruction ins) {
    //		resetLocalRegisters();

    boolean status = false;

    if(Operator.LW.equals(ins.getOperator()) && verifyMEMDependencies(buffer, ins)) {
      return true;
    } else if(Operator.LW.equals(ins.getOperator())) {
      return false;
    }

    if(ins.getRd() != null) {
      status = localRegisters[ins.getRd().getId()] == 1;
    }

    if(ins.getRs() != null && !status) {
      status = localRegisters[ins.getRs().getId()] == 1;
    }

    if(ins.getRt() != null && !status) {
      status = localRegisters[ins.getRt().getId()] == 1;
    }

    if(ins.getRd() != null) {
      localRegisters[ins.getRd().getId()] = 1;
    } else if(ins.getRt() != null) {
      localRegisters[ins.getRt().getId()] = 1;
    }

    return status;
  }

  public static void addDependencies(Instruction ins, GlobalData data) {
    if(!Operator.SW.equals(ins.getOperator())) {
      if(ins.getRd() != null) {
	data.getRegister(ins.getRd().getId()).setUsed(true);
	localRegisters[ins.getRd().getId()] = 1;
      } else if(ins.getRt() != null) {
	data.getRegister(ins.getRt().getId()).setUsed(true);
	localRegisters[ins.getRt().getId()] = 1;
      }
    }

  }

  public static void removeDependencies(InstructionExecutionResult ins, GlobalData data) {
    if(ins.getRegisterToUpdate() != -1) {
      data.getRegister(ins.getRegisterToUpdate()).setUsed(false);
    }
  }

  public static void resetLocalRegisters() {
    for(int i=0;i<localRegisters.length;i++) {
      localRegisters[i] = 0;
    }
  }

  private static boolean verifyMEMDependencies(Buffer buffer, Instruction ins) {
    // no SW should be present if LW is 'ins'
    boolean status = false;
    if(Operator.LW.equals(ins.getOperator())) {
      for(BufferEntry entry: buffer.getBufferEntries()) {
	if(Operator.SW.equals(entry.getInstruction().getOperator())) {
	  return true; 
	} else if(entry.getInstruction() == ins) {
	  break;
	}
      }
      // reached here means no SW present prior to instruction to issue

      int[] regs = new int[Constants.NO_OF_REGISTERS];
      for(BufferEntry entry: buffer.getBufferEntries()) {
	if(entry.getInstruction() != ins) {
	  if(regs[ins.getRt().getId()] == 1) {
	    status = true;
	    break;
	  } else {
	    regs[ins.getRt().getId()] = 1;
	  }
	} else {
	  break;
	}
      }
    }
    return status;
  }
}
