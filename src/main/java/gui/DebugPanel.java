package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The DebugPanel is used to show some "logs" or messages in the GUI during game play/development.
 */
public class DebugPanel extends JPanel {
    private static final Dimension PANEL_DIMENSION = new Dimension(600, 150);
    private final JTextArea jTextArea;

    public DebugPanel() {
        super(new BorderLayout());
        this.jTextArea = new JTextArea("");
        this.add(this.jTextArea);
        this.setPreferredSize(PANEL_DIMENSION);
        this.validate();
        this.setVisible(true);
    }

    /**
     *
     */
    public void redo() {
        this.validate();
    }

    /**
     * This method is used to update the content of the TextArea
     * @param obj object of any type that will be added to the TextArea
     */
    public void update(Object obj) {
        this.jTextArea.setText(obj.toString().trim());
        this.redo();
    }
}
