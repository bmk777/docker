import java.util.TreeMap;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Code ideas and concepts were borrowed from https://www.geeksforgeeks.org/how-to-find-top-n-records-using-mapreduce/.
 * Modifications have been made regarding the word count assignment.
 * 
 * I wasnt able to get the 
 * 
 */
public class WordCount {

    private TreeMap < Long, String > tmap;

    public static class TokenizerMapper extends Mapper < Object, Text, Text, IntWritable > {

        //Iniallize treemap to sort the data
        public void setup(Context context) throws IOException,
        InterruptedException {
            tmap = new TreeMap < Long, String > ();
        }
        //Holding values for the text and nums
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        //Map function was altered slightly, I switched the split indicator from a tab key to space to
        //reflect the spaces in words on the file. I also store the word and its count as variables as well
        //"tmap.put" puts our token into the tree we made previously
        //Cleanup method explained below
        public void map(Object key, Text value, Context context) throws IOException,
        InterruptedException {
            String[] tokens = value.toString().split(" ");
            String word = tokens[0];
            long amount = Long.parseLong(tokens[1]);

            tmap.put(word, amount);

            if (tmap.size() > 5) {
                tmap.remove(tmap.firstKey());
            }
        }

        //Cleanup function is executed once after the entire map task is complete, 
        //because previously after every key parse we were writing to the hdfs file system
        //But since we want to process the entire document before writing, we include cleanup to
        //execute once.
        public void cleanupMap(Context context) throws IOException,
        InterruptedException {
            for (Map.Entry < Long, String > entry: tmap.entrySet()) {

                long count = entry.getKey();
                String name = entry.getValue();

                context.write(new Text(name), new LongWritable(count));
            }
        }

        //Portions of reducer class were borrowed from https://www.geeksforgeeks.org/how-to-find-top-n-records-using-mapreduce/
        //Class starts the same way as the map class, treemap is initialized and called in the set up function
        public static class IntSumReducer extends Reducer < Text, IntWritable, Text, IntWritable > {
          private IntWritable result = new IntWritable();
  
          private TreeMap < Long, String > tmap2;
  
          String name = key.toString();
  
          public void setup(Context context) throws IOException,
          InterruptedException {
              tmap2 = new TreeMap < Long, String > ();
          }
          
          //reduce function starts the same way as the original map reduce function, in a loop we scroll through values
          //And increment the value of that appeared word
          public void reduce(Text key, Iterable < IntWritable > values, Context context) throws IOException,
          InterruptedException {
              int sum = 0;
              String name = key.toString();
              for (IntWritable val: values) {
                  sum += val.get();
              }
              

              //after incrementing the values per word, we are putting the totals in the new tree
              //This will automatically justify the top 5 appearing values.
              tmap2.put(sum, name);
              if (tmap2.size() > 5) {
                  tmap2.remove(tmap2.firstKey());
              }
          }
          
          //Clean up method is needed for the same reason as the map cleanup method
          //If we were to context write after every key read, our tree wouldnt accurately reflect the 
          //top appearing terms
          public void cleanup(Context context) throws IOException,
          InterruptedException {
  
              for (Map.Entry < Long, String > entry: tmap2.entrySet()) {
  
                  long count = entry.getKey();
                  String name = entry.getValue();
                  context.write(new LongWritable(count), new Text(name));
              }
          }
    }
        
      //Driver code borrowed from Hadoop's WordCount example
        public static void main(String[] args) throws Exception {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf, "word count");
            job.setJarByClass(WordCount.class);
            job.setMapperClass(TokenizerMapper.class);
            job.setCombinerClass(IntSumReducer.class);
            job.setReducerClass(IntSumReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        }

    }


}

  // //oRIGINAL
  // public class WordCount {
  //   private TreeMap<Long, String> tmap;
  //     public static class TokenizerMapper  extends Mapper<Object, Text, Text, IntWritable>{
            
  //   // public void setup(Context context) throws IOException, InterruptedException
  //     //  {
  //       //    tmap = new TreeMap<Long, String>();
  //      // }
  //       private final static IntWritable one = new IntWritable(1);
  //       private Text word = new Text();
  //       public void map(Object key, Text value, Context context
  //                       ) throws IOException, InterruptedException {
  //         StringTokenizer itr = new StringTokenizer(value.toString());
  //         while (itr.hasMoreTokens()) {
  //           word.set(itr.nextToken());
  //           context.write(word, one);
  //         }
  //       }
  //    }
  //   } 