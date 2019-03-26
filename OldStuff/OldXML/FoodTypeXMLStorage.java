package XML.StorageClasses;

import CustomMisc.Tree;
import XML.IStorableObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class FoodTypeXMLStorage {
    public static class DataDump extends Vector<IStorableObject.StorableForm> {

    }
    public Tree<IStorableObject.StorableForm, String> load(File file) {
        IStorableObject.StorableForm head = new IStorableObject.StorableForm();
        Tree<IStorableObject.StorableForm, String> result = new Tree<>(head);
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new FileInputStream( file );
            Document doc = db.parse( inputStream );
            inputStream.close();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            String foodPath = "/data/FoodType";
            XPathExpression foodNode = xpath.compile(foodPath);
            NodeList foodNodeList = (NodeList) foodNode.evaluate(doc, XPathConstants.NODESET);
            if (foodNodeList != null) {
                for (int x = 0; x < foodNodeList.getLength(); x++) {
                    Node food = foodNodeList.item(x);
                    NamedNodeMap foodAttributes = food.getAttributes();
                    IStorableObject.StorableForm foodEntry = new IStorableObject.StorableForm();
                    for (int y = 0; y < foodAttributes.getLength(); y++) {
                        Node attributeNode = foodAttributes.item(y);
                        String attributeName = attributeNode.getNodeName();
                        String attributeValue = attributeNode.getNodeValue();
                        foodEntry.put(attributeName, attributeValue);
                    }
                    Tree<IStorableObject.StorableForm, String> relationTree = new Tree<>(foodEntry);

                    relationTree.addTreeCollection(goTroughTree(foodEntry.get("type"), foodPath, xpath, doc));

                    result.addTreeChild(relationTree);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return result;
    }

    private ArrayList<Tree<IStorableObject.StorableForm, String>> goTroughTree(String lastNodeName, String path, XPath xpath, Document doc) {
        try {
            ArrayList<Tree<IStorableObject.StorableForm, String>> treeList = new ArrayList<>();
            path += "[@type='" + lastNodeName + "']/FoodType";
            XPathExpression foodNode = xpath.compile(path);
            NodeList foodNodeList = (NodeList) foodNode.evaluate(doc, XPathConstants.NODESET);
            if (foodNodeList != null) {
                for (int i = 0; i < foodNodeList.getLength(); i++) {
                    Node node2 = foodNodeList.item(i);
                    NamedNodeMap attributes = node2.getAttributes();
                    IStorableObject.StorableForm entry = new IStorableObject.StorableForm();
                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attributeNode = attributes.item(j);
                        String attributeName = attributeNode.getNodeName();
                        String attributeValue = attributeNode.getNodeValue();
                        entry.put(attributeName, attributeValue);
                    }
                    Tree<IStorableObject.StorableForm, String> tree = new Tree<>(entry);
                    treeList.add(tree);

                    tree.addTreeCollection(goTroughTree(entry.get("type"), path, xpath, doc));
                }
            }

            return treeList;
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
