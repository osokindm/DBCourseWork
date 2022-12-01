package database.activities.receptionist;

import database.Main;
import database.dialogs.BookingsFilterDialog;
import database.dialogs.RoomsFilterDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ReceptionistMenuActivity extends JPanel {

    private final Container container = new Container();
    private final JButton curBookingsButton = new JButton("Текущие бронирования");
    private final JButton bookNumberButton = new JButton("Забронировать номер");
    private final JButton backButton = new JButton("Назад");

    public ReceptionistMenuActivity() {
        setLayout(new BorderLayout());
        add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);

        initListeners();
        initContainer();
    }

    private void addButton(JButton button) {
        button.setMaximumSize(new Dimension(400, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initListeners() {
        curBookingsButton.addActionListener(e -> {
            try {
                showBookings();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        bookNumberButton.addActionListener(e -> {
            try {
                bookNumber();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        backButton.addActionListener(e -> onBack());
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        addButton(curBookingsButton);
        addButton(bookNumberButton);
        addButton(backButton);
        add(container, BorderLayout.CENTER);
    }

    private void bookNumber() throws SQLException {
        RoomsFilterDialog roomsFilterDialog = new RoomsFilterDialog();
        roomsFilterDialog.pack();
        roomsFilterDialog.setLocationRelativeTo(null);
        roomsFilterDialog.setVisible(true);
    }

    private void showBookings() throws SQLException {
        BookingsFilterDialog bookingsFilterDialog = new BookingsFilterDialog();
        bookingsFilterDialog.pack();
        bookingsFilterDialog.setLocationRelativeTo(null);
        bookingsFilterDialog.setVisible(true);
    }

    private void onBack() {
        Main.frameReceptionist.setVisible(false);
    }

}
