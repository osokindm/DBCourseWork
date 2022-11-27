package database.dialogs;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class DeleteReviewDialog extends JDialog {

    private final JTextField idTextView = new JTextField("id отзыва:");
    private final JTextField idTextEdit = new JTextField();

    private final JButton deleteReviewButton = new JButton("Удалить отзыв");

    private DataBaseTable dbTable;

    public DeleteReviewDialog(DataBaseTable dbTable) {
        this.dbTable = dbTable;
        initListeners();
        initContainers();
    }

    private void initListeners() {
        deleteReviewButton.addActionListener(n -> {
            try {
                onDeleteReview();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container idContainer = new Container();
        initTextField(idContainer, idTextView, idTextEdit);
        mainContainer.add(idContainer);


        deleteReviewButton.setMaximumSize(new Dimension(500, 30));
        mainContainer.add(deleteReviewButton);
        add(mainContainer);
    }

    private void initTextField(Container container, JTextField textView, JTextField textEdit) {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        textView.setBorder(new EmptyBorder(0, 0, 0, 0));
        textView.setEditable(false);
        textView.setHorizontalAlignment(SwingConstants.RIGHT);
        textView.setMaximumSize(new Dimension(300, 30));
        container.add(textView);
        textEdit.setMaximumSize(new Dimension(200, 30));
        container.add(textEdit);
    }

    private void onDeleteReview() throws SQLException {
        String id = idTextEdit.getText();
        try {
           Integer.parseInt(id);
        } catch (NumberFormatException e) {
            callAlert("Incorrect review ID value");
            return;
        }


        dispose();
        try{
            Main.sqlConnection.insertFunction("Exec deleteReview " + id);
        } catch (SQLException ignored) {
        }
        // чтобы удалить нужен не id, а номер строки. Либо получить номер строки, либо обновить всю таблицу (мб тригер какой-то)
        // dbTable.removeSelected(idParsed);

        dispose();
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }
}
