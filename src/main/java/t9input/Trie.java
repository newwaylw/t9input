package t9input;

import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Trie data structure for efficient search.
 * @author wei
 *
 */
public class Trie {

	private TrieNode root;
	
	public Trie() {
		root = new TrieNode(' ');
	}
	
	/**
	 * insert a word in its T9 numeral (e.g. "234") and the word ID to this trie 
	 * @param key T9 numeral input of a word
	 * @param wID the integer id of this word
	 */
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
	 * Recursively (Depth-first search) append values of all children node. 
	 * we are using a Map for a node's possible children, this means
	 * when getting all children nodes the order is not guaranteed
	 * @param n
	 * @param resultList
	 */
	private void getChildrenValues(TrieNode n, List<Integer> resultList){
		Map<Character, TrieNode> childrenMap = n.getChildren();
		if (!childrenMap.isEmpty()){
			Iterator<TrieNode> it = childrenMap.values().iterator();
			while(it.hasNext()){
				getChildrenValues(it.next(), resultList);
			}
		}
			//no children, reached terminal node 
			//so record all matching values
			resultList.addAll(n.getValues());
	}
	
	/**
	 * given an input (t9 style encoding), return all nodes who have
	 * the input as match or prefix match. Return a sorted (by descending frequency order)
	 * word ID list. This means exact matches may be ranked behind prefix matches.
	 * For example, input "866" and in a corpus where the frequency of "tomorrow"
	 * is larger than "too", it will display prefix match "tomorrow" before exact match "too", 
	 * which is not an ideal T9 input experience. 
	 * 
	 * @param input - t9 numeral input (e.g. "2234")
	 * @param nResults - number of matches returned
	 * @return
	 * 
	 * TODO we don't need to find all matches then sort, we can stop the search when 
	 * nResults are reached, to increase performance. 
	 * Perhaps use a list instead of a map so the order is preserved.
	 */
	public List<Integer> getResults(String input, int nResults){
		List<Integer> resultList = new ArrayList<Integer>();
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
		
		//sort by word ID. the lower word ID of a word, the higher frequency it has
		Collections.sort(resultList);
		if (resultList.size() < nResults){
			return resultList;
		}else
			return resultList.subList(0, nResults);
	}
}
