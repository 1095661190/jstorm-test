package org.example;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;
import backtype.storm.tuple.Values;


public class WordSpout extends BaseRichSpout {


    // 一定要 生成 一个 serialVersionUID，因为这些class 都是要经过序列化的
//    private static final long serialVersionUID = -4515102038086645770L;

    private String[] strs = {"one", "two", "three", "four", "five", "six"};
    SpoutOutputCollector collector;

    public WordSpout() {
        super();
//        System.out.println("WordSpout()****************************");
        System.out.println("..............");
        System.out.println("test..............");
    }

    /**
     * 定义发射的字段类型，是第一个要是执行的方法。
     *
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    /**
     * 打开 stream 流资源，只会执行一次
     *
     * @param conf
     * @param context
     * @param collector
     */
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        System.out.println("*****************open(Map conf, TopologyContext context, SpoutOutputCollector collector)");
        this.collector = collector;
    }

    /**
     * 循环执行，向外发送 Tuple
     */
    @Override
    public void nextTuple() {
        int index = RandomUtils.nextInt(6);

        try {
            Thread.sleep(5);
        }catch (Exception e){
            e.printStackTrace();
        }
        collector.emit(new Values(strs[index]));



        System.out.println("spout ***************nextTuple() : " + strs[index]);
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void ack(Object msgId) {
        super.ack(msgId);
    }

    @Override
    public void fail(Object msgId) {
        super.fail(msgId);
    }



    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}
