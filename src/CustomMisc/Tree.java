package CustomMisc;

import Engine.Logic.AnimalBodyPart;
import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/*
 * @param <T> the type of the value being stored
 * @param <U> the type of value used for searching
 */

public class Tree<T> implements ICreateCopy{

    private T head;
    private Tree<T> parent;
    private Vector<Tree <T>> childList = new Vector<>();
    private LinkedHashMap<T, Tree<T>> children = new LinkedHashMap<>();
    
    public Tree(T head){
        this.head = head;
    }

    public void addChild(T key, T wutAdd){
        Tree<T> parent = findFirstTree(key);
        parent.addChild(wutAdd);
    }

    private void addChild(T wutAdd){
        if (!containsKey(wutAdd)) {
            Tree<T> tree = new Tree(wutAdd);
            children.put(wutAdd, tree);
            tree.setParent(this);
            childList.add(tree);
        }
    }

    public void addTreeChild(Tree<T> wutAdd, Boolean checkRepeats){
        if (checkRepeats) {
            if(wutAdd != null) {
                if(!containsKey(wutAdd.getHead())) {
                    children.put(wutAdd.getHead(), wutAdd);
                    childList.add(wutAdd);
                    wutAdd.setParent(this);
                } else if (wutAdd.hasChildren()) {
                    Tree<T> actually = findFirstTree(wutAdd.getHead());
                    for (Tree<T> child : wutAdd.children.values()) {
                        actually.addTreeChild(child, true);
                    }
                }
            }
        } else {
            if(wutAdd != null) {
                children.put(wutAdd.getHead(), wutAdd);
                wutAdd.setParent(this);
                childList.add(wutAdd);
            }
        }
    }

    public void addTreeCollection(Collection<Tree<T>> wutAdd) {
        for (Tree<T> tree : wutAdd) {
            addTreeChild(tree, true);
        }
    }

    public boolean containsTree(Tree<T> tree) {
        if (equals(tree)) {
            return true;
        } else {
            return children.containsValue(tree);
        }
    }

    public boolean containsKey(T key) {
        if (head.equals(key)) {
            return true;
        } else {
            for (T t : children.keySet()) {
                if (t.equals(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void setParent(Tree<T> parent){
        this.parent = parent;
    }

    public Tree<T> findTree(T key){
        if (key.equals(head)) {
            return this;
        }
        if (childList.size() != 0) {
            for (Tree<T> t : childList) {
                if (key.equals(t.getHead())) {
                    return t;
                }
            }
        }

        return null;
    }

    public Tree<T> findFirstTree(T key){
        if (key.equals(head)) {
            return this;
        }
        if (children.size() != 0) {
            for (Tree<T> t : children.values()) {
                Tree<T> possibleTree = t.findFirstTree(key);
                if (possibleTree != null) {
                    return possibleTree;
                }
            }
        }

        return null;
    }

    public <U> T findKeyFromMethod(U key, Function<T, U> method){
        if (method.apply(head).equals(key)) {
            return this.getHead();
        }

        if (childList.size() != 0) {
            for (Tree<T> t :childList){
                if(method.apply(t.getHead()).equals(key)) {
                    return t.getHead();
                }
            }
        }

        return null;
    }

    public <U> T findFirstKeyFromMethod(U key, Function<T, U> method){
        if (method.apply(head).equals(key)) {
            return this.getHead();
        }

        if (children.size() != 0) {
            for (Tree<T> t : children.values()){
                T possibleKey = t.findFirstKeyFromMethod(key, method);
                if (possibleKey != null) {
                    return possibleKey;
                }
            }
        }

        return null;
    }

    public <U> Tree<T> findTreeFromMethod(U key, Function<T, U> method){
        if (method.apply(head).equals(key)) {
            return this;
        }

        if (childList.size() != 0) {
            for (Tree<T> t :childList){
                if(method.apply(t.getHead()).equals(key)) {
                    return t;
                }
            }
        }

        return null;
    }

    public <U> Tree<T> findFirstTreeFromMethod(U key, Function<T, U> method){
        if (method.apply(head).equals(key)) {
            return this;
        }

        if (children.size() != 0) {
            for (Tree<T> t : children.values()){
                Tree<T> possibleTree = t.findFirstTreeFromMethod(key, method);
                if (possibleTree != null) {
                    return possibleTree;
                }
            }
        }

        return null;
    }

    public <U> Tree<T> findFirstTreeFromMethod(U key, Method method, Object... args){
        try {
            if (method.invoke(key, args).equals(key)) {
                return this;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (children.size() != 0) {
            for (Tree<T> t : children.values()){
                Tree<T> possibleTree = t.findFirstTreeFromMethod(key, method);
                if (possibleTree != null) {
                    return possibleTree;
                }
            }
        }

        return null;
    }

    public Tree<T> getFirstChildTree() {
        Tree<T> returnTree = new Tree<>(null);
        for (Tree<T> tree : children.values()) {
            returnTree = tree;
            break;
        }

        System.out.println("returning: " + returnTree);

        return returnTree;
    }

    public <U> Pair<Integer, Tree<T>> navigateThroughTree(ArrayList<T> array, int index, Function<T, U> method) {
        U key = method.apply(array.get(index));

        if (getDirectTreeChildFromMethod(key, method) != null && index + 1 <= array.size() - 1) {
            index++;
            return getDirectTreeChildFromMethod(key, method).navigateThroughTree(array, index, method);
        } else {
            return new Pair<>(index, this);
        }
    }

    public <U> Tree<T> getDirectTreeChildFromMethod(U key, Function<T, U> method) {
        Tree<T> returnTree = null;
        if (hasChildren()) {
            for (Tree<T> tree : children.values()) {
                if(method.apply(tree.getHead()).equals(key)) {
                    returnTree = tree;
                }
            }
        }

        return returnTree;
    }

    public static <T> Tree<T> arrayListToTree(ArrayList<T> array, int index) {
        Tree<T> returnTree = new Tree<>(array.get(index));


        if (index + 1 <= array.size() - 1) {
            index++;
            returnTree.addTreeChild(Tree.arrayListToTree(array, index), true);
        }

        return returnTree;
    }

    private static ArrayList<Class> primativeClasses = new ArrayList<>();
    static {
        primativeClasses.add(Boolean.class);
        primativeClasses.add(Character.class);
        primativeClasses.add(Byte.class);
        primativeClasses.add(Short.class);
        primativeClasses.add(Integer.class);
        primativeClasses.add(Long.class);
        primativeClasses.add(Float.class);
        primativeClasses.add(Double.class);
        primativeClasses.add(Void.class);
        primativeClasses.add(String.class);
    }

    @Override
    public Tree<T> createCopy() {
        Tree<T> newTree = new Tree<>(getHead());

        if (primativeClasses.contains(head.getClass())) {
            newTree.head = head; //If strings becoem copied its because the String reference is being copied
        } else {
            try {
                newTree.head = (T) ((ICreateCopy) head).createCopy();
            } catch (ClassCastException ex) {
                throw new ClassCastException(head.getClass() + " is not an implementer of ICreateCopy");
            }
        }

        if (children.size() != 0) {
            for (Tree<T> childTree : children.values()) {
                newTree.addTreeChild(childTree.cloneRunTrough(this), true);
            }
        }
        return newTree;
    }

    private Tree<T> cloneRunTrough(Tree<T> parent){
        Tree<T> newTree = new Tree<>(getHead());

        if (primativeClasses.contains(head.getClass())) {
            newTree.head = head; //If strings becoem copied its because the String reference is being copied
        } else {
            try {
                newTree.head = (T) ((ICreateCopy) head).createCopy();
            } catch (ClassCastException ex) {
                throw new ClassCastException(head.getClass() + " is not an implementer of ICreateCopy");
            }
        }

        if (newTree.head instanceof AnimalBodyPart){ //TODO: Make this part of the copy for AnimalBodyPart
            AnimalBodyPart part = (AnimalBodyPart) newTree.head;
            part.setParent((AnimalBodyPart) parent.getHead());
        }

        newTree.parent = parent;
        if (children.size() != 0) {
            for (Tree<T> childTree : children.values()) {
                newTree.addTreeChild(childTree.cloneRunTrough(this), true);
            }
        }
        return newTree;
    }

    public Tree<T> getParent(){
        return parent;
    }

    public ArrayList<Tree<T>> getParents() {
        ArrayList<Tree<T>> returnArray = new ArrayList<>();

        if (parent != null) {
            returnArray.add(parent);

            ArrayList<Tree<T>> array = parent.getParents();
            if (array != null) {
                returnArray.addAll(array);
            }
        } else {
            return null;
        }

        return returnArray;
    }

    public HashMap<T, Tree<T>> getChildren() {
        return children;
    }

    public T getHead(){
        return head;
    }

    public Vector<Tree<T>> getChildList() {
        return childList;
    }

    public Tree<T> getTopParent(){
        if (getParent() != null) {
            return getParent().getTopParent();
        } else {
            return this;
        }
    }

    public boolean hasChildren() {
        return children.size() != 0;
    }

    public String toString(){
        String returnValue = getHead().toString() + " contains(\n";
        if (childList.size() != 0) {
            for (Map.Entry<T, Tree<T>> t : children.entrySet()) {
                T currentKey = t.getKey();
                Tree<T> child = t.getValue();
                returnValue += child.toString();
            }
        }

        returnValue += "), ";
        return returnValue;
    }
}
