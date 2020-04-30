import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TailwindConfigurable implements Configurable, Configurable.NoScroll {
    private JPanel rootPanel;
    private JTextArea customClassOrderTextArea;
    private JLabel AutoImport;
    private final TailwindSettings settings;

    public TailwindConfigurable(Project project) {
        settings = TailwindSettings.getInstance(project);
        AutoImport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TailwindUtility utility = new TailwindUtility();
                customClassOrderTextArea.setText(String.join("\n", utility.classOrder));
            }
        });
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
