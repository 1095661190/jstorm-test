package org.example;


import backtype.storm.task.IBolt;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class CountBolt  extends BaseRichBolt {


    OutputCollector collector;

    // 一定要 生成 一个 serialVersionUID，因为这些class 都是要经过序列化的
//    private static final long serialVersionUID = 8740926838799779884L;
    Map<String,Integer> map=new HashMap<>();
    private FileWriter writer;
    public CountBolt() {
        System.out.println("CountBolt:**********************************");
    }
    /**
     * prepare 在这里仅仅是启动了一个文件写的定时线程，每2秒将结果写到文件中，并清空map.
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector=collector;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        try {
            //以文件追加的方式打开文件
            writer = new FileWriter("/root/soft/jstorm-2.1.1/log.txt",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开启定时线程
        pool.scheduleAtFixedRate(()->{
            try {
//                writer.write("\r\n");
//                writer.write("***************************************");

                for(Entry<String, Integer> entry:map.entrySet()) {
                    writer.write(entry.getKey()+" : "+entry.getValue());
                    writer.write("\r\n");
                    System.out.println(entry.getKey()+" : "+entry.getValue());
                }
                writer.flush();
                map.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 30L, 500L, TimeUnit.MILLISECONDS);
    }

    /**
     * 收到 spout 发送来的 Word 进行统计
     */
    @Override
    public void execute(Tuple input) {
        System.out.println("**********execute(Tuple input)");
        String word=input.getString(0);
        if(map.get(word)==null) {
            map.put(word, 1);
        }else {
            map.put(word, map.get(word)+1);
        }
        this.collector.ack(input);

    }
    /**
     * Topology 被 shutdown时会被触发的动作，我们可以用来做一些清理工作
     */
    @Override
    public void cleanup() {
        System.out.println("*******************public void cleanup()");
        for(Entry<String, Integer> entry:map.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        map.clear();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }


}
