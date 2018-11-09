package ooc.cours1.tfidf;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WordPerDocGroup extends WritableComparator{
	
	protected WordPerDocGroup() {
        super(DocWordComparable.class, true);
    }
	
	@Override
	public int compare(WritableComparable  a, WritableComparable  b) {

		DocWordComparable a_key = (DocWordComparable) a;
		DocWordComparable b_key = (DocWordComparable) b;

        int result = a_key.getDocId().compareTo(b_key.getDocId());

        return result;
    }
}
