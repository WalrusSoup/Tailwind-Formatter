import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TailwindConfigurable implements Configurable, Configurable.NoScroll {
    private JPanel rootPanel;
    private JTextArea customClassOrderTextArea;
    private final TailwindSettings settings;

    public TailwindConfigurable(Project project) {
        settings = TailwindSettings.getInstance(project);
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Tailwind Formatter";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return this.rootPanel;
    }

    public boolean isModified() {
        return !customClassOrderTextArea.getText().equals(settings.getCustomClassOrder());
    }

    public void apply() throws ConfigurationException {
        settings.setCustomClassOrder(customClassOrderTextArea.getText().trim());
    }

    public void reset() {
        customClassOrderTextArea.setText(settings.getCustomClassOrder());
    }
}
