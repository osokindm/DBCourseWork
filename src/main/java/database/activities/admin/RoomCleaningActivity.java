package database.activities.admin;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class RoomCleaningActivity extends JPanel {
    private final JButton changeShift = new JButton("Поменять смену");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    public RoomCleaningActivity() throws SQLException {
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainerSouth();
        initButtonContainerNorth();
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"id", "userID", "name", "phone",
                "email", "shift"};

        String result = Main.sqlConnection.selectFunction("Exec viewMaid", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);
        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        changeShift.addActionListener(e -> onChangeShift());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainerSouth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(changeShift);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void initButtonContainerNorth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.NORTH);
    }

    private void onChangeShift() {
        var data = dbTable.getData();
        data.forEach(i -> {
            try {
                Main.sqlConnection.insertFunction("Exec changeShift "
                        + i.get(0) + "," + "'"
                        + i.get(5)+"'");
            } catch (SQLException ignore) {
            }
        });
    }

    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }
}
