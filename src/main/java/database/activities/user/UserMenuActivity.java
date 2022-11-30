package database.activities.user;

import database.Main;
import database.dialogs.BookingDateDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UserMenuActivity extends JPanel {

    private final Container container = new Container();
    private final JButton hotelReviews = new JButton("Отзывы об отеле");
    private final JButton bookNumberButton = new JButton("Забронировать номер");
    private final JButton backButton = new JButton("Назад");

    public UserMenuActivity() {
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
        hotelReviews.addActionListener(e -> {
            try {
                showReviews();
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
        addButton(hotelReviews);
        addButton(bookNumberButton);
        addButton(backButton);
        add(container, BorderLayout.CENTER);
    }

    private void bookNumber() throws SQLException {
        BookingDateDialog bookingDateDialog = new BookingDateDialog();
        bookingDateDialog.pack();
        bookingDateDialog.setLocationRelativeTo(null);
        bookingDateDialog.setVisible(true);

//        BookNumberActivity bookNumberActivity = new BookNumberActivity(dateIn, dateOut);
//        Main.frameUser.setContentPane(bookNumberActivity);
//        Main.frameUser.setVisible(true);
    }

    private void showReviews() throws SQLException {
        HotelReviewsActivity hotelReviewsActivity = new HotelReviewsActivity();
        Main.frameUser.setContentPane(hotelReviewsActivity);
        Main.frameUser.setVisible(true);
    }

    private void onBack() {
        Main.frameUser.setVisible(false);
    }

}
