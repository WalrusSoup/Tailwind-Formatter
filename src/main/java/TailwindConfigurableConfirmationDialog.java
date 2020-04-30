import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class TailwindConfigurableConfirmationDialog  extends DialogWrapper {

    public TailwindConfigurableConfirmationDialog() {
        super(true);
        init();
        setTitle("Are You Sure?");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("This will overwrite your existing settings. Please back up your existing classes before continuing.");
        label.setPreferredSize(new Dimension(100, 100));
        dialogPanel.add(label, BorderLayout.CENTER);
        return dialogPanel;
    }
}
