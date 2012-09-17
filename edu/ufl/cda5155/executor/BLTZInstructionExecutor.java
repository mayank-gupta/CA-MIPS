/**
 * 
 */
package edu.ufl.cda5155.executor;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.EntityManager;
import edu.ufl.cda5155.util.InstructionUtil;
import edu.ufl.cda5155.util.ProgramCounter;

/**
 * @author Mayank Gupta
 *
 */
public class BLTZInstructionExecutor implements Executor<InstructionExecutionResult> {

  @Override
  public InstructionExecutionResult execute(Instruction instruction, ProgramCounter programCounter,	GlobalData registers) {
    Register rs = instruction.getRs();
    int offset = (InstructionUtil.binaryToDecimal(instruction.getOffset(), 16) << 2);
    GlobalData gb = EntityManager.getInstance().getGlobalData();
    if(gb.getRegisterValue(rs.getId()) < 0) {
      programCounter.setInstructionAddress(programCounter.getInstructionAddress() + offset + 4);
    } else {
      programCounter.nextInstructionAddress();
    }
    return new InstructionExecutionResult(instruction, -1, -1);
  }

}


