# mapreduce 配置

1. 配置mapred-site.xml
	
	<property>
	    <name>mapreduce.framework.name</name>
	    <value>yarn</value>
	 </property>
	 
2.配置yarn-site.xml

	//指定resourceManager 为node1
	<property>
	  <name>yarn.resourcemanager.hostname</name>
	  <value>node1</value>
	</property>
	<property>
	  <name>yarn.nodemanager.aux-services</name>
	  <value>mapreduce_shuffle</value>
	</property>
	<property>
	  <name>yarn.nodemanager.aux-services.mapreduce_shuffle.class</name>
	  <value>org.apache.hadoop.mapred.ShuffleHandler</value>
	</property>
	
3.启动
	
	先停掉处zookeeper以外的所有服务 再重启
	./stop-all.sh
	
	./start-all.sh
	
4.查看启动结果

	node1上会有resourceManager进程
	每个dataNode上会有NodeManager进程
	