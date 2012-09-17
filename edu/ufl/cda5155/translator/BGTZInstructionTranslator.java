package edu.ufl.cda5155.translator;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.InstructionUtil;


public class BGTZInstructionTranslator implements Translator {

  @Override
  public boolean doesSatisfy(String instructionBinary) {
    return instructionBinary.substring(32-6, 32).matches("111000");
  }

  @Override
  public Instruction translate(String instructionBinary) {
    Instruction instruction = new Instruction();
    instruction.setOffset(substringAndCast(instructionBinary, 0, 16, String.class));

    instruction.setRs(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 21, 26, String.class))));
    instruction.setOpcode(substringAndCast(instructionBinary, 26, 32, String.class));
    instruction.setStringRepresentation("BGTZ R" + instruction.getRs().getId() +", #" + (InstructionUtil.binaryToDecimal(instruction.getOffset()) << 2));
    instruction.setOperator(Operator.BRANCH_ON_G_T_ZERO);
    return instruction;
  }

  private static <T> T substringAndCast(String str, int f, int s, Class<T> clazz) {
    return clazz.cast(new StringBuilder(InstructionUtil.substringAndCast(str, f, s, clazz).toString()).reverse().toString());
  }

}
