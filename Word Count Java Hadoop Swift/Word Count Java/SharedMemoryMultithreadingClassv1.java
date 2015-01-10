import java.util.*;
import java.io.*;


class ManyThreads extends Thread {

  private Thread th;
  private String thName;
  private int id;
  private String fname;

ManyThreads(String name, int count, String fileName) {
  thName = name;
  id = count;
  fname = fileName;
}
		
public void run() {
			    
  byte[] buf = new byte[(int) new File(fname  + id).length()]; 
  BufferedInputStream buffer = null;
		
    try {
					
           System.out.println(thName + " has started reading file : " + fname + id);
           buffer = new BufferedInputStream(new FileInputStream(fname  + id));
           buffer.read(buf);
		   buffer.close();
		   String string =  new String(buf);
		   HashMap<String, Integer> map = new HashMap<String, Integer>();
		   String str=string;
		   str = str.replaceAll("[^\\w\\s\\-\\>]","").replaceAll("\\s+", " ").trim(); 
		   String[] word = str.split(" ");     
		     for (int i = 0; i < word.length; i++) { 
       
		        if (map.containsKey(word[i])) { 
		          map.put(word[i], map.get(word[i]) + 1);
		        }

		        else { 
		          map.put(word[i], 1);
		        } 
		      } 
		      
		      File logFile = new File(fname + "output." + id); 
		      FileOutputStream fileos = new FileOutputStream(logFile);
		      PrintWriter pWriter = new PrintWriter(fileos);	
			       
			    for (String s : map.keySet()) { 
				    pWriter.write(s + ":" + map.get(s)  +  System.getProperty("line.separator"));
			
			     }
			    
			  pWriter.close(); // close the file
		
			}
		
		catch (IOException e) {
		  System.out.println("wordsfile not found");
	      e.printStackTrace();
		  }

	}
	
	
	
	public void start () {		
	  try{
	      
		    if(th == null) { 
			th = new Thread (this, thName);	
			th.start();
            th.join();
		 }
	 }
		
	 catch (Exception e) {
		 	
			System.out.println("Error while joining threads");
			e.printStackTrace();
			
		}
	}
} 


public final class SharedMemoryMultithreadingClassv1 {

        
        public static final int NUM_THREADS = 1; // Vary number of working threads
        public static final int FILE_CHUNKS = 320; // Vary number of file chunks
  
	    public static void main(String[] args) {
          
        long lStartTime = new Date().getTime(); // start time
		String fname = ("infile"); int count = 99; 
		for( int j =0; j < (FILE_CHUNKS/NUM_THREADS); j++) {
		  int num;
           
		 //Threads initialization
	    for (num = 1; num < NUM_THREADS + 1; num++) {
          count++;
          ManyThreads Th = new ManyThreads(("Thread-" + num) ,count,  fname);
          Th.start();
        }}

		
		long lEndTime = new Date().getTime(); // end time
		double elapsedTime = (double) ((lEndTime - lStartTime)/1000); //converting time to seconds.
		System.out.println("Time taken by " + (NUM_THREADS) + " Threads to execute WordCount application is " + (elapsedTime)/60 + " minutes");
	        
      		  MergeOutputFiles(fname);
                
           
	
	 }
	
	
public static void MergeOutputFiles(String fileName) {
        
  String fname = fileName;
  String THREADS_FILE_NAME = (fname + "output.");
  File ofile = new File(THREADS_FILE_NAME + "updated"); // Threads outputs are combined into this file
  int bRead = 0; 
  List<File> fList = new ArrayList<File>();
  for(int i = 100; i < (FILE_CHUNKS + 100); i++) {
    fList.add(new File(THREADS_FILE_NAME + i));
   }
         

       
  try {
        	
         FileOutputStream fostream = new FileOutputStream(ofile,true);
         for (File file : fList) {
         FileInputStream fistream = new FileInputStream(file);
         byte[] fBytes = new byte[(int) file.length()];
         bRead = fistream.read(fBytes, 0,(int)  file.length());
         assert(bRead == fBytes.length);
         assert(bRead == (int) file.length());
         fostream.write(fBytes);
         fostream.flush();
         fBytes = null;
         fistream.close();
         fistream = null;
                
          /* After combining threads, output of each thread is merged as per the keys*/
  		      
  		  
         BufferedReader isreader =  new BufferedReader(new FileReader(ofile));
         HashMap<String, Integer> map = new HashMap<String,Integer>();
         File logFile = new File("wordcount-java.txt"); 
	     FileOutputStream fileos = new FileOutputStream(logFile);
	  	 PrintWriter jWriter = new PrintWriter(fileos); 
	  	 String pLine = null;
	  				         
         while ((pLine = isreader.readLine()) != null) {		                	
           String[] kvPair  = pLine.split(":"); 
           String key = kvPair[0];
           int value = Integer.parseInt(kvPair[1]);
                  
           if(map.containsKey(key)) {
             value +=  map.get(key);
           }
                    
           map.put(key,value);
                  
       	  }
               
          /* List all map values */
  		  ArrayList<Integer> aList = new ArrayList<Integer>(); 
  	          aList.addAll(map.values());
  		   for (String s : map.keySet()) { 
  		  
  		       jWriter.write(" { " + s + " , " + map.get(s) + " } " +  System.getProperty("line.separator"));
  			 
  		    }
  			    
  		       
  		   jWriter.close(); // close the file
  		   isreader.close();
           }
                
            
            fostream.close();
            fostream = null;
          
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
      }
    
}
	
 
