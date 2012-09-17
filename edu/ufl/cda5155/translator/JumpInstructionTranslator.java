package edu.ufl.cda5155.translator;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.util.InstructionUtil;


public class JumpInstructionTranslator implements Translator {
  @Override
  public boolean doesSatisfy(String instructionBinary) {
    return instructionBinary.substring(32-6, 32).matches("010000");
  }

  @Override
  public Instruction translate(String instructionBinary) {
    Instruction instruction = new Instruction();
    instruction.setInstructionIndex(substringAndCast(instructionBinary, 0, 26, String.class));
    instruction.setOperator(Operator.JUMP);
    instruction.setStringRepresentation("J  #" + (InstructionUtil.binaryToDecimal(instruction.getInstructionIndex())<<2));
    return instruction;
  }

  private static <T> T substringAndCast(String str, int f, int s, Class<T> clazz) {
    return clazz.cast(new StringBuilder(InstructionUtil.substringAndCast(str, f, s, clazz).toString()).reverse().toString());
  }

}
