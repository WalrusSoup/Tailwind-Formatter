import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.List;

public class TailwindAction extends AnAction {

    public Boolean isCustomConfiguration = false;

    public TailwindAction() {
        super("Run Tailwind Formatter");
    }

    public void actionPerformed(AnActionEvent event) {
        VirtualFile currentFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if(currentFile == null) {
            return;
        }
        // Get all the required data from data keys
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();

        final TailwindParser parser = new TailwindParser(new TailwindSorter(getClassOrder(project), isCustomConfiguration));

        String body = parser.processBody(document.getText());
        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.setText(body);
        });
    }

    private List<String> getClassOrder(Project project) {
        TailwindUtility utility = new TailwindUtility();
        // TODO: This is deprecated
        VirtualFile configurationFile = VfsUtilCore.findRelativeFile(".tailwindorder", project.getBaseDir());
        if(configurationFile == null) {
            utility.loadDefaultClassOrder();
        } else {
            isCustomConfiguration = true;
            utility.loadClassOrderFromFile(configurationFile);
        }

        return utility.classOrder;
    }
}