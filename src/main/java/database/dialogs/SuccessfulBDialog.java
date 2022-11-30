package database.dialogs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SuccessfulBDialog extends JDialog {

    private final JTextField bookingIDTextView;
    private final JTextField totalCostTextView;
    private final JButton okButton = new JButton("Ок");

    public SuccessfulBDialog(String bookingID, int price) {
        bookingIDTextView = new JTextField("Номер брони: " + bookingID);
        totalCostTextView = new JTextField("Итоговая стоимость: " + price);
        initListeners();
        initContainers();
    }

    private void initListeners() {
        okButton.addActionListener(n -> dispose());
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container bookingIDContainer = new Container();
        initTextField(bookingIDContainer, bookingIDTextView);
        mainContainer.add(bookingIDContainer);

        Container totalCostContainer = new Container();
        initTextField(totalCostContainer, totalCostTextView);
        mainContainer.add(totalCostContainer);

        okButton.setMaximumSize(new Dimension(500, 30));
        mainContainer.add(okButton);
        add(mainContainer);
    }

    private void initTextField(Container container, JTextField textView) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        textView.setBorder(new EmptyBorder(0, 0, 0, 0));
        textView.setEditable(false);
        textView.setHorizontalAlignment(SwingConstants.RIGHT);
        textView.setMaximumSize(new Dimension(300, 30));
        container.add(textView);
    }
}
