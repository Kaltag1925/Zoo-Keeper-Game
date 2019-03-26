package XML.StorageClasses;

import CustomMisc.Tree;
import XML.IStorableObject;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Rhiwaow on 29/03/2017.
 */
public class AnimalBodyPartTemplateXMLStorage {


    public static class DataDump extends Vector<IStorableObject.StorableForm> {
    }

    public CopyOnWriteArrayList<Tree<IStorableObject.StorableForm, String>> load(File file ) {
        CopyOnWriteArrayList<Tree<IStorableObject.StorableForm, String>> result = new CopyOnWriteArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            NodeList nodes = doc.getElementsByTagName("Template");
            for( int i = 0; i < nodes.getLength(); i++ ) {
                String path = "/data/Template";

                Node node = nodes.item( i );
                NamedNodeMap attributes = node.getAttributes();
                IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                for( int j = 0; j < attributes.getLength(); j++ ) {
                    Node attributeNode = attributes.item( j );
                    String attributeName = attributeNode.getNodeName();
                    String attributeValue = attributeNode.getNodeValue();
                    entry.put( attributeName, attributeValue );
                }

                Tree<IStorableObject.StorableForm, String> parts = new Tree<>(entry);

                goTroughBodyPartTree(entry.get("name"), entry, parts, path, xpath, doc);

                result.add(parts);
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

                entry.put("parent", lastKey.get("name"));
//                System.out.println("===================");
//                System.out.println(lastNodeName);
//                System.out.println(entry);
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
        try {
            file.getParentFile().mkdirs();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootNode = doc.createElement( "data" );
            doc.appendChild( rootNode );

            for( IStorableObject object : data ) {
                Element entry = doc.createElement("Name");
                rootNode.appendChild( entry );
                HashMap< String, String > attributes = object.toStorage();
                for( String attribute : attributes.keySet() ) {
                    entry.setAttribute( attribute, attributes.get( attribute ).toString() );
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
}