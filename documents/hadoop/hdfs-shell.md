# HDFS shell命令 学习笔记    ---基于hadoop2.5.2

1.dfs

	./hdfs dfs -mkdir /root   创建目录
	
	./hdfs dfs -mkdir -p  /home/hadoop  创建多层目录
	
	./hdfs dfs -put /home/jdk/jdk-8u65-linux-x64.rpm  /root/jdkdir    上传文件前  必须确保目录是已经存在状态
	
	./hdfs dfs -cat /home/input/test.txt    获取hdfs /home/input/test.txt文件的内容
	
	./hdfs dfs -ls /home/input/			 跟Linux中的ls命令类似  列出/home/input/目录下的所有目录及文件
	
	