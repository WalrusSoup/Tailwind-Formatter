import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import sun.jvm.hotspot.debugger.win32.coff.TestParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TailwindUtility {
    private static final Logger logger = Logger.getInstance(TailwindUtility.class);
    public List<String> classOrder = Collections.emptyList();

    public TailwindUtility() {}

    public void loadDefaultClassOrder()
    {
        logger.info("Loading default class order");
        try {
            InputStream inputStream = TailwindUtility.class.getResourceAsStream("/classes.txt");
            classOrder = (new BufferedReader(new InputStreamReader(inputStream))).lines().collect(Collectors.toList());
        } catch (Exception e) {
            // I should probably do something here on the off chance we're missing that file
            logger.error("Error resolving class order from resources");
            logger.error(e.getMessage());
        }
    }

    public void loadClassOrderFromFile(VirtualFile file)
    {
        logger.info("Loading custom class order from user default .tailwindorder");
        try {
            InputStream inputStream = file.getInputStream();
            classOrder = (new BufferedReader(new InputStreamReader(inputStream))).lines().collect(Collectors.toList());
        } catch (Exception e) {
            // I should probably do something here on the off chance we're missing that file
            logger.error("Error resolving class order from user defined .tailwindorder file, will fall back to defaults");
            logger.error(e.getMessage());
            loadDefaultClassOrder();
        }
    }
}
