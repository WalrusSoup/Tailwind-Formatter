import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class TaiwindProjectHandler {
    public TaiwindProjectHandler(Project project) {
    }

    public static TaiwindProjectHandler getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, TaiwindProjectHandler.class);
    }
}
