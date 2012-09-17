package edu.ufl.cda5155.translator;

import java.util.ArrayList;
import java.util.List;

import edu.ufl.cda5155.domain.Instruction;


public class Category1InstructionTranslator implements Translator {

  private List<Translator> translators = new ArrayList<Translator>();
  private Translator matchedTranslator;

  public Category1InstructionTranslator() {
    translators.add(new BEQInstructionTranslator());
    translators.add(new BGTZInstructionTranslator());
    translators.add(new BLTZInstructionTranslator());
    translators.add(new BreakInstructionTranslator());
    translators.add(new JRInstructionTranslator());
    translators.add(new JumpInstructionTranslator());
    translators.add(new NOPInstructionTranslator());
    translators.add(new SLLInstructionTranslator());
    translators.add(new SRAInstructionTranslator());
    translators.add(new SRLInstructionTranslator());
    translators.add(new LWInstructionTranslator());
    translators.add(new SWInstructionTranslator());
  }

  @Override
  public boolean doesSatisfy(String instructionBinary) {
    for(Translator translator: translators) {
      if(translator.doesSatisfy(instructionBinary)) {
	matchedTranslator = translator;
	return true;
      }
    }
    return false;
  }

  @Override
  public Instruction translate(String instructionBinary) {
    return matchedTranslator.translate(instructionBinary);
  }

}
