package ooc.cours1.tfidf;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class WordPerDocMapper extends Mapper<LongWritable, Text, DocWordComparable, IntWritable>{
	
	private IntWritable occurence = new IntWritable();
    private DocWordComparable docWordKey = new DocWordComparable();
	
	@Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		//A remplacer par un stringtokenizer
		String[] tokens = value.toString().split("\t");
		docWordKey.setWord(new Text(tokens[1]));
		docWordKey.setDocId(new Text (tokens[0]));
		int occ = Integer.parseInt(tokens[2]);
		occurence.set(occ);
		context.write(docWordKey, occurence);
	}
		
}
