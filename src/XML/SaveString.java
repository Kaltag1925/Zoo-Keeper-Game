package XML;

import CustomMisc.StringCopy;
import CustomMisc.Tree;

public class SaveString {

    public static String createSaveString(Tree<StringCopy> data) {
        StringBuilder returnString = new StringBuilder();

        for (Tree<StringCopy> child : data.getChildren().values()) {
            returnString.append(child.getHead());

            if (child.hasChildren()) {
                returnString.append(":");
                returnString.append(saveThroughTree(child));
            }

            returnString.append(",");
        }

        return returnString.toString();
    }

    public static String saveThroughTree(Tree<StringCopy> data) {
        StringBuilder returnString = new StringBuilder();

        for (Tree<StringCopy> child : data.getChildren().values()) {
            returnString.append(child.getHead());

            if (child.hasChildren()) {
                returnString.append(":");
                returnString.append(saveThroughTree(child));
            }

            returnString.append(";");
        }

        return returnString.toString();
    }

    //TODO: Make everything into a tree

}
