package database.activities.user;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class EventsActivity extends JPanel {

    private final JButton backButton = new JButton("Назад");

    public EventsActivity() throws SQLException {
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainer();
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"description", "date", "age limit"};

        String result = Main.sqlConnection.selectFunction("Exec viewEvents", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        DataBaseTable dbTable = new DataBaseTable(res, columnsName);
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
