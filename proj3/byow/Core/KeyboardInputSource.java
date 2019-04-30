package byow.Core;

/**
 * Created by hug.
 */
//import byow.InputDemo.InputSource;
import edu.princeton.cs.introcs.StdDraw;

public class KeyboardInputSource {
    private static final boolean PRINT_TYPED_KEYS = false;
    public KeyboardInputSource() {

    }

    char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
        }
    }
}
