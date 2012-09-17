package edu.ufl.cda5155.translator;

import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.domain.Register;
import edu.ufl.cda5155.util.InstructionUtil;

public class AndImmInstructionTranslator implements Translator {

  @Override
  public boolean doesSatisfy(String instructionBinary) {
    return instructionBinary.substring(32-6, 32).matches("010011");
  }

  @Override
  public Instruction translate(String instructionBinary) {
    Instruction instruction = new Instruction();
    instruction.setImmediate(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 0, 15, String.class), 16));
    instruction.setRt(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 16, 21, String.class))));
    instruction.setRs(new Register(InstructionUtil.binaryToDecimal(substringAndCast(instructionBinary, 21, 26, String.class))));		
    instruction.setOpcode(substringAndCast(instructionBinary, 26, 32, String.class));
    instruction.setStringRepresentation("AND R" + instruction.getRt().getId() + ", R" + instruction.getRs().getId() + ", #" + instruction.getImmediate());
    instruction.setOperator(Operator.ANDI);
    return instruction;
  }

  private static <T> T substringAndCast(String str, int f, int s, Class<T> clazz) {
    return clazz.cast(new StringBuilder(InstructionUtil.substringAndCast(str, f, s, clazz).toString()).reverse().toString());
  }

}
