type file;

// The app "cat" takes an input file and cats the output to the specified output
// file
app (file out) wordcount (file input)
{
 sort "-n" @input stdout=@out;
}

// A filesystem mapper is used to map all files in a directory "inputs" with the
// suffix "txt" to an array of type files
file inputs[]    <filesys_mapper; location="inputs", prefix="god">;

//file script <"wordcount.sh">;
// Iterate over all files in parallel
foreach input,i in inputs
{
  file out <single_file_mapper; file=strcat("output/foo_",i,".out")>;
 //file out <"sim.out">;
  out = wordcount (input);
}

