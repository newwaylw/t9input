package t9input;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple object representing a trie node
 * @author wei
 *
 */
public class TrieNode {
	private Character key;
	private Map<Character, TrieNode> childrenMap;
	private List<Integer> idList;
	
	/**
	 * A trie node contains a key and a map of direct children of this key
	 * a key here will be a numeral character representing a T9 input.
	 * A list containing possible values associated with the path root to this node
	 * is also kept. a non zero length list means there is at least one word
	 * ends with this input
	 * 
	 * @param key
	 */
	public TrieNode(Character key){
		this.key = key;
		this.childrenMap = new HashMap<Character, TrieNode>();
		idList = new ArrayList<Integer>();
	}
	
	public Character getKey() {
		return this.key;
	}
	
	public void addValue(Integer v){
		this.idList.add(v);
	}
	
	public List<Integer> getValues(){
		return this.idList;
	}
	
	public boolean hasChildren(){
		return this.childrenMap.size() > 0;
	}
	
	/**
	 * return children of this node
	 * @return Map
	 */
	public Map<Character,TrieNode> getChildren() {
		return this.childrenMap;
	}
	
	/**
	 * add a child to this node
	 * @param childNode
	 */
	public void addChild(TrieNode childNode) {
		this.childrenMap.put(childNode.getKey(), childNode);
	}
}
