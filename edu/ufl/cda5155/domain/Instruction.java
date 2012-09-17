package edu.ufl.cda5155.domain;

public class Instruction {

  private Register rd;
  private Register rt;
  private Register rs;
  private String sa;
  private String offset;
  private String opcode;
  private String function;
  private String stringRepresentation;
  private Operator operator;
  private String code;
  private String hint;
  private String instructionIndex;
  private int immediate;
  private String rawInstruction;

  public Register getRd() {
    return rd;
  }
  public void setRd(Register rd) {
    this.rd = rd;
  }
  public Register getRt() {
    return rt;
  }
  public void setRt(Register rt) {
    this.rt = rt;
  }
  public String getSa() {
    return sa;
  }
  public void setSa(String sa) {
    this.sa = sa;
  }
  public String getOffset() {
    return offset;
  }
  public void setOffset(String offset) {
    this.offset = offset;
  }
  public String getOpcode() {
    return opcode;
  }
  public void setOpcode(String opcode) {
    this.opcode = opcode;
  }
  public String getFunction() {
    return function;
  }
  public void setFunction(String function) {
    this.function = function;
  }
  public String getStringRepresentation() {
    return stringRepresentation;
  }
  public void setStringRepresentation(String stringRepresentation) {
    this.stringRepresentation = stringRepresentation;
  }
  public Operator getOperator() {
    return operator;
  }
  public void setOperator(Operator operator) {
    this.operator = operator;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getHint() {
    return hint;
  }
  public void setHint(String hint) {
    this.hint = hint;
  }
  public String getInstructionIndex() {
    return instructionIndex;
  }
  public void setInstructionIndex(String instructionIndex) {
    this.instructionIndex = instructionIndex;
  }
  public int getImmediate() {
    return immediate;
  }
  public void setImmediate(int immediate) {
    this.immediate = immediate;
  }
  public Register getRs() {
    return rs;
  }
  public void setRs(Register rs) {
    this.rs = rs;
  }

  public String getRawInstruction() {
    return rawInstruction;
  }
  public void setRawInstruction(String rawInstruction) {
    this.rawInstruction = rawInstruction;
  }
  @Override
  public String toString() {
    return "Instruction [code=" + code + ", function=" + function
      + ", hint=" + hint + ", immediate=" + immediate
      + ", instructionIndex=" + instructionIndex + ", offset="
      + offset + ", opcode=" + opcode + ", operator=" + operator
      + ", rd=" + rd + ", rs=" + rs + ", rt=" + rt + ", sa=" + sa
      + ", stringRepresentation=" + stringRepresentation + "]";
  }


}
