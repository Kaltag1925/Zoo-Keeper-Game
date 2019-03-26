package XML.StorageClasses;

import Engine.People.PeopleClasses.Visitor;
import XML.IStorableObject;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public class VisitorXMLStorage {
    public static class DataDump extends Vector<IStorableObject.StorableForm> {
    }


    public HashMap<String, ArrayList<IStorableObject.StorableForm>> load(File file) {
        HashMap<String, ArrayList<IStorableObject.StorableForm>>  result = new HashMap<>();
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            String currentVisitorsPath = "data/NameList[@name='CurrentVisitors']/Visitor";
            XPathExpression currentVistorsNode = xpath.compile(currentVisitorsPath);
            NodeList currentVisitorsList = (NodeList) currentVistorsNode.evaluate(doc, XPathConstants.NODESET);
            result.put("currentVisitors", new ArrayList<>());
            for( int i = 0; i < currentVisitorsList.getLength(); i++ ) {
                Node node = currentVisitorsList.item( i );
                NamedNodeMap attributes = node.getAttributes();
                IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                for( int j = 0; j < attributes.getLength(); j++ ) {
                    Node attributeNode = attributes.item( j );
                    String attributeName = attributeNode.getNodeName();
                    String attributeValue = attributeNode.getNodeValue();
                    entry.put( attributeName, attributeValue );
                }
                result.get("currentVisitors").add(entry);
            }
            String planToVisitAgainPath = "data/NameList[@name='PlanToVisitAgain']/Visitor";
            XPathExpression planToVisitAgainNode = xpath.compile(planToVisitAgainPath);
            NodeList planToVisitAgainList = (NodeList) planToVisitAgainNode.evaluate(doc, XPathConstants.NODESET);
            result.put("planToVisitAgain", new ArrayList<>());
            for( int i = 0; i < planToVisitAgainList.getLength(); i++ ) {
                Node node = planToVisitAgainList.item( i );
                NamedNodeMap attributes = node.getAttributes();
                IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                for( int j = 0; j < attributes.getLength(); j++ ) {
                    Node attributeNode = attributes.item( j );
                    String attributeName = attributeNode.getNodeName();
                    String attributeValue = attributeNode.getNodeValue();
                    entry.put( attributeName, attributeValue );
                }
                result.get("planToVisitAgain").add(entry);
            }
        }catch( Exception ex ) {
            ex.printStackTrace();
        }

        return result;
    }

    public void save(File file, HashMap<String, Collection<Visitor>> data) {
        try {
            file.getParentFile().mkdirs();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootNode = doc.createElement( "data" );
            doc.appendChild( rootNode );

            Element currentVisitorsNode = doc.createElement("CurrentVisitors");
            rootNode.appendChild(currentVisitorsNode);
            for(Visitor object : data.get("currentVisitors")) {
                Element entry = doc.createElement("Visitor");
                currentVisitorsNode.appendChild(entry);
                HashMap<String, String> attributes = object.toStorage();
                for (String attribute : attributes.keySet()) {
                    entry.setAttribute(attribute, attributes.get(attribute).toString());
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(file);
                transformer.transform(source, streamResult);
            }

            Element planToVisitAgainNode = doc.createElement("PlanToVisitAgain");
            rootNode.appendChild(planToVisitAgainNode);
            for(Visitor object : data.get("PlanToVisitAgain")) {
                Element entry = doc.createElement("Visitor");
                planToVisitAgainNode.appendChild(entry);
                HashMap<String, String> attributes = object.toStorage();
                for (String attribute : attributes.keySet()) {
                    entry.setAttribute(attribute, attributes.get(attribute).toString());
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(file);
                transformer.transform(source, streamResult);
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
