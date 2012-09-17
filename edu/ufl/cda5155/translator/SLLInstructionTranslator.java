package edu.ufl.cda5155.translator;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.InstructionUtil;


public class SLLInstructionTranslator implements Translator {

  @Override
  public boolean doesSatisfy(String instructionBinary) {
    return instructionBinary.substring(0, 6).matches("0{6}") && !InstructionUtil.isAllZeroes(instructionBinary);
  }

  @Override
  public Instruction translate(String instructionBinary) {
    Instruction instruction = new Instruction();
    instruction.setSa(substringAndCast(instructionBinary, 6, 11, String.class));
    instruction.setRd(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 11, 16, String.class))));
    instruction.setRt(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 16, 21, String.class))));
    instruction.setOpcode(substringAndCast(instructionBinary, 26, 32, String.class));
    instruction.setStringRepresentation("SLL R" + instruction.getRd().getId() + ", R" + instruction.getRt().getId() + ", #" + InstructionUtil.binaryToDecimal(instruction.getSa()));
    instruction.setOperator(Operator.SHIFT_LEFT_LOGICAL);
    return instruction;
  }

  private static <T> T substringAndCast(String str, int f, int s, Class<T> clazz) {
    return clazz.cast(new StringBuilder(InstructionUtil.substringAndCast(str, f, s, clazz).toString()).reverse().toString());
  }

}
