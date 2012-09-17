package edu.ufl.cda5155.translator;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.util.InstructionUtil;


public class NOPInstructionTranslator implements Translator {

  @Override
  public boolean doesSatisfy(String instructionBinary) {
    return InstructionUtil.isAllZeroes(instructionBinary);
  }

  @Override
  public Instruction translate(String instructionBinary) {
    Instruction instruction = new Instruction();
    instruction.setOpcode("000000");
    instruction.setStringRepresentation("NOP");
    instruction.setOperator(Operator.NOP);
    return instruction;
  }

}
