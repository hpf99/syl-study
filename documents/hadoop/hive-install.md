# 一、hive安装 1.2.1 
	
1.官网上下载 apache-hive-1.2.1-bin.tar.gz 并解压到/home/hive目录下

2.因为hive是基于hadoop的数据仓库，所以必须配置hadoop环境变量 

	export HADOOP_HOME=/home/hadoop/hadoop-2.5.2
	export PATH=$HADOOP_HOME/bin:$PATH
	export PATH=$HADOOP_HOME/sbin:$PATH
	
3.进入到bin目录下  执行 ./hive 启动

4.hive需要连接关系型数据库   以 MySQL为例 配置

1)copy hive-default.xml.template 并改名为 hive-site.xml 并编辑 添加MySQL配置    hive 默认使用derby数据库，
	直接修改以下三项就可以

	<property>
	    <name>javax.jdo.option.ConnectionURL</name>
	    <value>jdbc:mysql://node3/hive</value>
	    <description>JDBC connect string for a JDBC metastore</description>
	</property>
	<property>
	    <name>javax.jdo.option.ConnectionUserName</name>
	    <value>root</value>
	    <description>Username to use against metastore database</description>
	</property>
	<property>
	    <name>javax.jdo.option.ConnectionPassword</name>
	    <value>shiyanlei</value>
	    <description>password to use against metastore database</description>
	</property>

2)下载MySQL驱动包 mysql-connector-java-5.1.38.jar 添加到 hive/lib/ 目录下

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

	hadoop目录下存在老版本jline：<br/>
	/hadoop-2.5.2/share/hadoop/yarn/lib：<br/>
	-rw-r--r-- 1 root root  87325 Mar 10 18:10 jline-0.9.94.jar

* 解决：

	cp /hive/apache-hive-1.2.1-bin/lib/jline-2.12.jar /hadoop-2.5.2/share/hadoop/yarn/lib
	
2.启动./hive 报错

	[root@node1 bin]# ./hive

	Logging initialized using configuration in jar:file:/home/hive/lib/hive-common-1.2.1.jar!/hive-log4j.properties
	Exception in thread "main" java.lang.RuntimeException: java.lang.IllegalArgumentException: java.net.URISyntaxException: Relative path in absolute URI: ${system:java.io.tmpdir%7D/$%7Bsystem:user.name%7D
		at org.apache.hadoop.hive.ql.session.SessionState.start(SessionState.java:522)
		at org.apache.hadoop.hive.cli.CliDriver.run(CliDriver.java:677)
		at org.apache.hadoop.hive.cli.CliDriver.main(CliDriver.java:621)
		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.lang.reflect.Method.invoke(Method.java:497)
		at org.apache.hadoop.util.RunJar.main(RunJar.java:212)
	Caused by: java.lang.IllegalArgumentException: java.net.URISyntaxException: Relative path in absolute URI: ${system:java.io.tmpdir%7D/$%7Bsystem:user.name%7D
		at org.apache.hadoop.fs.Path.initialize(Path.java:206)
		at org.apache.hadoop.fs.Path.<init>(Path.java:172)
		at org.apache.hadoop.hive.ql.session.SessionState.createSessionDirs(SessionState.java:563)
		at org.apache.hadoop.hive.ql.session.SessionState.start(SessionState.java:508)
		... 7 more
	Caused by: java.net.URISyntaxException: Relative path in absolute URI: ${system:java.io.tmpdir%7D/$%7Bsystem:user.name%7D
		at java.net.URI.checkPath(URI.java:1823)
		at java.net.URI.<init>(URI.java:745)
		at org.apach
		
* 解决方案
	
	将${system:java.io.tmpdir}，替换为/home/hive/tmp/
	
3.执行查询时报错
	
	Failed with exception java.io.IOException:java.lang.IllegalArgumentException: java.net.URISyntaxException: 
	Relative path in absolute URI: ${system:user.name%7D

* 解决方案

	把hive-site.xml文件中的${system:user.name}全部替换为一个实际的路径 
	
	保存重启 即可
	
	

	
	