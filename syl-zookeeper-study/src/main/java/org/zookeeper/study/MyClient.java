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
	
	//znode 根节点
	public String root ="/myconf";
	
	//数据库连接节点
	public String urlNode=root +"/url";
	
	//用户名节点
	public String userNameNode =root + "/username";
	
	//密码节点
	public String passwdNode=root + "/passwd";
	
	private String url;
	private String username;
	private String password;
	
	public void process(WatchedEvent event) {
		EventType type = event.getType();
		if(type == EventType.None){
			logger.info("连接成功！");
		}else if(type == EventType.NodeCreated){
			logger.info("创建节点成功");
			initValue();
		}else if (type == EventType.NodeDataChanged) {
			logger.info("更新节点数据成功");
			initValue();
		}else if (type == EventType.NodeChildrenChanged) {
			logger.info("子节点更新成功");
			initValue();
		}else if (type == EventType.NodeDeleted) {
			logger.info("节点删除成功");
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
