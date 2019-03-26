package XML.StorageClasses;

import CustomMisc.Tree;
import XML.IStorableObject;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Rhiwaow on 29/03/2017.
 */
public class AnimalXMLStorage {


    public static class DataDump extends Vector<IStorableObject.StorableForm> {
    }

    public HashMap<IStorableObject.StorableForm, Tree<IStorableObject.StorableForm, String>> load(File file) {
        HashMap<IStorableObject.StorableForm, Tree<IStorableObject.StorableForm, String>> result = new HashMap<>();
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            NodeList nodes = doc.getElementsByTagName("Animal");
            for( int i = 0; i < nodes.getLength(); i++ ) {
                Node node = nodes.item( i );
                NamedNodeMap attributes = node.getAttributes();
                IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                for( int j = 0; j < attributes.getLength(); j++ ) {
                    Node attributeNode = attributes.item( j );
                    String attributeName = attributeNode.getNodeName();
                    String attributeValue = attributeNode.getNodeValue();
                    entry.put( attributeName, attributeValue );
                }

                String path = "/data/Animal";

                if (((Element) node).getElementsByTagName("BodyPart") != null) {
                    path += "[@animalSpecies='" + entry.get("animalSpecies") + "']/BodyPart";
                    XPathExpression node2 = xpath.compile(path);
                    NodeList nodes2 = (NodeList) node2.evaluate(doc, XPathConstants.NODESET);
                    for (int x = 0; x < nodes2.getLength(); x++) {
                        Node part = nodes2.item(x);
                        NamedNodeMap attributes2 = part.getAttributes();
                        IStorableObject.StorableForm entry2 = new IStorableObject.StorableForm();
                        for (int y = 0; y < attributes2.getLength(); y++) {
                            Node attributeNode = attributes2.item(y);
                            String attributeName = attributeNode.getNodeName();
                            String attributeValue = attributeNode.getNodeValue();
                            entry2.put(attributeName, attributeValue);
                        }
                        Tree<IStorableObject.StorableForm, String> parts = new Tree<>(entry2);

                        goTroughBodyPartTree(entry2.get("name"), entry2, parts, path, xpath, doc);

                        result.put(entry, parts);

                    }
                }
            }

        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

    private void goTroughBodyPartTree(String lastNodeName, IStorableObject.StorableForm lastKey, Tree<IStorableObject.StorableForm, String> list, String path, XPath xpath, Document doc) {
        try {
            path += "[@name='" + lastNodeName + "']/BodyPart";
            XPathExpression node = xpath.compile(path);
            NodeList nodes = (NodeList) node.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node2 = nodes.item(i);
                NamedNodeMap attributes = node2.getAttributes();
                IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attributeNode = attributes.item(j);
                    String attributeName = attributeNode.getNodeName();
                    String attributeValue = attributeNode.getNodeValue();
                    entry.put(attributeName, attributeValue);
                }

                entry.put("parent", lastNodeName);
                list.addChild(lastKey, entry);

                if(node2.hasChildNodes()) {
                    goTroughBodyPartTree(entry.get("name"), entry, list, path, xpath, doc);
                }
            }
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();

        }
    }

    public void save(File file, Collection< IStorableObject > data) {
//        try {
//            file.getParentFile().mkdirs();
//
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.newDocument();
//            Element rootNode = doc.createElement( "data" );
//            doc.appendChild( rootNode );
//
//            for( IStorableObject object : data ) {
//                Element entry = doc.createElement("Animal");
//                rootNode.appendChild( entry );
//                HashMap< String, String > attributes = object.toStorage();
//                for( String attribute : attributes.keySet() ) {
//                    entry.setAttribute( attribute, attributes.get( attribute ).toString() );
//                }
//            }
//
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource( doc );
//            StreamResult streamResult = new StreamResult( file );
//            transformer.transform( source, streamResult );
//        }catch( Exception ex ) {
//            ex.printStackTrace();
//        }
    }
}