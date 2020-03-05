import java.util.HashMap; 
import java.util.Map;
import java.util.BitSet;  
import java.io.IOException;
import java.util.PriorityQueue; 

public class buildHuffman implements Runnable{
  
  String file; 
  BitSet huffmanBitSet;
  int initialSize; 
  boolean multi; 
  int begin; 
  int end; 
  double startEncode = 0; 
  double startFreq = 0; 
  double elapsedFreq = 0; 
  double elapsedEncode = 0; 
  
  public buildHuffman(String file, int initialSize, boolean multi, int begin, int end) {
    this.file = file;
    this.initialSize = initialSize; 
    this.multi = multi;
    this.begin = begin; 
    this.end = end;  
    huffmanBitSet = new BitSet(initialSize); 
  }
  
  public void encode(Node root, String str, Map<Character, String> huffmanCode) {
    if(root == null) {
      return; 
    }
    
    if(root.left == null  && root.right == null) {
      huffmanCode.put(root.letter, str); 
    }
    
    encode(root.left, str + "0", huffmanCode); 
    encode(root.right, str + "1", huffmanCode); 
  }
  
  public void run(){
    Map<Character, Integer> freq = new HashMap<>();
    startFreq = System.currentTimeMillis(); 
    
    for(int i = 0; i < file.length(); i++) {
      if(!freq.containsKey(file.charAt(i))) {
        freq.put(file.charAt(i), 0); 
      }
      freq.put(file.charAt(i), freq.get(file.charAt(i)) + 1);
    }
    elapsedFreq = System.currentTimeMillis() - startFreq; 
    
    PriorityQueue<Node> minHeap = new PriorityQueue<>((l,r) -> l.freq - r.freq); 
    for(Map.Entry<Character, Integer> entry: freq.entrySet()) {
      minHeap.add(new Node(entry.getKey(), entry.getValue())); 
    } 
    
    while(minHeap.size() != 1) {
      Node left = minHeap.poll(); 
      Node right = minHeap.poll();
      int sum = left.freq + right.freq;
      minHeap.add(new Node('\0', sum, left, right));   
    }
    
    Node root = minHeap.peek();
    startEncode = System.currentTimeMillis(); 
    HashMap<Character, String> huffmanCode = new HashMap<>(); 
    encode(root, "", huffmanCode);
    
    int counter = begin; 
    for(int i = 0; i < file.length(); i++) {
      String current = huffmanCode.get(file.charAt(i)); 
      for(int j = 0; j < current.length(); j++) {
        if(current.charAt(j) == '1') {
          huffmanBitSet.set(counter);
          counter++;    
        }
      }
    }
    elapsedEncode = System.currentTimeMillis() - startEncode;
    System.out.println("This thread finished"); 
  }
  
  public void printStats() {
    
    double finalSize = huffmanBitSet.toByteArray().length;  
    System.out.println("Encode Time Cost: " + elapsedEncode);
    System.out.println("Freq Time Cost: " + elapsedFreq);
    System.out.println("Compressed Size: " + finalSize);
    double percentage = (finalSize/initialSize)*100; 
    System.out.println("Percentage of Compression: " + percentage);  
  }
}
