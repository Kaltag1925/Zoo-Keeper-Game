package XML.StorageClasses;

import CustomMisc.Tree;
import Engine.Animal.AnimalBodyPart;
import Engine.Animal.AnimalReal;
import Engine.Animal.Food.AnimalFoodReal;
import Engine.Companies.AnimalZoo;
import XML.IStorableObject;
import javafx.util.Pair;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalZooXMLStorage {


    public static class DataDump extends Vector<IStorableObject.StorableForm> {
    }


    public CopyOnWriteArrayList<Pair<IStorableObject.StorableForm, DataDump>> load(File file) {
        CopyOnWriteArrayList<Pair<IStorableObject.StorableForm, DataDump>> result = new CopyOnWriteArrayList<>();
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();

            NodeList nodes = doc.getElementsByTagName("Company");
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

                DataDump animals = new DataDump();
                Pair pair = new Pair(entry, animals);
                result.add(pair);

                Element companies = (Element) node;
                NodeList nodes2 = companies.getElementsByTagName("Animal");
                for(int i2 = 0; i2 < nodes2.getLength(); i2++) {
                    Node node2 = nodes2.item( i2 );
                    NamedNodeMap attributes2 = node2.getAttributes();
                    IStorableObject.StorableForm entry2 = new IStorableObject.StorableForm();
                    for( int j = 0; j < attributes2.getLength(); j++ ) {
                        Node attributeNode = attributes2.item( j );
                        String attributeName = attributeNode.getNodeName();
                        String attributeValue = attributeNode.getNodeValue();
                        entry2.put( attributeName, attributeValue );
                    }
                    animals.add(entry2);
                }
            }

        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

    public void save(File file, Collection<AnimalZoo> data) {
        try {
            file.getParentFile().mkdirs();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootNode = doc.createElement( "data" );
            doc.appendChild( rootNode );

            for(AnimalZoo zoo : data) {
                Element entry = doc.createElement("Company");
                rootNode.appendChild( entry );
                HashMap< String, String > attributes = zoo.toStorage();
                for( String attribute : attributes.keySet() ) {
                    entry.setAttribute( attribute, attributes.get( attribute ).toString() );
                }
                for(AnimalReal animal : zoo.getAnimals().values()){
                    Element entry2 = doc.createElement("Animal");
                    entry.appendChild(entry2);
                    HashMap<String, String> attributes2 = animal.toStorage();
                    for( String attribute2 : attributes2.keySet()) {
                        entry2.setAttribute(attribute2, attributes2.get(attribute2).toString());
                    }

                    Element bodyPart = doc.createElement("BodyPart");
                    entry2.appendChild(bodyPart);
                    Tree<AnimalBodyPart, String> body = animal.getBodyPartMap();
                    HashMap< String, String > bodyPartAttributes = body.getHead().toStorage();
                    for( String attribute : bodyPartAttributes.keySet() ) {
                        entry.setAttribute( attribute, bodyPartAttributes.get( attribute ).toString() );
                    }
                    if (body.getChildren().size() != 0) {
                        for (Tree<AnimalBodyPart, String> nextBodyTree : body.getChildren().values()) {
                            goTroughBodyParts(bodyPart, doc, animal, nextBodyTree);
                        }
                    }
                }
                for (AnimalFoodReal food : zoo.getFoods().values()) {
                    Element foodElem = doc.createElement("Food");
                    entry.appendChild(foodElem);
                    HashMap<String, String> attributes2 = food.toStorage();
                    for( String attribute2 : attributes2.keySet()) {
                        foodElem.setAttribute(attribute2, attributes2.get(attribute2).toString());
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

    private void goTroughBodyParts(Element lastElement, Document doc, AnimalReal animal, Tree<AnimalBodyPart, String> currentTree) {
        Element bodyPart = doc.createElement("BodyPart");
        HashMap< String, String > bodyPartAttributes = currentTree.getHead().toStorage();
        for( String attribute : bodyPartAttributes.keySet() ) {
            bodyPart.setAttribute( attribute, bodyPartAttributes.get( attribute ).toString() );
        }
        lastElement.appendChild(bodyPart);
        if (currentTree.getChildren().size() != 0) {
            for (Tree<AnimalBodyPart, String> nextBodyTree : currentTree.getChildren().values()) {
                goTroughBodyParts(bodyPart, doc, animal, nextBodyTree);
            }
        }
    }
}