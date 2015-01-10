***************************************************************SharedMemory***********************************************

Shared-Memory WordCount
----------------------
Explanation of Code

1.Files are splitted into chunks and distributed among threads so that they can execute their tasks concurrently and produce respective output files.
2.First the the file is read into a buffered array and the data is stored as a String so that word splitting can be performed. 
3.Words are splitted and stored into a String array. Next stored words are passed into a list and Hash Map is used to map key value pairs where key is the word and value is the number of occurrences of that word in that list.
4.After the word count the output data is stored in individual output files for each thread with unique names.
5.In the end all the output files are merged by using a merge function (a custom function created in the program) and saved as a log file (wordcount-java.txt).
6.Two constants are defined: NUM_THREADS AND FILE_CHUNKS. Vary number of threads and chunks by changing the values in this constant. Second constant defines the chunks into which the big dataset is required to be splitted.
For 10GB I splitted the bigdata into 320 chunks and tested my code by setting FILE_CHUNKS = 320

File Names(which files contains what):

1. jar-file-wordcount : jar file for word count including Classes but not the input file as it was too big. While running 
the program note that this big dataset is divided into small chunks and fed into the program (320 chunks are used).

2. SharedMemoryMultithreadingClassv1.java : Java word count source file


Commands to execute and compile code:

1. Compilation: 
> Creat class files for the java files:
javac SharedMemoryMultithreadingClassv1.java
> Run java file   
java SharedMemoryMultithreadingClassv1
> Create jar file out of the class files: 
jar -cf jar-file-wordcount.jar ManyThreads.class SharedMemoryMultithreadingClassv1.class
> Run jar file
java -cp jar-file-wordcount.jar SharedMemoryMultithreadingClassv1

Command to split files:

>Keep input file prefix as 'infile' as below while splitting big dataset file.

split -n l/(Chunks) --suffix-length=3 --numeric-suffixes=100 <filename> infile

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Sort on Shared-Memory
---------------------

Explanation of Code:

1.Sort logic implementation starts by creating 26 output files (as there are 26 alphabets in English).Each file is supposed to store words which start with the same letter/character as the one shown in the file name. For e.g.  word "Lemon" will be stored in "outputL" file. Then a file pointer array is created to point to the specific word files and to sort the data stored in these files.
2.The processing starts by reading the "Input" file. A "list" of words is created from the input file. Each word in the list is picked and the first character of that word is pulled out to identify the Output file name wherein this word will be sent for storage. For e.g.  word "Lemon" will be stored in "outputL" file. A proper mapping has been done between the characters and the file names using the ASCII codes.
3.After collecting all the words starting with ‘a’ alphabets in one file, ‘b’ in other file and so on, there are 26 files in place. These data in each of these files is sorted internally inside the file. Next step is to merge these sorted files (or bucket of words) in an output log file.  
4.Vary the number of threads by changing the value of the constant NUM_THREADS.

I have assumed that duplicates are not required to be removed as it's not mentioned anywhere in the question. But, I also tried the program by removing the duplicates and using below additional code snippet:
Using set for removing duplicates and then reading it into a list and applying collection sort. Observed better performance with this approach. 

Set<String> aSet = new HashSet<String>();
  for (String w : word) {
    aSet.add(w);
}
System.out.println("aSet is: " + aSet);
List<String> aList = new ArrayList<String>();  
   for (String w : aSet) {
     aList.add(w);
}
Collections.sort(bList);

Commands to execute and compile code:

1. compilation: 
> Create class files for the java files:
javac SharedMemorySort.java
> Run java file   
java SharedMemorySort
> Create jar file out of the class files: 
jar -cf jar-file-sort.jar SortThreads.class SharedMemorySort.class
> Run jar file
java -cp jar-file-sort.jar SharedMemorySort

Command to split files:
>Keep input file prefix as 'infile' as below while splitting big dataset file.

split -n l/(Chunks) --suffix-length=3 --numeric-suffixes=100 <filename> infile

File Names:

1. jar-file-sort : jar file for sort including Classes but not the input file as it was too big. While running 
the program note that this big dataset is divided into small chunks and fed into the program(320 chunks are used).
2. SharedMemorySort.java : Java sort source file


**********************************************************************************************************************************************

***************************************************************Hadoop*************************************************************************
Hadoop Word Count:
Compiling: 

1. Creat class files for the java files: 
> javac -classpath hadoop/hadoop-core-*.jar -d . *.java
2. Creat jar file out of the class files: 
> jar -cvf MyWordCount.jar -C . . 

Running:
> hadoop jar MyWordCount.jar WordCountDriver 

Notes: 
1. The driver class name must be given as the first arguemnt to the jar file when running. It is WordCountDriver
2. Input directory name in HDFS is "input". Put the input files inside this folder. 
3. Output directory name is "output". 
4. Edit WordCountDriver.java to modify the configurations such as the input/output directory, etc. 

Hadoop Sort:
Compiling: 

1. Creat class files for the java files: 
> javac -classpath hadoop/hadoop-core-*.jar -d . *.java 
2. Creat jar file out of the class files: 
> jar -cvf MySort.jar -C . . 

Running:
> hadoop jar MySort.jar MySortDriver 

Notes: 

1. The driver class name must be given as the first arguemnt to the jar file when running. It is MySortDriver
2. Input directory name in HDFS is "input". Put the input files inside this folder. 
3. Output directory name is "output".
4. Edit WordCountDriver.java to modify the configurations such as the input/output directory, etc. 
******************************************************************************************************************************************************
***************************************************************Swift**********************************************************************************
Swift Word Count and Sorting:

This folder contains:

-> wordcount.swift - The swift program  to do word on the 10GB dataset.
-> wordcount.sh - The shell script program, which will be incorporated with swift to perform wordcount.
-> configs - The config file which contains the information about how many workers to launch, wat type  of instance, image files and location
-> wordcount-swift.txt - Output of the word count on 10gb datat set.
-> Wordcount.log - The log file of the wordcoutn swift run.
-> swift.conf - Edit this file, in order to determine whether you want to run it locally or in a cluster.
   - "Local" - "Cloud static" respectively
-> sort.swift - Swift program to perform sorting on a 10gb dataset. It does not incorporate any application files to drive it. Sorting here is done by swift keywords.
-> sorting.log -  The log file of the sort swift run.
-> sort1MB-swift - The first 1mb output of the sorted 10gb data.

Commands for execution:

-> After setting up the config file, run the "source setup.sh"(inside the ecc2 folder) to launch the cluster with the head node and requested number of  workers.
-> "connect headnode" to connect to the head node.
-> Inside the swift-cloud-tutorial folder in the head node, create a folder of any name.
-> Place the wordcount.swift/sort.swift inside the folder.
-> Create a directory name "input" inside the newly created fodler.
-> Place the 10gb data set inside this folder. 
-> The 10gb can be broken into chunks with the following command split -b 40m filename segment
-> Place the swift.conf file inside the newly created folder, edit the argument type to "cloud-static","local" according to your needs.
-> Place the shell script inside the newly created folder.
-> Now go to the swift-cloud-tutorial folder and run the  setup file.
-> Now run the swift program inside the newly created folder, by the command swift filename. Now the program starts.
-> for  changinf the permission of a file - sudo chmod 777 filename.
-> for  a newly added volume, run, "chown user path"
-> Once u got the output from swift, run the merge .sh file to merge the output in different files.
-> change the prefix and suffix inside the merge program, according your output files. It is the first line of  the program.
-> The merge.sh includes a command line argument, which the output file location

THE OUTPUT OF THE 10GB DATASET, WORDCOUNT - 
The file was too big to upload in the black board.
When open the log files, .swift files, .conf, .sh files it might disorted. When I open in a linux machine it perfect
**********************************************************************************************************************************************************************







 
                         

  
