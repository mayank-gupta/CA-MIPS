
import java.io.IOException;

/**
 * @author Mayank Gupta
 *
 */
public class MIPSsim {

  public static void main(String[] args) throws IOException {
    if(args.length != 2) {
      System.err.println("Input args not valid !");
    } else {
      Project1Executor executor = new Project1Executor();
      executor.execute(args[0], args[1]);
    }
  }
}
