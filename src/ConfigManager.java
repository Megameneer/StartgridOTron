import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

class ConfigManager {
    private final File xmlFile;
    private final Transformer transformer;
    private final DocumentBuilder documentBuilder;
    private Document configDocument;
    private LinkedHashSet<String> saves;

    ConfigManager() throws
            ParserConfigurationException,
            IOException,
            TransformerException {
        var documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        var transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        xmlFile = new File("config.xml");
        if (!xmlFile.exists()) createNewConfig();
        try {
            configDocument = documentBuilder.parse(xmlFile);
        } catch (SAXException saxe) {
            configDocument = documentBuilder.newDocument();
            createNewConfig(configDocument);
        }
        configDocument.getDocumentElement().normalize();
        var savesNodeList = configDocument.getElementsByTagName("recentSaves").item(0).getChildNodes();
        saves = new LinkedHashSet<>();
        for (var i = 0; i < savesNodeList.getLength(); i++) {
            var x = savesNodeList.item(i).getTextContent();
            saves.add(savesNodeList.item(i).getTextContent());
        }
    }

    private void createNewConfig() throws TransformerException {
        createNewConfig(documentBuilder.newDocument());
    }

    private void createNewConfig(Document configDocument) throws TransformerException {
        configDocument.appendChild(configDocument.createElement("recentSaves"));
        transform(configDocument);
    }

    LinkedHashSet<String> getSaves() {
        return saves;
    }

    void addSave(Save save) {
        var savePath = save.getLocation().getAbsolutePath();
        var root = configDocument.getDocumentElement();
        var saveElement = configDocument.createElement("save");
        var saveNodes = root.getChildNodes();
        if (saves.contains(savePath)) {
            for (var i = 0; i < saveNodes.getLength(); i++) {
                var saveNode = saveNodes.item(i);
                if (saveNode.getTextContent().equals(savePath)) root.removeChild(saveNode);
            }
            saves.remove(savePath);
        }
        saveElement.appendChild(configDocument.createTextNode(savePath));
        root.insertBefore(saveElement, saveNodes.item(0));
        try {
            transform();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        var newSaves = new LinkedHashSet<String>();
        newSaves.add(savePath);
        newSaves.addAll(saves);
        saves = newSaves;
    }

    private void transform() throws TransformerException {
        transform(configDocument);
    }

    private void transform(Document configDocument) throws TransformerException {
        var domSource = new DOMSource(configDocument);
        var streamResult = new StreamResult(xmlFile);
        transformer.transform(domSource, streamResult);
    }
}