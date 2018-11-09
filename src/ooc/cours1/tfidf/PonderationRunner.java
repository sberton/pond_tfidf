package ooc.cours1.tfidf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.filecache.DistributedCache;


public class PonderationRunner extends Configured implements Tool {

    public int run(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage: [input] [output] [stopWordFile]");
            System.exit(-1);
        }

        // Création d'un job en lui fournissant la configuration et une description textuelle de la tâche
        Configuration conf = getConf();
        DistributedCache.addCacheFile(new Path(args[2]).toUri(), conf);
        //Job jobStep1 = Job.getInstance(getConf());
       
        Job jobStep1 = new Job(conf, "wordcount");
        //jobStep1.setJobName("wordcount");

        // On précise les classes MyProgram, Map et Reduce
        jobStep1.setJarByClass(PonderationRunner.class);
        jobStep1.setMapperClass(WordCountMapper.class);
        jobStep1.setReducerClass(WordCountReducer.class);

        // Set the output Key, Value types for the Mapper
        jobStep1.setMapOutputKeyClass(DocWordComparable.class);
        jobStep1.setMapOutputValueClass(IntWritable.class);
        
        // Set the output Key, Value types for the Reducer
        jobStep1.setOutputKeyClass(DocWordComparable.class);
        jobStep1.setOutputValueClass(IntWritable.class);
        
        Path inputFilePath = new Path(args[0]);
        Path outputFilePath1 = new Path(args[1]+"_1");
        Path outputFilePath2 = new Path(args[1]+"_2");
        Path outputFilePath3 = new Path(args[1]+"_3");
        // Définition des fichiers d'entrée et de sorties (ici considérés comme des arguments à préciser lors de l'exécution)
        FileInputFormat.addInputPath(jobStep1, inputFilePath);
        FileOutputFormat.setOutputPath(jobStep1, outputFilePath1);

        //Suppression du fichier de sortie s'il existe déjà
        FileSystem fs = FileSystem.newInstance(getConf());
        if (fs.exists(outputFilePath1)) {
            fs.delete(outputFilePath1, true);
        }
        if (fs.exists(outputFilePath2)) {
            fs.delete(outputFilePath2, true);
        }
        if (fs.exists(outputFilePath3)) {
            fs.delete(outputFilePath3, true);
        }
        jobStep1.waitForCompletion(true) ;
        
        
        Job jobStep2 = new Job(conf, "wordperdoc");
        jobStep2.setJarByClass(PonderationRunner.class);
        jobStep2.setPartitionerClass(WordPerDocPartitionner.class);
        jobStep2.setGroupingComparatorClass(WordPerDocGroup.class);
        jobStep2.setMapperClass(WordPerDocMapper.class);  
        jobStep2.setReducerClass(WordPerDocReducer.class);
        
        // Set the output Key, Value types for the Mapper
        jobStep2.setMapOutputKeyClass(DocWordComparable.class);
        jobStep2.setMapOutputValueClass(IntWritable.class);
        
        // Set the output Key, Value types for the Reducer
        jobStep2.setOutputKeyClass(DocWordComparable.class);
        jobStep2.setOutputValueClass(WordPerDocWritable.class);
        
        
        FileInputFormat.addInputPath(jobStep2, outputFilePath1);
        FileOutputFormat.setOutputPath(jobStep2, outputFilePath2);
        jobStep2.waitForCompletion(true);
        
        Job jobStep3 = new Job(conf, "tfidf");
        jobStep3.setJarByClass(PonderationRunner.class);
        jobStep3.setPartitionerClass(TfidfPartitionner.class);
        jobStep3.setGroupingComparatorClass(TfidfGroup.class);
        jobStep3.setMapperClass(TfidfMapper.class);  
        jobStep3.setReducerClass(TfidfReducer.class);
        
        // Set the output Key, Value types for the Mapper
        jobStep3.setMapOutputKeyClass(DocWordComparable.class);
        jobStep3.setMapOutputValueClass(WordPerDocWritable.class);
        
        // Set the output Key, Value types for the Reducer
        jobStep3.setOutputKeyClass(DocWordComparable.class);
        jobStep3.setOutputValueClass(DoubleWritable.class);
        
        
        FileInputFormat.addInputPath(jobStep3, outputFilePath2);
        FileOutputFormat.setOutputPath(jobStep3, outputFilePath3);
        jobStep3.waitForCompletion(true);
        
        
        return 1;
    }

    public static void main(String[] args) throws Exception {
    	PonderationRunner ponderationRunner = new PonderationRunner();
        int res = ToolRunner.run(ponderationRunner, args);
        System.exit(res);
    }
}