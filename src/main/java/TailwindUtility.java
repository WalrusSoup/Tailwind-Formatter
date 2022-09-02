import sun.jvm.hotspot.debugger.win32.coff.TestParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TailwindUtility {
    public List<String> classOrder = Collections.emptyList();

    public TailwindUtility() {
        try {
            InputStream inputStream = TailwindUtility.class.getResourceAsStream("/classes.txt");
            classOrder = (new BufferedReader(new InputStreamReader(inputStream))).lines().collect(Collectors.toList());
        } catch (Exception e) {
            // I should probably do something here on the off chance we're missing that file
            System.out.println("Error resolving class order from resources");
            System.out.println(e.getMessage());
        }
    }
}
