import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

class ChangeAmountOfHeatsPerMancheDialog extends Dialog<Integer> {
    private final Spinner<Integer> amountOfHeatsPerMancheSpinner;

    ChangeAmountOfHeatsPerMancheDialog() {
        setTitle("Wijzig aantal heats per manche");
        setHeaderText("Wijzig aantal heats per manche");
        setContentText("Voer het nieuwe aantal heats per manches in");
        GridPane gridPane = new GridPane();
        amountOfHeatsPerMancheSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        amountOfHeatsPerMancheSpinner.setEditable(true);
        amountOfHeatsPerMancheSpinner.setPromptText("Aantal heats per manche");
        gridPane.addRow(0, new Label("Aantal heats per manche:"), amountOfHeatsPerMancheSpinner);
        var cancel = new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().setContent(gridPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, cancel);
        setResultConverter(param -> param.equals(ButtonType.OK) ? amountOfHeatsPerMancheSpinner.getValue() : null);
    }
}