import java.util.*;
import java.io.*;


class SortThreads extends Thread {

	private Thread th;
	private String thName;
	private int id;
    private String fname;
	private BufferedWriter[] bufferWriter;

	SortThreads(String name, int num, String fileName,BufferedWriter[] bufWriter) {
		thName = name;
		id = num;
		bufferWriter = bufWriter;
                fname = fileName;
    }
	
	/* Thread run function */	
	public void run() {
			    
		
		byte[] buf = new byte[(int) new File(fname  + id).length()]; // read input file
		BufferedInputStream buffer = null;
		
		try {
					
			  System.out.println(thName + " has started reading file : " + fname + id);
              buffer = new BufferedInputStream(new FileInputStream(fname  + id));
              buffer.read(buf);                   
			  buffer.close();
			  String str =  new String(buf); // Data read from the the file is stored as a String
			  str = str.toLowerCase();   
		      String regex = "[^a-zA-Z]";  // only keep alphabets and remove integers and special characters
		      str = str.replaceAll(regex, " ").replaceAll("\\s+", " ").trim() ; // Trim leading and trailing spaces
		      String[] word = str.split(" "); // word split is performed at a space
		      
		      /* spliced words are stored in a list */
		      List<String> aList = new ArrayList<String>();  
		      for (int i = 0; i < word.length; i++) { 
		        aList.add(word[i]);
		        
		      } 
		      Collections.sort(aList); // sort the list
		      
		      
		      /* Iterate through each word in a list */
		      for (String s : aList) { 
			    char firstLetter = s.charAt(0); // pull out the first character from each word
		    	bufferWriter[((int)firstLetter - 97)].write(s +  System.getProperty("line.separator")); //use file pointers as per the first  letter in a word and write data in a file.
		      }
		      }
		
		      catch (IOException e) {
		      System.out.println("wordsfile not found");
	          e.printStackTrace();
		  }

	}
	
	
	
	public void start () {
		//System.out.println(thName + " started");
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


public final class SharedMemorySort {
        
        public static final int NUM_THREADS = 1; // Vary number of working threads
        public static final int FILE_CHUNKS = 320; // Vary number of file chunks
 
	    public static void main(String[] args) {
                
                
        long lStartTime = new Date().getTime(); // start time
		// make a call to partFile function
		String fname = ("infile"); int count = 99; 
		try{
		for( int j =0; j < (FILE_CHUNKS/NUM_THREADS); j++) {
		int num;          
		
			BufferedWriter[] bufferWriter = new BufferedWriter[26]; // define an array to store file pointers
		      int fPointers = 0;
		      for(int x = 97 ; x < 123; x++) {
		      char s = (char)(x);
		      File logFile = new File("output" + s); // create output files equal to the total number of alphabets 
	    	  if(!logFile.exists()){
	            logFile.createNewFile();   
	    	  }
	    	  FileWriter fWritter = new FileWriter(logFile,true);  // store the last file pointer position and use file writer to append data
	    	  BufferedWriter  bWriter = new BufferedWriter(fWritter);
		      
	    	  bufferWriter[fPointers] = new BufferedWriter(bWriter);
	    	  fPointers++;
		      }	
			
		  for (num = 1; num < NUM_THREADS + 1; num++) {
                  count++;
		  SortThreads Th = new SortThreads(("Thread-" + num) ,count, fname, bufferWriter);
		  Th.start();
		
		  }}
		
		long lEndTime = new Date().getTime(); // end time
		double elapsedTime = (double)(lEndTime - lStartTime)/1000;
		System.out.println("Time taken by " + (NUM_THREADS) + " Threads to execute WordCount application is " + (elapsedTime)/60 + "minutes");		
		MergeOutputFiles(); 
		}

		catch(IOException e) {
		       System.out.println("Error while splitting files");
		       e.printStackTrace();
	 }
		
	 }
	
	
	
public static void MergeOutputFiles() {
		            
			        String THREADS_FILE_NAME = "output";
			        File ofile = new File("sort1MB-sharedmemory.txt"); // Buckets outputs are combined into this file
			        int bytesRead = 0;
			        List<File> fList = new ArrayList<File>();
			        
			        for(int x = 97 ; x < 123; x++) {
				     	 
					   
					     char s = (char)(x);
					     
					      
			          fList.add(new File("output" + s));
			        }
			     
			        try {
			        	FileOutputStream fostream = new FileOutputStream(ofile,true);
			        	
			        	/* Iterate through the output files list to be merged*/
			        	
			            for (File file : fList) {
			            	FileInputStream fistream = new FileInputStream(file);
			                byte[] fileBytes = new byte[(int) file.length()];
			                bytesRead = fistream.read(fileBytes, 0,(int)  file.length());
			                assert(bytesRead == fileBytes.length);
			                assert(bytesRead == (int) file.length());
			                fostream.write(fileBytes);
			                fostream.flush();
			                fileBytes = null;
			                fistream.close();
			                fistream = null;
			             }

			            fostream.close();
			            fostream = null;
			          
			        }
			        catch (Exception exception){
			            exception.printStackTrace();
			        }
		
			    }
			

	 }
	
 
