package database.dialogs;

import database.DataBaseTable;
import database.Main;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class OrderServiceDialog extends JDialog {

    private final JTextField serviceIDTextView = new JTextField("Номер услуги: ");
    private final JTextField isPaidTextView = new JTextField("Услуга оплачена? ");
    private final JTextField dateTextView = new JTextField("Дата ");


    private final JComboBox<String> serviceIDBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});
    private final JComboBox<String> isPaidBox = new JComboBox<>(new String[]{"Да", "Нет"});
    private final JXDatePicker datePicker;

    private final JButton confirmButton = new JButton("Подтвердить");
    private final DataBaseTable dbTable;

    public OrderServiceDialog(DataBaseTable dbTable) {
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
        confirmButton.addActionListener(n -> onConfirm());
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container dateContainer = new Container();
        initDateField(dateContainer, dateTextView, datePicker);
        mainContainer.add(dateContainer);

        Container serviceIDContainer = new Container();
        initComboBox(serviceIDContainer, serviceIDTextView, serviceIDBox);
        mainContainer.add(serviceIDContainer);

        Container isPaidContainer = new Container();
        initComboBox(isPaidContainer, isPaidTextView, isPaidBox);
        mainContainer.add(isPaidContainer);

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

    private void onConfirm() {
        String serviceID = serviceIDBox.getSelectedItem().toString();
        String isPaid = isPaidBox.getSelectedItem().toString()
                .equals("Нет") ? "False" : "True";
        String date;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        date = formatter.format(datePicker.getDate());

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

}
