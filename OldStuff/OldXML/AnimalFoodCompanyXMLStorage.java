package XML.StorageClasses;

import Engine.Animal.Food.AnimalFoodReal;
import Engine.Companies.AnimalFoodCompany;
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

public class AnimalFoodCompanyXMLStorage {
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

                DataDump food = new DataDump();
                Pair pair = new Pair(entry, food);
                result.add(pair);

                Element companies = (Element) node;
                NodeList nodes2 = companies.getElementsByTagName("Food");
                for(int j = 0; j < nodes2.getLength(); j++) {
                    Node node2 = nodes2.item( j);
                    NamedNodeMap attributes2 = node2.getAttributes();
                    IStorableObject.StorableForm entry2 = new IStorableObject.StorableForm();
                    for( int x = 0; x < attributes2.getLength(); x++ ) {
                        Node attributeNode = attributes2.item(x);
                        String attributeName = attributeNode.getNodeName();
                        String attributeValue = attributeNode.getNodeValue();
                        entry2.put( attributeName, attributeValue );
                    }
                    food.add(entry2);
                }
            }

        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

    public void save(File file, Collection<AnimalFoodCompany> data) {
        try {
            file.getParentFile().mkdirs();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootNode = doc.createElement( "data" );
            doc.appendChild( rootNode );

            for( AnimalFoodCompany object : data) {
                Element entry = doc.createElement("Company");
                rootNode.appendChild( entry );
                HashMap< String, String > attributes = object.toStorage();
                for( String attribute : attributes.keySet() ) {
                    entry.setAttribute( attribute, attributes.get( attribute ).toString() );
                }
                for(AnimalFoodReal food : object.getFoods().values()){
                    Element entry2 = doc.createElement("Food");
                    entry.appendChild(entry2);
                    HashMap<String, String> attributes2 = food.toStorage();
                    for( String attribute2 : attributes2.keySet()) {
                        entry2.setAttribute(attribute2, attributes2.get(attribute2).toString());
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
}
