package org.zookeeper.study;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClient implements Watcher{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	ZooKeeper zk = null;
	
	//znode ���ڵ�
	public String root ="/myconf";
	
	//���ݿ����ӽڵ�
	public String urlNode=root +"/url";
	
	//�û����ڵ�
	public String userNameNode =root + "/username";
	
	//����ڵ�
	public String passwdNode=root + "/passwd";
	
	private String url;
	private String username;
	private String password;
	
	public void process(WatchedEvent event) {
		EventType type = event.getType();
		if(type == EventType.None){
			logger.info("���ӳɹ���");
		}else if(type == EventType.NodeCreated){
			logger.info("�����ڵ�ɹ�");
			initValue();
		}else if (type == EventType.NodeDataChanged) {
			logger.info("���½ڵ����ݳɹ�");
			initValue();
		}else if (type == EventType.NodeChildrenChanged) {
			logger.info("�ӽڵ���³ɹ�");
			initValue();
		}else if (type == EventType.NodeDeleted) {
			logger.info("�ڵ�ɾ���ɹ�");
		}
	}

	
	
	public MyClient(){
		
	}
	
	public void initValue(){
		try {
			url = new String(zk.getData(urlNode,true, null));
			username = new String(zk.getData(userNameNode,true, null));
			password = new String(zk.getData(passwdNode,true, null));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void getZK(){
		try {
			zk = new ZooKeeper("node1:2181",3000,this);
			zk.addAuthInfo("digest", "passwrod".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		MyClient client = new MyClient();
		client.getZK();
		client.initValue();
		
		int i=0;
		while(true){
			System.out.println(String.format("url:%s", client.getUrl()));
			System.out.println(String.format("username:%s", client.getUsername()));
			System.out.println(String.format("password:%s", client.getPassword()));
			i++;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
}
