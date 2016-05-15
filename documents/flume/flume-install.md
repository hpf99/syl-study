## flume 介绍安装  版本apache-flume-1.6.0-bin.tar.gz

* 简介
	```
		Flume是Cloudera提供的一个高可用的，高可靠的，分布式的海量日志采集、聚合和传输的系统，Flume支持在日志系统中定制各类数据发送方，用于收集数据；同时，Flume提供对数据进行简单处理，并写到各种数据接受方（可定制）的能力。
	当前Flume有两个版本Flume 0.9X版本的统称Flume-og，Flume1.X版本的统称Flume-ng。由于Flume-ng经过重大重构，与Flume-og有很大不同，使用时请注意区分。
	```

* 安装
	 + 下载 apache-flume-1.6.0-bin.tar.gz 压缩包 解压到/home/flume目录下
	```
		编辑conf/flume-env.sh 文件 ， 配置JAVA_HOME 环境变量
		export JAVA_HOME=/usr/java/jdk1.8.0_65
	```
	 +  编写flume 配置文件 conf/example
	```
		#agent1表示代理名称
		agent1.sources=source1
		agent1.sinks=sink1
		agent1.channels=channel1
	```	
	```	
		#Spooling Directory是监控指定文件夹中新文件的变化，一旦新文件出现，就解析该文件内容，
		#然后写入到channle。写入完成后，标记该文件已完成或者删除该文件。
		#配置source1
		agent1.sources.source1.type=spooldir
		agent1.sources.source1.spoolDir=/root/hmbbs
		agent1.sources.source1.channels=channel1
		agent1.sources.source1.fileHeader=false
		agent1.sources.source1.interceptors=i1
		agent1.sources.source1.interceptors.i1.type=timestamp
	```
	```	
		#配置sink1
		agent1.sinks.sink1.type=hdfs
		agent1.sinks.sink1.hdfs.path=hdfs://sylhadoop/hmbbs
		agent1.sinks.sink1.hdfs.fileType=DataStream
		agent1.sinks.sink1.hdfs.writeFormat=TEXT
		agent1.sinks.sink1.hdfs.rollInterval=1
		agent1.sinks.sink1.channel=channel1
		agent1.sinks.sink1.hdfs.filePrefix=%Y-%m-%d
	```
	```
		#配置channel1
		agent1.channels.channel1.type=file
		agent1.channels.channel1.checkpointDir=/root/hmbbs_tmp/123
		agent1.channels.channel1.dataDirs=/root/hmbbs_tmp/
	```
	 + 创建所需目录
	```
	 /root/hmbbs 和 hdfs://sylhadoop/hmbbs
	```
	 + 启动
	```
	 bin/flume-ng agent -n agent1 -c conf -f conf/example -Dflume.root.logger=DEBUG,console
	```
	 + 测试
	```
	 往/root/hmbbs/目录下放一个新文件，这时flume就会读取该文件并发送到hdfs指定的目录/hmbbs目录下
	```

	 
