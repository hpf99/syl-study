# 一、hadoop 的安装  以2.5.2为例
1. 服务器列表

node  | NN  | DN  |ZK  | ZKFC  | JN  | RM | DM 
---   | --- | --- | ---| ----- | --- | ---| ---
node1 | 1   |     | 1  |   1   |     | 1  |    
node2 | 1   | 1   | 1  |   1   |  1  |    | 1  
node3 |     | 1   | 1  |       |  1  |    | 1  
node4 |     | 1   |    |       |  1  |    | 1  
	
2. 安装hadoop 

1) 下载 hadoop-2.5.2.tar.gz 压缩包 并解压到/home/hadoop/下边<br/>
2) [hadoop官网](http://hadoop.apache.org/docs/r2.5.2/hadoop-project-dist/hadoop-hdfs/HDFSHighAvailabilityWithQJM.html)
