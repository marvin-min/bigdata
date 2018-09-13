package com.jarorwar.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * Copyright by 中旌影视 (c) 2018 Inc.
 *
 * @描述
 * @Author Administrator
 * @创建时间 2018/7/24/024 10:44
 */
public class WordCount {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //创建Configuration
        Configuration configuration = new Configuration();
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        configuration.set("fs.defaultFS", "hdfs://hadoop:9000");
        System.setProperty("hadoop.home.dir", "E:/tools/hadoop-2.7.6");
        //创建Job
        Job job = Job.getInstance(configuration, "wordcount");

        //设置job的处理类
        job.setJarByClass(WordCount.class);

        //设置作业处理的输入路径
        FileInputFormat.setInputPaths(job, new Path("/demo.txt"));

        //设置map相关参数
        job.setMapperClass(WordMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //设置reduce相关参数
        job.setReducerClass(WordReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //设置作业处理的输出路径
        FileOutputFormat.setOutputPath(job, new Path("/output"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}


class WordMapper extends Mapper<Text, LongWritable, Text, LongWritable> {

    LongWritable one = new LongWritable(1);

    @Override
    protected void map(Text key, LongWritable value, Context context) throws IOException, InterruptedException {
        // 接收到的每一行数据
        String line = key.toString();

        //按照指定分隔符进行拆分
        String[] words = line.split(" ");

        for (String word : words) {
            // 通过上下文把map的处理结果输出
            context.write(new Text(word), one);
        }
    }
}

class WordReduce extends Reducer<Text, LongWritable, Text, LongWritable> {


    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long sum = 0;

        for (LongWritable value : values) {
            sum += value.get();
        }

        context.write(key, new LongWritable(sum));
    }
}