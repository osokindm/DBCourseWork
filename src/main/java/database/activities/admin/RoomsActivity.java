package database.activities.admin;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class RoomsActivity extends JPanel {

    private final JButton changeBedsNumber = new JButton("Изменить количество спальных мест");
    private final JButton changeCapacity = new JButton("Изменить вместимость");
    private final JButton changeAdditionalInfo = new JButton("Изменить доп. информацию");
    private final JButton roomCleaningButton = new JButton("Обслуживание номеров");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    public RoomsActivity() throws SQLException {
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainerSouth();
        initButtonContainerNorth();
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"number", "numberOfRooms", "roomArea", "classID",
                "numberOfBeds", "safe", "capacity", "viewFromWindow", "additionalInfo",
                "maidID", "hotelID"};

        String result = Main.sqlConnection.selectFunction("Exec roomInfo", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);
        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        changeBedsNumber.addActionListener(e -> onChangeBedsNumber());
        changeCapacity.addActionListener(e -> onChangeCapacity());
        changeAdditionalInfo.addActionListener(e -> onChangeAdditionalInfo());
        roomCleaningButton.addActionListener(e -> showRoomCleaning());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainerSouth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(changeBedsNumber);
        buttonContainer.add(changeCapacity);
        buttonContainer.add(changeAdditionalInfo);
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void initButtonContainerNorth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(backButton);
        buttonContainer.add(roomCleaningButton);
        add(buttonContainer, BorderLayout.NORTH);
    }

    private void showRoomCleaning() {
        try {
            RoomCleaningActivity roomCleaningActivity = new RoomCleaningActivity();
            Main.frameAdmin.setContentPane(roomCleaningActivity);
            Main.frameAdmin.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void onChangeCapacity() {
        changeFieldInt(6, "Exec changeCapacity ");
    }

    private void onChangeAdditionalInfo() {
        changeFieldString(8, "Exec changeAdditionalInfo ");
    }

    private void onChangeBedsNumber() {
        changeFieldInt(4, "Exec changeBedsNumber ");
    }

    private void changeFieldInt(int valueColumn, String command) {
        final int roomNumberColumn = 0;
        var data = dbTable.getData();
        data.forEach(i -> {
            try {
                Main.sqlConnection.insertFunction(command + " "
                        + i.get(roomNumberColumn) + ", "
                        + i.get(valueColumn));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void changeFieldString(int valueColumn, String command) {
        final int roomNumberColumn = 0;
        var data = dbTable.getData();
        data.forEach(i -> {
            try {
                Main.sqlConnection.insertFunction(command + " "
                        + i.get(roomNumberColumn) + ", "
                        + "'" + i.get(valueColumn) + "'");
            } catch (SQLException ignore) {
            }
        });
    }

    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }
}
