import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
public class HuffmanCode {
  
  public static void main(String args[]) {
    try{
     
     Thread t = Thread.currentThread(); 
     //File noncoded = new File("/home/ruth/Downloads/DeclatationIndependence.txt");
     System.out.println("Single Thread"); 
     String contents = new String(Files.readAllBytes(Paths.get("/home/ruth/Downloads/DeclatationIndependence.txt")));
     int initialSize = contents.length();
     System.out.println("Initial Size: " + initialSize);
     buildHuffman bh = new buildHuffman(contents, initialSize, false, 0, contents.length());  
     bh.run();
     bh.printStats(); 
     
     //
     System.out.println(" ");
     System.out.println("Multi Thread");  
     ThreadPoolExecutor x = (ThreadPoolExecutor) Executors.newFixedThreadPool(3); 
     int beginLimit = 0; 
     int nextLimit = contents.length()/4; 
     for(int i = 0; i < 3; i++) {
      String smallerContents = contents.substring(beginLimit, nextLimit);
      x.execute(new buildHuffman(smallerContents, initialSize, true, beginLimit, nextLimit)); 
      System.out.println("Started Thread");
      beginLimit = nextLimit; 
      nextLimit += nextLimit; 
      
     }
     try{
      t.sleep(100); 
     }catch(InterruptedException e) {
     
     }
     
     x.shutdown(); 
     
     bh.printStats(); 
     //
    }catch(IOException e) {
      System.out.println(e); 
    }
  }
 }
