#	һ��zookeeper �İ�װ  ��3.4.8Ϊ��
###		1.������װ
			1)��װĿ¼��/home/zookeeper/
			2)�ϴ�ѹ���� zookeeper-3.4.8.tar.gz ��ѹ����ǰĿ¼�²�����Ϊ zookeeper
			3)��/home/zookeeper/�´��� zk1Ŀ¼ ������data ��logĿ¼ 
			4)����/home/zookeeper/zookeeper/conf/zoo_sample.cfg ������Ϊzoo.cfg ���༭zoo.cfg
				tickTime=2000 Zookeeperʹ�õĻ���ʱ�䣬ʱ�䵥λΪ���롣�������������ƣ�����������С��session��ʱʱ��Ϊ��������ʱ��
				initLimit=5        ��Zookeeper�������޶�quorum�е�Zookeeper���������ӵ�Leader�ĳ�ʱʱ��. ��ʱ�ò�������Ϊ5, 
							     ˵��ʱ������Ϊ5��tickTime, ��5*2000=10000ms=10s
				syncLimit=5        ��ʶ Leader �� Follower ֮�䷢����Ϣ�������Ӧ��ʱ���ʱ�� ��ʱ�ò�������Ϊ5, 
							      ˵��ʱ������Ϊ5��tickTime, ��5*2000=10000ms=10s
				dataDir=/home/zookeeper/zk1/data   ����Ŀ¼. ����������Ŀ¼
				dataLogDir=/home/zookeeper/zk1/log   logĿ¼, ͬ������������Ŀ¼. ���û�����øò���, ��ʹ�ú�dataDir��ͬ������
				clientPort=2181  ����client���ӵĶ˿ں�
			5)���� ./zkServer.sh start  
			6)�ͻ�������  ./zkCli.sh -server ip:2181
			7)ֹͣ ./zkServer.sh stop
		
###		2.��Ⱥ
			1)��ÿ̨�������ϰ�װ���յ�����װ�ķ�ʽ��װ
			2)�༭zoo.cfg
				server.1=10.1.39.43:2888:3888  
				server.2=10.1.39.47:2888:3888    
				server.3=10.1.39.48:2888:3888
			3)��ÿ��dataDirĿ¼���½�myid�ļ���д��һ������, �����ֱ�ʾ���ǵڼ���server. �����ֱ����zoo.cfg�ļ��е�server.X�е�Xһһ��Ӧ
			4)�ֱ���� ÿ��binĿ¼��  ./zkServer.sh start  ��������
