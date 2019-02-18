//import javax.swing.*;
//import java.awt.*;
//
//class RacerList extends JPanel {
//    private MainScreen mainScreen;
//
//    RacerList(MainScreen mainScreen) {
//        this.mainScreen = mainScreen;
//        setLayout(new GridLayout(0,1));
//        createList();
//    }
//
//    void createList() {
//        for (RacingCategory racingCategory :
//                mainScreen.getSave().getRacingCategories()) {
//            add(new JLabel(racingCategory.toString() + ":"));
//            mainScreen
//                    .getSave()
//                    .getRacers()
//                    .stream()
//                    .filter(racer -> racer.getRacingCategory().equals(racingCategory))
//                    .forEach(racer -> add(new JLabel(racer.toString())));
//            add(new JLabel());
//        }
//    }
//
//    void recreateList() {
//        removeAll();
//        createList();
//    }
//}