package main.guiTable;

import main.details.DetailsFunction;
import main.serverAccess.ServerAccess;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class AuthPanel extends JPanel {
    private ServerAccess serverAccess;
    public JPanel mainPanel;

    public AuthPanel() {
        this.serverAccess = new ServerAccess();
        mainPanel = new JPanel();
        Frame jFrame = new JFrame("Authorization");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        JLabel fromLabel = new JLabel("UserName: ");
        JLabel toLable = new JLabel("Password: ");
        JLabel warningLabel = new JLabel();
        JButton insertButton = new JButton("Sing in");
        JTextField fromField = new JTextField(15);
        JTextField toField = new JTextField(15);

        insertButton.addActionListener(e-> {
           if (fromField.getText().isEmpty() || toField.getText().isEmpty()) {
               warningLabel.setText("Insert text in field");
               return;
           }
            try {
                if (!serverAccess.auth(fromField.getText(), toField.getText())) {
                    warningLabel.setText("UserName or password are wrong");
                    return;
                } else {
                    String role = serverAccess.getRole();
                    if (role.equals("ROLE_ADMIN")) {
                        AdminUI adminUI = new AdminUI(serverAccess);
                    } else if (role.equals("ROLE_USER")) {
                        UserUI userUI = new UserUI(serverAccess);
                    }
                }
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        jPanel.add(fromLabel, c);
        c.gridx = 1;
        jPanel.add(fromField, c);
        c.gridx = 0;
        c.gridy = 1;
        jPanel.add(toLable, c);
        c.gridx = 1;
        jPanel.add(toField, c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        jPanel.add(insertButton, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        jPanel.add(warningLabel, c);
        mainPanel.add(jPanel);
        jFrame.add(mainPanel);
        jFrame.setPreferredSize(new Dimension(500, 200));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2,
                dim.height / 2 - jFrame.getSize().height / 2);
        ((JFrame) jFrame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
