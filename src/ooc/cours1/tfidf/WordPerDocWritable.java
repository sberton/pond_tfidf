package ooc.cours1.tfidf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class WordPerDocWritable implements Writable  {

	private IntWritable occurence = new IntWritable();
	private IntWritable wordPerDoc = new IntWritable();

	@Override
	public String toString() {
		return occurence + "\t"+ wordPerDoc ;
	}

	public WordPerDocWritable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WordPerDocWritable(IntWritable occ, IntWritable wpd) {
		super();
		this.occurence=occ;
		this.wordPerDoc=wpd;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		occurence.readFields(input);
		wordPerDoc.readFields(input);
	}

	public IntWritable getOccurence() {
		return occurence;
	}

	public void setOccurence(IntWritable occurence) {
		this.occurence = occurence;
	}

	public IntWritable getWordPerDoc() {
		return wordPerDoc;
	}

	public void setWordPerDoc(IntWritable wordPerDoc) {
		this.wordPerDoc = wordPerDoc;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		occurence.write(output);
		wordPerDoc.write(output);

	}

}
