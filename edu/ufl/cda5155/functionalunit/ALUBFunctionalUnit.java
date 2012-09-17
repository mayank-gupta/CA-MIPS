/**
 * 
 */
package edu.ufl.cda5155.functionalunit;

import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.util.EntityManager;

/**
 * @author Mayank Gupta
 *
 */
public class ALUBFunctionalUnit extends AbstractFunctionalUnit {

  // make ALUBFU stateful
  private InstructionExecutionResult resultToWrite;
  private boolean isStalled = false;

  public ALUBFunctionalUnit(int cyclesToComplete) {
    super(cyclesToComplete);
  }

  @Override
  public void executeInternal() {
    EntityManager em = EntityManager.getInstance();
    if(!em.getPreALU_BBuffer().isEmpty() && !isStalled && isNextCycle(em.getProgramCounter().getCycle(), em.getPreALU_BBuffer().getData(0).getCycle()-1)) {
      isStalled = true;
      Instruction toExecute = em.getPreALU_BBuffer().getAndRemove(0).getInstruction();
      resultToWrite = em.getExecutor().execute(toExecute, em.getProgramCounter(), em.getGlobalData());
    }
  }

  @Override
  public void writeToBuffer() {
    // need to reset as well in this
    if(resultToWrite != null) {
      EntityManager em = EntityManager.getInstance();
      em.getPostALU_BBuffer().addToBuffer(new BufferEntry(resultToWrite.getInstruction(), em.getProgramCounter().getCycle() + 1, resultToWrite));
      resultToWrite = null;
      isStalled = false;

      // not required but be on safe side
    }
  }

  @Override
  public boolean isStalled() {
    return isStalled;
  }


}
