/**
 * 
 */
package edu.ufl.cda5155.domain;

/**
 * @author Mayank Gupta
 *
 */
public final class BufferEntry {

  private Instruction instruction;
  private int cycle;
  private InstructionExecutionResult result;

  public BufferEntry(Instruction instruction, int cycle, InstructionExecutionResult result) {
    super();
    this.instruction = instruction;
    this.cycle = cycle;
    this.result = result;
  }

  public Instruction getInstruction() {
    return instruction;
  }

  public int getCycle() {
    return cycle;
  }

  public InstructionExecutionResult getResult() {
    return result;
  }

}
