/**
 * 
 */
package edu.ufl.cda5155.functionalunit;

import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.util.EntityManager;

/**
 * @author Mayank Gupta
 *
 */
public class MEMFunctionalUnit extends AbstractFunctionalUnit {

  private InstructionExecutionResult executionResult;


  public MEMFunctionalUnit(int cyclesToComplete) {
    super(cyclesToComplete);
  }

  /* (non-Javadoc)
   * @see edu.ufl.cda5155.functionalunit.AbstractFunctionalUnit#executeInternal()
   */
  @Override
  public void executeInternal() {
    EntityManager em = EntityManager.getInstance();
    if(!em.getPreMEMBuffer().isEmpty()) {
      BufferEntry toExecute = em.getPreMEMBuffer().getData(0); 
      if(isNextCycle(em.getProgramCounter().getCycle()+1, toExecute.getCycle())) {
	executionResult = executeInstruction(em.getPreMEMBuffer());
      }
    }
  }

  /* (non-Javadoc)
   * @see edu.ufl.cda5155.functionalunit.AbstractFunctionalUnit#writeToBuffer()
   */
  @Override
  public void writeToBuffer() {
    if(executionResult != null) {
      EntityManager em = EntityManager.getInstance();
      if(Operator.LW.equals(executionResult.getInstruction().getOperator())) {
	//write to PostMEM Buffer
	em.getPostMEMBuffer().addToBuffer(new BufferEntry(executionResult.getInstruction(), em.getProgramCounter().getCycle() + 1, executionResult));
	executionResult = null;
      }
    }
  }

  /* (non-Javadoc)
   * @see edu.ufl.cda5155.functionalunit.FunctionalUnit#isStalled()
   */
  @Override
  public boolean isStalled() {
    return false;
  }

}
