package ooc.cours1.tfidf;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

// A compléter selon le problème
public class WordCountReducer extends Reducer<DocWordComparable,IntWritable,DocWordComparable,IntWritable> {
    // Écriture de la fonction reduce
	
	private IntWritable totalWordCount = new IntWritable();
	
    @Override
    protected void reduce(DocWordComparable key, Iterable<IntWritable> values, Context context) throws IOException,InterruptedException
    {
    	int sum = 0;
    	
    	for (IntWritable value : values){
    		sum += value.get();
    	}
    	
        totalWordCount.set(sum);;
        context.write(key, totalWordCount);
    }
}

