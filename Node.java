package morse;

/*
Class to represent a letter-symbol pair and links to two children Nodes
*/
public class Node
{
    private Character letter;
    private String symbolCode;
    private int depthLevel; //corresponds to length of symbol
    private Node leftNode;
    private Node rightNode;
    
    /*
    Children Nodes are null when Node is constructed
    */
    public Node(Character letter, String symbol)
    {
        this.letter = letter;
        this.symbolCode = symbol;
        depthLevel = symbol.length();
        leftNode = null;
        rightNode = null;
    }

    public Character getLetter() 
    {
        return letter;
    }

    public String getSymbol() 
    {
        return symbolCode;
    }

    public int getDepthLevel() 
    {
        return depthLevel;
    }

    public Node getLeftNode() 
    {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) 
    {
        this.leftNode = leftNode;
    }
    
    public void setLetter(Character word) 
    {
        this.letter = word;
    }

    public void setSymbol(String symbol) 
    {
        this.symbolCode = symbol;
    }

    public void setDepthLevel(int depthLevel) 
    {
        this.depthLevel = depthLevel;
    }
    
    public Node getRightNode() 
    {
        return rightNode;
    }

    public void setRightNode(Node rightNode) 
    {
        this.rightNode = rightNode;
    }
}
