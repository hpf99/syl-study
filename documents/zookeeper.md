#	一、zookeeper 的安装  以3.4.8为例
###	1.单机安装
1)安装目录：/home/zookeeper/
2)上传压缩包 zookeeper-3.4.8.tar.gz 解压到当前目录下并改名为 zookeeper
3)在/home/zookeeper/下创建 zk1目录 并创建data 和log目录 
4)复制/home/zookeeper/zookeeper/conf/zoo_sample.cfg 并改名为zoo.cfg 并编辑zoo.cfg
	tickTime=2000 Zookeeper使用的基本时间，时间单位为毫秒。它用于心跳机制，并且设置最小的
					session超时时间为两倍心跳时间
	initLimit=5        是Zookeeper用它来限定quorum中的Zookeeper服务器连接到Leader的超时时间.
				          此时该参数设置为5，说明时间限制为5倍tickTime, 即5*2000=10000ms=10s
	syncLimit=5        标识 Leader 与 Follower 之间发送消息，请求和应答时间最长时间 此时该参数设置为5, 
				      说明时间限制为5倍tickTime, 即5*2000=10000ms=10s
	dataDir=/home/zookeeper/zk1/data   数据目录. 可以是任意目录
	dataLogDir=/home/zookeeper/zk1/log   log目录, 同样可以是任意目录. 如果没有设置该参数, 
											    将使用和dataDir相同的设置
	clientPort=2181  监听client连接的端口号
5)启动 ./zkServer.sh start  
6)客户端连接  ./zkCli.sh -server ip:2181
7)停止 ./zkServer.sh stop
		
### 2.集群
1)在每台服务器上安装按照单机安装的方式安装
2)编辑zoo.cfg
	server.1=10.1.39.43:2888:3888  
	server.2=10.1.39.47:2888:3888    
	server.3=10.1.39.48:2888:3888
3)在每个dataDir目录下新建myid文件，写入一个数字, 该数字表示这是第几号server. 
      该数字必须和zoo.cfg文件中的server.X中的X一一对应
4)分别进入 每个bin目录下  ./zkServer.sh start  启动服务
		
		
#	二、zookeeper原理
[原理](http://cailin.iteye.com/blog/2014486/)
