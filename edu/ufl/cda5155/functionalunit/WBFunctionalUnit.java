package edu.ufl.cda5155.functionalunit;

import static edu.ufl.cda5155.util.DependencyManager.removeDependencies;
import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.util.EntityManager;
public class WBFunctionalUnit extends AbstractFunctionalUnit {


  public WBFunctionalUnit(int cyclesToComplete) {
    super(cyclesToComplete);
  }

  @Override
  public void executeInternal() {
    // nothing
  }

  @Override
  public void writeToBuffer() {
    EntityManager em = EntityManager.getInstance();
    GlobalData gb = em.getGlobalData();
    Buffer postALUBuffer = em.getPostALUBuffer();
    Buffer postALU_BBuffer = em.getPostALU_BBuffer();
    Buffer postMEMBuffer = em.getPostMEMBuffer();

    writeBackResultFromBuffer(postALUBuffer, gb, em);
    writeBackResultFromBuffer(postALU_BBuffer, gb, em);
    writeBackResultFromBuffer(postMEMBuffer, gb, em);
  }

  @Override
  public boolean isStalled() {
    return false;
  }

  private void writeBackResultFromBuffer(Buffer buffer, GlobalData gb, EntityManager em) {
    if(!buffer.isEmpty()) {
      BufferEntry entry = buffer.getData(0);
      InstructionExecutionResult result = entry.getResult();
      if(em.getProgramCounter().getCycle() == entry.getCycle()) {
	buffer.getAndRemove(0);
	// commit result
	gb.setRegisterValue(result.getRegisterToUpdate(), result.getRegisterValue());
	removeDependencies(entry.getResult(), gb);
      }
    }
  }

}
