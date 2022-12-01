package database.dialogs;

import database.Main;
import database.activities.admin.BookingActivity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class BookingsFilterDialog extends JDialog {

    private final JTextField classIDTextView = new JTextField("Класс номера:");
    private final JTextField classIDTextEdit = new JTextField();

    private final JButton okButton = new JButton("Перейти к бронированиям");

    public BookingsFilterDialog() {
        initListeners();
        initContainers();
    }

    private void initListeners() {
        okButton.addActionListener(n -> {
            try {
                onConfirm();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container classIDContainer = new Container();
        initTextField(classIDContainer, classIDTextView, classIDTextEdit);
        mainContainer.add(classIDContainer);

        okButton.setMaximumSize(new Dimension(500, 30));
        mainContainer.add(okButton);
        add(mainContainer);
    }

    private void initTextField(Container container, JTextField textView, JTextField textEdit) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        textView.setBorder(new EmptyBorder(0, 0, 0, 0));
        textView.setEditable(false);
        textView.setHorizontalAlignment(SwingConstants.RIGHT);
        textView.setMaximumSize(new Dimension(300, 30));
        container.add(textView);
        textEdit.setMaximumSize(new Dimension(200, 30));
        container.add(textEdit);
    }

    private void onConfirm() throws SQLException {
        try {
            int classID = Integer.parseInt(classIDTextEdit.getText());
            if (classID < 1 || classID > 3) {
                callAlert("Incorrect class ID");
            }
        } catch (NumberFormatException e) {
            callAlert("Incorrect class ID");
        }

        BookingActivity bookingActivity = new BookingActivity(classIDTextEdit.getText());
        Main.frameReceptionist.setContentPane(bookingActivity);
        Main.frameReceptionist.setVisible(true);
        dispose();
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.setLocationRelativeTo(null);
        alert.pack();
        alert.setVisible(true);
    }
}
