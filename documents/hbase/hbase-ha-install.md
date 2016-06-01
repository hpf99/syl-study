## Hbase 基于 Hadoop 2.5.2 HA	搭建  hbase以 hbase-1.0.3-bin.tar.gz 版本为例

### 一、服务器列表

node  | NN  | DN  |ZK  | ZKFC | JN  | RM | DM  | HIVE | MYSQL |  HM   |  RS 
---   | --- | --- | ---| -----| --- | ---| --- | ---  | ----  | ----- | -----
node1 | 1   |     | 1  |   1  |     | 1  |     |  1   |		  |       |   1  
node2 | 1   | 1   | 1  |   1  |  1  |    | 1   |      |		  |       |   1
node3 |     | 1   | 1  |      |  1  |    | 1   |      |   1	  |   1   |   1
node4 |     | 1   |    |      |  1  |    | 1   |      |		  |   1   |   1

> 	NN:NameNode DN:DataNode ZK:ZooKeeper ZKFC:zookeeper Failover Controller 
>	JN:JournalNode RM:ResourceManager DM:DataManager HM:HMaster RS:RegionServer

### 二、准备

* 下载 hbase-1.0.3-bin.tar.gz 包，并上传到四台服务器的/home/hbase/下，并解压

* Java环境 jdk已经安装

* zookeeper 集群是已经在运行状态

* hadoop 集群是已经在运行状态

### 三、安装

* 编辑conf/hbase-env.sh 

> 1. 配置JAVA_HOME 环境变量

```
export JAVA_HOME=/usr/java/jdk1.8.0_65
```

* 编辑 hbase-site.xml

> 加一个属性 hbase.cluster.distributed 设置为 true 然后把 hbase.rootdir 设置为HDFS的NameNode的位置. 
> hdfs://sylhadoop/hbase  中的sylhadoop 是在hadoop中的配置的namenode集群服务名称

```
<property>
    <name>hbase.rootdir</name>
    <value>hdfs://sylhadoop/hbase</value>
    <description>The directory shared by RegionServers.</description>
</property>
<property>
    <name>hbase.cluster.distributed</name>
    <value>true</value>
</property>
```

* 编辑conf/regionservers 列出了你希望运行的全部 HRegionServer

> 一行写一个host (就像Hadoop里面的 slaves 一样). 列在这里的server会随着集群的启动而启动，集群的停止而停止

```
node1
node2
node3
node4
```

* Hbase中配置Zookeeper 集群 编辑 conf/hbase-env.sh 和 conf/hbase-site.xml

> 让HBase使用一个已有的不被HBase托管的Zookeeper集群，需要设置 conf/hbase-env.sh文件中的HBASE_MANAGES_ZK 属性为 false
> export HBASE_MANAGES_ZK=false

```
编辑 conf/hbase-site.xml
<property>
      <name>hbase.zookeeper.quorum</name>
      <value>node1,node2,node3</value>
</property>
<property>
      <name>hbase.zookeeper.property.dataDir</name>
      <value>/opt/zookeeper</value>
</property>
```

* HDFS客户端配置

```
拷贝hadoop中的 hdfs-site.xml文件到 HBASE_HOME/conf/目录下
```

* 启动hbase
```
bin/start-hbase.sh
```

> 在哪台机器上启动那台就是HMaster 如果想要在启一个HMaster 可以到那台机器上单独启动

```
./hbase-daemon.sh start master
```
