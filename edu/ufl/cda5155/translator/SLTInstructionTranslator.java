package edu.ufl.cda5155.translator;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.InstructionUtil;


public class SLTInstructionTranslator implements Translator {

  @Override
  public boolean doesSatisfy(String instructionBinary) {
    return instructionBinary.substring(0, 6).matches("010101");
  }

  @Override
  public Instruction translate(String instructionBinary) {
    Instruction instruction = new Instruction();
    instruction.setHint(substringAndCast(instructionBinary, 6, 11, String.class));
    instruction.setRt(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 16, 21, String.class))));
    instruction.setRd(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 11, 16, String.class))));
    instruction.setRs(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 21, 26, String.class))));
    instruction.setOpcode(substringAndCast(instructionBinary, 26, 32, String.class));
    instruction.setStringRepresentation("SLT R" + instruction.getRd().getId() +", R" + instruction.getRs().getId() +", R" + instruction.getRt().getId());
    instruction.setOperator(Operator.SLT);
    return instruction;
  }

  private static <T> T substringAndCast(String str, int f, int s, Class<T> clazz) {
    return clazz.cast(new StringBuilder(InstructionUtil.substringAndCast(str, f, s, clazz).toString()).reverse().toString());
  }
}
