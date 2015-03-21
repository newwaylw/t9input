package t9input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * T9 style input
 * @author wei
 *
 */

public class T9TextInput {
  public static Map<Character, Integer> t9Map;
  private List<String> wordList;
  private Trie t9Trie;
  
  public T9TextInput(){
	  //initialise T9 character map
	  t9Map = new HashMap<Character, Integer>();
	  t9Map.put('a', 2);
	  t9Map.put('b', 2);
	  t9Map.put('c', 2);
	  t9Map.put('d', 3);
	  t9Map.put('e', 3);
	  t9Map.put('f', 3);
	  t9Map.put('g', 4);
	  t9Map.put('h', 4);
	  t9Map.put('i', 4);
	  t9Map.put('j', 5);
	  t9Map.put('k', 5);
	  t9Map.put('l', 5);
	  t9Map.put('m', 6);
	  t9Map.put('n', 6);
	  t9Map.put('o', 6);
	  t9Map.put('p', 7);
	  t9Map.put('q', 7);
	  t9Map.put('r', 7);
	  t9Map.put('s', 7);
	  t9Map.put('t', 8);
	  t9Map.put('u', 8);
	  t9Map.put('v', 8);
	  t9Map.put('w', 9);
	  t9Map.put('x', 9);
	  t9Map.put('y', 9);
	  t9Map.put('z', 9);	  
	  this.wordList = new ArrayList<String>();
	  t9Trie = new Trie();
  }
  
  // return the T9 style numeral encoding for a word
  public String getT9Encoding(String word) {
	  StringBuffer buf = new StringBuffer();
	  for(char c: word.toCharArray()){
		  char lc = Character.toLowerCase(c);
		  if(t9Map.containsKey(lc)){
			  buf.append(t9Map.get(lc));
		  }
	  }
	  return buf.toString();
  }
  
  /**
   * build T9 trie from file (a sorted unigram file)
   * @param fileName
   */
  public void buildTrie(String fileName) {
		Scanner sc;
		try {
			sc = new Scanner(new File(fileName));
		
			int wID = 0;
			while (sc.hasNextLine()){
				String word = sc.nextLine().trim();
				this.wordList.add(word);
				String t9EncodingStr = this.getT9Encoding(word);
				t9Trie.insert(t9EncodingStr, wID++);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
  }
  
  public List<String> getWordList(String t9encoding, int nResults){
	  List<Integer> wIDList = t9Trie.getResults(t9encoding, nResults);
	  List<String> wList = new ArrayList<String>();
	  for(Integer i : wIDList){
		  String word = this.wordList.get(i);
		  wList.add(word);
	  }
	  return wList;
  }
  
  public static void main(String[] args){
	  T9TextInput textInput = new T9TextInput();
	  textInput.buildTrie("src/test/resources/unigram.txt");

	  try {
		  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		  
		  while(true){
			  System.out.print("Please enter T9 input: ");
			  String input = reader.readLine();
			  List<String> list = textInput.getWordList(input, 5);
			  if (list.size()==0){
				  System.out.println("No Suggestions");
			  }else{
				  for(String r: list){
					  System.out.print(r+";");
				  }
				  System.out.println();
			  }
		  }//while
	  } catch (IOException e) {
		  e.printStackTrace();
	  	}
  }
}
