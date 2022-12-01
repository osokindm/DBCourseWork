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

public class OrderServiceDialog extends JDialog {

    private final JTextField serviceIDTextView = new JTextField("Номер услуги: ");
    private final JTextField isPaidTextView = new JTextField("Услуга оплачена? ");
    private final JTextField dateTextView = new JTextField("Дата ");

    private final JTextField serviceIDTextEdit = new JTextField();
    private final JTextField isPaidTextEdit = new JTextField("Нет");
    private final JTextField dateTextEdit = new JTextField();

    private final JButton confirmButton = new JButton("Подтвердить");
    private final DataBaseTable dbTable;

    public OrderServiceDialog(DataBaseTable dbTable) {
        this.dbTable = dbTable;
        initListeners();
        initContainers();
    }

    private void initListeners() {
        confirmButton.addActionListener(n -> onConfirm());
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container serviceIDContainer = new Container();
        initTextField(serviceIDContainer, serviceIDTextView, serviceIDTextEdit);
        mainContainer.add(serviceIDContainer);

        Container isPaidContainer = new Container();
        initTextField(isPaidContainer, isPaidTextView, isPaidTextEdit);
        mainContainer.add(isPaidContainer);

        Container dateContainer = new Container();
        initTextField(dateContainer, dateTextView, dateTextEdit);
        mainContainer.add(dateContainer);

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
        textEdit.setMaximumSize(new Dimension(200, 30));
        container.add(textEdit);
    }

    private void onConfirm() {
        String serviceID = serviceIDTextEdit.getText();
        String isPaid = isPaidTextEdit.getText().equals("Нет") ? "False" : "True";
        String date = dateTextEdit.getText();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        try {
            formatter.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            callAlert("Incorrect date format");
            return;
        }

        if (serviceID == null || date == null
                || serviceID.isEmpty() || date.isEmpty()) {
            callAlert("Please fill in all the fields");
        }

        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            try {
                Main.sqlConnection.insertFunction("Exec orderService "
                        + serviceID + ", "
                        + dbTable.getInfo(i, 3) + ", "
                        + "'" + isPaid + "'" + ", "
                        + "'" + date + "'"
                );
            } catch (SQLException ignore) {
            }
        });
        dispose();
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.setLocationRelativeTo(null);
        alert.pack();
        alert.setVisible(true);
    }
}
