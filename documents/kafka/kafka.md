2.创建Topic

/opt/cloudera/parcels/KAFKA-2.0.1-1.2.0.1.p0.5/lib/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

/opt/cloudera/parcels/KAFKA-2.0.1-1.2.0.1.p0.5/lib/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic page_visits

 

3.查看命令

/opt/cloudera/parcels/KAFKA-2.0.1-1.2.0.1.p0.5/lib/kafka/bin/kafka-topics.sh --list --zookeeper localhost:2181

4.  发送消息

/opt/cloudera/parcels/KAFKA-2.0.1-1.2.0.1.p0.5/lib/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic page_visits

5.  消费消息

/opt/cloudera/parcels/KAFKA-2.0.1-1.2.0.1.p0.5/lib/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic page_visits --from-beginning

6.  多 Broker 方式

 

/opt/cloudera/parcels/KAFKA-2.0.1-1.2.0.1.p0.5/lib/kafka/bin/kafka-server-start.sh config/server-1.properties &

bin/kafka-server-start.sh config/server-2.properties &

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic visits

bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic visits

bin/kafka-console-producer.sh --broker-list localhost:9092 --topic visits

7.  停止服务

pkill -9 -f config/server.properties

 
8.删除无用的topic

bin/kafka-run-class.sh kafka.admin.DeleteTopicCommand --topic visits --zookeeper sjxt-hd02:2181,sjxt-hd03:2181,sjxt-hd04:2181

 

beta in 0.8.1

bin/kafka-topics.sh --zookeeper zk_host:port --delete --topic my_topic_name