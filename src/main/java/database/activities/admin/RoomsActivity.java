package database.activities.admin;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class RoomsActivity extends JPanel {

    private final JButton changeBedsNumber = new JButton("Изменить количество спальных мест");
    private final JButton changeCapacity = new JButton("Изменить вместимость");
    private final JButton changeAdditionalInfo = new JButton("Изменить доп. информацию");
    private final JButton alterRoomInfoButton = new JButton("Изменить данные комнаты");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    public RoomsActivity() throws SQLException {
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainer();
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
        alterRoomInfoButton.addActionListener(e -> onAlterRoomInfo());

        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(alterRoomInfoButton);
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void onAlterRoomInfo() {
        //dialog
    }

    private void onChangeCapacity() {
        changeField(0,6, "Exec changeCapacity");
    }

    private void onChangeAdditionalInfo() {
        changeField(0,8, "Exec changeAdditionalInfo");
    }

    private void onChangeBedsNumber() {
        changeField(0, 4, "Exec changeBedsNumber");
    }

    private void changeField(int roomNumber, int valueToChange, String command) {
        Vector<Vector> data = dbTable.getData();
        data.forEach(i -> {
            try {
                Main.sqlConnection.insertFunction(command
                        + i.get(roomNumber) + ","
                        + i.get(valueToChange));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }
}
