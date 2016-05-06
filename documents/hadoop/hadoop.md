# 一、hadoop 的安装  以2.5.2为例
### 1. 服务器列表

node  | NN  | DN  |ZK  | ZKFC | JN  | RM | DM 
---   | --- | --- | ---| -----| --- | ---| ---
node1 | 1   |     | 1  |   1  |     | 1  |    
node2 | 1   | 1   | 1  |   1  |  1  |    | 1  
node3 |     | 1   | 1  |      |  1  |    | 1  
node4 |     | 1   |    |      |  1  |    | 1  
	
### 2. 安装hadoop [hadoop官网](http://hadoop.apache.org/docs/r2.5.2/hadoop-project-dist/hadoop-hdfs/HDFSHighAvailabilityWithQJM.html) 配置

1) 下载 hadoop-2.5.2.tar.gz 压缩包 并解压到/home/hadoop/下边<br/>
2) 编辑 hdfs-site.xml 添加一些配置
	
	<property>
	  <name>dfs.nameservices</name>
	  <value>mycluster</value>
	</property>
	
	//配置高可用的两个节点
	<property>
	  <name>dfs.ha.namenodes.mycluster</name>
	  <value>nn1,nn2</value>
	</property>
	
	//配置两台机器的rpc协议端口
	<property>
	  <name>dfs.namenode.rpc-address.mycluster.nn1</name>
	  <value>machine1.example.com:8020</value>
	</property>
	<property>
	  <name>dfs.namenode.rpc-address.mycluster.nn2</name>
	  <value>machine2.example.com:8020</value>
	</property>
	
	//配置两台机器的http协议端口
	<property>
	  <name>dfs.namenode.http-address.mycluster.nn1</name>
	  <value>machine1.example.com:50070</value>
	</property>
	<property>
	  <name>dfs.namenode.http-address.mycluster.nn2</name>
	  <value>machine2.example.com:50070</value>
	</property>
	
	//配置一组JN的连接地址 用来供namenode 读/写
	<property>
	  <name>dfs.namenode.shared.edits.dir</name>
	  <value>qjournal://node1.example.com:8485;node2.example.com:8485;node3.example.com:8485/mycluster</value>
	</property>
	
	//HDFS Java类客户端 ，用来连接活跃的namenode
	<property>
	  <name>dfs.client.failover.proxy.provider.mycluster</name>
	  <value>org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider</value>
	</property>
	
	//需要故障转移的时候 连接活跃namenode 的方式  这里是 SSH方式
	<property>
	  <name>dfs.ha.fencing.methods</name>
	  <value>sshfence</value>
	</property>
	//配置SSH连接的私钥 
	<property>
	  <name>dfs.ha.fencing.ssh.private-key-files</name>
	  <value>/home/exampleuser/.ssh/id_rsa</value>
	</property>
	
	//配置JN的数据存放目录
	<property>
	  <name>dfs.journalnode.edits.dir</name>
	  <value>/path/to/journal/node/local/data</value>
	</property>
	//配置namenode自动切换
	<property>
	   <name>dfs.ha.automatic-failover.enabled</name>
	   <value>true</value>
	 </property>

3)编辑 core-site.xml 添加一些配置
	
	//配置默认的namenode
	<property>
	  <name>fs.defaultFS</name>
	  <value>hdfs://mycluster</value>
	</property>
	
	//配置zookeeper 集群的连接
	<property>
	   <name>ha.zookeeper.quorum</name>
	   <value>zk1.example.com:2181,zk2.example.com:2181,zk3.example.com:2181</value>
	 </property>
	 
	//配置hadoop的数据存储目录（系统默认是在linux服务的一个临时目录下，重启后就会消失）
	<property>
	   <name>hadoop.tmp.dir</name>
	   <value>/home/hadoop</value>
	 </property>
	
4)编辑 slaves 添加datanode节点的名称

	node2
	node3
	node4
	
5)编辑 hadoop-env.sh 添加JAVA_HOME的环境变量
	
	export JAVA_HOME=/usr/java/jdk1.8.0_65
	
### 3.启动
	
1)首先启动 三个节点的journalNode 
	
	./hadoop-daemon.sh start journalnode 
	
2)两个nameNode中随便选一个 格式化
	
	./hdfs namenode -format
	
3)同步另一个nameNode的数据

	./hadoop-daemon.sh start namenode 
	./hdfs namenode -bootstrapStandby
	
4)初始化高可用的状态到zookeeper
	
	./hdfs zkfc -formatZK
	

