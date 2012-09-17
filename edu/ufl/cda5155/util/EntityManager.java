/**
 * 
 */
package edu.ufl.cda5155.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.executor.DefaultExecutor;
import edu.ufl.cda5155.executor.Executor;

/**
 * @author Mayank Gupta
 *
 */
public final class EntityManager {

  private final GlobalData globalData;

  private final Buffer preIssueBuffer;
  private final Buffer preMEMBuffer;
  private final Buffer preALUBuffer;
  private final Buffer preALU_BBuffer;
  private final Buffer postMEMBuffer;
  private final Buffer postALUBuffer;
  private final Buffer postALU_BBuffer;
  private ProgramCounter pc;
  private Map<Integer, Instruction> instructionToExecute;
  private List<Instruction> waitingInstructions;
  private List<Instruction> executedInstructions;
  private boolean isALUBFunctionalUnitBusy;
  private Executor<InstructionExecutionResult> executor;


  private static EntityManager manager;

  public EntityManager(GlobalData globalData, Map<Integer, Instruction> translatedInstructions) {
    super();
    this.globalData = globalData;
    preIssueBuffer = new Buffer(Constants.PRE_ISSUE_BUFFER_SIZE);
    preALUBuffer = new Buffer(Constants.PRE_ALU_BUFFER_SIZE);
    preALU_BBuffer = new Buffer(Constants.PRE_ALUB_BUFFER_SIZE);
    preMEMBuffer = new Buffer(Constants.PRE_MEM_BUFFER_SIZE);
    postMEMBuffer = new Buffer(Constants.POST_MEM_BUFFER_SIZE);
    postALU_BBuffer = new Buffer(Constants.POST_ALUB_BUFFER_SIZE);
    postALUBuffer = new Buffer(Constants.POST_ALU_BUFFER_SIZE);
    pc = new ProgramCounter();
    instructionToExecute = translatedInstructions;
    waitingInstructions = new ArrayList<Instruction>();
    executedInstructions = new ArrayList<Instruction>();
    isALUBFunctionalUnitBusy = false;
    executor = new DefaultExecutor();
  }

  public GlobalData getGlobalData() {
    return globalData;
  }

  public Buffer getPreIssueBuffer() {
    return preIssueBuffer;
  }

  public Buffer getPreMEMBuffer() {
    return preMEMBuffer;
  }

  public Buffer getPreALUBuffer() {
    return preALUBuffer;
  }

  public Buffer getPreALU_BBuffer() {
    return preALU_BBuffer;
  }

  public Buffer getPostMEMBuffer() {
    return postMEMBuffer;
  }

  public Buffer getPostALUBuffer() {
    return postALUBuffer;
  }

  public Buffer getPostALU_BBuffer() {
    return postALU_BBuffer;
  }

  public ProgramCounter getProgramCounter() {
    return pc;
  }

  public Map<Integer, Instruction> getInstructionToExecute() {
    return instructionToExecute;
  }

  public void addWaitingInstruction(Instruction ins) {
    waitingInstructions.add(ins);
  }

  public void removeWaitingInstruction() {
    waitingInstructions.remove(0);
  }

  public List<Instruction> getWaitingInstructions() {
    return waitingInstructions;
  }

  public void addExecutedInstruction(Instruction ins) {
    executedInstructions.add(ins);
  }

  public void removeExecutedInstruction() {
    executedInstructions.remove(0);
  }

  public List<Instruction> getExecutedInstructions() {
    return executedInstructions;
  }

  public boolean isALUBFunctionalUnitBusy() {
    return isALUBFunctionalUnitBusy;
  }

  public void setALUBFunctionalUnitBusy(boolean isALUBFunctionalUnitBusy) {
    this.isALUBFunctionalUnitBusy = isALUBFunctionalUnitBusy;
  }

  public Executor<InstructionExecutionResult> getExecutor() {
    return executor;
  }

  public static EntityManager getInstance() {
    return manager;
  }

  public static void setEntityManager(EntityManager entityManager) {
    manager = entityManager;
  }
}
