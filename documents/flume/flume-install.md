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
	 +  

