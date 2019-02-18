import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

class TopBarFX extends MenuBar {
    private ConfigManager configManager;

    private Menu openRecent;

    private Menu categoriesMenu;
    private MenuItem addCategory;

    private Menu generatorMenu;
    private MenuItem generateStartGrid;

    TopBarFX(MainScreenJavaFX mainScreenJavaFX) {
        configManager = mainScreenJavaFX.getConfigManager();
        var fileMenu = new Menu("Bestand");
        var newFile = new MenuItem("Nieuwe cross (Ctrl + N)");
        newFile.setOnAction(event -> mainScreenJavaFX.newCross());
        var openFile = new MenuItem("Cross openen (Ctrl + O)");
        openFile.setOnAction(event -> mainScreenJavaFX.openCross());
        var saveFile = new MenuItem("Cross opslaan (Ctrl + S)");
        saveFile.setOnAction(event -> mainScreenJavaFX.saveCross());
        var separator = new SeparatorMenuItem();
        openRecent = new Menu("Recente crosses");
        var noRecentSavesFound = new MenuItem("<geen recente crosses gevonden>");
        openRecent.getItems().add(noRecentSavesFound);
        openRecent.setOnShowing(event -> {
            var menuItems = openRecent.getItems();
            menuItems.removeAll(menuItems);
            if (configManager != null) {
                var saves = configManager.getSaves();
                for (String save :
                        saves) {
                    var recentSave = new MenuItem(save);
                    recentSave.setOnAction(recentSaveEvent -> mainScreenJavaFX.openRecentCross(save));
                    openRecent.getItems().add(recentSave);
                }
            } else openRecent.getItems().add(new MenuItem("<kan recente crosses niet vinden>"));
        });
        var close = new MenuItem("Sluiten (Ctrl + Q)");
        close.setOnAction(event -> mainScreenJavaFX.close());
        fileMenu.getItems().addAll(newFile, openFile, saveFile, separator, openRecent, close);

        var manchesMenu = new Menu("Manches");
        var setAmountOfManches = new MenuItem("Aantal manches instellen");
        setAmountOfManches.setOnAction(event -> mainScreenJavaFX.openAmountOfManchesDialog());
        manchesMenu.getItems().add(setAmountOfManches);

        getMenus().addAll(fileMenu, manchesMenu);
    }
}