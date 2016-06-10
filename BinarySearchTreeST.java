  /**
     * Neil Christian
     * INFS 519
     * PA7
     * 
     * 
     * The following program is a binary search tree implementation of a symbol table.
     */
import java.util.Iterator;
import java.util.NoSuchElementException;


public class BinarySearchTreeST<Key extends Comparable, Value>
    implements OrderedSymbolTable<Key, Value>
{
	private Node root;  //Root of the BST
	private int size;
    
	class Node{
		private Key key;	//Key
		private Value value; //Data
		private Node left;  //Left Child
		private Node right; //Right Child
		int height;
		
		public Node(Key key, Value value, int height){
			this.key = key;
			this.value = value;
			this.height = height;
		}
	}
	
    public BinarySearchTreeST()
    {
    	this.root = null;
    }
    
    /**
     * Gets the number of elements currently in the symbol table.
     * @return size
     */
    public int size()
    {
        return size(root); 
    }
    
    public int size(Node t){
    	if(t == null) return 0;
    	else return t.height;
    }
    	
    /**
     * Determines if there are no elements in the  symbol table.
     * @return true if no elements, false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    
    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public void put( Key key, Value value ){
    	
        if( key == null){
        	throw new NullPointerException();
        }
        root = put(root, key, value);
        	
    }
    
    private Node put(Node t, Key key, Value value){
    	if(t == null){
    		return new Node(key, value, 1);
    	}
    	int compare = key.compareTo(t.key);
    	if(compare<0){
    		t.left = put(t.left, key, value);
    	}
    	else if (compare>0){
    		t.right = put(t.right, key, value);
    	}
    	else
    		t.height = 1+size(t.left)+size(t.right);
    	return t;
    }
 
    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key )
    {
        return get(root, key);
    }
    
    public Value get(Node t, Key key){
    	
    	if(t == null){
    		return null;
    	}
    	int compare = key.compareTo(t.key);
    	if (compare < 0){
    		return get(t.left, key);
    	}
    	else if(compare>0){
    		return get(t.right, key);
    	}
    	else
    		return t.value;
    }
    	
    /**
    * Iterable that enumerates (in sorted order) each key in the table.
    * @return iterable
    */
  
    
    public Iterable<Key> keys()
    {
    	DynamicArray<Key> list = new DynamicArray<Key>();
    	this.keys(root, list);
    	return list;
    }
    
    private void keys(Node t, DynamicArray<Key> list){
    	if(root == null){
    		return;
    	}
    }
    private void keys( Node t, List list )  {
    	        if( t== null ) return;
    	        this.keys(t.left, list);
    	        list.add(t.key);
    	        this.keys(t.right, list);
    	    }
    private void checkEmpty()
    {
        if( this.size == 0 )
        {
            throw new NoSuchElementException("SymbolTable is empty");
        }
    }
    /**
     * Finds and returns minimum key
     * @return smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() throws NoSuchElementException
    {
        checkEmpty();
        return this.min(this.root).key;
    }
    
    private Node min( Node t )
    {
        if( t != null )
        {
            while( t.left != null ) t = t.left;
        }
        return t;
    }
    /**
     * Finds and returns maximum key
     * @return largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    
    public Key max() throws NoSuchElementException
    {
        checkEmpty();
        return this.max(this.root).key;
    }
    
    private Node max( Node t )
    {
        if( t != null )
        {
            while( t.right != null ) t = t.right;
        }
        return t;
    }
    
    
    /**
     * Removes the minimum key from the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() throws NoSuchElementException
    {
    	if(isEmpty()) throw new NoSuchElementException();
    	root = deleteMax(root);
    }
    
    public Node deleteMax(Node t){
    	if(t.right == null){ 
    		return t.left;
    	}
    	t.right = deleteMax(t.right);
    	t.height = size(t.left)+size(t.right)+1;
    	return t;
    }
    

    /**
     * Removes the maximum key from the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin( ) throws NoSuchElementException
    {
    	if(isEmpty()) throw new NoSuchElementException();
    	root = deleteMin(root);
    	
    }
    public Node deleteMin(Node t){
    	if(t.left == null){
    		return t.right;
    	}
    	t.left = deleteMin(t.left);
    	t.height = size(t.left)+ size(t.right)+1;
    	return t;
    	}
    
    
    
    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Key key : this.keys())
        {
            Value item = this.get(key);
            if( first ) first = false;
            else buf.append( ", " );
            buf.append( key );
            buf.append( "->" );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        Stdio.open( "operations.txt" );
        BinarySearchTreeST<Integer,String> st = new BinarySearchTreeST<Integer,String>();
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equalsIgnoreCase("insert") )
            {
                int key    = Stdio.readInt();
                String val = Stdio.readString();
                st.put( key, val );
                Stdio.println( "insert="+st.toString() );
            }
            else if( method.equalsIgnoreCase("deleteMin") )
            {
                st.deleteMin();
                Stdio.println( "deleteMin" );
            }
            else if( method.equalsIgnoreCase("deleteMax") )
            {
                st.deleteMax();
                Stdio.println( "deleteMax" );
            }
            else if( method.equalsIgnoreCase("size") )
            {
                Stdio.println( "size="+st.size() );
            }
            else if( method.equalsIgnoreCase("min") )
            {
                Stdio.println( "min="+st.min() );
            }
            else if( method.equalsIgnoreCase("max") )
            {
                Stdio.println( "max="+st.max() );
            }
            else if( method.equalsIgnoreCase("isEmpty") )
            {
                Stdio.println( "isEmpty?="+st.isEmpty() );
            }
        }
        Stdio.println( "Final symbol table=" +st.toString() );
        Stdio.close();
    }
}
