package main.guiTable;

import main.details.DetailsFunction;
import main.entity.Auto;
import main.serverAccess.ServerAccess;

import javax.swing.*;
import java.awt.*;

public class GetCar {
    public JPanel mainPanel;

    public GetCar(ServerAccess serverAccess, Long journal_id) {
        mainPanel = new JPanel();
        Frame jFrame = new JFrame("Get New Car");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        JLabel numberLabel = new JLabel("Number: ");
        JTextField numberField = new JTextField(15);
        JLabel colorLabel = new JLabel("Color: ");
        JTextField colorField = new JTextField(15);
        JLabel markLabel = new JLabel("Mark: ");
        JTextField markField = new JTextField(15);
        JLabel warningLabel = new JLabel();
        JButton insertButton = new JButton("Insert Driver");

        insertButton.addActionListener(e-> {
            if (numberField.getText().isEmpty() ||
                    colorField.getText().isEmpty() ||
                    markField.getText().isEmpty()) {
                warningLabel.setText("Insert text in all fields");
                return;
            }
            if (!DetailsFunction.CheckNumber(numberField.getText())) {
                warningLabel.setText("Numbers need looks like \"E777EE\"");
                return;
            }
            try {
                Auto auto = new Auto(numberField.getText(), colorField.getText(), markField.getText());
                serverAccess.insertAuto(auto);
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
        jPanel.add(numberLabel, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        jPanel.add(numberField, c);
        c.gridwidth = 2;
        c.gridy = 1;
        c.gridx = 0;
        jPanel.add(colorLabel, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 1;
        jPanel.add(colorField, c);
        c.gridwidth = 2;
        c.gridy = 2;
        c.gridx = 0;
        jPanel.add(markLabel, c);
        c.gridwidth = 2;
        c.gridy = 2;
        c.gridx = 1;
        jPanel.add(markField, c);
        c.gridwidth = 2;
        c.gridy = 3;
        c.gridx = 0;
        jPanel.add(insertButton, c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 8;
        jPanel.add(warningLabel, c);

        mainPanel.add(jPanel);
        jFrame.add(mainPanel);
        jFrame.setPreferredSize(new Dimension(500, 500));
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
