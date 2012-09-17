package edu.ufl.cda5155.executor;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.InstructionUtil;
import edu.ufl.cda5155.util.ProgramCounter;

public class LWInstructionExecutor implements Executor<InstructionExecutionResult> {

  @Override
  public InstructionExecutionResult execute(Instruction instruction, ProgramCounter programCounter,	GlobalData data) {
    Register rt = instruction.getRt();
    int offset = InstructionUtil.binaryToDecimal(instruction.getOffset(), 16);
    int base = data.getRegisterValue(instruction.getRs().getId());
    // update Rd in global registers as well
    //		programCounter.nextInstructionAddress();
    //		data.setRegisterValue(rt.getId(), data.getMemoryValueDirect(offset + base));
    return new InstructionExecutionResult(instruction, rt.getId(), data.getMemoryValueDirect(offset + base));
  }
}
