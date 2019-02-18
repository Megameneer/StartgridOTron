import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

class SaveManagerFX {
    private Transformer transformer;
    private MainScreenJavaFX mainScreenJavaFX;
    private DocumentBuilder documentBuilder;

    SaveManagerFX(MainScreenJavaFX mainScreenJavaFX) throws
            ParserConfigurationException,
            TransformerConfigurationException
    {
        this.mainScreenJavaFX = mainScreenJavaFX;
        var documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        var transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
    }

    Save retrieveSaveFromFile(File saveFile) throws SaveFileCorruptedException {
        Document saveDocument = null;
        try {
            saveDocument = documentBuilder.parse(saveFile);
            saveDocument.getDocumentElement().normalize();
        } catch (SAXException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cross kon niet worden geopend");
            alert.showAndWait();
        }
        if (saveDocument != null) {
            var saveNodeList = saveDocument.getDocumentElement();
            var racingCategoriesNodeList =
                    saveNodeList.getElementsByTagName("racingCategories").item(0).getChildNodes();
            var racersNodeList = saveNodeList.getElementsByTagName("racers").item(0).getChildNodes();
            var manchesNodeList = saveNodeList.getElementsByTagName("manches").item(0).getChildNodes();
            var racingCategories = getRacingCategoriesFromSave(racingCategoriesNodeList);
            var racers = getRacersFromSave(racersNodeList, racingCategories);
            var manches = getManchesFromSave(manchesNodeList, racingCategories);
            return new Save(saveFile, racingCategories, racers, manches);
        } else return null;
    }

    private ArrayList<ArrayList<RacingCategory>> getManchesFromSave(
            NodeList manchesNodeList, LinkedHashSet<RacingCategory> racingCategories
    ) throws SaveFileCorruptedException {
        var manchesArrayListArrayList = new ArrayList<ArrayList<RacingCategory>>();
        manchesArrayListArrayList.add(new ArrayList<>());
        for (var i = 0; i < manchesNodeList.getLength(); i++) {
            var mancheNode = manchesNodeList.item(i);
            var mancheNodeChildNodes = mancheNode.getChildNodes();
            RacingCategory racingCategory = null;
            for (var j = 0; j < mancheNodeChildNodes.getLength(); j++) {
                var currentMancheNodeChildNode = mancheNodeChildNodes.item(j);
                var currentMancheNodeChildNodeName = currentMancheNodeChildNode.getNodeName();
                var currentMancheNodeChildNodeTextContext = currentMancheNodeChildNode.getTextContent();
                switch (currentMancheNodeChildNodeName) {
                    case "heat" :
                        if (
                                racingCategories
                                        .stream()
                                        .noneMatch(
                                                racingCategoryAsPredicate ->
                                                        racingCategoryAsPredicate
                                                                .getName()
                                                                .equals(currentMancheNodeChildNodeTextContext)
                                        )
                                )
                            throw new SaveFileCorruptedException();
                        for (var racingCategoryInLinkedHashSet :
                                racingCategories) {
                            if (racingCategoryInLinkedHashSet.getName().equals(currentMancheNodeChildNodeTextContext)) {
                                racingCategory = racingCategoryInLinkedHashSet;
                                manchesArrayListArrayList.get(i).add(racingCategory);
                            }
                        }
                        break;
                    default: throw new SaveFileCorruptedException();
                }
            }
        }
        return manchesArrayListArrayList;
    }

    private LinkedHashSet<RacingCategory> getRacingCategoriesFromSave(NodeList racingCategoriesNodeList)
            throws SaveFileCorruptedException {
        var racingCategoriesLinkedHashSet = new LinkedHashSet<RacingCategory>();
        for (var i = 0; i < racingCategoriesNodeList.getLength(); i++) {
            var racingCategoryNode = racingCategoriesNodeList.item(i);
            var racingCategoryChildNodes = racingCategoryNode.getChildNodes();
            var name = "";
            for (var j = 0; j < racingCategoryChildNodes.getLength(); j++) {
                var currentRacingCategoryNodeChildNode = racingCategoryChildNodes.item(j);
                var currentRacingCategoryNodeChildNodeName = currentRacingCategoryNodeChildNode.getNodeName();
                var currentRacerNodeChildTextContext = currentRacingCategoryNodeChildNode.getTextContent();
                switch (currentRacingCategoryNodeChildNodeName) {
                    case "name" :
                        name = currentRacerNodeChildTextContext;
                        break;
                    default: throw new SaveFileCorruptedException();
                }
            }
            racingCategoriesLinkedHashSet.add(new RacingCategory(name));
        }
        return racingCategoriesLinkedHashSet;
    }

    private LinkedHashSet<Racer> getRacersFromSave(
            NodeList racersNodeList,
            LinkedHashSet<RacingCategory> racingCategories
    ) throws SaveFileCorruptedException {
        var racersLinkedHashSet = new LinkedHashSet<Racer>();
        for (var i = 0; i < racersNodeList.getLength(); i++) {
            var racerNode = racersNodeList.item(i);
            var racerNodeChildNodes = racerNode.getChildNodes();
            var startNumber = 0;
            var firstName = "";
            var lastName = "";
            RacingCategory racingCategory = null;
            for (var j = 0; j < racerNodeChildNodes.getLength(); j++) {
                var currentRacerNodeChildNode = racerNodeChildNodes.item(j);
                var currentRacerNodeChildNodeName = currentRacerNodeChildNode.getNodeName();
                var currentRacerNodeChildTextContent = currentRacerNodeChildNode.getTextContent();
                switch (currentRacerNodeChildNodeName) {
                    case "startNumber" :
                        startNumber = Integer.parseInt(currentRacerNodeChildTextContent);
                        break;
                    case "firstName" :
                        firstName = currentRacerNodeChildTextContent;
                        break;
                    case "lastName" :
                        lastName = currentRacerNodeChildTextContent;
                        break;
                    case "racingCategory" :
                        if (
                                racingCategories
                                        .stream()
                                        .noneMatch(
                                                racingCategoryAsPredicate ->
                                                        racingCategoryAsPredicate
                                                                .getName()
                                                                .equals(currentRacerNodeChildTextContent)
                                        )
                                )
                            throw new SaveFileCorruptedException();
                        for (var racingCategoryInLinkedHashSet :
                                racingCategories) {
                            if (racingCategoryInLinkedHashSet.getName().equals(currentRacerNodeChildTextContent))
                                racingCategory = racingCategoryInLinkedHashSet;
                        }
                        break;
                    default: throw new SaveFileCorruptedException();
                }
            }
            racersLinkedHashSet.add(new Racer(startNumber, firstName, lastName, racingCategory));
        }
        return racersLinkedHashSet;
    }

    void saveSaveToFile() throws TransformerException {
        var save = mainScreenJavaFX.getSave();
        var savePath = save.getLocation();
        var saveDocument = documentBuilder.newDocument();
        saveDocument.appendChild(saveDocument.createElement("save"));
        var root = saveDocument.getDocumentElement();
        var racingCategoriesElement = saveDocument.createElement("racingCategories");
        root.appendChild(racingCategoriesElement);
        for (RacingCategory racingCategory :
                save.getRacingCategories()) {
            var racingCategoryElement = saveDocument.createElement("racingCategory");
            var nameElement = saveDocument.createElement("name");
            nameElement.appendChild(saveDocument.createTextNode(racingCategory.getName()));
            racingCategoryElement.appendChild(nameElement);
            racingCategoriesElement.appendChild(racingCategoryElement);
        }
        var racersElement = saveDocument.createElement("racers");
        root.appendChild(racersElement);
        for (Racer racer :
                save.getRacers()) {
            var racerElement = saveDocument.createElement("racer");
            var startNumberElement = saveDocument.createElement("startNumber");
            startNumberElement.appendChild(saveDocument.createTextNode(Integer.toString(racer.getStartNumber())));
            racerElement.appendChild(startNumberElement);
            var firstNameElement = saveDocument.createElement("firstName");
            firstNameElement.appendChild(saveDocument.createTextNode(racer.getFirstName()));
            racerElement.appendChild(firstNameElement);
            var lastNameElement = saveDocument.createElement("lastName");
            lastNameElement.appendChild(saveDocument.createTextNode(racer.getLastName()));
            racerElement.appendChild(lastNameElement);
            var racingCategoryElement = saveDocument.createElement("racingCategory");
            racingCategoryElement.appendChild(saveDocument.createTextNode(racer.getRacingCategory().toString()));
            racerElement.appendChild(racingCategoryElement);
            racersElement.appendChild(racerElement);
        }
        var manchesElement = saveDocument.createElement("manches");
        root.appendChild(manchesElement);
        for (ArrayList<RacingCategory> manche :
                save.getManches()) {
            var mancheElement = saveDocument.createElement("manche");
            for (RacingCategory heat :
                    manche) {
                var heatElement = saveDocument.createElement("heat");
                heatElement.appendChild(saveDocument.createTextNode(heat.toString()));
                mancheElement.appendChild(heatElement);
            }
            manchesElement.appendChild(mancheElement);
        }
        var domSource = new DOMSource(saveDocument);
        var streamResult = new StreamResult(savePath);
        transformer.transform(domSource, streamResult);
    }
}