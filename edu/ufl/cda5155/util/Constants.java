/**
 * 
 */
package edu.ufl.cda5155.util;

/**
 * @author Mayank Gupta
 *
 */
public final class Constants {

  private Constants() {

  }

  public static final int NO_OF_REGISTERS = 32;
  public static final int NO_OF_MEM_LOCATIONS = 24;
  public static final int BASE_ADDRESS = 64;
  public static final int PRE_ISSUE_BUFFER_SIZE = 4;
  public static final int PRE_MEM_BUFFER_SIZE = 2;
  public static final int PRE_ALU_BUFFER_SIZE = 2;
  public static final int PRE_ALUB_BUFFER_SIZE = 2;
  public static final int POST_ALU_BUFFER_SIZE = 2;
  public static final int POST_MEM_BUFFER_SIZE = 2;
  public static final int POST_ALUB_BUFFER_SIZE = 2;
  public static final int NO_OF_INS_ISSUED_PER_CYCLE = 2;
}
