## solr6.0.0 启动 脚本参考文档   [官网链接](https://cwiki.apache.org/confluence/display/solr/Solr+Start+Script+Reference )

### start and restart  启动  重启

    bin/solr start [options]
    bin/solr start -help
    bin/solr restart [options]
    bin/solr restart -help
    
    -a "<string>" 添加JVM参数：   
        如bin/solr start -a "-Xdebug -Xrunjdwp:transport=dt_socket, server=y,suspend=n,address=1044"
    
    -cloud 以SolrCloud模式启动，也可以使用 -c  这种方式启动也会启动solr内嵌的zookeeper实例： 
        bin/solr start -c

    -d <dir> 指定server目录 
        bin/solr start -d newServerDir
        
    -e <name> 启动一个solr 提供的example，为了帮助快速学习
        可用的选项：cloud  techproducts dih schemaless
        
    -f 前台启动   不是守护进程启动
        bin/solr start -f
        
    -h <hostname>  指定hostname ,默认是localhost
        bin/solr start -h search.mysolr.com
        
    -m <memory> 指定虚拟机堆内存 Start Solr with the defined value as the min (-Xms) and max (-Xmx) heap size for the JVM.
        bin/solr start -m 1g
    
    -p <port> 指定端口  默认8983
        bin/solr start -p 8655
        
    -s <dir> 设置 solr.solr.home 系统属性 
        bin/solr start -s newHome
    
    -V  启动solr时，打印solr脚本中的详细信息
        bin/solr start -V
    
    -z <zkHost> 启动solr时，指定Zookeeper连接串 ，需要和-c一起使用，只能在solrCloud模式下使用
        如果没有指定，则使用solr内嵌的zookeeper实例
        bin/solr start -c -z server1:2181,server2:2181
		
### 下列命令是等价的
	
	bin/solr start
	bin/solr start -h localhost -p 8983 -d server -s solr -m 512m
	
	bin/solr start -c
	bin/solr start -cloud
	注意：在solrCloud模式下启动solr ， 如果没有指定-z <zkHost>  ，就会启动solr内嵌的zookeeper实例，并且端口为solr 的port+1000  如： solr 的端口为 8983  zookeeper的端口就为：9983
	
	IMPORTANT: If your ZooKeeper connection string uses a chroot, such as localhost:2181/solr, then you need to bootstrap the /solr znode before launching SolrCloud using the bin/solr script. To do this, you need to use the zkcli.sh script shipped with Solr, such as:
	
	server/scripts/cloud-scripts/zkcli.sh -zkhost localhost:2181/solr -cmd bootstrap -solrhome server/solr
	
### 设置Java系统属性 Setting Java System Properties

	The bin/solr script will pass any additional parameters that begin with -D to the JVM, which allows you to set arbitrary Java system properties. For example, to set the auto soft-commit frequency to 3 seconds, you can do:
	bin/solr start -Dsolr.autoSoftCommit.maxTime=3000
	
### 停止
	
	bin/solr stop [options]

	bin/solr stop -help
	
	-p <port> 指定要停止的端口
		bin/solr stop -p 8983
	
	-all 停止所有的solr实例
		bin/solr stop -all
		
	-k <key> Stop key used to protect from stopping Solr inadvertently; default is "solrrocks".
		bin/solr stop -k solrrocks
		
### 信息
	
	bin/solr version 显示版本
	bin/solr status  现在当前机器的所有solr节点的状态
		
		Found 2 Solr nodes:
 
		Solr process 39920 running on port 7574
		{
		  "solr_home":"/Applications/Solr/example/cloud/node2/solr/",
		  "version":"X.Y.0",
		  "startTime":"2015-02-10T17:19:54.739Z",
		  "uptime":"1 days, 23 hours, 55 minutes, 48 seconds",
		  "memory":"77.2 MB (%15.7) of 490.7 MB",
		  "cloud":{
			"ZooKeeper":"localhost:9865",
			"liveNodes":"2",
			"collections":"2"}}
		 
		Solr process 39827 running on port 8865
		{
		  "solr_home":"/Applications/Solr/example/cloud/node1/solr/",
		  "version":"X.Y.0",
		  "startTime":"2015-02-10T17:19:49.057Z",
		  "uptime":"1 days, 23 hours, 55 minutes, 54 seconds",
		  "memory":"94.2 MB (%19.2) of 490.7 MB",
		  "cloud":{
			"ZooKeeper":"localhost:9865",
			"liveNodes":"2",
			"collections":"2"}}
			
	bin/solr healthcheck [options]
	bin/solr healthcheck -help
	
	-c <collection>  检查某个collection的健康状态
		bin/solr healthcheck -c gettingstarted
	
	-z <zkhost>
		bin/solr healthcheck -z localhost:2181
		
### collections or cores
	
	注意：create 会自动发现当前的启动模式（SolrCloud or standalone ） 
	
	bin/solr create options
	bin/solr create -help
	
	-c <name> core 或 collection 的名称  （必填）
		bin/solr create -c mycollection
	
	-d <confdir>  配置文件路径
		bin/solr create -d basic_configs
		
	-n <configName>  配置文件名称 默认是core的名称
		bin/solr create -n basic
		
	-p <port>  指定要创建到哪个端口所在的solr实例
		bin/solr create -p 8983
	
	-s <shards> 只在solrCloud模式下起作用 collection 要被分到几个shard上   默认是1
	-shards
		bin/solr create -s 2
	
	-rf <replicas> collection 中的文档 要被复制几分    默认1
	-replicationFactor 
		bin/solr create -rf 2
		
	


	bin/solr delete [options]
	bin/solr delete -help
	
	注意：在solrCloud模式下，删除一个collection时，delete命令会自动检查 这个collection的配置是否被其他 collection 占用， 如果没有占用， 则会删除zookeeper上边的配置路径
	
	-c <name> 删除core
		bin/solr delete -c mycoll
	
	-deleteConfig <true|false>  是否删除zookeeper上的配置路径  默认是true   如果配置的路径被其他的collection占用了，即便是设置为true 也不会删除配置路径
		bin/solr delete -deleteConfig false
	
	-p <port> 当本地启动多个solr实例时， 指定端口表示指定删除哪个实例上的collection
		bin/solr delete -p 8983
	
	
###	ZooKeeper 操作
	
	Uploading a Configuration Set
	
	bin/solr zk [options]
	bin/solr zk -help
	
	-upconfig  上传配置文件到zookeeper
		bin/solr zk  -upconfig
		
	-n <name> 指定配置名称
		bin/solr zk -n myconfig
		
	-d <configset dir> 指定配置文件的路径  如果只指定了要下载配置文件的名称 默认路径是：SOLR_HOME/server/solr/configsets 
		-d directory_under_configsets
		-d /absolute/path/to/configset/source
	
	-z <zkHost> 指定要上传到的zookeeper的地址
		-z 123.321.23.43:2181
	
	bin/solr zk -upconfig -z 111.222.333.444:2181 -n mynewconfig -d /path/to/configset
	
	
	Downloading a Configuration Set
	
	-downconfig  下载配置
		bin/solr zk  -downconfig
	-n <name> 指定要下载的配置名称
		-n myconfig
	
	-d <configset dir> 指定配置文件要下载的路径，如果只指定了要下载配置文件的名称 默认路径是：SOLR_HOME/server/solr/configsets 
		-d directory_under_configsets -d /absolute/path/to/configset/destination
	
	
	-z <zkHost> 指定要上传到的zookeeper的地址
		-z 123.321.23.43:2181
	
	bin/solr zk -downconfig -z 111.222.333.444:2181 -n mynewconfig -d /path/to/configset
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	