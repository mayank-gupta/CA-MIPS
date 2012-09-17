/**
 * 
 */
package edu.ufl.cda5155.executor;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.util.ProgramCounter;

/**
 * @author Mayank Gupta
 *
 */
public class NOPInstructionExecutor implements Executor<Void> {

  @Override
  public Void execute(Instruction instruction, ProgramCounter programCounter,	GlobalData registers) {
    //programCounter.nextInstructionAddress();
    return null;
  }


}
