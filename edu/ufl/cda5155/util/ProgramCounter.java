/**
 * 
 */
package edu.ufl.cda5155.util;


public class ProgramCounter {

  private int instructionAddress;
  private int cycle = 1;

  public int getInstructionAddress() {
    return instructionAddress;
  }

  public void setInstructionAddress(int instructionAddress) {
    this.instructionAddress = instructionAddress;
  }

  public int nextInstruction() {
    return ++instructionAddress;
  }

  public int nextInstructionAddress() {
    instructionAddress += 4;
    return instructionAddress;
  }

  public int prevInstructionAddress() {
    instructionAddress -= 4;
    return instructionAddress;
  }

  public int getCycle() {
    return cycle;
  }

  public void nextCycle() {
    cycle++;
  }


}
