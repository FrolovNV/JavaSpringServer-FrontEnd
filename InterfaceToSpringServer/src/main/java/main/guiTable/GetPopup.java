package main.guiTable;

import main.entity.Auto;
import main.entity.AutoPersonnel;

import main.serverAccess.ServerAccess;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class GetPopup {
    private JPanel mainPanel;
    private JComboBox<String> listAutoPersonnel;
    private JComboBox<String> listAuto;

    public GetPopup(ServerAccess serverAccess, Long id) throws Exception{
        mainPanel = new JPanel();
        Frame jFrame = new JFrame("Get PopUp");
        JPanel jPanel = new JPanel();
        List<AutoPersonnel> autoPersonnels = serverAccess.findAllAutoPersonnel();
        List<Auto> autoList = serverAccess.findAllAuto();
        Vector<String> vector = new Vector<>();
        Vector<String> vectorAuto = new Vector<>();
        for (int i = 0; i < autoPersonnels.size(); i++) {
            vector.add(autoPersonnels.get(i).getFirstName() + " " + autoPersonnels.get(i).getLastName()
                    + " " + autoPersonnels.get(i).getPatherName());
        }
        this.listAutoPersonnel = new JComboBox<String>(vector);

        for (int i = 0; i < autoList.size(); i++) {
            vectorAuto.add(autoList.get(i).getNum() + " " + autoList.get(i).getMark());
        }

        this.listAuto = new JComboBox<String>(vectorAuto);

        jPanel.setLayout(new GridBagLayout());

        JLabel fromLabel = new JLabel("Auto Personnel: ");
        JLabel toLabel = new JLabel("Auto :");
        JLabel warningLabel = new JLabel();
        JButton insertButton = new JButton("Get this Route");
        JButton getAnotherDriver = new JButton("Create New Driver");
        JButton getNewAuto = new JButton("Create New Auto");

        insertButton.addActionListener(e-> {
            try {
                int indexAutoPersonnel = listAutoPersonnel.getSelectedIndex();
                int indexAuto = listAuto.getSelectedIndex();
                AutoPersonnel autoPersonnel = autoPersonnels.get(indexAutoPersonnel);
                Auto auto = autoList.get(indexAuto);
                auto.setAutoPersonnel(autoPersonnel);
                serverAccess.setAutoPersonnelInAuto(auto.getId(), autoPersonnel);
                serverAccess.setAutoWithPersonnel(id, auto);
                serverAccess.setTimeOutInJournal(id);
                jFrame.setVisible(false);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });

        getAnotherDriver.addActionListener(e -> {
            try {
                GetDriver getDriver = new GetDriver(serverAccess, id);
                jFrame.setVisible(false);
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        });

        getNewAuto.addActionListener(e-> {
            try {
                GetCar getNewDriverPopUP = new GetCar(serverAccess, id);
                jFrame.setVisible(false);
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        jPanel.add(fromLabel, c);
        c.gridx = 1;
        jPanel.add(listAutoPersonnel, c);
        c.gridx = 0;
        c.gridy = 1;
        jPanel.add(toLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        jPanel.add(listAuto, c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        jPanel.add(insertButton, c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        jPanel.add(getAnotherDriver, c);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        jPanel.add(getNewAuto, c);
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridx = 0;
        jPanel.add(warningLabel, c);
        mainPanel.add(jPanel);
        jFrame.add(mainPanel);
        jFrame.setPreferredSize(new Dimension(600, 450));
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
