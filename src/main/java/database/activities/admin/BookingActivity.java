package database.activities.admin;

import database.DataBaseTable;
import database.Main;
import database.activities.receptionist.ReceptionistMenuActivity;
import database.dialogs.OrderServiceDialog;
import database.dialogs.RegisterGuestDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class BookingActivity extends JPanel {

    private final JButton alterBooking = new JButton("Редактировать бронь");
    private final JButton orderService = new JButton("Заказать услугу");
    private final JButton addGuests = new JButton("Добавить гостей");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;
    private final User windowIsUsedBy;
    private enum User {
        RECEPTIONIST, ADMIN
    }

    public BookingActivity() throws SQLException {
        windowIsUsedBy = User.ADMIN;
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainerSouth();
        initButtonContainerNorth();
    }

    public BookingActivity(String classID) throws SQLException {
        windowIsUsedBy = User.RECEPTIONIST;
        setLayout(new BorderLayout());
        initTableWithFilter(classID);
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

    private void initTableWithFilter(String classID) throws SQLException {
        String[] columnsName = {"dateIn", "dateOut", "guestID", "id",
                "roomNumber", "totalCost"};

        String result = Main.sqlConnection.selectFunction("Exec checkBookings " + classID, columnsName.length);

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
        if (windowIsUsedBy.equals(User.RECEPTIONIST)) {
            orderService.addActionListener((e -> onOrderService()));
            addGuests.addActionListener(e -> onAddGuests());
        }
    }

    private void initButtonContainerSouth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(alterBooking);
        if (windowIsUsedBy.equals(User.RECEPTIONIST)) {
            buttonContainer.add(orderService);
            buttonContainer.add(addGuests);
        }
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

    private void onAddGuests() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            RegisterGuestDialog registerAndBookDialog = new RegisterGuestDialog(dbTable.getInfo(i, 3));
            registerAndBookDialog.pack();
            registerAndBookDialog.setLocationRelativeTo(null);
            registerAndBookDialog.setVisible(true);
        });

    }

    private void onOrderService() {
        // IMPORTANT: when ordering service booking ID should be selected (4th column)
        OrderServiceDialog orderServiceDialog = new OrderServiceDialog(dbTable);
        orderServiceDialog.pack();
        orderServiceDialog.setLocationRelativeTo(null);
        orderServiceDialog.setVisible(true);
    }

    private void onBack() {
        if (windowIsUsedBy.equals(User.ADMIN)) {
            Main.frameAdmin.setContentPane(new AdminMenuActivity());
            Main.frameAdmin.setVisible(true);
        } else if (windowIsUsedBy.equals(User.RECEPTIONIST)){
            Main.frameReceptionist.setContentPane(new ReceptionistMenuActivity());
            Main.frameReceptionist.setVisible(true);
        }
    }

}
