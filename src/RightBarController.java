import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

class RightBarController {
    private Save save;
    private RightBarModel rightBarModel;
    private RightBarView rightBarView;

    RightBarController(RightBarModel rightBarModel, RightBarView rightBarView) {
        this.rightBarModel = rightBarModel;
        this.rightBarView = rightBarView;
        rightBarView.showManches(rightBarModel.getManches());
        var elementsThatListenToAllKeys = rightBarView.getElementsThatListenToAllKeys();
        if (!elementsThatListenToAllKeys.equals(null)) {
            rightBarView.getElementsThatListenToAllKeys().forEach(new Consumer<ListView<RacingCategory>>() {
                @Override
                public void accept(ListView<RacingCategory> racingCategoryListView) {
                    racingCategoryListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode().equals(KeyCode.DELETE))
                                deleteManche(event, racingCategoryListView.getSelectionModel().getSelectedItems());
                        }
                    });
                }
            });
        }

        rightBarModel.setManches(makeDefaultManches());
        updateView();
    }

    private void deleteManche(KeyEvent event, ObservableList<RacingCategory> selectedRacingCategories) {

    }

    void updateView() {
        rightBarView.showManches(rightBarModel.getManches());
    }

    private ArrayList<ArrayList<RacingCategory>> makeDefaultManches(LinkedHashSet<RacingCategory> racingCategories) {
        var manches = new ArrayList<ArrayList<RacingCategory>>();
        for (var i = 0; i < 3; i++) { // 3 manches aanmaken
            manches.add(new ArrayList<>(racingCategories));
        }
        return manches;
    }

    ArrayList<ArrayList<RacingCategory>> makeDefaultManches() {
        return makeDefaultManches(new LinkedHashSet<>());
    }

    public void setSave(Save save) {
        this.save = save;
        rightBarModel.setManches(save.getManches());
        updateView();
    }

    void addRightBarHeat(RacingCategory racingCategory) {
        rightBarModel.getManches().forEach(manche -> manche.add(racingCategory));
        updateView();
    }
}