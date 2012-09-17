package edu.ufl.cda5155.domain;

public enum Operator {

  SHIFT_LEFT_LOGICAL,
    SHIFT_RIGHT_LOGICAL,
    SHIFT_RIGHT_ARITHMETIC,
    NOP,
    BREAK,
    JUMP,
    JUMP_REGISTER,
    BRANCH_ON_EQUAL,
    BRANCH_ON_L_T_ZERO,
    BRANCH_ON_G_T_ZERO,
    LW,
    SW,
    ADD,
    ADDI,
    SUB,
    SUBI,
    MUL,
    MULI,
    NOR,
    NORI,
    AND,
    ANDI,
    SLT,
    SLTI;

  public boolean isBranchInstruction() {
    return this.equals(JUMP) || this.equals(JUMP_REGISTER) || this.equals(BRANCH_ON_EQUAL) || this.equals(BRANCH_ON_G_T_ZERO) || this.equals(BRANCH_ON_L_T_ZERO);    
  }

  public boolean isALUInstruction() {
    return !(isBranchInstruction() || isALUBInstruction() || isMEMInstruction());    
  }

  public boolean isALUBInstruction() {
    return this.equals(SHIFT_LEFT_LOGICAL) || this.equals(SHIFT_RIGHT_ARITHMETIC) || this.equals(SHIFT_RIGHT_LOGICAL) || this.equals(MUL) || this.equals(MULI);    
  }

  public boolean isMEMInstruction() {
    return this.equals(LW) || this.equals(SW);    
  }

  public boolean isBREAKInstruction() {
    return this.equals(BREAK);    
  }

  public boolean isNOPInstruction() {
    return this.equals(NOP);    
  }
}
