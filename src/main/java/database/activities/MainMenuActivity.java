package database.activities;

import database.Main;
import database.activities.admin.AdminMenuActivity;
import database.activities.receptionist.ReceptionistMenuActivity;
import database.activities.user.UserMenuActivity;

import javax.swing.*;

public class MainMenuActivity extends JPanel {
    private JButton adminButton;
    private JButton userButton;
    private JButton receptionistButton;
    private JPanel mainPanel;

    public MainMenuActivity() {
        initListeners();
        add(mainPanel);
    }

    private void initListeners() {
        adminButton.addActionListener(e -> onAdmin());
        receptionistButton.addActionListener(e -> onReceptionist());
        userButton.addActionListener(e -> onUser());
    }

    private void onAdmin() {
        if(Main.frameAdmin != null) {
            Main.frameAdmin.setVisible(true);
            return;
        }
        Main.frameAdmin = new JFrame("Admin");
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setSize(1000, 1000);
        Main.frameAdmin.setVisible(true);
        Main.frameAdmin.setLocationRelativeTo(null);
    }

    private void onReceptionist() {
        if(Main.frameReceptionist != null) {
            Main.frameReceptionist.setVisible(true);
            return;
        }
        Main.frameReceptionist = new JFrame("Agent");
        Main.frameReceptionist.setContentPane(new ReceptionistMenuActivity());
        Main.frameReceptionist.setSize(1000, 1000);
        Main.frameReceptionist.setVisible(true);
        Main.frameReceptionist.setLocationRelativeTo(null);
    }

    private void onUser() {
        if(Main.frameUser != null) {
            Main.frameUser.setVisible(true);
            return;
        }
        Main.frameUser = new JFrame("User");
        Main.frameUser.setContentPane(new UserMenuActivity());
        Main.frameUser.setSize(1000, 1000);
        Main.frameUser.setVisible(true);
        Main.frameUser.setLocationRelativeTo(null);
    }

}
