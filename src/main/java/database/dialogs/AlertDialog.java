package database.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AlertDialog extends JDialog {

    private final JTextArea errorName;
    private final JButton OKButton = new JButton("OK");
    private final Container container = new Container();

    public AlertDialog(String errorName) {

        setModal(true);

        this.errorName = new JTextArea("ERROR: " + errorName);
        this.errorName.setEditable(false);

        initContainer();
        initListeners();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        errorName.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(errorName);

        OKButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(OKButton);

        add(container);

    }

    private void initListeners() {
        OKButton.addActionListener(e -> onOK());
    }

    public void onOK() {
        dispose();
    }

}
