/**
 * 
 */
package edu.ufl.cda5155.executor;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import static edu.ufl.cda5155.util.InstructionUtil.binaryToDecimal;
import edu.ufl.cda5155.util.EntityManager;
import edu.ufl.cda5155.util.ProgramCounter;

/**
 * @author Mayank Gupta
 *
 */
public class JumpInstructionExecutor implements Executor<InstructionExecutionResult> {

  @Override
  public InstructionExecutionResult execute(Instruction instruction, ProgramCounter programCounter,	GlobalData registers) {
    GlobalData gb = EntityManager.getInstance().getGlobalData();
    programCounter.setInstructionAddress(binaryToDecimal(instruction.getInstructionIndex())<<2);
    return new InstructionExecutionResult(instruction, -1, -1);
  }


}
