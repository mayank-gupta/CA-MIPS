package edu.ufl.cda5155.executor;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.util.ProgramCounter;

public interface Executor<T> {

  T execute(Instruction instruction, ProgramCounter programCounter, GlobalData registers);

}
