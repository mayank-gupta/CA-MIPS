package edu.ufl.cda5155.translator;
import edu.ufl.cda5155.domain.Instruction;


public interface Translator {

  boolean doesSatisfy(String instructionBinary);

  Instruction translate(String instructionBinary);

}
