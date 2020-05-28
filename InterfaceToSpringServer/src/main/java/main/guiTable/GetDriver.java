package main.guiTable;

import main.details.DetailsFunction;
import main.entity.Auto;
import main.entity.AutoPersonnel;
import main.serverAccess.ServerAccess;

import javax.swing.*;
import java.awt.*;

public class GetDriver {
    public JPanel mainPanel;

    public GetDriver(ServerAccess serverAccess, Long journal_id) {
        mainPanel = new JPanel();
        Frame jFrame = new JFrame("Get New Driver");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        JLabel firstNameLabel = new JLabel("FName: ");
        JTextField firstNameField = new JTextField(15);
        JLabel lastName = new JLabel("LName:");
        JTextField lastNameField = new JTextField(15);
        JLabel patherNameLabel = new JLabel("PName:");
        JTextField patherNameField = new JTextField(15);
        JLabel warningLabel = new JLabel();
        JButton insertButton = new JButton("Insert Driver");

        insertButton.addActionListener(e-> {
            if (firstNameField.getText().isEmpty() ||
                    lastNameField.getText().isEmpty() ||
                    patherNameField.getText().isEmpty()) {
                warningLabel.setText("Insert text in all fields");
                return;
            }

            if (DetailsFunction.checkString(firstNameField.getText()) ||
                    DetailsFunction.checkString(lastNameField.getText()) ||
                    DetailsFunction.checkString(patherNameField.getText())) {
                warningLabel.setText("All name must be Char");
                return;
            }

            try {
                AutoPersonnel ap = new AutoPersonnel(firstNameField.getText(), lastNameField.getText(), patherNameField.getText());
                Long id = serverAccess.insertAutoPersonnel(ap);
                GetPopup getPopup = new GetPopup(serverAccess, journal_id);
                jFrame.setVisible(false);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        jPanel.add(firstNameLabel, c);
        c.gridx = 1;
        jPanel.add(firstNameField, c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        jPanel.add(lastName, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        jPanel.add(lastNameField, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        jPanel.add(patherNameLabel, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 3;
        jPanel.add(patherNameField, c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        jPanel.add(insertButton, c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        jPanel.add(warningLabel, c);

        mainPanel.add(jPanel);
        jFrame.add(mainPanel);
        jFrame.setPreferredSize(new Dimension(500, 500));
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
