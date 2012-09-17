/**
 * 
 */
package edu.ufl.cda5155.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.ufl.cda5155.util.Constants;

/**
 * @author Mayank Gupta
 *
 */
public class GlobalData {

  private Register[] registers = new Register[Constants.NO_OF_REGISTERS];
  private Map<Integer, Memory> memory = new LinkedHashMap<Integer, Memory>();
  private int baseMemLocation;

  public GlobalData(int[] regValues, int[] memoryValues, int baseMemLocation) {
    if(regValues != null) {
      for(int i=0;i<Constants.NO_OF_REGISTERS;i++) {
	registers[i] = new Register(i, regValues[i]);
      }
    } else {
      for(int i=0;i<Constants.NO_OF_REGISTERS;i++) {
	registers[i] = new Register(i, 0);
      }
    }

    if(memoryValues != null) {
      for(int i=0;i<Constants.NO_OF_MEM_LOCATIONS;i++) {
	memory.put(baseMemLocation + i*4, new Memory(baseMemLocation + i*4, memoryValues[i]));
      }
    } else {
      for(int i=0;i<Constants.NO_OF_MEM_LOCATIONS;i++) {
	memory.put(baseMemLocation + i*4, new Memory(baseMemLocation + i*4, 0));
      }
    }

    this.baseMemLocation = baseMemLocation;
  }

  public void setRegisterValue(int id, int value) {
    registers[id].setValue(value);
  }

  public void updateRegisterValue(int id, int value) {
    registers[id].setValue(registers[id].getValue() + value);
  }

  public int getRegisterValue(int id) {
    return registers[id].getValue();
  }

  public Register getRegister(int id) {
    return registers[id];
  }

  public Register[] getAllRegisters() {
    return registers;
  }

  public void setMemoryValue(int id, int value) {
    memory.get(baseMemLocation + id*4).setValue(value);
  }

  public void setMemoryValueDirect(int id, int value) {
    memory.get(id).setValue(value);
  }

  public int getMemoryValue(int id) {
    return memory.get(baseMemLocation + id*4).getValue();
  }

  public int getMemoryValueDirect(int id) {
    return memory.get(id).getValue();
  }

  public Map<Integer, Memory> getAllMemory() {
    return memory;
  }

  public int getBaseMemLocation() {
    return baseMemLocation;
  }

  public void setBaseMemLocation(int baseMemLocation) {
    this.baseMemLocation = baseMemLocation;
  }


}
