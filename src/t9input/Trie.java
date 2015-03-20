package t9input;

import java.util.Collections;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Trie {

	private TrieNode root;
	
	public Trie() {
		root = new TrieNode('-');
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
	
	public void getChildrenValues(TrieNode n, List<Integer> resultList){
		Map<Character, TrieNode> childrenMap = n.getChildren();
		if (!childrenMap.isEmpty()){
			Iterator<TrieNode> it = childrenMap.values().iterator();
			while(it.hasNext()){
				getChildrenValues(it.next(), resultList);
			}
		}//else{
			//no children, reached terminal node or no such node
			for(Integer i : n.getValues())
			    System.out.println("add prefix result wID:"+i);
			resultList.addAll(n.getValues());
		//}
	}
	
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
		//append exact matches first
		//resultList.addAll(n.getValues());
		//get all n's children values (prefix matches)
		getChildrenValues(n, resultList);
		Collections.sort(resultList);
		if (resultList.size() < nResults){
			return resultList;
		}else
			return resultList.subList(0, nResults);
	}
}
