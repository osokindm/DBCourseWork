package database.activities.user;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class HotelReviewsActivity extends JPanel {

    private final JButton backButton = new JButton("Назад");
    private DataBaseTable dbTable;

    public HotelReviewsActivity() throws SQLException {
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainer();
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"review", "rating", "date"};

        String result = Main.sqlConnection.selectFunction("Exec viewHotelReviews", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);
        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.NORTH);
    }

    private void onBack() {
        Main.frameUser.setContentPane(new UserMenuActivity());
        Main.frameUser.setVisible(true);
    }
}
