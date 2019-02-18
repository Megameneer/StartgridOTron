//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import javax.swing.*;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.File;
//import java.io.IOException;
//import java.util.LinkedHashSet;
//
//class SaveManager {
//    private MainScreen mainScreen;
//    private DocumentBuilder documentBuilder;
//
//    SaveManager(MainScreen mainScreen) throws ParserConfigurationException {
//        this.mainScreen = mainScreen;
//        var documentBuilderFactory = DocumentBuilderFactory.newInstance();
//        documentBuilder = documentBuilderFactory.newDocumentBuilder();
//    }
//
//    Save retrieveSaveFromFile(File saveFile) {
//        Document saveDocument = null;
//        try {
//            saveDocument = documentBuilder.parse(saveFile);
//            saveDocument.getDocumentElement().normalize();
//        } catch (SAXException | IOException e) {
//            JOptionPane.showMessageDialog(mainScreen, "Cross kon niet worden geopend");
//        }
//        if (saveDocument != null) {
//            var saveNodeList = saveDocument.getDocumentElement();
//            var racingCategoriesNodeList =
//                    saveNodeList.getElementsByTagName("racingCategories").item(0).getChildNodes();
//            var racersNodeList = saveNodeList.getElementsByTagName("racers").item(0).getChildNodes();
//            var racingCategories = getRacingCategoriesFromSave(racingCategoriesNodeList);
//            var racers = getRacersFromSave(racersNodeList, racingCategories);
//            return new Save(saveFile, racingCategories, racers);
//        } else {
//            return null;
//        }
//    }
//
//    private LinkedHashSet<RacingCategory> getRacingCategoriesFromSave(NodeList racingCategoriesNodeList) {
//        var racingCategoriesLinkedHashSet = new LinkedHashSet<RacingCategory>();
//        for (var i = 0; i < racingCategoriesNodeList.getLength(); i++) {
//            var racingCategoryNode = racingCategoriesNodeList.item(i);
//            racingCategoriesLinkedHashSet.add(new RacingCategory(racingCategoryNode.getTextContent()));
//        }
//        return racingCategoriesLinkedHashSet;
//    }
//
//    private LinkedHashSet<Racer> getRacersFromSave(
//            NodeList racersNodeList,
//            LinkedHashSet<RacingCategory> racingCategories
//    ) {
//        var racersLinkedHashSet = new LinkedHashSet<Racer>();
//        for (var i = 0; i < racersNodeList.getLength(); i++) {
//            var racerNode = racersNodeList.item(i);
//            var racerNodeChildNodes = racerNode.getChildNodes();
//            var startNumber = 0;
//            var firstName = "";
//            var lastName = "";
//            RacingCategory racingCategory = null;
//            for (var j = 0; j < racerNodeChildNodes.getLength(); j++) {
//                var currentRacerNodeChildNode = racerNodeChildNodes.item(j);
//                var currentRacerNodeChildNodeName = currentRacerNodeChildNode.getNodeName();
//                var currentRacerNodeChildTextContent = currentRacerNodeChildNode.getTextContent();
//                switch (currentRacerNodeChildNodeName) {
//                    case "startNumber" :
//                        startNumber = Integer.parseInt(currentRacerNodeChildTextContent);
//                        break;
//                    case "firstName" :
//                        firstName = currentRacerNodeChildTextContent;
//                        break;
//                    case "lastName" :
//                        lastName = currentRacerNodeChildTextContent;
//                        break;
//                    default:
//                        for (var racingCategoryInLinkedHashSet :
//                                racingCategories) {
//                            if (racingCategoryInLinkedHashSet.getName().equals(currentRacerNodeChildTextContent))
//                                racingCategory = racingCategoryInLinkedHashSet;
//                        }
//                        break;
//                }
//            }
//            racersLinkedHashSet.add(new Racer(startNumber, firstName, lastName, racingCategory));
//        }
//        return racersLinkedHashSet;
//    }
//
//    void saveSaveToFile() {
//        var save = mainScreen.getSave();
//        var savePath = save.getLocation();
//        var saveDocument = documentBuilder.newDocument();
//        saveDocument.appendChild(saveDocument.createElement("save"));
//        var root = saveDocument.getDocumentElement();
////        var racingCategoriesElement =
//        for (RacingCategory racingCategory :
//                save.getRacingCategories()) {
//            var racingCategoryElement = saveDocument.createElement("racingCategory");
//            racingCategoryElement.appendChild(saveDocument.createTextNode(racingCategory.toString()));
//        }
//
//    }
//}