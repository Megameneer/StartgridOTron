import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

public class MainScreenJavaFX extends Application {
    private boolean shortcutMustBeActivated;
    private ConfigManager configManager;
    private Save save;
    private File saveFile;
    private SaveManagerFX saveManagerFX;
    private FileChooser sgotFileChooser;
    private Stage primaryStage;
    private LeftBar leftBar;
    private int amountOfManches;
    private RightBarController rightBarController;
//    private int amountOfHeats;

    @Override
    public void start(Stage primaryStage) {
        shortcutMustBeActivated = true;
        this.primaryStage = primaryStage;
        try {
            configManager = new ConfigManager();
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            configManager = null;
        }
        save = new Save();
        sgotFileChooser = makeSGOTFileChooser();
        try {
            saveManagerFX = new SaveManagerFX(this);
        } catch (ParserConfigurationException e) {
            var newOpenSaveError = new Alert(Alert.AlertType.ERROR);
            newOpenSaveError.setTitle("Fout");
            newOpenSaveError.setHeaderText("Interne fout");
            newOpenSaveError.setContentText("Er kunnen geen bestanden worden aangemaakt, geopend of opgeslagen");
        } catch (TransformerConfigurationException e) {
            var saveError = new Alert(Alert.AlertType.ERROR);
            saveError.setTitle("Fout");
            saveError.setHeaderText("Interne fout");
            saveError.setContentText("Er kunnen geen bestanden worden opgeslagen");
        }
        leftBar = new LeftBar(this);
        RightBarModel rightBarModel = new RightBarModel();
        RightBarView rightBarView = new RightBarView();
        rightBarController = new RightBarController(rightBarModel, rightBarView);
//        rightBarController.setManches()
        var pane = new BorderPane(null, new TopBarFX(this), rightBarView,null,leftBar);
        var scene = new Scene(pane);
        var newFile = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        var openFile = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        var saveFile = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        var close = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        scene.setOnKeyPressed(event -> {
            if (shortcutMustBeActivated) {
                if (newFile.match(event)) {
                    shortcutMustBeActivated = false;
                    newCross();
                }
                else if (openFile.match(event)) {
                    shortcutMustBeActivated = false;
                    openCross();
                }
                else if (saveFile.match(event)) {
                    shortcutMustBeActivated = false;
                    saveCross();
                }
                else if (close.match(event)){
                    shortcutMustBeActivated = false;
                    close();
                }
            }
        });
        scene.setOnKeyReleased(event -> shortcutMustBeActivated = true);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    ConfigManager getConfigManager() {
        return configManager;
    }

    LinkedHashSet<Racer> getRacers() {
        return getSave().getRacers();
    }

    void newCross() {
        Alert confirmNewCross = new Alert(Alert.AlertType.CONFIRMATION);
        confirmNewCross.setTitle("Nieuwe cross maken");
        confirmNewCross.setHeaderText("Wilt u echt een nieuwe cross aanmaken?");
        confirmNewCross.setContentText("De wijzigingen in de huidige cross gaan dan verloren");
        var result = confirmNewCross.showAndWait();
        result.ifPresent(buttonType -> {
            if (result.get() == ButtonType.OK) {
                save = new Save();
                setSaveFileLabel(save.getLocation());
                leftBar.getRacerListFX().recreateList();
                rightBarController.makeDefaultManches();
            }
        });
    }

    private boolean askUserToOpenOtherCross() {
        Alert confirmOpenOtherCross = new Alert(Alert.AlertType.CONFIRMATION);
        confirmOpenOtherCross.setTitle("Andere cross openen?");
        confirmOpenOtherCross.setHeaderText("Wilt u echt een andere cross openen?");
        confirmOpenOtherCross.setContentText("De wijzigingen in de huidige cross gaan dan verloren");
        var result = confirmOpenOtherCross.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    void openCross() {
        if (askUserToOpenOtherCross()) {
            saveFile = sgotFileChooser.showOpenDialog(primaryStage);
            if (saveFile != null) {
                try {
                    save = saveManagerFX.retrieveSaveFromFile(saveFile);
                } catch (SaveFileCorruptedException sfce) {
                    makeBrokenFileAlert();
                }
                loadSaveInApplication();
            }
        }
    }

    private void loadSaveInApplication() {
        configManager.addSave(save);
        setSaveFileLabel(save.getLocation());
        leftBar.getRacerListFX().setSave(save);
        rightBarController.setSave(save);
    }

    private void setSaveFileLabel(File saveFile) {
        var fileLabel = leftBar.getFileLabel();
        if (saveFile == null) fileLabel.setText("<onopgeslagen bestand>");
        else fileLabel.setText(saveFile.getName());
    }

    void saveCross() {
        saveFile = sgotFileChooser.showSaveDialog(primaryStage);
        if (saveFile != null) {
            save.setLocation(saveFile);
            try {
                saveManagerFX.saveSaveToFile();
            } catch (TransformerException e) {
                var saveError = new Alert(Alert.AlertType.ERROR);
                saveError.setTitle("Fout");
                saveError.setHeaderText("Interne fout");
                saveError.setContentText("Fout bij het opslaan van het bestand");
                saveError.showAndWait();
            }
        }
    }

    void openRecentCross(String recentCrossPath) {
        if (askUserToOpenOtherCross()) {
            try {
                save = saveManagerFX.retrieveSaveFromFile(new File(recentCrossPath));
            } catch (SaveFileCorruptedException sfce) {
                makeBrokenFileAlert();
            }
            loadSaveInApplication();
        }
    }

    private void makeBrokenFileAlert() {
        var fileBrokenError = new Alert(Alert.AlertType.ERROR);
        fileBrokenError.setTitle("Fout");
        fileBrokenError.setHeaderText("Bestand beschadigd");
        fileBrokenError.setContentText("Kan bestand niet openen");
        fileBrokenError.showAndWait();
    }

    void close() {
        var confirmClose = new Alert(Alert.AlertType.CONFIRMATION);
        confirmClose.setTitle("Sluiten?");
        confirmClose.setHeaderText("Wilt u echt het programma sluiten?");
        confirmClose.setContentText("De wijzigingen in deze cross gaan dan verloren");
        confirmClose.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.OK)) System.exit(0);
        });
    }

    Save getSave() {
        return save;
    }

    private FileChooser makeSGOTFileChooser() {
        var sgotChooser = new FileChooser();
        sgotChooser.setTitle("Kies een cross");
        sgotChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("StartGrid-O-Tron-bestanden (.SGOT)", "*.sgot"),
                new FileChooser.ExtensionFilter("Alle bestanden", "*.*")
        );
        return sgotChooser;
    }

    void openAddRacerDialog() {
        if (save.getRacingCategories().size() == 0) {
            var noCategoriesMade = new Alert(Alert.AlertType.ERROR);
            noCategoriesMade.setTitle("Fout");
            noCategoriesMade.setHeaderText("Er is nog geen raceklasse aangemaakt");
            noCategoriesMade.setContentText("U moet eerst een raceklasse aanmaken voordat u coureurs kunt aanmaken");
            noCategoriesMade.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            noCategoriesMade.showAndWait();
        }
        else {
            var addRacerDialogFX = new AddRacerDialogFX(this);
            var result = addRacerDialogFX.showAndWait();
            result.ifPresent(this::tryToAddRacer);
        }
    }

    private void tryToAddRacer(Racer racer) {
        if (racer.getFirstName().isEmpty()) {
            showNoTextEnteredDialog("voornaam");
            openAddRacerDialog();
        } else if (racer.getLastName().isEmpty()) {
            showNoTextEnteredDialog("achternaam");
            openAddRacerDialog();
        } else if (racer.getRacingCategory() == null) {
            showNoTextEnteredDialog("raceklasse");
            openAddRacerDialog();
        } else {
            var racers = save.getRacers();
            if (racers.stream().anyMatch(racerFromLambda -> racerFromLambda.equals(racer))) {
                showDuplicateItemDialog("coureur");
                openAddRacerDialog();
            } else {
                save.getRacers().add(racer);
                leftBar.getRacerListFX().recreateList();
            }
        }
    }

    void openAmountOfManchesDialog() {
        var amountOfManchesDialog = new TextInputDialog();
        amountOfManchesDialog.setTitle("Aantal manches");
        amountOfManchesDialog.setHeaderText("Voer het aantal manches in");
        amountOfManchesDialog.setContentText("Aantal manches:");
        var result = amountOfManchesDialog.showAndWait();
        result.ifPresent(amountOfManches -> leftBar.setAmountOfManches(Integer.parseInt(amountOfManches)));
    }

    private void showDuplicateItemDialog(String itemThatAlreadyExists) {
        var couldNotAddError = new Alert(Alert.AlertType.ERROR);
        couldNotAddError.setTitle("Fout");
        couldNotAddError.setHeaderText("Kon de nieuwe " + itemThatAlreadyExists + " niet toevoegen");
        couldNotAddError.setContentText("De " + itemThatAlreadyExists + " bestond al");
        couldNotAddError.showAndWait();
    }

    private void tryToAddRacingCategory(RacingCategory racingCategory) {
        if (racingCategory.toString().isEmpty()) {
            showNoTextEnteredDialog("klasse");
            openAddRacingCategoryDialog();
        } else {
            var racingCategories = save.getRacingCategories();
            if (racingCategories.contains(racingCategory)) {
                showDuplicateItemDialog("klasse");
                openAddRacingCategoryDialog();
            } else {
                racingCategories.add(racingCategory);
                leftBar.getRacerListFX().recreateList();
                rightBarController.addRightBarHeat(racingCategory);
            }
        }
    }

    void openAddRacingCategoryDialog() {
        var addCategoryDialog = new AddCategoryDialog();
        var result = addCategoryDialog.showAndWait();
        result.ifPresent(this::tryToAddRacingCategory);
    }

    private void showNoTextEnteredDialog(String errorneousFactor) {
        var inputValuesErrorAlert = new Alert(Alert.AlertType.ERROR);
        inputValuesErrorAlert.setTitle("Invoerfout");
        inputValuesErrorAlert.setHeaderText("U heeft geen " + errorneousFactor + " ingevuld");
        inputValuesErrorAlert.setContentText(
                "Voer een " + errorneousFactor + " in om de coureur in te kunnen voeren"
        );
        inputValuesErrorAlert.show();
    }
}