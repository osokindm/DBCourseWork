package database.activities.admin;

import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AdminMenuActivity extends JPanel {

    private final Container container = new Container();
    private final JButton bookingsButton = new JButton("Бронирования");
    private final JButton roomsButton = new JButton("Номера");
    private final JButton reviewsButton = new JButton("Отзывы");
    private final JButton backButton = new JButton("Назад");

    public AdminMenuActivity() {
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
        bookingsButton.addActionListener(e -> {
            try {
                showBookings();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        roomsButton.addActionListener(e -> {
            try {
                showRooms();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        reviewsButton.addActionListener(e -> {
            try {
                showReviews();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        backButton.addActionListener(e -> onBack());
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        addButton(bookingsButton);
        addButton(roomsButton);
        addButton(reviewsButton);
        addButton(backButton);
        add(container, BorderLayout.CENTER);
    }

    private void showRooms() throws SQLException {
        RoomsActivity roomsActivity = new RoomsActivity();
        Main.frameAdmin.setContentPane(roomsActivity);
        Main.frameAdmin.setVisible(true);
    }

    private void showReviews() throws SQLException {
        ReviewsActivity reviewsActivity = new ReviewsActivity();
        Main.frameAdmin.setContentPane(reviewsActivity);
        Main.frameAdmin.setVisible(true);
    }

    private void showBookings() throws SQLException {
        BookingActivity bookingActivity = new BookingActivity();
        Main.frameAdmin.setContentPane(bookingActivity);
        Main.frameAdmin.setVisible(true);
    }

    private void onBack() {
        Main.frameAdmin.setVisible(false);
    }
}
