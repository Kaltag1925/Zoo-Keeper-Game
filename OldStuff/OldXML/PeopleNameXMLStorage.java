package XML.StorageClasses;

import XML.IStorableObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PeopleNameXMLStorage {

    public HashMap<String, ArrayList<IStorableObject.StorableForm>> load(File file ) {
        HashMap<String, ArrayList<IStorableObject.StorableForm>> result = new HashMap<>();
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            String firstNamePath = "data/NameList[@type='First']/Name";
            XPathExpression firstNameNode = xpath.compile(firstNamePath);
            NodeList firstNameNodes = (NodeList) firstNameNode.evaluate(doc, XPathConstants.NODESET);
            result.put("firstName", new ArrayList<>());
            for( int i = 0; i < firstNameNodes.getLength(); i++ ) {
                Node node = firstNameNodes.item( i );
                NamedNodeMap attributes = node.getAttributes();
                IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                for( int j = 0; j < attributes.getLength(); j++ ) {
                    Node attributeNode = attributes.item( j );
                    String attributeName = attributeNode.getNodeName();
                    String attributeValue = attributeNode.getNodeValue();
                    entry.put( attributeName, attributeValue );
                }
                result.get("firstName").add(entry);
            }

            String lastNamePath = "data/NameList[@type='Last']/Name";
            XPathExpression lastNameNode = xpath.compile(lastNamePath);
            NodeList lastNameNodes = (NodeList) lastNameNode.evaluate(doc, XPathConstants.NODESET);
            result.put("lastName", new ArrayList<>());
            for( int i = 0; i < lastNameNodes.getLength(); i++ ) {
                Node node = lastNameNodes.item( i );
                NamedNodeMap attributes = node.getAttributes();
                IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                for( int j = 0; j < attributes.getLength(); j++ ) {
                    Node attributeNode = attributes.item( j );
                    String attributeName = attributeNode.getNodeName();
                    String attributeValue = attributeNode.getNodeValue();
                    entry.put( attributeName, attributeValue );
                }
                result.get("lastName").add(entry);
            }

        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

}
