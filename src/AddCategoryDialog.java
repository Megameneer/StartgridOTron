import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

class AddCategoryDialog extends Dialog<RacingCategory> {
    private TextField categoryNameTextField;

    AddCategoryDialog() {
        setTitle("Klasse toevoegen");
        setHeaderText("Voeg een klasse toe");
        GridPane gridPane = new GridPane();
        categoryNameTextField = new TextField();
        categoryNameTextField.setPromptText("Naam");
        Platform.runLater(() -> categoryNameTextField.requestFocus());
        gridPane.addRow(0, new Label("Naam:"), categoryNameTextField);
        var cancel = new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().setContent(gridPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, cancel);
        setResultConverter(param -> param.equals(ButtonType.OK) ?
                new RacingCategory(categoryNameTextField.getText()) :
                null);
    }
}