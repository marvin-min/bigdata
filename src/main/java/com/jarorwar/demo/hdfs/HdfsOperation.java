package com.jarorwar.demo.hdfs;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Copyright by 中旌影视 (c) 2018 Inc.
 *
 * @描述
 * @Author Administrator
 * @创建时间 2018/7/23/023 16:34
 */
public class HdfsOperation {

    private FileSystem fs;
    private Configuration conf;

    public HdfsOperation() throws IOException {
        conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.set("fs.defaultFS", "hdfs://hadoop:9000");
        fs = FileSystem.get(conf);
    }

    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "marvin");
        try {
            HdfsOperation hdfsOperation = new HdfsOperation();
//            hdfsOperation.uploadFile();
//            hdfsOperation.readFile();
//            hdfsOperation.writeContentToHdfs();
//            hdfsOperation.close();
            hdfsOperation.stats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stats() throws IOException {
        ContentSummary summary = fs.getContentSummary(new Path("/"));
        System.out.println(summary.getDirectoryCount()+ " -- " +summary.getFileAndDirectoryCount());

    }

    public void uploadFile() throws IOException {
        fs.copyFromLocalFile(new Path("C:\\Users\\Administrator\\Desktop\\demo-content.log"), new Path("/demo.txt"));
    }

    public String readFile() throws Exception {
        StringBuffer sb = new StringBuffer();
        String filePath = "hdfs://hadoop:9000/demo.txt";
        Path path = new Path(filePath);
        FileSystem fs = FileSystem.get(new URI(filePath), conf);

        System.out.println("READING ============================");
        FSDataInputStream is = fs.open(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        boolean flag = true;

        while (flag) {
            String content = br.readLine();

            if (StringUtils.isBlank(content)) {
                flag = false;
            } else {
                sb.append(content);
                System.out.println(content);
            }
        }
        br.close();
        return sb.toString();
    }

    public void writeContentToHdfs() throws IOException {
        System.out.println("WRITING ============================");
        byte[] buff = "您好啊~这是中文啊~\n".getBytes();
        FSDataOutputStream os = fs.create(new Path("/123.txt"));
        os.write(buff, 0, buff.length);
        os.close();
    }

    public void close() throws IOException {
        fs.close();
    }
}
