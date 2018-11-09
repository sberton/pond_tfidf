package ooc.cours1.tfidf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class DocWordComparable implements WritableComparable<DocWordComparable>{
	
	
	private Text docId = new Text();
	private Text word = new Text();
	
	public DocWordComparable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DocWordComparable(Text myDocId,Text myWord) {
		super();
		this.docId=myDocId;
		this.word=myWord;
	}
	
	
	@Override
	public void readFields(DataInput input) throws IOException {
		docId.readFields(input);
		word.readFields(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {
		docId.write(output);
		word.write(output);
		
	}

	public Text getDocId() {
		return docId;
	}

	public void setDocId(Text docId) {
		this.docId = docId;
	}

	public Text getWord() {
		return word;
	}

	public void setWord(Text word) {
		this.word = word;
	}
	
	public void set(DocWordComparable other) {
		this.docId = new Text(other.getDocId());
		this.word = new Text(other.getWord());
	}
	
	@Override
	public String toString(){
		return docId + "\t" + word;
	}
	
	@Override
	public int compareTo(DocWordComparable o) {
		int compare = this.getDocId().compareTo(o.getDocId());
		if (compare != 0) {
			return compare;
		}
		return this.getWord().compareTo(o.getWord());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docId == null) ? 0 : docId.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocWordComparable other = (DocWordComparable) obj;
		if (docId == null) {
			if (other.docId != null)
				return false;
		} else if (!docId.equals(other.docId))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

}
