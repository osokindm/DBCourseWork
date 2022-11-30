package database.activities.user;

import database.DataBaseTable;
import database.Main;
import database.dialogs.RegisterGuestDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class BookNumberActivity extends JPanel {


    private final JButton bookNumber = new JButton("Забронировать номер");
    private final JButton backButton = new JButton("Назад");

    private final String dateIn;
    private final String dateOut;
    private DataBaseTable dbTable;

    public BookNumberActivity(String dateIn, String dateOut) throws SQLException {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        setLayout(new BorderLayout());
        initTable(dateIn, dateOut);
        initListeners();
        initButtonContainerSouth();
        initButtonContainerNorth();
    }

    private void initTable(String dateIn, String dateOut) throws SQLException {
        String[] columnsName = {"number", "capacity", "numberOfBeds", "numberOfRooms",
                "roomArea", "viewFromWindow"};

        String result = Main.sqlConnection.selectFunction(
                "Exec viewAvailableRooms " + "'" + dateIn + "'" + ", " + "'" + dateOut + "'",
                columnsName.length
        );

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);
        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        bookNumber.addActionListener(e -> onBookNumber());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainerSouth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(bookNumber);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void initButtonContainerNorth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.NORTH);
    }

    private void onBookNumber() {
        // 1. choose one number
        // 2. register a guest
        // 3. get booking id
        RegisterGuestDialog registerGuestDialog = new RegisterGuestDialog(dbTable, dateIn, dateOut);
        registerGuestDialog.pack();
        registerGuestDialog.setLocationRelativeTo(null);
        registerGuestDialog.setVisible(true);
    }

    private void onBack() {
        Main.frameUser.setContentPane(new UserMenuActivity());
        Main.frameUser.setVisible(true);
    }

}
