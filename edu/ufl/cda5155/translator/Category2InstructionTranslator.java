package edu.ufl.cda5155.translator;

import java.util.ArrayList;
import java.util.List;

import edu.ufl.cda5155.domain.Instruction;


public class Category2InstructionTranslator implements Translator {

  private List<Translator> translators = new ArrayList<Translator>();
  private Translator matchedTranslator;

  public Category2InstructionTranslator() {
    translators.add(new AddInstructionTranslator());
    translators.add(new AddImmInstructionTranslator());
    translators.add(new SubInstructionTranslator());
    translators.add(new SubImmInstructionTranslator());
    translators.add(new MulInstructionTranslator());
    translators.add(new MulImmInstructionTranslator());
    translators.add(new AndInstructionTranslator());
    translators.add(new AndImmInstructionTranslator());
    translators.add(new NORInstructionTranslator());
    translators.add(new NorImmInstructionTranslator());
    translators.add(new SLTInstructionTranslator());
    translators.add(new SLTImmInstructionTranslator());
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
