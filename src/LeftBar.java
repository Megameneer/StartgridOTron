import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

class LeftBar extends ScrollPane {
    private Label amountOfManches;
//    private Label amountOfHeats;
    private Label fileLabel;
    private RacerListFX racerListFX;

    LeftBar(MainScreenJavaFX mainScreenJavaFX) {
        fileLabel = new Label("<geen bestand gekozen>");
        amountOfManches = new Label("0");
//        amountOfHeats = new Label("0");
        racerListFX = new RacerListFX(mainScreenJavaFX);
        var addRacerButton = new Button("Coureur toevoegen");
        addRacerButton.setOnAction(event -> mainScreenJavaFX.openAddRacerDialog());
        var addCategoryButton = new Button("Klasse toevoegen");
        addCategoryButton.setOnAction(event -> mainScreenJavaFX.openAddRacingCategoryDialog());
//        var removeDriverButton = new Button("Coureur verwijderen");
//        removeDriverButton.setVisible(false);
//        removeDriverButton.setOnAction(event -> mainScreenJavaFX.removeDriver(racerListFX.getChildren()));
        var content = new VBox(
                new Label("Gekozen bestand:"),
                fileLabel,
                new Label("Aantal manches"),
                amountOfManches,
//                new Label("Aantal heats"),
//                amountOfHeats,
                racerListFX,
                addRacerButton,
                addCategoryButton
        );
        setContent(content);
        setFitToWidth(true);
    }

    Label getFileLabel() {
        return fileLabel;
    }

    RacerListFX getRacerListFX() {
        return racerListFX;
    }

    void setAmountOfManches(int amountOfManches) {
        this.amountOfManches.setText(String.valueOf(amountOfManches));
    }

//    void setAmountOfHeats(int amountOfHeats) {
//        this.amountOfHeats.setText(String.valueOf(amountOfHeats));
//    }

//    void showDeleteButton() {
//
//    }
//
//    void hideDeleteButton() {
//
//    }
}