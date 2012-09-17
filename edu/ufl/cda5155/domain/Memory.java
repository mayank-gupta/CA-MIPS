package edu.ufl.cda5155.domain;

public class Memory {

  private final int id;
  private int value;

  public Memory(int id, int value) {
    super();
    this.id = id;
    this.value = value;
  }

  public Memory(int id) {
    super();
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }


}
