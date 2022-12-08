package database.dialogs;

import database.Main;
import database.activities.admin.BookingActivity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class BookingsFilterDialog extends JDialog {

    private final JTextField classIDTextView = new JTextField("Класс номера:");
    private final JComboBox<String> classIDBox = new JComboBox<>(new String[]{"1", "2", "3"});

    private final JButton confirmButton = new JButton("Перейти к бронированиям");

    public BookingsFilterDialog() {
        initListeners();
        initContainers();
    }

    private void initListeners() {
        confirmButton.addActionListener(n -> {
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
        initComboBox(classIDContainer, classIDTextView, classIDBox);
        mainContainer.add(classIDContainer);

        Container buttonContainer = new Container();
        initButton(buttonContainer, confirmButton);
        mainContainer.add(buttonContainer);

        add(mainContainer);
    }

    private void initComboBox(Container container, JTextField textView, JComboBox<String> comboBox) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        textView.setBorder(new EmptyBorder(0, 0, 0, 0));
        textView.setEditable(false);
        textView.setHorizontalAlignment(SwingConstants.RIGHT);
        textView.setMaximumSize(new Dimension(300, 30));
        container.add(textView);

        comboBox.setMaximumSize(new Dimension(200, 30));
        container.add(comboBox);
    }

    private void initButton(Container container, JButton confirmButton) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        confirmButton.setHorizontalAlignment(SwingConstants.CENTER);
        confirmButton.setMaximumSize(new Dimension(300, 30));
        container.add(confirmButton);
    }

    private void onConfirm() throws SQLException {
        String classID = classIDBox.getSelectedItem().toString();

        BookingActivity bookingActivity = new BookingActivity(classID);
        Main.frameReceptionist.setContentPane(bookingActivity);
        Main.frameReceptionist.setVisible(true);
        dispose();
    }

}
