package edu.ufl.cda5155.domain;

public class Register {

  private final int id;
  private int value;
  private boolean isUsed;

  public Register(int id, int value) {
    super();
    this.id = id;
    this.value = value;
    isUsed = false;
  }

  public Register(int id) {
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

  public boolean isUsed() {
    return isUsed;
  }

  public void setUsed(boolean isUsed) {
    this.isUsed = isUsed;
  }



}
