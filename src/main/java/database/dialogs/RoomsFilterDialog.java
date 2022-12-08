package database.dialogs;

import database.Main;
import database.activities.user.BookNumberActivity;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RoomsFilterDialog extends JDialog {

    private final JTextField dateInTextView = new JTextField("Дата въезда:");
    private final JTextField dateOutTextView = new JTextField("Дата выезда:");
    private final JTextField classIDTextView = new JTextField("Класс номера:");
    private final JXDatePicker datePickerIn;
    private final JXDatePicker datePickerOut;
    private final JComboBox<String> classIDBox = new JComboBox<>(new String[]{"1", "2", "3"});

    private final JButton confirmButton = new JButton("Перейти к номерам");

    public RoomsFilterDialog() {
        datePickerIn = initDatePicker();
        datePickerOut = initDatePicker();
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

        Container dateInContainer = new Container();
        initDateField(dateInContainer, dateInTextView, datePickerIn);
        mainContainer.add(dateInContainer);

        Container dateOutContainer = new Container();
        initDateField(dateOutContainer, dateOutTextView, datePickerOut);
        mainContainer.add(dateOutContainer);

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
        String dateIn;
        String dateOut;
        String classID = classIDBox.getSelectedItem().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        dateIn = formatter.format(datePickerIn.getDate());
        dateOut = formatter.format(datePickerOut.getDate());

        try {
            BookNumberActivity bookNumberActivity = new BookNumberActivity(dateIn, dateOut, classID);
            Main.frameReceptionist.setContentPane(bookNumberActivity);
            Main.frameReceptionist.setVisible(true);
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
