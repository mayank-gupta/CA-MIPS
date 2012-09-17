/**
 * 
 */
package edu.ufl.cda5155.executor;

import java.util.HashMap;
import java.util.Map;

import edu.ufl.cda5155.domain.GlobalData;
import edu.ufl.cda5155.domain.Instruction;
import edu.ufl.cda5155.domain.InstructionExecutionResult;
import edu.ufl.cda5155.domain.Operator;
import edu.ufl.cda5155.util.ProgramCounter;

/**
 * @author Mayank Gupta
 *
 */
public class DefaultExecutor implements Executor<InstructionExecutionResult> {

  private Map<Operator, Executor<InstructionExecutionResult>> executorMapper = new HashMap<Operator, Executor<InstructionExecutionResult>>();

  public DefaultExecutor() {
    executorMapper.put(Operator.ADD, new AddInstructionExecutor());
    executorMapper.put(Operator.ADDI, new AddImmInstructionExecutor());
    executorMapper.put(Operator.SUB, new SubInstructionExecutor());
    executorMapper.put(Operator.SUBI, new SubImmInstructionExecutor());
    executorMapper.put(Operator.MUL, new MulInstructionExecutor());
    executorMapper.put(Operator.MULI, new MulImmInstructionExecutor());
    executorMapper.put(Operator.NOR, new NORInstructionExecutor());
    executorMapper.put(Operator.NORI, new NORImmInstructionExecutor());
    executorMapper.put(Operator.SLT, new SLTInstructionExecutor());
    executorMapper.put(Operator.SLTI, new SLTImmInstructionExecutor());
    executorMapper.put(Operator.AND, new AndInstructionExecutor());
    executorMapper.put(Operator.ANDI, new AndImmInstructionExecutor());
    executorMapper.put(Operator.JUMP, new JumpInstructionExecutor());
    executorMapper.put(Operator.JUMP_REGISTER, new JRInstructionExecutor());
    executorMapper.put(Operator.BRANCH_ON_EQUAL, new BEQInstructionExecutor());
    executorMapper.put(Operator.BRANCH_ON_L_T_ZERO, new BLTZInstructionExecutor());
    executorMapper.put(Operator.BRANCH_ON_G_T_ZERO, new BGTZInstructionExecutor());
    executorMapper.put(Operator.SW, new SWInstructionExecutor());
    executorMapper.put(Operator.LW, new LWInstructionExecutor());
    executorMapper.put(Operator.SHIFT_LEFT_LOGICAL, new SLLInstructionExecutor());
    executorMapper.put(Operator.SHIFT_RIGHT_ARITHMETIC, new SRAInstructionExecutor());
    executorMapper.put(Operator.SHIFT_RIGHT_LOGICAL, new SRLInstructionExecutor());
    //executorMapper.put(Operator.NOP, new NOPInstructionExecutor());
  }

  @Override
  public InstructionExecutionResult execute(Instruction instruction, ProgramCounter programCounter, GlobalData registers) {
    if(executorMapper.containsKey(instruction.getOperator())) {
      return executorMapper.get(instruction.getOperator()).execute(instruction, programCounter, registers); 
    } else {
      throw new IllegalArgumentException("No mapped executor against this instruction");
    }
  }

}
