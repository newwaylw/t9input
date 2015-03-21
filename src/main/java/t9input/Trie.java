package t9input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Trie {

	private TrieNode root;
	
	public Trie() {
		root = new TrieNode(' ');
	}
	
	/**
	 * insert a word in its T9 numeral (e.g. "234") and the wID to this trie 
	 * @param key T9 numeral input of a word
	 * @param wID the integer id of this word
	 */
	public void insert(String key, Integer wID){
		TrieNode n = root;
		for(char c : key.toCharArray()){
			Map<Character, TrieNode> childrenMap = n.getChildren();
			//no such children, create a new node and add it as a child of the current node
			if (!childrenMap.containsKey(c)){
				TrieNode cNode = new TrieNode(c);
				childrenMap.put(c, cNode);
				n = cNode;
			}else{
				//advance to the child node with the same key
				n = childrenMap.get(c);
			}
		}
		n.addValue(wID);
	}
	
	/**
	 * Recursively append values of all children node. Append closer (shorter)
	 * values first (Breadth-first search)
	 * we are using a Map for a node's possible children, this means
	 * when getting all children nodes the order is not guaranteed
	 * @param n
	 * @param resultList
	 */
	private void getChildrenValues(TrieNode n, List<Integer> resultList){
		Map<Character, TrieNode> childrenMap = n.getChildren();
		if (!childrenMap.isEmpty()){
			List<TrieNode> l = new ArrayList<TrieNode>(childrenMap.values());
			for(TrieNode nd : l){
				//add all children on this level
				resultList.addAll(nd.getValues());
				//sort the value, which is an integer word ID
				Collections.sort(resultList);
				getChildrenValues(nd, resultList);
			}
		}
	}
	
	/**
	 * given an input (t9 style encoding), return all nodes who have
	 * the input as match or prefix match. shorter prefix matches are ranked higher, then sorted by descending frequency order
	 * 
	 * For example, input "866" and in a corpus where the frequency of "tomorrow"
	 * is larger than "too", it will display prefix match "too" before exact match "tomorrow", 
	 * since "too" is closer (3 input) than "tomorrow" (8 input) 
	 * 
	 * @param input - t9 numeral input (e.g. "2234")
	 * @param nResults - number of matches returned
	 * @return
	 */
	public List<Integer> getResults(String input, int nResults){
		List<Integer> resultList = new LinkedList<Integer>();
		TrieNode n = root;
		for (Character c : input.toCharArray()){
			Map<Character, TrieNode> childrenMap = n.getChildren();
			if(!childrenMap.containsKey(c)){
				return resultList;
			}else{
				n = childrenMap.get(c);
			}
		}
		//get all n's children values (prefix matches)
		getChildrenValues(n, resultList);
		
		//insert exact matches to the head of list
		resultList.addAll(0,n.getValues());
		if (resultList.size() < nResults){
			return resultList;
		}else
			return resultList.subList(0, nResults);
	}
}
