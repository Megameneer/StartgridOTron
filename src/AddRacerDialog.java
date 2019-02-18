//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.util.LinkedHashSet;
//
//class AddRacerDialog extends JDialog implements ActionListener {
//    private final MainScreen mainScreen;
//    private JSpinner startNumber;
//    private JTextField firstName;
//    private JTextField lastName;
//    private JButton confirm;
//    private JButton escape;
//    private JList<RacingCategory> racingCategoryJList;
//    private GridBagConstraints gridBagConstraints;
//
//    AddRacerDialog(MainScreen mainScreen) {
//        super(mainScreen);
//        this.mainScreen = mainScreen;
//        setTitle("Voeg coureur toe");
//        setLayout(new GridBagLayout());
//        gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
//        gridBagConstraints.anchor = GridBagConstraints.WEST;
//
//        gridBagConstraints.insets = MainScreen.UPPER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 0;
//        add(new JLabel("Startnummer:"), gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.UPPER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        startNumber = new JSpinner();
//        JComponent editor = startNumber.getEditor();
//        JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor) editor).getTextField();
//        jFormattedTextField.setColumns(4);
//        add(startNumber, gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.CENTER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.ipady = 0;
//        add(new JLabel("Voornaam:"), gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.CENTER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        firstName = new JTextField(10);
//        this.add(firstName, gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.CENTER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 2;
//        add(new JLabel("Achternaam:"), gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.CENTER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        lastName = new JTextField(10);
//        add(lastName, gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.CENTER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 4;
//        add(new JLabel("Klasse:"), gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.CENTER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        addPlaceholderJListComponent();
//        add(racingCategoryJList, gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.LOWER_LEFT;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 5;
//        confirm = new JButton("Ok");
//        confirm.addActionListener(mainScreen);
//        add(confirm, gridBagConstraints);
//
//        gridBagConstraints.insets = MainScreen.LOWER_RIGHT;
//        gridBagConstraints.gridx = 1;
//        escape = new JButton("Annuleren");
//        escape.addActionListener(this);
//        add(escape, gridBagConstraints);
//
//        addEscapeListener(this);
//        pack();
//    }
//
//    JSpinner getStartNumber() {
//        return startNumber;
//    }
//
//    JTextField getFirstName() {
//        return firstName;
//    }
//
//    JTextField getLastName() {
//        return lastName;
//    }
//
//    JList<RacingCategory> getRacingCategoryJList() {
//        return racingCategoryJList;
//    }
//
//    JButton getConfirm() {
//        return confirm;
//    }
//
//    private void addEscapeListener(JDialog dialog) {
//        ActionListener escListener = e -> dialog.dispose();
//        dialog.getRootPane().registerKeyboardAction(escListener,
//                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
//                JComponent.WHEN_IN_FOCUSED_WINDOW);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == escape) dispose();
//    }
//
//    boolean inputValuesAreOK(String firstName, String lastName, RacingCategory racingCategory) {
//        if (firstName.equals("")) {
//            JOptionPane.showMessageDialog(this, "U heeft geen voornaam ingevuld");
//            return false;
//        } else if (lastName.equals("")) {
//            JOptionPane.showMessageDialog(this, "U heeft geen achternaam ingevuld");
//            return false;
//        } else if (racingCategory == null) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "U heeft nog geen klasse geselecteerd voor deze coureur"
//            );
//            return false;
//        }
//        return true;
//    }
//
//    void regenrateCategoryList() {
//        var racingCategoriesLinkedHashSet = mainScreen.getSave().getRacingCategories();
//        var amountOfCategories = racingCategoriesLinkedHashSet.size();
//        if (amountOfCategories == 0) addPlaceholderJListComponent();
//        else {
//            RacingCategory[] racingCategoriesArray = new RacingCategory[amountOfCategories];
//            for (var i = 0; i < amountOfCategories; i++)
//                racingCategoriesArray[i] = racingCategoriesLinkedHashSet.stream().skip(i).findFirst().get();
//            remove(racingCategoryJList);
//            racingCategoryJList = new JList<>(racingCategoriesArray);
//        }
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 4;
//        add(racingCategoryJList, gridBagConstraints);
//        pack();
//    }
//
//    private void addPlaceholderJListComponent() {
//        RacingCategory[] placeholderJListItemArray = {new RacingCategory("<er is nog geen klasse gemaakt>")};
//        racingCategoryJList = new JList<>(placeholderJListItemArray);
//    }
//}