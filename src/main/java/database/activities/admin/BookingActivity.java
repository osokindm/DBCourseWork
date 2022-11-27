package database.activities.admin;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class BookingActivity extends JPanel {

    private final JButton alterBooking = new JButton("Редактировать бронь");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    public BookingActivity() throws SQLException {
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainerSouth();
        initButtonContainerNorth();
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"dateIn", "dateOut", "guestID", "id",
                "roomNumber", "totalCost"};

        String result = Main.sqlConnection.selectFunction("Exec viewBookings", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);
        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        alterBooking.addActionListener(e -> onAlterBooking());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainerSouth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(alterBooking);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void initButtonContainerNorth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.NORTH);
    }

    private void onAlterBooking() {
        var data = dbTable.getData();
        data.forEach(i -> {
            try {
                Main.sqlConnection.insertFunction("Exec alterBooking" + " "
                        + "'" + i.get(0) + "'" + ", "
                        + "'" + i.get(1) + "'" + ", "
                        + i.get(2) + ", "
                        + i.get(3) + ", "
                        + i.get(4) + ", "
                        + i.get(5));
            } catch (SQLException ignore) {
            }
        });
    }

    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }
}
