package ooc.cours1.tfidf;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.Set;
import java.io.File; 
import java.util.Scanner; 

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.fs.Path;

public class WordCountMapper extends Mapper<LongWritable, Text, DocWordComparable, IntWritable> {
	
	
	private final static IntWritable one = new IntWritable(1);
    private DocWordComparable docWordKey = new DocWordComparable();
    Path[] cachedFiles;
    String filename = "";
    Set<String> stopwordsList = new HashSet<String>();
    
    protected void setup(Context context) throws IOException, InterruptedException {
    	cachedFiles=context.getLocalCacheFiles();
    	File file = new File(cachedFiles[0].toString()); 
    	Scanner sc = new Scanner(file); 
    	while (sc.hasNextLine()) {
    		stopwordsList.add(sc.nextLine()); 
    	} 
    	sc.close();
    }
    
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().replaceAll("[^\\p{Alpha}]+", " ");
		String token;
		
		/* Récupération du nom de fichier via le context*/
		filename = ((FileSplit) context.getInputSplit()).getPath().getName();
        StringTokenizer tokenizer = new StringTokenizer(line);
        while (tokenizer.hasMoreTokens()) {
			token=tokenizer.nextToken().toLowerCase();
			if (token.length() > 2 && !stopwordsList.contains(token)){
				docWordKey.setWord(new Text(token));
				docWordKey.setDocId(new Text (filename));
				context.write(docWordKey, one);
			}
        }
    }
}

