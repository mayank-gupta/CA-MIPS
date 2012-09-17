/**
 * 
 */
package edu.ufl.cda5155.domain;

/**
 * @author Mayank Gupta
 *
 */
public final class InstructionExecutionResult {

  private final Instruction instruction;
  private final int registerToUpdate;
  private final int registerValue;

  public InstructionExecutionResult(Instruction instruction,
      int registerToUpdate, int registerValue) {
    super();
    this.instruction = instruction;
    this.registerToUpdate = registerToUpdate;
    this.registerValue = registerValue;
  }

  public Instruction getInstruction() {
    return instruction;
  }

  public int getRegisterToUpdate() {
    return registerToUpdate;
  }

  public int getRegisterValue() {
    return registerValue;
  }




}
