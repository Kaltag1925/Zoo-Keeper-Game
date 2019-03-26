package XML.StorageClasses;
import Engine.Animal.AnimalReal;
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

/**
 * Created by Rhiwaow on 29/03/2017.
 */

public class AnimalRealXMLStorage {


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
                result.add( entry );
            }

        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

    public void save(File file, Collection<AnimalReal> data) {
        try {
            file.getParentFile().mkdirs();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootNode = doc.createElement( "data" );
            doc.appendChild( rootNode );

            for(AnimalReal object : data) {
                Element entry = doc.createElement("Animal");
                rootNode.appendChild( entry );
                HashMap< String, String > attributes = object.toStorage();
                for( String attribute : attributes.keySet() ) {
                    entry.setAttribute( attribute, attributes.get( attribute ).toString() );
                }
//                for(AnimalBodyPart part : object.getBodyPartMap.children()){
//                    Element entry2 = doc.createElement("Animal");
//                    entry.appendChild(entry2);
//                    HashMap< String, String > attributes2 = part.toStorage();
//                    for( String attribute2 : attributes2.keySet() ) {
//                        entry2.setAttribute( attribute2, attributes2.get( attribute2 ).toString() );
//                    }
//                }

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