package t9input;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Trie {

	private TrieNode root;
	
	public Trie() {
		root = new TrieNode(' ');
	}
	
	public void insert(String key, Integer wID){
		TrieNode n = root;
		for(char c : key.toCharArray()){
			Map<Character, TrieNode> childrenMap = n.getChildren();
			if (!childrenMap.containsKey(c)){
				TrieNode cNode = new TrieNode(c);
				childrenMap.put(c, cNode);
				n = cNode;
			}else{
				n = childrenMap.get(c);
			}
		}
		n.addValue(wID);
	}
	
	/**
	 * we are using a Map for a node's possible children, this means
	 * when getting all children nodes the order is not guaranteed
	 * @param n
	 * @param resultList
	 */
	public void getChildrenValues(TrieNode n, List<Integer> resultList){
		Map<Character, TrieNode> childrenMap = n.getChildren();
		if (!childrenMap.isEmpty()){
			List<TrieNode> l = new ArrayList<TrieNode>(childrenMap.values());
			for(TrieNode nd : l){
				//add shorter words first, sort by descending probability (frequency)
				resultList.addAll(nd.getValues());
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
		//Collections.sort(resultList);
		if (resultList.size() < nResults){
			return resultList;
		}else
			return resultList.subList(0, nResults);
	}
}
