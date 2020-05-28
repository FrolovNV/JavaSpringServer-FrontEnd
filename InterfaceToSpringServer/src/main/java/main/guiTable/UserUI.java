package main.guiTable;

import main.details.DetailsFunction;
import main.entity.Journal;
import main.serverAccess.ServerAccess;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserUI {
    private ServerAccess serverAccess;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private TableModule tableModule;
    private JTable mainTable;
    private JScrollPane tableScrollBar;
    private JButton refreshButton;
    private JButton getRoute;
    private JButton backButton;
    private JButton filterDGButton;
    private JButton filterGButton;
    private JTextArea textArea;
    private Long ROUTE_ID = null;

    public UserUI(ServerAccess serverAccess) throws Exception {
        this.mainFrame = new JFrame("User window");
        this.mainPanel = new JPanel();
        this.serverAccess = serverAccess;
        this.tableModule = new TableModule();
        this.textArea = new JTextArea(18, 50);
        this.tableModule.setColumnNames(new String[]{"id", "routes_id", "in_process"});
        this.refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(100, 25));
        this.getRoute = new JButton("Get Route");
        getRoute.setPreferredSize(new Dimension(150, 25));
        this.backButton = new JButton("Back Auto");
        backButton.setPreferredSize(new Dimension(150, 25));
        this.filterDGButton = new JButton("Filter By Did't Get");
        filterDGButton.setPreferredSize(new Dimension(160, 25));
        this.filterGButton = new JButton("Filter by Got");
        filterGButton.setPreferredSize(new Dimension(160, 25));

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

        getRoute.addActionListener(e-> {
            try {
                getRoutes();
            }
            catch (Exception exp) {
                System.out.println("Trouble with getting");
            }

        });

        backButton.addActionListener(e -> {
            try {
                backButton();
            }
            catch (Exception exp) {
                System.out.println("Trouble with back");
            }

        });

        filterGButton.addActionListener(e -> {
            try {
                filterDid();

            } catch (Exception exp) {
                System.out.println("Didn't find");
            }
        });

        filterDGButton.addActionListener(e->{
            try {
                filterByGot();
            } catch (Exception exp) {
                System.out.println("Didn't find");
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
        br.add(getRoute, BorderLayout.EAST);
        br.add(backButton, BorderLayout.WEST);
        JPanel centerPanel = new JPanel();

        tableScrollBar.setPreferredSize(new Dimension(450, 400));
        lpanel.add(tableScrollBar);
        textArea.setPreferredSize(new Dimension(450, 400));
        rpanel.add(textArea);

        topPanel.add(filterDGButton, BorderLayout.LINE_START);
        topPanel.add(filterGButton, BorderLayout.LINE_END);
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
        refresh();
        mainFrame.setVisible(true);
    }

    public void refresh() throws Exception {
        String bigDataJournal = serverAccess.getRoutesData();
        if (bigDataJournal.isEmpty()) {
            String str[][] = {};
            tableModule.updateValues(str);
            tableScrollBar.updateUI();
            return;
        }
        String str[][] = DetailsFunction.parsingData(bigDataJournal, 3);
        tableModule.updateValues(str);
        tableScrollBar.updateUI();
    }

    public void getRoutes() throws Exception {
        Journal journal = serverAccess.findJournalByRouteId(ROUTE_ID);
        GetPopup getPopup = new GetPopup(serverAccess, journal.getId());
        refresh();
    }

    public void backButton() throws Exception {
        Journal journal = serverAccess.findJournalByRouteId(ROUTE_ID);
        serverAccess.setTimeInInJournal(journal.getId());
        refresh();
    }

    public void filterByGot() throws Exception {
        String bigDataJournal = serverAccess.getRoutesData();
        if (bigDataJournal.isEmpty()) {
            String str[][] = {};
            tableModule.updateValues(str);
            tableScrollBar.updateUI();
            return;
        }
        String str[][] = DetailsFunction.filter(bigDataJournal, "Got");
        tableModule.updateValues(str);
        tableScrollBar.updateUI();
    }

    public void filterDid() throws Exception {
        String bigDataJournal = serverAccess.getRoutesData();
        if (bigDataJournal.isEmpty()) {
            String str[][] = {};
            tableModule.updateValues(str);
            tableScrollBar.updateUI();
            return;
        }
        String str[][] = DetailsFunction.filter(bigDataJournal, "Didn't_Get");
        tableModule.updateValues(str);
        tableScrollBar.updateUI();
    }

    private void selectTableRow() throws Exception{
        int r = mainTable.getSelectedRow();
        if (tableModule.getRowCount() == 0 || r < 0) {
            selectTableProduct(-1, "", "");
        } else {
            selectTableProduct(r, (String) tableModule.getValueAt(r, 0), (String)tableModule.getValueAt(r, 2));
        }
    }

    private void selectTableProduct(int index, String id, String status) throws Exception {
        if (index == -1) {
            textArea.setText("");
            getRoute.setEnabled(index >= 0);
            backButton.setEnabled(index >= 0);
            return;
        }
        if (status.equals("Got")) {
            getRoute.setEnabled(false);
            backButton.setEnabled(true);
        } else {
            getRoute.setEnabled(true);
            backButton.setEnabled(false);
        }
        ROUTE_ID = Long.parseLong(id);
        Long idJ = Long.parseLong(id);
        Journal journal = serverAccess.findJournalByRouteId(idJ);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Routes:\n" +
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
                    "       patherName: " + journal.getAuto_id().getAutoPersonnel().getId() + "\n");
        } else {
            stringBuilder.append("auto and autoPersonnel: Not chosen\n");
        }
        textArea.setText(stringBuilder.toString());
    }
}
