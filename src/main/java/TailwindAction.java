import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class TailwindAction extends AnAction {
    TailwindParser parser = new TailwindParser();

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
        String body = parser.processBody(document.getText());
        WriteCommandAction.runWriteCommandAction(project, () -> {
                document.setText(body);
        });
    }
}