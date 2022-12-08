package database.dialogs;

import database.DataBaseTable;
import database.Main;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewDialog extends JDialog {

    private final JTextField classIDTextView = new JTextField("id класса:");
    private final JTextField reviewTextView = new JTextField("Отзыв:");
    private final JTextField ratingTextView = new JTextField("Оценка:");
    private final JTextField guestIDTextView = new JTextField("id гостя:");
    private final JTextField dateTextView = new JTextField("Дата:");
    private final JTextField userIDTextView = new JTextField("id пользователя:");

    private final JTextField reviewTextEdit = new JTextField();
    private final JComboBox<String> ratingBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

    private final JTextField guestIDTextEdit = new JTextField();
    private final JXDatePicker datePicker;
    private final JTextField userIDTextEdit = new JTextField();
    private final JComboBox<String> classIDBox = new JComboBox<>(new String[]{"1", "2", "3", "NULL"});

    private final JButton addReviewButton = new JButton("Добавить отзыв");

    private final DataBaseTable dbTable;

    public AddReviewDialog(DataBaseTable dbTable) {
        this.dbTable = dbTable;
        datePicker = initDatePicker();
        initListeners();
        initContainers();
    }

    private JXDatePicker initDatePicker() {
        JXDatePicker picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
        return picker;
    }

    private void initListeners() {
        addReviewButton.addActionListener(n -> {
            try {
                onAddReview();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container dateContainer = new Container();
        initDateField(dateContainer, dateTextView, datePicker);
        mainContainer.add(dateContainer);

        Container classIDContainer = new Container();
        initComboBox(classIDContainer, classIDTextView, classIDBox);
        mainContainer.add(classIDContainer);

        Container ratingContainer = new Container();
        initComboBox(ratingContainer, ratingTextView, ratingBox);
        mainContainer.add(ratingContainer);

        Container reviewContainer = new Container();
        initTextField(reviewContainer, reviewTextView, reviewTextEdit);
        mainContainer.add(reviewContainer);

        Container guestIDContainer = new Container();
        initTextField(guestIDContainer, guestIDTextView, guestIDTextEdit);
        mainContainer.add(guestIDContainer);

        Container userIDContainer = new Container();
        initTextField(userIDContainer, userIDTextView, userIDTextEdit);
        mainContainer.add(userIDContainer);

        Container buttonContainer = new Container();
        initButton(buttonContainer, addReviewButton);
        mainContainer.add(buttonContainer);

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

    private void initDateField(Container container, JTextField textView, JXDatePicker datePicker) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        textView.setBorder(new EmptyBorder(0, 0, 0, 0));
        textView.setEditable(false);
        textView.setHorizontalAlignment(SwingConstants.RIGHT);
        textView.setMaximumSize(new Dimension(300, 30));
        container.add(textView);
        datePicker.setMaximumSize(new Dimension(1000, 30));
        container.add(datePicker);
    }

    private void initButton(Container container, JButton confirmButton) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        confirmButton.setHorizontalAlignment(SwingConstants.CENTER);
        confirmButton.setMaximumSize(new Dimension(300, 30));
        container.add(confirmButton);
    }


    private void onAddReview() throws SQLException {
        String classID = classIDBox.getSelectedItem().toString();
        String review = reviewTextEdit.getText();
        String rating = ratingBox.getSelectedItem().toString();
        String guestID = guestIDTextEdit.getText();
        String userID = userIDTextEdit.getText();
        String date;

        if (review == null || review.isEmpty()) {
            callAlert("Empty review");
        }

        try {
            // either guestID or userID should be not null
            // if some ID is null we set "NULL" to this variable
            if (guestID == null || guestID.isEmpty()) {
                if (userID == null || userID.isEmpty()) {
                    callAlert("Both user ID and guest ID are empty");
                    return;
                } else {
                    Integer.parseInt(userID);
                    guestID = "NULL";
                }
            } else {
                Integer.parseInt(guestID);
                if (userID == null || userID.isEmpty()) {
                    userID = "NULL";
                } else {
                    Integer.parseInt(userID);
                }
            }
        } catch (NumberFormatException e) {
            callAlert("Incorrect guest ID or user ID value");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        date = formatter.format(datePicker.getDate());

        dispose();
        String resultId = Main.sqlConnection.insertFunctionWithResult(
                "Exec addReview " + classID + ", "
                        + "'" + review + "'" + ", " + rating + ", " + guestID + ", "
                        + "'" + date + "'" + ", " + userID);

        String[] newReview = {resultId, classID, review, rating, guestID, date, userID};
        dbTable.addRow(newReview);
        dispose();
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.setLocationRelativeTo(null);
        alert.pack();
        alert.setVisible(true);
    }

}
