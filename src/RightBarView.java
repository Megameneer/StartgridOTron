import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

class RightBarView extends ScrollPane {
    private final MenuItem deleteCategoryItem;
    private final ContextMenu mancheListViewMenu;
    private final MenuItem categoryUpItem;
    private final MenuItem categoryDownItem;
    private final MenuItem copyCategoryItem;
    private final MenuItem deleteMancheItem;
    private final MenuItem copyMancheItem;
    private ArrayList<RacingCategory> clickedManche;
    private RacingCategory clickedHeat;
    private ContextMenu mancheLabelMenu;

    private ArrayList<Control> elementsThatListenToDeleteAndCopyKey;
    private ArrayList<ListView<RacingCategory>> elementsThatListenToAllKeys;

    RightBarView() {
        mancheListViewMenu = new ContextMenu();
        deleteCategoryItem = new MenuItem("Verwijder klasse (Delete)");
        deleteMancheItem = new MenuItem("Verwijder manche");
        categoryUpItem = new MenuItem("Klasse omhoog verplaatsen (↑)");
        categoryDownItem = new MenuItem("Klasse omlaag verplaatsen (↓)");
        copyCategoryItem = new MenuItem("Klasse kopiëren (Ctrl + C)");
        copyMancheItem = new MenuItem("Manche kopiëren");
        mancheListViewMenu.getItems().addAll(
                deleteCategoryItem,
                deleteMancheItem,
                categoryUpItem,
                categoryDownItem,
                copyCategoryItem,
                copyMancheItem
        );
        mancheLabelMenu = new ContextMenu();
        mancheLabelMenu.getItems().addAll(deleteMancheItem, copyMancheItem);
    }

    void showManches(ArrayList<ArrayList<RacingCategory>> manches) {
        elementsThatListenToDeleteAndCopyKey = new ArrayList<>();
        elementsThatListenToAllKeys = new ArrayList<>();
        var content = new VBox();
        for (var i = 0; i < manches.size(); i++) {
            var currentManche = manches.get(i);
            var mancheLabel = new Label("Manche " + (i + 1));
            mancheLabel.setOnMouseClicked(event -> showMancheLabelMenu(event, currentManche, mancheLabel));
            elementsThatListenToDeleteAndCopyKey.add(mancheLabel);
            if (currentManche.size() == 0) {
                var placeholderLabel = new Label("<er is nog geen klasse aangemaakt>");
                placeholderLabel.setOnMouseClicked(
                        event -> showMancheLabelMenu(event, currentManche, placeholderLabel)
                );
                elementsThatListenToDeleteAndCopyKey.add(placeholderLabel);
            } else {
                var mancheListView = new ListView<>(FXCollections.observableArrayList(manches.get(i)));
                mancheListView.setOnMouseClicked(event -> {
                    clickedHeat = mancheListView.getSelectionModel().getSelectedItem();
                    if (event.getButton() == MouseButton.SECONDARY) {
                        clickedManche = currentManche;
                        mancheListViewMenu.show(mancheListView, event.getScreenX(), event.getScreenY());
                    }
                });
                elementsThatListenToDeleteAndCopyKey.add(mancheListView);
                elementsThatListenToAllKeys.add(mancheListView);
            }
        }
        content.getChildren().addAll(elementsThatListenToDeleteAndCopyKey);
        setContent(content);
    }

    private void showMancheLabelMenu(MouseEvent event, ArrayList<RacingCategory> currentManche, Label mancheLabel) {
        if (event.getButton() == MouseButton.SECONDARY) {
            clickedManche = currentManche;
            mancheLabelMenu.show(mancheLabel, event.getScreenX(), event.getScreenY());
        }
    }

    MenuItem getDeleteCategoryItem() {
        return deleteCategoryItem;
    }

    public MenuItem getCategoryUpItem() {
        return categoryUpItem;
    }

    public MenuItem getCategoryDownItem() {
        return categoryDownItem;
    }

    public MenuItem getCopyCategoryItem() {
        return copyCategoryItem;
    }

    public MenuItem getDeleteMancheItem() {
        return deleteMancheItem;
    }

    public MenuItem getCopyMancheItem() {
        return copyMancheItem;
    }

    public ArrayList<RacingCategory> getClickedManche() {
        return clickedManche;
    }

    public RacingCategory getClickedHeat() {
        return clickedHeat;
    }

    public ArrayList<Control> getElementsThatListenToDeleteAndCopyKey() {
        return elementsThatListenToDeleteAndCopyKey;
    }

    ArrayList<ListView<RacingCategory>> getElementsThatListenToAllKeys() {
        return elementsThatListenToAllKeys;
    }
}