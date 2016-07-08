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