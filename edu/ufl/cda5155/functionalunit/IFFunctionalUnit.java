package edu.ufl.cda5155.functionalunit;

import static edu.ufl.cda5155.util.DependencyManager.*;

import java.util.Map;

import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.Constants;
import edu.ufl.cda5155.util.EntityManager;
import edu.ufl.cda5155.util.ProgramCounter;

public class IFFunctionalUnit extends AbstractFunctionalUnit {

  public boolean isStalled = false;
  public boolean isFinished = false;

  public IFFunctionalUnit(int cyclesToComplete) {
    super(cyclesToComplete);
  }

  @Override
  public void executeInternal() {
    // OMG...nothing to do
  }

  @Override
  public void writeToBuffer() {
    // 1. If no space in buffer in previous cycle, then donot fetch anything, else fetch amount of space available in pre-issue buffer
    if(!isFinished) {
      verifyStall();
      EntityManager em = EntityManager.getInstance();
      if(!em.getExecutedInstructions().isEmpty()) {
	em.removeExecutedInstruction();
      }
      Buffer preIssueBuffer = em.getPreIssueBuffer();
      Map<Integer, Instruction> translatedInstructions = em.getInstructionToExecute();
      ProgramCounter pc = em.getProgramCounter();
      int space = preIssueBuffer.available();
      if(space != 0) {
	for(int i=0;i<(space > 2 ? 2 : space);i++) {
	  Instruction fetchedIns = translatedInstructions.get(pc.getInstructionAddress());
	  if(!fetchedIns.getOperator().isBREAKInstruction() && isStalled) {
	    // stalled cannot do anything
	  } else if(fetchedIns.getOperator().isBREAKInstruction()) {
	    isFinished = true;
	    em.addExecutedInstruction(fetchedIns);
	    break;
	  } else if(fetchedIns.getOperator().isBranchInstruction()) {
	    em.addWaitingInstruction(fetchedIns);
	    isStalled = true;
	    // perform another dummy fetch to confirm next instruction is BREAK or not.
	    // space should be available
	    if(i < (space > 2 ? 2 : space)-1) {
	      Instruction dummyFetchedIns = translatedInstructions.get(pc.nextInstructionAddress());
	      if(!dummyFetchedIns.getOperator().isBREAKInstruction()) {
		pc.prevInstructionAddress();
	      }
	    }
	  } else {
	    preIssueBuffer.addToBuffer(new BufferEntry(fetchedIns, em.getProgramCounter().getCycle(), null));
	    pc.nextInstructionAddress();
	  }
	}
      }
      if(!em.getWaitingInstructions().isEmpty()) {
	Instruction branchIns = em.getWaitingInstructions().get(0);

	// verify if there is any local and global dependencies still left
	if(!checkDependency(branchIns, em.getGlobalData())) {
	  // no global dependencies
	  // but there can be dependencies with not-issued instructions
	  if(!verifyLocalDependency(branchIns, em.getPreIssueBuffer())) {
	    // no dependency ... execute branch instruction
	    em.getExecutor().execute(branchIns, em.getProgramCounter(), em.getGlobalData());
	    em.removeWaitingInstruction();
	    em.addExecutedInstruction(branchIns);
	    isStalled = false;
	  }
	}

      }
    }

  }

  private boolean verifyLocalDependency(Instruction branchIns, Buffer preIssueBuffer) {
    int []r = new int[Constants.NO_OF_REGISTERS];
    for(BufferEntry entry: preIssueBuffer.getBufferEntries()) {
      if(!Operator.SW.equals(entry.getInstruction().getOperator())) {
	if (entry.getInstruction().getRd() != null) {
	  r[entry.getInstruction().getRd().getId()] = 1;
	} else if (entry.getInstruction().getRt() != null) {
	  r[entry.getInstruction().getRt().getId()] = 1;
	}
      }
    }

    if(branchIns.getRt() != null) {
      return r[branchIns.getRt().getId()] == 1;
    } else if(branchIns.getRs() != null) {
      return r[branchIns.getRs().getId()] == 1;
    }
    return false;
  }

  @Override
  public boolean isStalled() {
    return isStalled && EntityManager.getInstance().getPreIssueBuffer().isFull();
  }

  private void verifyStall() {
    if(isStalled) {
      if(EntityManager.getInstance().getWaitingInstructions().isEmpty()) {
	if(!EntityManager.getInstance().getPreIssueBuffer().isFull()) {
	  isStalled = false;
	}
      }
    }
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }




}
