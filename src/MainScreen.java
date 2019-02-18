//import javax.swing.*;
//import javax.swing.filechooser.FileFilter;
//import javax.xml.parsers.ParserConfigurationException;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashSet;
//
//class MainScreen extends JFrame implements ActionListener, KeyListener {
//    private static final int outerBordersMargin = 5;
//    private static final int innerBordersMargin = 2;
//    static final Insets UPPER_LEFT =
//            new Insets(outerBordersMargin,outerBordersMargin,innerBordersMargin,innerBordersMargin);
//    static final Insets UPPER_RIGHT =
//            new Insets(outerBordersMargin,innerBordersMargin,innerBordersMargin,outerBordersMargin);
//    static final Insets CENTER_LEFT =
//            new Insets(innerBordersMargin,outerBordersMargin,innerBordersMargin,innerBordersMargin);
//    static final Insets CENTER_RIGHT =
//            new Insets(innerBordersMargin,innerBordersMargin,innerBordersMargin,outerBordersMargin);
//    static final Insets LOWER_LEFT =
//            new Insets(innerBordersMargin,outerBordersMargin,outerBordersMargin,innerBordersMargin);
//    static final Insets LOWER_RIGHT =
//            new Insets(innerBordersMargin,innerBordersMargin,outerBordersMargin,outerBordersMargin);
//    private boolean shortcutMustBeActivated;
//    private ConfigManager configManager;
//    private HashSet<Integer> keysPressed;
//    private JLabel fileLabel;
//    private JFileChooser sgotFileChooser;
//    private File saveFile;
//    private SaveManager saveManager;
//    private Save save;
//    private AddRacerDialog addRacerDialog;
//    private RacerList racerList;
//    private JButton addRacerButton;
//
//    MainScreen(ConfigManager configManager) throws HeadlessException, ParserConfigurationException {
//        shortcutMustBeActivated = true;
//        this.configManager = configManager;
//        save = new Save();
//        addRacerDialog = new AddRacerDialog(this);
//        saveManager = new SaveManager(this);
//        sgotFileChooser = makeSGOTFileChooser();
//        keysPressed = new HashSet<>();
//        setFocusable(true);
//        addKeyListener(this);
//        setTitle("StartGrid-O-Tron");
//        setLayout(new GridBagLayout());
//        var gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
//
//        var topBar = new TopBar(this);
//        setJMenuBar(topBar);
//
//        gridBagConstraints.insets = UPPER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 0;
//        add(new JLabel("Huidig crossbestand: "), gridBagConstraints);
//
//        gridBagConstraints.insets = UPPER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        fileLabel = new JLabel("<geen bestand gekozen>");
//        add(fileLabel, gridBagConstraints);
//
//        gridBagConstraints.insets = CENTER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//        add(new JLabel("Coureurs:"), gridBagConstraints);
//
//        gridBagConstraints.insets = CENTER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        racerList = new RacerList(this);
//        add(racerList, gridBagConstraints);
//
//        gridBagConstraints.insets = LOWER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 2;
//        addRacerButton = new JButton("Coureur toevoegen");
//        addRacerButton.addActionListener(this);
//        add(addRacerButton, gridBagConstraints);
//
//        gridBagConstraints.insets = LOWER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.weightx = 1;
//        gridBagConstraints.weighty = 1;
//        add(new JLabel(), gridBagConstraints);
//    }
//
//    ConfigManager getConfigManager() {
//        return configManager;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == addRacerDialog.getConfirm()) addRacer();
//        else if (e.getSource() == addRacerButton) openAddRacerDialog();
//    }
//
//    private void openAddRacerDialog() {
//        addRacerDialog.setVisible(true);
//    }
//
//    private void addRacer() {
//        var firstName = addRacerDialog.getFirstName().getText();
//        var lastName = addRacerDialog.getLastName().getText();
//        var racingClass = addRacerDialog.getRacingCategoryJList().getSelectedValue();
//        if (addRacerDialog.inputValuesAreOK(firstName, lastName, racingClass)) {
//            addRacerDialog.setVisible(false);
//            var startingNumber = (int) addRacerDialog.getStartNumber().getValue();
//            var racer = new Racer(startingNumber, firstName, lastName, racingClass);
//            save.getRacers().add(racer);
//            racerList.recreateList();
//            revalidate();
//            repaint();
//        }
//    }
//
//    private JFileChooser makeSGOTFileChooser() {
//        var configChooser = new JFileChooser();
//        var sgotFilter = new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                if (pathname.isDirectory()) return true;
//                var fileName = pathname.getName();
//                var extensionStart = fileName.lastIndexOf('.');
//                return extensionStart > 0 && fileName.substring(extensionStart).equals(".sgot");
//            }
//
//            @Override
//            public String getDescription() {
//                    return "StartGrid-O-Tron-bestanden (.SGOT)";
//                }
//        };
//        configChooser.addChoosableFileFilter(sgotFilter);
//        configChooser.setFileFilter(sgotFilter);
//        return configChooser;
//    }
//
//    private void makeNewSaveFile() {
//        var result = sgotFileChooser.showSaveDialog(this);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            var selectedFile = sgotFileChooser.getSelectedFile();
//            var selectedFileExtension = getExtension(selectedFile);
//            if (!selectedFileExtension.equals(".sgot")) {
//                selectedFile = new File(selectedFile.getAbsolutePath() + ".sgot");
//            }
//            System.out.println(sgotFileChooser.getSelectedFile().getName());
//            var fileIsSuccessfullyCreated = false;
//            try {
//                fileIsSuccessfullyCreated = selectedFile.createNewFile();
//            } catch (IOException ignored) {
//            } finally {
//                if (!fileIsSuccessfullyCreated)
//                    JOptionPane.showMessageDialog(
//                        this,
//                        "Er is een fout opgetreden bij het opslaan van het bestand"
//                );
//            }
//        }
//    }
//
//    private String getExtension(File selectedFile) {
//        String extension = "";
//        var selectedFileName = selectedFile.getName();
//        var i = selectedFileName.lastIndexOf('.');
//        if (i > 0) extension = selectedFileName.substring(i+1);
//        return extension;
//    }
//
//    private void setSaveFileLabel(File saveFile) {
//        fileLabel.setText(saveFile.getName());
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        System.out.println(e.getKeyCode());
//        keysPressed.add(e.getKeyCode());
//        checkKeys();
//    }
//
//    private void checkKeys() {
//        if (shortcutMustBeActivated) {
//            if (keysPressed.contains(KeyEvent.VK_CONTROL)) {
//                if (keysPressed.contains(KeyEvent.VK_Q)) {
//                    shortcutMustBeActivated = false;
//                    closeApplication();
//                } else if (keysPressed.contains(KeyEvent.VK_N)) {
//                    shortcutMustBeActivated = false;
//                    newCross();
//                } else if (keysPressed.contains(KeyEvent.VK_O)) {
//                    shortcutMustBeActivated = false;
//                    openCross();
//                } else if (keysPressed.contains(KeyEvent.VK_S)) {
//                    shortcutMustBeActivated = false;
//                    saveCross();
//                }
//            }
//        }
//    }
//
//    void openCross() {
//        if (
//                JOptionPane.showConfirmDialog(
//                        this,
//                        "Wilt u echt een andere cross openen en daarmee de wijzigingen in deze cross verwerpen?"
//                ) ==
//                        JOptionPane.YES_OPTION
//                ) {
//            if (sgotFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//                saveFile = sgotFileChooser.getSelectedFile();
//                save = saveManager.retrieveSaveFromFile(saveFile);
//                loadSaveInApplication();
//            }
//        }
//    }
//
//    void newCross() {
//        if (
//                JOptionPane.showConfirmDialog(this, "Wilt u echt een nieuwe cross aanmaken?") ==
//                        JOptionPane.YES_OPTION
//                ) {
//            save = new Save();
//        }
//    }
//
//    void closeApplication() {
//        System.exit(0);
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        shortcutMustBeActivated = true;
//    }
//
//    void openRecentCross(String recentCrossPath) {
//        save = saveManager.retrieveSaveFromFile(new File(recentCrossPath));
//        loadSaveInApplication();
//    }
//
//    private void loadSaveInApplication() {
//        configManager.addSave(save);
//        setSaveFileLabel(save.getLocation());
//        racerList.recreateList();
//        addRacerDialog.regenrateCategoryList();
//        revalidate();
//        repaint();
//        pack();
//    }
//
//    Save getSave() {
//        return save;
//    }
//
//    void saveCross() {
//
//    }
//}