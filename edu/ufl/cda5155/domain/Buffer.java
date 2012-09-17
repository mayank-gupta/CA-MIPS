/**
 * 
 */
package edu.ufl.cda5155.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mayank Gupta
 *
 */
public class Buffer {

  private List<BufferEntry> buffer;
  private final int MAX_SIZE;

  public Buffer(int bSize) {
    buffer = new ArrayList<BufferEntry>();
    MAX_SIZE = bSize;
  }

  public BufferEntry getData(int i) {
    if(i > buffer.size()) {
      throw new IllegalArgumentException("Buffer null at location accessed");
    }
    return buffer.get(i);
  }

  public List<BufferEntry> getBufferEntries() {
    return buffer;
  }

  public BufferEntry getAndRemove(int i) {
    if(i >= buffer.size()) {
      throw new IllegalArgumentException("Index not apt: Buffer size=" + buffer.size() + " but requested index : " + i);
    }
    final BufferEntry temp = buffer.get(i);
    buffer.remove(temp);
    return temp;
  }

  public boolean remove(Instruction ins) {
    int index = 0;
    boolean found = true;
    if(!isEmpty()) { 
      do {
	if(getData(index).getInstruction() == ins) {
	  found = true;
	}
      } while((index+1) < getBufferEntries().size());
    }
    return found;
  }

  public void addToBuffer(BufferEntry ins) {
    if(isFull()) {
      throw new IllegalArgumentException("Buffer Full");
    }
    buffer.add(ins); 
  }

  public boolean isFull() {
    return buffer.size() >= (MAX_SIZE);
  }

  public int available() {
    int available = MAX_SIZE - buffer.size();
    return available < 0 ? 0 : available;
  }

  public boolean isEmpty() {
    return buffer.isEmpty(); 
  }
}
