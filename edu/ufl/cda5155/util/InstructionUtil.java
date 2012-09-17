package edu.ufl.cda5155.util;

import edu.ufl.cda5155.domain.Buffer;
import edu.ufl.cda5155.domain.BufferEntry;
import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;

public final class InstructionUtil {

  private InstructionUtil() {
    // no instantiation plz
  }

  public static boolean isAllZeroes(String toCheck) {
    return toCheck.matches("0{32}");
  }

  public static <T> T substringAndCast(String str, int f, int s, Class<T> clazz) {
    return clazz.cast(str.substring(f, s));
  }

  public static Integer binaryToDecimal(String binary) {
    if(binary.length() == 32 && binary.charAt(0) == '1') {
      // two's complement
      StringBuilder twosComplement = new StringBuilder();
      for(int i=31;i>=0;i--) {
	if(binary.charAt(i) == '1') {
	  twosComplement.append("0");
	} else {
	  twosComplement.append("1");
	}
      }
      return ((Integer.parseInt(twosComplement.reverse().toString(), 2) + 1)*-1);
    }
    else {
      return Integer.parseInt(binary, 2);
    }
  }

  public static Integer binaryToDecimal(String binary, int length) {
    if(binary.length() == length && binary.charAt(0) == '1') {
      // two's complement
      StringBuilder twosComplement = new StringBuilder();
      for(int i=length-1;i>=0;i--) {
	if(binary.charAt(i) == '1') {
	  twosComplement.append("0");
	} else {
	  twosComplement.append("1");
	}
      }
      return ((Integer.parseInt(twosComplement.reverse().toString(), 2) + 1)*-1);
    }
    else {
      return Integer.parseInt(binary, 2);
    }
  }

  public static String decimalToBinary(int decimal) {
    return Integer.toBinaryString(decimal);
  }

  public static String prependChar(String str, int length, char toPrepend) {
    StringBuilder builder = new StringBuilder();
    int interimLength = length - str.length(); 
    while(interimLength > 0) {
      builder.append(toPrepend);
      interimLength--;
    }
    builder.append(str);
    return builder.toString();
  }

  public static String preformatInstruction(String str, char toPrepend) {
    StringBuilder builder = new StringBuilder();
    builder.append(str.substring(0, 6));
    builder.append(toPrepend);
    builder.append(str.substring(6, 11));
    builder.append(toPrepend);
    builder.append(str.substring(11, 16));
    builder.append(toPrepend);
    builder.append(str.substring(16, 21));
    builder.append(toPrepend);
    builder.append(str.substring(21, 26));
    builder.append(toPrepend);
    builder.append(str.substring(26, 32));
    return builder.toString();
  }

  public static Buffer getPreMappedBuffer(EntityManager em, Instruction ins) {
    if(ins.getOperator().isMEMInstruction()) {
      return em.getPreMEMBuffer();
    } else if(ins.getOperator().isALUBInstruction()) {
      return em.getPreALU_BBuffer();
    } else {
      return em.getPreALUBuffer();
    }
  }

  public static void main(String[] args) {
    System.out.println(prependChar("8", 2, '0'));
  }

}
