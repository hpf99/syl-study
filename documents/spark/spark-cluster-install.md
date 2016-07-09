### spark 独立集群安装 以spark-1.6.1-bin-hadoop2.4.tgz 为例
* 前提条件
```
1. JAVA_HOME 环境变量的配置 1.8
2. HADOOP_HOME 环境变量的配置 2.5.2
3. SCALA_HOME 环境变量的配置  2.10.6
```
* 解压 spark-1.6.1-bin-hadoop2.4.tgz 到 /home/目录下 编辑spark-env.sh
```
//conf/spark-env.sh中添加以下内容
export JAVA_HOME=/usr/java/jdk1.8.0_65
export SCALA_HOME=/home/scala-2.10.6
export SPARK_MASTER_IP=192.168.0.106
export SPARK_WORKER_MEMORY=1g
export HADOOP_CONF_DIR=/home/hadoop/hadoop-2.5.2/etc/hadoop
export HADOOP_HOME=/home/hadoop/hadoop-2.5.2
```
* 编辑conf/slaves
```
//ndoe1是master 也是worker
node1
node3
node4
```
* 启动集群
```
sbin/start-all.sh
//在各个节点下jps查看进程  node1下会有 master worker两个
//其他的节点只有worker
//http://node1:8080进入web界面查看
Spark Master at spark://node1:7077

bin/spark-shell 启动spark shell
//http://node1:4040  spark shell web界面
```
* scala 程序连接spark
```
object SparkStudy {

    def main(args: Array[String]) {
        val env = Utils.getConfiguration();
        val sparkConfig = new SparkConf();
        sparkConfig.setAppName("study_spark")
        sparkConfig.setMaster("spark://node1:7077");
        val ct = new SparkContext(sparkConfig);
        val data = ct.textFile("D:\\desktop\\hive.txt")
        data.filter(line=>{
            println(line);
            return true;
        })
    }
}
```

* 高可用性
> 独立调度的集群能够容忍worker节点的失败（在spark本身来说，它能够将失败的工作移到其他的work节点上）。
> 然而调度需要master做出调度决策，而这会造成单点失败：如果master挂了，任何应用都不能提交和调度。
* 基于zookeeper的高可用
> 配置
> 要使用这种恢复模式，你需要在spark-env中设置SPARK_DAEMON_JAVA_OPTS，可用的属性如下：

系统属性|含义
--  | --
spark.deploy.recoveryMode|设为ZOOKEEPER以启用热备master恢复模式（默认空）
spark.deploy.zookeeper.url|Zookeeper集群URL（如：node1:2181,node2:2181,node3:2181）
spark.deploy.zookeeper.dir|用于存储可恢复状态的Zookeeper目录（默认 /spark）


> 例如以下配置 复制到各个节点上 重新启动服务 ，在另外一个节点上执行./start-master.sh 再启动一个master 为standby
```
export JAVA_HOME=/usr/java/jdk1.8.0_65
export SCALA_HOME=/home/scala-2.10.6
#export SPARK_MASTER_IP=192.168.0.106
export SPARK_WORKER_MEMORY=1g
export HADOOP_CONF_DIR=/home/hadoop/hadoop-2.5.2/etc/hadoop
export SPARK_DAEMON_JAVA_OPTS="-Dspark.deploy.recoveryMode=ZOOKEEPER -Dspark.deploy.zookeeper.url=node1:2181,node2:2181,node3:2181"
```
* 查看配置基于zookeeper的高可用结果
[master](../image/spark-master.png)
[standby](../image/spark-standby.png)