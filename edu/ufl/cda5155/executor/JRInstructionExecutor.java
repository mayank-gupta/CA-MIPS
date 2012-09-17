/**
 * 
 */
package edu.ufl.cda5155.executor;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.util.EntityManager;
import edu.ufl.cda5155.util.ProgramCounter;

public class JRInstructionExecutor implements Executor<InstructionExecutionResult> {

  @Override
  public InstructionExecutionResult execute(Instruction instruction, ProgramCounter programCounter, GlobalData registers) {
    GlobalData gb = EntityManager.getInstance().getGlobalData();
    programCounter.setInstructionAddress(gb.getRegisterValue(instruction.getRt().getId()));
    return new InstructionExecutionResult(instruction, -1, -1);
  }



}
