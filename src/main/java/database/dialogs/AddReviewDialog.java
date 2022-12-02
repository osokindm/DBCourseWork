package database.dialogs;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddReviewDialog extends JDialog {

    private final JTextField classIDTextView = new JTextField("id класса:");
    private final JTextField reviewTextView = new JTextField("Отзыв:");
    private final JTextField ratingTextView = new JTextField("Оценка:");
    private final JTextField guestIDTextView = new JTextField("id гостя:");
    private final JTextField dateTextView = new JTextField("Дата:");
    private final JTextField userIDTextView = new JTextField("id пользователя:");


    private final JTextField classIDTextEdit = new JTextField();
    private final JTextField reviewTextEdit = new JTextField();
    private final JTextField ratingTextEdit = new JTextField();
    private final JTextField guestIDTextEdit = new JTextField();
    private final JTextField dateTextEdit = new JTextField();
    private final JTextField userIDTextEdit = new JTextField();

    private final JButton addReviewButton = new JButton("Добавить отзыв");

    private final DataBaseTable dbTable;

    public AddReviewDialog(DataBaseTable dbTable) {
        this.dbTable = dbTable;
        initListeners();
        initContainers();
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

        Container classIDContainer = new Container();
        initTextField(classIDContainer, classIDTextView, classIDTextEdit);
        mainContainer.add(classIDContainer);

        Container reviewContainer = new Container();
        initTextField(reviewContainer, reviewTextView, reviewTextEdit);
        mainContainer.add(reviewContainer);

        Container ratingContainer = new Container();
        initTextField(ratingContainer, ratingTextView, ratingTextEdit);
        mainContainer.add(ratingContainer);

        Container guestIDContainer = new Container();
        initTextField(guestIDContainer, guestIDTextView, guestIDTextEdit);
        mainContainer.add(guestIDContainer);

        Container dateContainer = new Container();
        initTextField(dateContainer, dateTextView, dateTextEdit);
        mainContainer.add(dateContainer);

        Container userIDContainer = new Container();
        initTextField(userIDContainer, userIDTextView, userIDTextEdit);
        mainContainer.add(userIDContainer);

        addReviewButton.setMaximumSize(new Dimension(500, 30));
        mainContainer.add(addReviewButton);
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


    private void onAddReview() throws SQLException {
        String classID = classIDTextEdit.getText();
        String review = reviewTextEdit.getText();
        String rating = ratingTextEdit.getText();
        String guestID = guestIDTextEdit.getText();
        String date = dateTextEdit.getText();
        String userID = userIDTextEdit.getText();

        try {
            if (classID != null && !classID.isEmpty()) {
                if ((Integer.parseInt(classID)) > 3 || Integer.parseInt(classID) < 1) {
                    callAlert("Class ID can only be 1/2/3");
                }
            } else {
                classID = "NULL";
            }
        } catch (NumberFormatException e) {
            callAlert("Incorrect class ID value");
            return;
        }

        if (review == null || review.isEmpty()) {
            callAlert("Empty review");
        }

        try {
            if ((Integer.parseInt(rating)) > 5 || Integer.parseInt(rating) < 1) {
                callAlert("Rating can only be 1/2/3/4/5");
            }
        } catch (NumberFormatException e) {
            callAlert("Incorrect rating value");
            return;
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
        try {
            formatter.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            callAlert("Incorrect date format");
            return;
        }

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
