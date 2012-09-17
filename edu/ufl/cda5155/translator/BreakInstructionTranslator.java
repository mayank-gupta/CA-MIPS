package edu.ufl.cda5155.translator;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.util.InstructionUtil;


public class BreakInstructionTranslator implements Translator {

  @Override
  public boolean doesSatisfy(String instructionBinary) {
    return instructionBinary.substring(0, 6).matches("101100");
  }

  @Override
  public Instruction translate(String instructionBinary) {
    Instruction instruction = new Instruction();
    instruction.setCode(substringAndCast(instructionBinary, 6, 27, String.class));
    instruction.setOpcode(substringAndCast(instructionBinary, 26, 32, String.class));
    instruction.setStringRepresentation("BREAK");
    instruction.setOperator(Operator.BREAK);
    return instruction;
  }

  private static <T> T substringAndCast(String str, int f, int s, Class<T> clazz) {
    return clazz.cast(new StringBuilder(InstructionUtil.substringAndCast(str, f, s, clazz).toString()).reverse().toString());
  }

}
