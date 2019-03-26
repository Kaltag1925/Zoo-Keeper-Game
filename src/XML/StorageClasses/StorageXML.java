package XML.StorageClasses;

import CustomMisc.Tree;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class StorageXML {
    public CopyOnWriteArrayList<Tree<IStorableObject.StorableForm>> load(File file ) {
        CopyOnWriteArrayList<Tree<IStorableObject.StorableForm>> result = new CopyOnWriteArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();


            NodeList nodes = doc.getChildNodes();
            for( int i = 0; i < nodes.getLength(); i++ ) {
                Node node = nodes.item( i );
                if (node.getNodeName() != "#text") {
                    NamedNodeMap attributes = node.getAttributes();
                    IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                    if(attributes != null) {
                        for (int j = 0; j < attributes.getLength(); j++) {
                            Node attributeNode = attributes.item(j);
                            String attributeName = attributeNode.getNodeName();
                            String attributeValue = attributeNode.getNodeValue();
                            entry.put(attributeName, attributeValue);
                        }
                    }

                    entry.put("nodeName", node.getNodeName());

                    Tree<IStorableObject.StorableForm> parts = new Tree<>(entry);

                    if(node.hasChildNodes()) {
                        NodeList childNodes = node.getChildNodes();
                        for (int x = 0; x < childNodes.getLength(); x++) {
                            Node childNode = childNodes.item(x);
                            if (childNode.getNodeName() != "#text") {
                                parts.addTreeChild(loadThroughTree(childNode), false);
                            }
                        }
                    }

                    result.add(parts);
                }
            }

        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

    private Tree<IStorableObject.StorableForm> loadThroughTree(Node currentNode) {
        IStorableObject.StorableForm entry = new IStorableObject.StorableForm();

        if (currentNode.hasAttributes()) {
            NamedNodeMap attributes = currentNode.getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                Node attributeNode = attributes.item(j);
                String attributeName = attributeNode.getNodeName();
                String attributeValue = attributeNode.getNodeValue();
                entry.put(attributeName, attributeValue);
            }
        }

        entry.put("nodeName", currentNode.getNodeName());

        Tree<IStorableObject.StorableForm> currentTree = new Tree<>(entry);

        if (currentNode.hasChildNodes()) {
            NodeList childNodes = currentNode.getChildNodes();
            for (int x = 0; x < childNodes.getLength(); x++) {
                Node childNode = childNodes.item(x);
                if (childNode.getNodeName() != "#text") {
                    currentTree.addTreeChild(loadThroughTree(childNode), false);
                }
            }
        }

        return currentTree;
    }

    public void save(File file, Vector<Tree<IStorableObject.StorableForm>> data) {
        try {
            file.getParentFile().mkdirs();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootNode = doc.createElement( "data" );
            doc.appendChild( rootNode );

            for (Tree<IStorableObject.StorableForm> tree : data) {
                IStorableObject.StorableForm object = tree.getHead();
                Element entry = doc.createElement(object.get("nodeName"));
                object.remove("nodeName");
                rootNode.appendChild(entry);
                for (String attribute : object.keySet()) {
                    if (object.get(attribute) != null) {
                        entry.setAttribute(attribute, object.get(attribute).toString());
                    } else {
                        entry.setAttribute(attribute, "");
                    }
                }
                if (!tree.getChildren().isEmpty()) {
                    for (Tree<IStorableObject.StorableForm> treeChild : tree.getChildren().values()) {
                        saveThroughTree(treeChild, entry, doc);
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource( doc );
            StreamResult streamResult = new StreamResult( file );
            transformer.transform( source, streamResult );
        }catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void saveThroughTree(Tree<IStorableObject.StorableForm> tree, Element lastElement, Document doc) {
        IStorableObject.StorableForm object = tree.getHead();
        Element entry = doc.createElement(object.get("nodeName")); //TODO: It is copying the storableform reference
        object.remove("nodeName");
        lastElement.appendChild(entry);
        for (String attribute : object.keySet()) {
            if (object.get(attribute) != null) {
                entry.setAttribute(attribute, object.get(attribute).toString());
            } else {
                entry.setAttribute(attribute, "");
            }
        }
        if (!tree.getChildren().isEmpty()) {
            for (Tree<IStorableObject.StorableForm> treeChild : tree.getChildren().values()) {
                saveThroughTree(treeChild, entry, doc);
            }
        }
    }
}
