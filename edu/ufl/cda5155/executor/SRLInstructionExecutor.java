package edu.ufl.cda5155.executor;

import static edu.ufl.cda5155.util.InstructionUtil.binaryToDecimal;
import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.EntityManager;
import edu.ufl.cda5155.util.ProgramCounter;

public class SRLInstructionExecutor implements Executor<InstructionExecutionResult> {

  @Override
  public InstructionExecutionResult execute(Instruction instruction, ProgramCounter programCounter,	GlobalData registers) {
    Register rd = instruction.getRd();
    Register rt = instruction.getRt();
    int sa = binaryToDecimal(instruction.getSa());
    // update Rd in global registers as well
    //		programCounter.nextInstructionAddress();
    //		registers.setRegisterValue(rd.getId(), registers.getRegisterValue(rt.getId()) >>> sa);
    GlobalData gb = EntityManager.getInstance().getGlobalData();
    return new InstructionExecutionResult(instruction, rd.getId(), (gb.getRegister(rt.getId()).getValue()) >>> sa);
  }
}
