package ooc.cours1.tfidf;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class TfidfMapper extends Mapper<LongWritable, Text, DocWordComparable, WordPerDocWritable>{
	private WordPerDocWritable outValue = new WordPerDocWritable();
    private DocWordComparable outKey = new DocWordComparable();
    
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		

    	StringTokenizer tokens = new StringTokenizer(value.toString(), "\t");
    	String filename = tokens.nextToken();
    	String word = tokens.nextToken();
    	int nbOcc = Integer.parseInt(tokens.nextToken());
    	int nbWordDoc = Integer.parseInt(tokens.nextToken());
    	
    	outKey.setDocId(new Text(filename));
    	outKey.setWord(new Text(word));
    	outValue.setOccurence(new IntWritable(nbOcc));
    	outValue.setWordPerDoc(new IntWritable(nbWordDoc));
		
		context.write(outKey, outValue);
	}
}
