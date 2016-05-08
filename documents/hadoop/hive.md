# 一、hive安装 1.2.1 
	
1)官网上下载 apache-hive-1.2.1-bin.tar.gz 并解压到/home/hive目录下

2)因为hive是基于hadoop的数据仓库，所以必须配置hadoop环境变量 

	export HADOOP_HOME=/home/hadoop/hadoop-2.5.2
	export PATH=$HADOOP_HOME/bin:$PATH
	export PATH=$HADOOP_HOME/sbin:$PATH
	
3)进入到bin目录下  执行 ./hive 启动

# 二、问题

1.执行./hive 时 可能会报错

	[root@node1 bin]# ./hive

	Logging initialized using configuration in jar:file:/home/hive/lib/hive-common-1.2.1.jar!/hive-log4j.properties
	[ERROR] Terminal initialization failed; falling back to unsupported
	java.lang.IncompatibleClassChangeError: Found class jline.Terminal, but interface was expected
		at jline.TerminalFactory.create(TerminalFactory.java:101)
		at jline.TerminalFactory.get(TerminalFactory.java:158)
		at jline.console.ConsoleReader.<init>(ConsoleReader.java:229)
		at jline.console.ConsoleReader.<init>(ConsoleReader.java:221)
		at jline.console.ConsoleReader.<init>(ConsoleReader.java:209)
		at org.apache.hadoop.hive.cli.CliDriver.setupConsoleReader(CliDriver.java:787)
		at org.apache.hadoop.hive.cli.CliDriver.executeDriver(CliDriver.java:721)
		at org.apache.hadoop.hive.cli.CliDriver.run(CliDriver.java:681)
		at org.apache.hadoop.hive.cli.CliDriver.main(CliDriver.java:621)
		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.lang.reflect.Method.invoke(Method.java:497)
		at org.apache.hadoop.util.RunJar.main(RunJar.java:212)
	
	Exception in thread "main" java.lang.IncompatibleClassChangeError: Found class jline.Terminal, but interface was expected
		at jline.console.ConsoleReader.<init>(ConsoleReader.java:230)
		at jline.console.ConsoleReader.<init>(ConsoleReader.java:221)
		at jline.console.ConsoleReader.<init>(ConsoleReader.java:209)
		at org.apache.hadoop.hive.cli.CliDriver.setupConsoleReader(CliDriver.java:787)
		at org.apache.hadoop.hive.cli.CliDriver.executeDriver(CliDriver.java:721)
		at org.apache.hadoop.hive.cli.CliDriver.run(CliDriver.java:681)
		at org.apache.hadoop.hive.cli.CliDriver.main(CliDriver.java:621)
		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.lang.reflect.Method.invoke(Method.java:497)
		at org.apache.hadoop.util.RunJar.main(RunJar.java:212)
		
* 原因

	hadoop目录下存在老版本jline：
	/hadoop-2.5.2/share/hadoop/yarn/lib：
	-rw-r--r-- 1 root root  87325 Mar 10 18:10 jline-0.9.94.jar

* 解决：

	cp /hive/apache-hive-1.2.1-bin/lib/jline-2.12.jar /hadoop-2.5.2/share/hadoop/yarn/lib