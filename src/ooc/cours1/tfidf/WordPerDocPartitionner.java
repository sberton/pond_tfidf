package ooc.cours1.tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class WordPerDocPartitionner extends Partitioner<DocWordComparable, IntWritable>{

	@Override
	public int getPartition(DocWordComparable docWordKey, IntWritable val, int i) {
		String docIdPartition = docWordKey.getDocId().toString();
		return docIdPartition.hashCode() % i;
	}
	
}
