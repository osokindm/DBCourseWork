package database.dialogs;

import database.Main;
import database.activities.user.BookNumberActivity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BookingDateDialog extends JDialog {

    private final JTextField dateInTextView = new JTextField("Дата въезда:");
    private final JTextField dateOutTextView = new JTextField("Дата выезда:");
    private final JTextField dateInTextEdit = new JTextField();
    private final JTextField dateOutTextEdit = new JTextField();
    private final JButton confirmButton = new JButton("Подтвердить");

    public BookingDateDialog() {
        initListeners();
        initContainers();
    }

    private void initListeners() {
        confirmButton.addActionListener(n -> onConfirmReview());
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container dateInContainer = new Container();
        initTextField(dateInContainer, dateInTextView, dateInTextEdit);
        mainContainer.add(dateInContainer);

        Container dateOutContainer = new Container();
        initTextField(dateOutContainer, dateOutTextView, dateOutTextEdit);
        mainContainer.add(dateOutContainer);

        confirmButton.setMaximumSize(new Dimension(500, 30));
        mainContainer.add(confirmButton);
        add(mainContainer);
    }

    private void initTextField(Container container, JTextField textView, JTextField textEdit) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        textView.setBorder(new EmptyBorder(0, 0, 0, 0));
        textView.setEditable(false);
        textView.setHorizontalAlignment(SwingConstants.RIGHT);
        textView.setMaximumSize(new Dimension(300, 30));
        container.add(textView);
        textEdit.setMaximumSize(new Dimension(3000, 30));
        container.add(textEdit);
    }

    private void onConfirmReview() {
        String dateIn = dateInTextEdit.getText();
        String dateOut = dateOutTextEdit.getText();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        try {
            formatter.parse(dateIn);
            formatter.parse(dateOut);
        } catch (ParseException | IllegalArgumentException e) {
            callAlert("Incorrect date format");
            return;
        }

        try {
            BookNumberActivity bookNumberActivity = new BookNumberActivity(dateIn, dateOut);
            Main.frameUser.setContentPane(bookNumberActivity);
            Main.frameUser.setVisible(true);
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.setLocationRelativeTo(null);
        alert.pack();
        alert.setVisible(true);
    }
}
