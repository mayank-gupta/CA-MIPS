/**
 * 
 */
package edu.ufl.cda5155.functionalunit;

import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.util.EntityManager;

/**
 * @author Mayank Gupta
 *
 */
public class ALUFunctionalUnit extends AbstractFunctionalUnit {

  // make ALUFU stateful
  private InstructionExecutionResult resultToWrite;

  public ALUFunctionalUnit(int cyclesToComplete) {
    super(cyclesToComplete);
  }

  @Override
  public void executeInternal() {
    EntityManager em = EntityManager.getInstance();
    if(!em.getPreALUBuffer().isEmpty()) {
      BufferEntry entry = em.getPreALUBuffer().getData(0);
      if(em.getProgramCounter().getCycle() >= entry.getCycle()) {
	resultToWrite = executeInstruction(em.getPreALUBuffer());
      }
    }
  }

  @Override
  public void writeToBuffer() {
    // need to reset as well in this
    if(resultToWrite != null) {
      EntityManager em = EntityManager.getInstance();
      em.getPostALUBuffer().addToBuffer(new BufferEntry(resultToWrite.getInstruction(), em.getProgramCounter().getCycle()+1, resultToWrite));
      resultToWrite = null;
    }
  }

  @Override
  public boolean isStalled() {
    return false;
  }

}
