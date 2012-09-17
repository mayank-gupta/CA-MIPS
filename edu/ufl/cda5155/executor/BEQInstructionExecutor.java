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
public class BEQInstructionExecutor implements Executor<InstructionExecutionResult> {

  @Override
  public InstructionExecutionResult execute(Instruction instruction, ProgramCounter programCounter,	GlobalData registers) {
    Register rs = instruction.getRs();
    Register rt = instruction.getRt();
    int offset = (InstructionUtil.binaryToDecimal(instruction.getOffset(), 16) << 2);
    GlobalData gb = EntityManager.getInstance().getGlobalData();
    // update Rd in global registers as well
    if(gb.getRegisterValue(rs.getId()) == gb.getRegisterValue(rt.getId())) {
      programCounter.setInstructionAddress(programCounter.getInstructionAddress() + offset + 4);
    } else {
      programCounter.nextInstructionAddress();
    }
    return new InstructionExecutionResult(instruction, -1, -1);
  }

}


