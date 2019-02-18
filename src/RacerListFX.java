import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

class RacerListFX extends VBox {
    private ContextMenu listViewMenu;
    private Save save;
    private Racer clickedRacer;
    private RacingCategory clickedCategory;
    private ContextMenu placeholderLabelMenu;

    RacerListFX(MainScreenJavaFX mainScreenJavaFX) {
        save = mainScreenJavaFX.getSave();
        listViewMenu = new ContextMenu();
        var deleteDriverMenuItem = new MenuItem("Verwijder coureur (Delete)");
        deleteDriverMenuItem.setOnAction(event -> deleteRacer());
        var renameCategory = new MenuItem("Hernoem klasse");
        renameCategory.setOnAction(event -> this.renameCategory());
        listViewMenu.getItems().addAll(deleteDriverMenuItem, renameCategory);
        placeholderLabelMenu = new ContextMenu();
        placeholderLabelMenu.getItems().addAll(renameCategory);
        createList();
    }

    private void renameCategory() {
        var newCategoryNameDialog = new TextInputDialog();
        newCategoryNameDialog.setTitle("Hernoem klasse");
        newCategoryNameDialog.setHeaderText("Hernoem klasse");
        newCategoryNameDialog.setContentText("Voer de nieuwe klassenaam in");
        var result = newCategoryNameDialog.showAndWait();
        result.ifPresent(s -> {
            if (!s.isEmpty()) {
                clickedCategory.setName(s);
                recreateList();
            }
        });
    }

    private void createList() {
        getChildren().add(new Label("Klassen:"));
        var racingCategories = new ArrayList<>(save.getRacingCategories());
        if (racingCategories.isEmpty()) getChildren().add(new Label("<nog geen klassen aangemaakt>"));
        else {
            for (RacingCategory racingCategory :
                    racingCategories) {
                var racingCategoryLabel = new Label(racingCategory.toString());
                racingCategoryLabel.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        clickedCategory = racingCategory;
                        placeholderLabelMenu.show(racingCategoryLabel, event.getScreenX(), event.getScreenY());
                    }
                });
                getChildren().add(racingCategoryLabel);
                ObservableList<Racer> racersForThisCategory = FXCollections.observableArrayList();
                save
                        .getRacers()
                        .stream()
                        .filter(racer -> racer.getRacingCategory().equals(racingCategory))
                        .forEach(racersForThisCategory::add);
                if (racersForThisCategory.size() == 0) {
                    var racerControl = new Label("<nog geen coureurs voor deze klasse>");
                    racerControl.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            clickedCategory = racingCategory;
                            placeholderLabelMenu.show(racerControl, event.getScreenX(), event.getScreenY());
                        }
                    });
                    getChildren().add(racerControl);
                } else {
                    var racerControl = new ListView<>(racersForThisCategory);
                    racerControl.setOnMouseClicked(event -> {
                        clickedRacer = racerControl.getSelectionModel().getSelectedItem();
                        if (event.getButton() == MouseButton.SECONDARY) {
                            clickedCategory = racingCategory;
                            listViewMenu.show(racerControl, event.getScreenX(), event.getScreenY());
                        }
                    });
                    racerControl.setOnKeyPressed(event -> {
                        if (event.getCode().equals(KeyCode.DELETE)) deleteRacer();
                    });
                    getChildren().add(racerControl);
                }
                if (!racingCategory.equals(racingCategories.get(racingCategories.size() - 1)))
                    getChildren().add(new Label());
            }
        }
    }

    public void setSave(Save save) {
        this.save = save;
        recreateList();
    }

    void recreateList() {
        getChildren().removeAll(getChildren());
        createList();
    }

    private void deleteRacer() {
        save.removeRacer(clickedRacer);
        recreateList();
    }
}