package org.zookeeper.study.test;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * zookeeperѧϰ 1
 * zookeeper���� ɾ �� ��
 * @author yanlei.shi
 *
 */
public class ZookeeperStudyTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ZooKeeper zk =null;
	
	//zk���ڵ��ַ ���˿�
	public String zkurl="node1:2181";
	
	//�ͻ�������zk��������ʱʱ�� 
	public int timeout = 3000;
	
	//znode ���ڵ�
	public String root ="/myconf";
	
	//���ݿ����ӽڵ�
	public String urlNode=root +"/url";
	
	//�û����ڵ�
	public String userNameNode =root + "/username";
	
	//����ڵ�
	public String passwdNode=root + "/passwd";
	
	public String auth_type = "digest";

	public String auth_passwd = "passwrod";
	
	public String urlStr = "node1:3306";
	
	public String username = "root";
	
	public String passwd = "shiyanlei";
	
	
	
	
	
	
	
	/**
	 * ��ʼ��zkʵ��
	 */
	@Before
	public void init(){
		try {
			zk = new ZooKeeper(zkurl,timeout,new Watcher() {
				
				public void process(WatchedEvent event) {
					System.out.println("�������¼���"+event.getType());
				}
			});
			//�ж��Ƿ������Ϸ���
			if(ZooKeeper.States.CONNECTED != zk.getState()){
				logger.info("�������ӷ�����  state:{}", zk.getState().name());
				Thread.sleep(3000);
			}
			//���Ȩ����֤
			zk.addAuthInfo(auth_type, auth_passwd.getBytes());
			logger.info("���Ȩ����֤ authType:{},authPasswd:{}", auth_type,auth_passwd);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ���Դ���znode
	 */
	@Test
	public void testCreateZnode(){
		try {
			if(zk.exists(root, true)==null){
				logger.info("�����ڵ�znode:{}", root);
				zk.create(root, "root".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			if(zk.exists(urlNode, true)==null){
				logger.info("�����ڵ�znode:{}", urlNode);
				zk.create(urlNode, urlStr.getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			if(zk.exists(userNameNode, true)==null){
				logger.info("�����ڵ�znode:{}", userNameNode);
				zk.create(userNameNode, username.getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			if(zk.exists(passwdNode, true)==null){
				logger.info("�����ڵ�znode:{}", passwdNode);
				zk.create(passwdNode, passwd.getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ���Ի�ȡ�ڵ�����
	 */
	@Test
	public void testGetZnode(){
		try {
			Stat stat = zk.exists(urlNode, false);
			byte[] data = zk.getData(urlNode, false, stat);
			logger.info("url�ڵ��ȡ������ֵΪ:{}", new String(data));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ���Ը��½ڵ��ϵ�����
	 */
	@Test
	public void testUpdateData(){
		try {
			Stat stat = zk.exists(passwdNode, true);
			Stat newstat = zk.setData(passwdNode, "duannannan".getBytes(), stat.getVersion());
			byte[] data = zk.getData(passwdNode, true, newstat);
			logger.info("password�ڵ��ȡ������ֵΪ:{}", new String(data));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
