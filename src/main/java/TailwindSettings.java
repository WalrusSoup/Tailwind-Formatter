import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@State(name = "TailwindFormatterSettings", storages = {@Storage("tailwind-formatter.xml")})
public class TailwindSettings implements PersistentStateComponent<TailwindSettings> {
    @NotNull
    private String customClassOrder = "";

    public static TailwindSettings getInstance(Project project) {
        return ServiceManager.getService(project, TailwindSettings.class);
    }

    public void setCustomClassOrder(@NotNull String customClassOrder) {
        this.customClassOrder = customClassOrder;
    }

    @NotNull
    public String getCustomClassOrder() {
        return customClassOrder;
    }

    @NotNull
    public List<String> getCustomClassOrderList() {
        if (customClassOrder.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.asList(customClassOrder.split("\n"));
    }

    @Nullable
    @Override
    public TailwindSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TailwindSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
