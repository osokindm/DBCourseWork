package database.activities.admin;

import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AdminMenuActivity extends JPanel {

    private final Container container = new Container();
    private final JButton roomsButton = new JButton("Номера");
    private final JButton reviewsButton = new JButton("Отзывы");
    private final JButton roomCleaningButton = new JButton("Обслуживание номеров");

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
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        addButton(roomsButton);
        addButton(reviewsButton);

        add(container, BorderLayout.CENTER);
    }

    private void showRooms() throws SQLException {
        RoomsActivity roomsActivity = new RoomsActivity();
        Main.frameAdmin.setContentPane(roomsActivity);
        Main.frameAdmin.setVisible(true);
    }

    private void showReviews() throws SQLException {
        RoomsActivity roomsActivity = new RoomsActivity();
        Main.frameAdmin.setContentPane(roomsActivity);
        Main.frameAdmin.setVisible(true);
    }

    private void showRoomCleaning() throws SQLException {
        // возможно без отдельного активити, а отобразить на room
    }
}
