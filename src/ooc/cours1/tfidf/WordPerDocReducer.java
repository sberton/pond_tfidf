package ooc.cours1.tfidf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordPerDocReducer extends Reducer<DocWordComparable,IntWritable,DocWordComparable,WordPerDocWritable> {
	
Map<DocWordComparable, IntWritable> motsNboccurrenceMap = new HashMap<DocWordComparable, IntWritable>();
	
DocWordComparable wordDocname = new DocWordComparable();
WordPerDocWritable wordcountWordperdoc = new WordPerDocWritable();
	
	protected void reduce(DocWordComparable cle, Iterable<IntWritable> valeurs, Context context) throws IOException, InterruptedException {
		motsNboccurrenceMap = new HashMap<DocWordComparable, IntWritable>();
		int count = 0;
		
		// Calcul du nombre total de mots dans chaque document
		// Mise en mémoire de chaque mot et le nombre d'occurence
		for(IntWritable value : valeurs) {
			//System.out.println(cle);
			// Attention aux références ! Même référence, seul le contenu change
			motsNboccurrenceMap.put(new DocWordComparable(new Text(cle.getDocId()), new Text(cle.getWord())), new IntWritable(value.get()));
			count += value.get();
		}
		
		// Nombre total de mot dans un document
		wordcountWordperdoc.setWordPerDoc(new IntWritable(count));
		
		// Pour chaque mot en mémoire recréer la sortie sousforme <K,V> avec K : WordDocnameWritable (filename, word) et V : WordcountWordperdocWritable (wordcount, wordperdoc)
		for(Map.Entry<DocWordComparable, IntWritable> entry : motsNboccurrenceMap.entrySet()) {
			wordDocname.set(entry.getKey());
			
			wordcountWordperdoc.setOccurence(entry.getValue());
			context.write(wordDocname, wordcountWordperdoc);
		}

	}
}
