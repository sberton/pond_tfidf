package ooc.cours1.tfidf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TfidfReducer extends Reducer<DocWordComparable,WordPerDocWritable,DocWordComparable,DoubleWritable>{
	Map<DocWordComparable, WordPerDocWritable> tfidfMap = new HashMap<DocWordComparable, WordPerDocWritable>();
	DoubleWritable tfidf = new DoubleWritable();
	DocWordComparable wordDocname = new DocWordComparable();
	WordPerDocWritable wordcountWordperdoc = new WordPerDocWritable();
		
	protected void reduce(DocWordComparable cle, Iterable<WordPerDocWritable> valeurs, Context context) throws IOException, InterruptedException {
		tfidfMap = new HashMap<DocWordComparable, WordPerDocWritable>();
		int nbDocTotal = 2;
 		int nbDocWithWord = 0;
		for(WordPerDocWritable value : valeurs) {;
			tfidfMap.put(new DocWordComparable(new Text(cle.getDocId()),new Text(cle.getWord())), 
					new WordPerDocWritable(new IntWritable(value.getOccurence().get()),new IntWritable(value.getWordPerDoc().get())));
			nbDocWithWord++;
		}
			

		
		for(Map.Entry<DocWordComparable, WordPerDocWritable> entry : tfidfMap.entrySet()) {
			wordDocname.set(entry.getKey());
			double freqDoc = (double)entry.getValue().getOccurence().get();
			double nbTotalWord = (double)entry.getValue().getWordPerDoc().get();
			tfidf = new DoubleWritable(freqDoc / nbTotalWord * Math.log(nbDocTotal/nbDocWithWord));
			//tfidf = new DoubleWritable(nbDocWithWord);
			context.write(wordDocname, tfidf);
		}
	}
}
