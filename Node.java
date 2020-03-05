public class Node {
  
  char letter; 
  int freq; 
  Node left; 
  Node right; 
  
  public Node(char letter, int frequency) {
    this.letter = letter; 
    this.freq = frequency; 
  }
  
  public Node(char letter, int frequency, Node left, Node right) {
    this.letter = letter; 
    this.freq = frequency; 
    this.left = left; 
    this.right = right; 
  }
}
