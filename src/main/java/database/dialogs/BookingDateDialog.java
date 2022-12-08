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

public class BookingDateDialog extends JDialog {

    private final JTextField dateInTextView = new JTextField("Дата въезда:");
    private final JTextField dateOutTextView = new JTextField("Дата выезда:");
    private final JButton confirmButton = new JButton("Подтвердить");
    private final JXDatePicker datePickerIn;
    private final JXDatePicker datePickerOut;

    public BookingDateDialog() {
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
        confirmButton.addActionListener(n -> onConfirmReview());
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

        Container buttonContainer = new Container();
        initButton(buttonContainer, confirmButton);
        mainContainer.add(buttonContainer);

        add(mainContainer);
    }

    private void initButton(Container container, JButton confirmButton) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        confirmButton.setHorizontalAlignment(SwingConstants.CENTER);
        confirmButton.setMaximumSize(new Dimension(300, 30));
        container.add(confirmButton);
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

    private void onConfirmReview() {
        String dateIn;
        String dateOut;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        dateIn = formatter.format(datePickerIn.getDate());
        dateOut = formatter.format(datePickerOut.getDate());

        try {
            BookNumberActivity bookNumberActivity = new BookNumberActivity(dateIn, dateOut);
            Main.frameUser.setContentPane(bookNumberActivity);
            Main.frameUser.setVisible(true);
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
