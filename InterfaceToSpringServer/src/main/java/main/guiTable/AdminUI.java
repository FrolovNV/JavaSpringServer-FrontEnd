package main.guiTable;

import main.details.DetailsFunction;
import main.entity.Journal;
import main.serverAccess.ServerAccess;

import javax.swing.*;
import java.awt.*;

public class AdminUI {
    private ServerAccess serverAccess;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private TableModule tableModule;
    private JTable mainTable;
    private JScrollPane tableScrollBar;
    private JButton refreshButton;
    private JButton addButton;
    private JTextArea textArea;
    private JButton filterIn;
    private JButton filterOut;
    private JButton filterBack;

    public AdminUI(ServerAccess serverAccess) throws Exception {
        this.mainFrame = new JFrame("Admin window");
        this.mainPanel = new JPanel();
        this.serverAccess = serverAccess;
        this.tableModule = new TableModule();
        this.textArea = new JTextArea();
        this.tableModule.setColumnNames(new String[]{"id", "timeOut", "timeIn", "routes_id", "auto_id"});
        this.refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(100, 25));
        this.addButton = new JButton("Add Routes");
        addButton.setPreferredSize(new Dimension(150, 25));
        filterIn = new JButton("Filter by In");
        filterIn.setPreferredSize(new Dimension(150, 25));
        filterOut = new JButton("Filter by Out");
        filterOut.setPreferredSize(new Dimension(150, 25));
        filterBack = new JButton("Filter by Back");

        JPanel rpanel = new JPanel();
        JPanel lpanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        refreshButton.addActionListener(e -> {
            try {
                refresh();
            } catch (Exception e1) {
                System.out.println("Trouble with refresh data");
            }
        });

        addButton.addActionListener(e-> {
            try {
                addRoutes();
            }
            catch (Exception exp) {
                System.out.println("Trouble with adding");
            }

        });

        filterIn.addActionListener(e -> {
            try {
                filterIn();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        filterOut.addActionListener(e -> {
            try {
                filterOut();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        filterBack.addActionListener(e-> {
            try {
                filterBack();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        this.mainTable = new JTable(tableModule);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = mainTable.getSelectionModel();

        selectionModel.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;
            try {
                selectTableRow();
            }
            catch (Exception e1) {
                System.out.println("Something wroung with textarea");
            }
        });

        tableModule.addTableModelListener(e -> {
            try {
                selectTableRow();
            }
            catch (Exception exp) {
                System.out.println("Something wroung with textarea");
            }
        });
        this.tableScrollBar = new JScrollPane(mainTable);

        JPanel bl = new JPanel();
        JPanel br = new JPanel();
        bl.add(refreshButton, BorderLayout.WEST);
        br.add(addButton, BorderLayout.EAST);
        JPanel centerPanel = new JPanel();

        tableScrollBar.setPreferredSize(new Dimension(450, 400));
        lpanel.add(tableScrollBar);
        textArea.setPreferredSize(new Dimension(450, 400));
        rpanel.add(textArea);

        topPanel.add(filterIn, BorderLayout.LINE_START);
        topPanel.add(filterOut, BorderLayout.CENTER);
        topPanel.add(filterBack, BorderLayout.LINE_END);

        centerPanel.add(lpanel, BorderLayout.WEST);
        centerPanel.add(rpanel, BorderLayout.EAST);
        bottomPanel.add(bl, BorderLayout.WEST);
        bottomPanel.add(br, BorderLayout.EAST);

        mainFrame.setPreferredSize(new Dimension(1200, 600));
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2,
                dim.height / 2 - mainFrame.getSize().height / 2);


        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void refresh() throws Exception {
        String bigDataJournal = serverAccess.getBigDataFromJournal();
        if (bigDataJournal.isEmpty()) {
            String str[][] = {};
            tableModule.updateValues(str);
            tableScrollBar.updateUI();
            return;
        }
        String str[][] = DetailsFunction.parsingData(bigDataJournal, 5);
        tableModule.updateValues(str);
        tableScrollBar.updateUI();
    }

    public void filterIn() throws Exception{
        String bigDataJournal = serverAccess.getBigDataFromJournal();
        if (bigDataJournal.isEmpty()) {
            String str[][] = {};
            tableModule.updateValues(str);
            tableScrollBar.updateUI();
            return;
        }
        String str[][] = DetailsFunction.filterIn(bigDataJournal);
        tableModule.updateValues(str);
        tableScrollBar.updateUI();
    }

    public void filterOut() throws Exception{
        String bigDataJournal = serverAccess.getBigDataFromJournal();
        if (bigDataJournal.isEmpty()) {
            String str[][] = {};
            tableModule.updateValues(str);
            tableScrollBar.updateUI();
            return;
        }
        String str[][] = DetailsFunction.filterOut(bigDataJournal);
        tableModule.updateValues(str);
        tableScrollBar.updateUI();
    }

    public void filterBack() throws Exception{
        String bigDataJournal = serverAccess.getBigDataFromJournal();
        if (bigDataJournal.isEmpty()) {
            String str[][] = {};
            tableModule.updateValues(str);
            tableScrollBar.updateUI();
            return;
        }
        String str[][] = DetailsFunction.filterBack(bigDataJournal);
        tableModule.updateValues(str);
        tableScrollBar.updateUI();
    }

    public void addRoutes() throws Exception {
        AddPopup addPopup = new AddPopup(serverAccess);
        refresh();
    }

    private void selectTableRow() throws Exception{
        int r = mainTable.getSelectedRow();
        if (tableModule.getRowCount() == 0 || r < 0) {
            selectTableProduct(-1, "");
        } else {
            selectTableProduct(r, (String) tableModule.getValueAt(r, 0));
        }
    }

    private void selectTableProduct(int index, String id) throws Exception {
        if (index == -1) {
            textArea.setText("");
            return;
        }
        Long idJ = Long.parseLong(id);
        Journal journal = serverAccess.findJournalById(idJ);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Journal\n" +
                "id: " + journal.getId() + "\n" +
                "timeOut: " + journal.getTimeOut() + "\n" +
                "timeIn: " + journal.getTimeIn() + "\n" +
                "routes:\n" +
                "   id: " + journal.getRoutes_id().getId() + "\n" +
                "   name: " + journal.getRoutes_id().getName() + "\n");
        if (!journal.getAuto_id().getId().equals((long)-1)) {
            stringBuilder.append("auto:\n" +
                    "   id: " + journal.getAuto_id().getId() + "\n" +
                    "   num: " + journal.getAuto_id().getNum() + "\n" +
                    "   color: " + journal.getAuto_id().getColor() + "\n" +
                    "   mark: " + journal.getAuto_id().getMark() + "\n" +
                    "   auto_personnel:\n" +
                    "       id: " + journal.getAuto_id().getAutoPersonnel().getId() + "\n" +
                    "       firstName: " + journal.getAuto_id().getAutoPersonnel().getFirstName() + "\n" +
                    "       lastName: " + journal.getAuto_id().getAutoPersonnel().getLastName() + "\n" +
                    "       patherName: " + journal.getAuto_id().getAutoPersonnel().getPatherName() + "\n");
        } else {
            stringBuilder.append("auto: Not chosen\n");
        }
        textArea.setText(stringBuilder.toString());
    }
}
