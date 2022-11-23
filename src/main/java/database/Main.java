package database;

import database.activities.MainMenuActivity;

import javax.swing.*;
import java.sql.*;

public class Main {

    public static final int ADMIN_ROLE = 0;
    public static final int AGENT_ROLE = 1;
    public static final int USER_ROLE = 2;
    private static int currentRole = 1;
    private static final String CONNECTION_URL =
            "jdbc:sqlserver://LAPTOP-U8FDODOC\\SQLEXPRESS;database=CourseWorkHotel;" +
                    "integratedSecurity=true;encrypt=false;";

    public static int USERID = 1;
    public static JFrame frameAdmin;
    public static JFrame frameReceptionist;
    public static JFrame frameUser;
    public static SQLConnection sqlConnection;

    public static void main(String[] args) {

        sqlConnection = new SQLConnection(CONNECTION_URL);

        JFrame mainFrame = new JFrame("CourseWork");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(new MainMenuActivity());
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    public static int getCurrentRole() {
        return currentRole;
    }

}