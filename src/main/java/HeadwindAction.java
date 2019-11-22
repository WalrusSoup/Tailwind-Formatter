import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

public class HeadwindAction extends AnAction {
    TailwindParser parser = new TailwindParser();

    public HeadwindAction() {
        super("Run Headwind");
    }

    public void actionPerformed(AnActionEvent event) {
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