package ooc.cours1.tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class TfidfPartitionner extends Partitioner<DocWordComparable, WordPerDocWritable>{

	
	
	@Override
	public int getPartition(DocWordComparable docWordKey, WordPerDocWritable val, int i) {
		String docPartition = docWordKey.getWord().toString();
		return docPartition.hashCode() % i;
	}

}
