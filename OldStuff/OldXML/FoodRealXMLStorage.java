package XML.StorageClasses;

import Engine.Animal.Food.AnimalFoodReal;
import XML.IStorableObject;
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

public class FoodRealXMLStorage {


    public static class DataDump extends Vector<IStorableObject.StorableForm> {
    }


    public DataDump load(File file) {
        DataDump result = new DataDump();
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();

            NodeList nodes = doc.getElementsByTagName("Food");
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
                result.add( entry );
            }

        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

    public void save(File file, Collection<AnimalFoodReal> data) {
        try {
            file.getParentFile().mkdirs();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootNode = doc.createElement( "data" );
            doc.appendChild( rootNode );

            for(AnimalFoodReal object : data) {
                Element entry = doc.createElement("Food");
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