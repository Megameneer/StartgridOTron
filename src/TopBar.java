//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
//public class TopBar extends JMenuBar implements MouseListener, ActionListener {
//    private MainScreen mainScreen;
//    private ConfigManager configManager;
//
//    private JMenu fileMenu;
//    private JMenuItem newFile;
//    private JMenuItem openFile;
//    private JMenuItem saveFile;
//    private JMenu openRecent;
//    private JMenuItem close;
//
//    private JMenu manchesMenu;
//    private JMenuItem setAmountOfManches;
//    private JMenuItem setAmountOfHeats;
//
//    private JMenu categoriesMenu;
//    private JButton addCategory;
//
//    private JMenu generatorMenu;
//    private JMenuItem generateStartGrid;
//
//    TopBar(MainScreen mainScreen) {
//        super();
//        this.mainScreen = mainScreen;
//        configManager = mainScreen.getConfigManager();
//        fileMenu = new JMenu("Bestand");
//        add(fileMenu);
//        newFile = new JMenuItem("Nieuwe cross (Ctrl + N)", UIManager.getIcon("FileView.fileIcon"));
//        newFile.addActionListener(this);
//        fileMenu.add(newFile);
//        openFile = new JMenuItem("Cross openen (Ctrl + O)", UIManager.getIcon("FileView.directoryIcon"));
//        openFile.addActionListener(this);
//        fileMenu.add(openFile);
//        saveFile = new JMenuItem("Cross opslaan (Ctrl + S)", UIManager.getIcon("FileView.hardDriveIcon"));
//        saveFile.addActionListener(this);
//        fileMenu.add(saveFile);
//        fileMenu.addSeparator();
//        openRecent = new JMenu("Recente crosses");
//        openRecent.addMouseListener(this);
//        fileMenu.add(openRecent);
//        fileMenu.addSeparator();
//        close = new JMenuItem("Sluiten (Ctrl + Q)");
//        close.addActionListener(this);
//        fileMenu.add(close);
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//        openRecent.removeAll();
//        if (configManager != null) {
//            var saves = configManager.getSaves();
//            for (String save :
//                    saves) {
//                var recentSave = new JMenuItem(save);
//                recentSave.addActionListener(this);
//                openRecent.add(recentSave);
//            }
//        } else openRecent.add("Kan recente bestanden niet openen");
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == close) mainScreen.closeApplication();
//        else if (e.getSource() == newFile) mainScreen.newCross();
//        else if (e.getSource() == openFile) mainScreen.openCross();
//        else if (e.getSource() == saveFile) mainScreen.saveCross();
//        for (var recentSaveComponent :
//                openRecent.getMenuComponents()) {
//            var recentSaveJMenuItem = (JMenuItem) recentSaveComponent;
//            if (e.getSource().equals(recentSaveJMenuItem)) mainScreen.openRecentCross(recentSaveJMenuItem.getText());
//        }
//    }
//}