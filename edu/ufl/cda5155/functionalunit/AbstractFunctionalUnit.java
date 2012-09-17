/**
 * 
 */
package edu.ufl.cda5155.functionalunit;

import java.util.List;

import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.util.EntityManager;

/**
 * @author Mayank Gupta
 *
 */
public abstract class AbstractFunctionalUnit implements FunctionalUnit {

  private boolean isBusy = false;
  private final int cyclesToComplete;
  private int executedCyclesToComplete;

  public AbstractFunctionalUnit(int cyclesToComplete) {
    super();
    this.cyclesToComplete = cyclesToComplete;
    this.executedCyclesToComplete = cyclesToComplete;
  }


  public abstract void executeInternal();
  public abstract void writeToBuffer();


  @Override
  public final void execute() {
    if(!isStalled()) {
      isBusy = true;
      executeInternal();
    }

    completeCycle();

    if(isExecutionComplete()) {
      writeToBuffer();
      isBusy = false;
    }
  }


  protected int getCurrentExecutedCycles() {
    return executedCyclesToComplete;
  }

  protected boolean isExecutionComplete() {
    return executedCyclesToComplete == 0;
  }

  protected void completeCycle() {
    if(executedCyclesToComplete > 0) {
      executedCyclesToComplete --;
    }
  }


  public boolean isBusy() {
    return isBusy;
  }


  public int getCyclesToComplete() {
    return cyclesToComplete;
  }


  public void setBusy(boolean isBusy) {
    this.isBusy = isBusy;
  }

  protected void removeEntriesFromBuffer(Buffer buffer, List<Integer> list) {
    if(list != null && buffer != null) {
      int count = 0;
      for(Integer entry: list) {
	BufferEntry e = buffer.getAndRemove(entry - (count ++));
      }
    }
  }


  /**
   * acycle should be bcycle + 1
   * @param acycle
   * @param bcycle
   * @return
   */
  protected boolean isNextCycle(int acycle, int bcycle) {
    return acycle >= (bcycle+1);
  }

  protected InstructionExecutionResult executeInstruction(Buffer buffer) {
    EntityManager em = EntityManager.getInstance();
    return em.getExecutor().execute(buffer.getAndRemove(0).getInstruction(), em.getProgramCounter(), em.getGlobalData());
  }

  public boolean isFinished() {
    return false;
  }
}

