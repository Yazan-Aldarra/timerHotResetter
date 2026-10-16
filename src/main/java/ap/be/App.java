package ap.be;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static List<String> names = new ArrayList<>();

    public static void main(String[] args) {
        new JettyServer().start();
    }
}
