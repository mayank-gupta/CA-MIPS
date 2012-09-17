/**
 * 
 */
package edu.ufl.cda5155.functionalunit;

import static edu.ufl.cda5155.util.DependencyManager.addDependencies;
import static edu.ufl.cda5155.util.DependencyManager.checkDependency;
import static edu.ufl.cda5155.util.DependencyManager.resetLocalRegisters;
import static edu.ufl.cda5155.util.DependencyManager.verifyLocalDependency;
import static edu.ufl.cda5155.util.InstructionUtil.getPreMappedBuffer;

import java.util.ArrayList;
import java.util.List;

import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.Constants;
import edu.ufl.cda5155.util.EntityManager;

/**
 * @author Mayank Gupta
 *
 */
public class IssueFunctionalUnit extends AbstractFunctionalUnit {


  public IssueFunctionalUnit(int cyclesToComplete) {
    super(cyclesToComplete);
  }

  /* (non-Javadoc)
   * @see edu.ufl.cda5155.functionalunit.AbstractFunctionalUnit#executeInternal()
   */
  @Override
  public void executeInternal() {
    // is this really required
  }

  /* (non-Javadoc)
   * @see edu.ufl.cda5155.functionalunit.AbstractFunctionalUnit#writeToBuffer()
   */
  @Override
  public void writeToBuffer() {
    EntityManager em = EntityManager.getInstance();
    List<Integer> toRemoveEntries = new ArrayList<Integer>();
    int count = 0;
    Buffer mappedBuffer = null;
    int index = 0;
    BufferEntry preIssuedIns = null;
    boolean isIssued = false;
    int issueCount = 0;
    int size  = em.getPreIssueBuffer().getBufferEntries().size();
    while(!em.getPreIssueBuffer().isEmpty() && count <= size-1 && issueCount < 2) {
      preIssuedIns = em.getPreIssueBuffer().getData(index);
      //		for(BufferEntry preIssuedIns: em.getPreIssueBuffer().getBufferEntries()) {
      // maintain the order 0..3
      if(isNextCycle(em.getProgramCounter().getCycle(), preIssuedIns.getCycle()) && !verifyLocalDependency(em.getPreIssueBuffer(), preIssuedIns.getInstruction()) && 
	  !checkDependency(preIssuedIns.getInstruction(), em.getGlobalData())) {

	// instruction can be issued in this cycle
	mappedBuffer = getPreMappedBuffer(em, preIssuedIns.getInstruction());
	if(!mappedBuffer.isFull()) {
	  boolean isALUBInstruction = preIssuedIns.getInstruction().getOperator().isALUBInstruction();
	  //					if(!isALUBInstruction || (isALUBInstruction && !em.isALUBFunctionalUnitBusy())) {

	  isIssued = true;
	  mappedBuffer.addToBuffer(new BufferEntry(preIssuedIns.getInstruction(), em.getProgramCounter().getCycle() + (isALUBInstruction ? 2 : 1), null));
	  addDependencies(preIssuedIns.getInstruction(), em.getGlobalData());
	  //						em.getPreIssueBuffer().remove(preIssuedIns.getInstruction());
	  toRemoveEntries.add(index);
	  removeEntriesFromBuffer(em.getPreIssueBuffer(), toRemoveEntries);
	  toRemoveEntries.clear();
	  issueCount ++;
	  if(toRemoveEntries.size() >= Constants.NO_OF_INS_ISSUED_PER_CYCLE) {
	    break;
	  }
	  }
	if(!isIssued) {
	  index ++;
	}
	isIssued = false;
	// remove from preissue buffer
	} else {
	  index++;
	}
	// next instruction in preissue buffer
	count++;
	  }
      resetLocalRegisters();
      }

    /* (non-Javadoc)
     * @see edu.ufl.cda5155.functionalunit.FunctionalUnit#isStalled()
     */
    @Override
    public boolean isStalled() {
      return false;
    }

    }
