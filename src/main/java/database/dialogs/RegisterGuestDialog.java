package database.dialogs;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class RegisterGuestDialog extends JDialog {
    private final static int STANDART = 4;
    private final static int BUSINESS = 9;
    private final static int LUXE = 11;
    private final JTextField userIDTextView = new JTextField("ID пользователя:");
    private final JTextField nameTextView = new JTextField("Имя:");
    private final JTextField birthDateTextView = new JTextField("Дата рождения:");
    private final JTextField passportTextView = new JTextField("Данные паспорта:");
    private final JTextField sexTextView = new JTextField("Пол:");
    private final JTextField phoneNumberTextView = new JTextField("Номер телефона:");
    private final JTextField emailTextView = new JTextField("email:");

    private final JTextField userIDTextEdit = new JTextField();
    private final JTextField nameTextEdit = new JTextField();
    private final JTextField birthDateTextEdit = new JTextField();
    private final JTextField passportTextEdit = new JTextField();
    private final JTextField sexTextEdit = new JTextField();
    private final JTextField phoneNumberTextEdit = new JTextField();
    private final JTextField emailTextEdit = new JTextField();

    private final JButton registerButton = new JButton("Зарегистрироваться");
    private final DataBaseTable dbTable;
    private final String dateIn;
    private final String dateOut;
    private String guestID;


    public RegisterGuestDialog(DataBaseTable dbTable, String dateIn, String dateOut) {
        this.dbTable = dbTable;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        initListeners();
        initContainers();
    }

    private void initListeners() {
        registerButton.addActionListener(n -> {
            try {
                onRegister();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container userIDContainer = new Container();
        initTextField(userIDContainer, userIDTextView, userIDTextEdit);
        mainContainer.add(userIDContainer);

        Container nameContainer = new Container();
        initTextField(nameContainer, nameTextView, nameTextEdit);
        mainContainer.add(nameContainer);

        Container birthDateContainer = new Container();
        initTextField(birthDateContainer, birthDateTextView, birthDateTextEdit);
        mainContainer.add(birthDateContainer);

        Container passportContainer = new Container();
        initTextField(passportContainer, passportTextView, passportTextEdit);
        mainContainer.add(passportContainer);

        Container sexContainer = new Container();
        initTextField(sexContainer, sexTextView, sexTextEdit);
        mainContainer.add(sexContainer);

        Container phoneNumberContainer = new Container();
        initTextField(phoneNumberContainer, phoneNumberTextView, phoneNumberTextEdit);
        mainContainer.add(phoneNumberContainer);

        Container emailContainer = new Container();
        initTextField(emailContainer, emailTextView, emailTextEdit);
        mainContainer.add(emailContainer);

        registerButton.setMaximumSize(new Dimension(500, 30));
        mainContainer.add(registerButton);
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

    private void onRegister() throws SQLException {
        String userID = userIDTextEdit.getText();
        String name = nameTextEdit.getText();
        String birthDate = birthDateTextEdit.getText();
        String passport = passportTextEdit.getText();
        String sex = sexTextEdit.getText();
        String phoneNumber = phoneNumberTextEdit.getText();
        String email = emailTextEdit.getText();

        if (userID == null || userID.isEmpty()) {
            userID = "NULL";
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            phoneNumber = "NULL";
        }
        if (email == null || email.isEmpty()) {
            email = "NULL";
        }

        if (name == null || sex == null || passport == null
                || name.isEmpty() || sex.isEmpty() || passport.isEmpty()) {
            callAlert("Please fill all the fields");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        try {
            formatter.parse(birthDate);
        } catch (ParseException | IllegalArgumentException e) {
            callAlert("Incorrect date format");
            return;
        }

        guestID = Main.sqlConnection.insertFunctionWithResult(
                "Exec registerGuest " + userID + ", "
                        + "'" + name + "'" + ", " + "'" + birthDate + "'" + ", "
                        + passport + ", " + "'" + sex + "'" + ", "
                        + phoneNumber + ", " + "'" + email + "'");
        // after the guest has been registered we have to make a booking
        bookNumber();
        dispose();
    }

    private void bookNumber() {
        int[] selected = dbTable.getSelected();
//        if (selected.length > 1) {
//            return;
//        }
        Arrays.stream(selected).forEach(i -> {
            try {
                int price = getRoomCost(dbTable.getInfo(i, 0));
                String bookingID = Main.sqlConnection.insertFunctionWithResult("Exec bookNumber "
                        + "'" + dateIn + "'" + ", " + "'" + dateOut + "'" + ", "
                        + guestID + ", "
                        + dbTable.getInfo(i, 0) + ", "
                        + price
                );
                showConfirmation(bookingID, price);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                callAlert("Incorrect room number value");
            }
        });
    }

    private int getRoomCost(String roomNumber) throws NumberFormatException {
        int number = Integer.parseInt(roomNumber);
        if (number > 0 && number <= STANDART) {
            return 100;
        } else if (number > STANDART && number <= BUSINESS) {
            return 200;
        } else if (number > BUSINESS && number <= LUXE) {
            return 300;
        } else {
            throw new NumberFormatException();
        }
    }

    private void showConfirmation(String bookingID, int price) {
        SuccessfulBDialog successfulBDialog = new SuccessfulBDialog(bookingID, price);
        successfulBDialog.pack();
        successfulBDialog.setLocationRelativeTo(null);
        successfulBDialog.setVisible(true);
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.setLocationRelativeTo(null);
        alert.pack();
        alert.setVisible(true);
    }

}
