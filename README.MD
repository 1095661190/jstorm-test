
因为storm-ui也需要storm.yaml的配置，默认使用~/.jstorm/storm.yaml


提交的命令为
jstorm jar test.jar com.my.TestTopology xxx，其中xxx为自定义topology的名称
还有一些常用的命令如下：

jstorm list
jstorm kill xxx   --kill topology，kill后再jstorm-ui中会看到该topology暂时处于kill状态，一小段时间后，该topology就消失了
jstorm restart xxx ---重启topology



http://node1:8080/jstorm-ui-2.1.1/


启动Nimbus：进入JStorm目录，执行
python2 bin/jstorm  nimbus
启动Supervisor：进入JStorm目录，执行
python2 bin/jstorm  supervisor



python2 bin/jstorm jar jstorm_demo-0.0.1-SNAPSHOT.jar com.example.RemoteTopology  test

