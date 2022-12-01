package database.activities.admin;

import database.DataBaseTable;
import database.Main;
import database.dialogs.AddReviewDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class ReviewsActivity extends JPanel {

    private final JButton addReview = new JButton("Добавить отзыв");
    private final JButton deleteReview = new JButton("Удалить отзыв");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    public ReviewsActivity() throws SQLException {
        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainerSouth();
        initButtonContainerNorth();
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"id", "classID", "review", "rating",
                "guestID", "date", "userID"};

        String result = Main.sqlConnection.selectFunction("Exec viewReviews", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);
        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        addReview.addActionListener(e -> onAddReview());
        deleteReview.addActionListener(e -> onDeleteReview());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainerSouth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(addReview);
        buttonContainer.add(deleteReview);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void initButtonContainerNorth() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.NORTH);
    }

    private void onDeleteReview() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            try {
                Main.sqlConnection.insertFunction("Exec deleteReview " +  dbTable.getInfo(i,0));
            } catch (SQLException ignore) {
            }
            dbTable.removeSelected(i);
        });

    }

    private void onAddReview() {
        AddReviewDialog addReviewDialog = new AddReviewDialog(this.dbTable);
        addReviewDialog.pack();
        addReviewDialog.setLocationRelativeTo(null);
        addReviewDialog.setVisible(true);
    }


    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }
}
