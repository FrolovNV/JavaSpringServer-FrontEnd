package main.guiTable;

import main.details.DetailsFunction;
import main.entity.Routes;
import main.serverAccess.ServerAccess;

import javax.swing.*;
import java.awt.*;

public class AddPopup {
    public JPanel mainPanel;

    public AddPopup(ServerAccess serverAccess) {
        mainPanel = new JPanel();
        Frame jFrame = new JFrame("Authorization");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        JLabel fromLabel = new JLabel("Route's Name: ");
        JLabel warningLabel = new JLabel();
        JButton insertButton = new JButton("Sing in");
        JTextField fromField = new JTextField(15);

        insertButton.addActionListener(e-> {
            if (fromField.getText().isEmpty()) {
                warningLabel.setText("Insert text in field");
                return;
            }
            if (DetailsFunction.checkString(fromField.getText())) {
                warningLabel.setText("Routes must be only char");
                return;
            }
            try {
                Routes routes = new Routes(fromField.getText());
                serverAccess.insertRouteWithJournal(routes);
                jFrame.setVisible(false);
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
        jFrame.setPreferredSize(new Dimension(300, 100));
        jFrame.pack();
        jFrame.setVisible(true);
    }


}
