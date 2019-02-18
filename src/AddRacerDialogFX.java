import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

class AddRacerDialogFX extends Dialog<Racer> {
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private Spinner<Integer> startNumberSpinner;

    private ListView<RacingCategory> racingCategoryListView;

    AddRacerDialogFX(MainScreenJavaFX mainScreenJavaFX) {
        setTitle("Coureur toevoegen");
        setHeaderText("Voeg een coureur toe");
        GridPane gridPane = new GridPane();
        startNumberSpinner = new Spinner<>(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        startNumberSpinner.setEditable(true);
        startNumberSpinner.setPromptText("Startnummer");
        Platform.runLater(() -> startNumberSpinner.requestFocus());
        gridPane.addRow(0, new Label("Startnummer:"), startNumberSpinner);
        firstNameTextField = new TextField();
        firstNameTextField.setPromptText("Voornaam");
        gridPane.addRow(1, new Label("Voornaam:"), firstNameTextField);
        lastNameTextField = new TextField();
        lastNameTextField.setPromptText("Achternaam");
        gridPane.addRow(2, new Label("Achternaam:"), lastNameTextField);
        var racingCategoriesLinkedHashSet = mainScreenJavaFX.getSave().getRacingCategories();
        racingCategoryListView = new ListView<>(FXCollections.observableArrayList(racingCategoriesLinkedHashSet));
        gridPane.addRow(3, new Label("Klasse:"), racingCategoryListView);
        var cancel = new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().setContent(gridPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, cancel);
        setResultConverter(param -> param.equals(ButtonType.OK) ?
                new Racer(
                        startNumberSpinner.getValue(),
                        firstNameTextField.getText(),
                        lastNameTextField.getText(),
                        racingCategoryListView.getSelectionModel().getSelectedItem()
                ) : null);
    }
}