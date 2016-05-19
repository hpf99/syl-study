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
 * zookeeper学习 1
 * zookeeper的增 删 改 查
 * @author yanlei.shi
 *
 */
public class ZookeeperStudyTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ZooKeeper zk =null;
	
	//zk单节点地址 ：端口
	public String zkurl="node1:2181";
	
	//客户端连接zk服务器超时时间 
	public int timeout = 3000;
	
	//znode 根节点
	public String root ="/myconf";
	
	//数据库连接节点
	public String urlNode=root +"/url";
	
	//用户名节点
	public String userNameNode =root + "/username";
	
	//密码节点
	public String passwdNode=root + "/passwd";
	
	public String auth_type = "digest";

	public String auth_passwd = "passwrod";
	
	public String urlStr = "node1:3306";
	
	public String username = "root";
	
	public String passwd = "shiyanlei";
	
	
	
	
	
	
	
	/**
	 * 初始化zk实例
	 */
	@Before
	public void init(){
		try {
			zk = new ZooKeeper(zkurl,timeout,new Watcher() {
				
				public void process(WatchedEvent event) {
					System.out.println("触发了事件："+event.getType());
				}
			});
			//判断是否连接上服务
			if(ZooKeeper.States.CONNECTED != zk.getState()){
				logger.info("正在连接服务器  state:{}", zk.getState().name());
				Thread.sleep(3000);
			}
			//添加权限验证
			zk.addAuthInfo(auth_type, auth_passwd.getBytes());
			logger.info("添加权限验证 authType:{},authPasswd:{}", auth_type,auth_passwd);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 测试创建znode
	 */
	@Test
	public void testCreateZnode(){
		try {
			if(zk.exists(root, true)==null){
				logger.info("创建节点znode:{}", root);
				zk.create(root, "root".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			if(zk.exists(urlNode, true)==null){
				logger.info("创建节点znode:{}", urlNode);
				zk.create(urlNode, urlStr.getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			if(zk.exists(userNameNode, true)==null){
				logger.info("创建节点znode:{}", userNameNode);
				zk.create(userNameNode, username.getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			if(zk.exists(passwdNode, true)==null){
				logger.info("创建节点znode:{}", passwdNode);
				zk.create(passwdNode, passwd.getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 测试获取节点数据
	 */
	@Test
	public void testGetZnode(){
		try {
			Stat stat = zk.exists(urlNode, false);
			byte[] data = zk.getData(urlNode, false, stat);
			logger.info("url节点获取的数据值为:{}", new String(data));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 测试更新节点上的数据
	 */
	@Test
	public void testUpdateData(){
		try {
			Stat stat = zk.exists(passwdNode, true);
			Stat newstat = zk.setData(passwdNode, "duannannan".getBytes(), stat.getVersion());
			byte[] data = zk.getData(passwdNode, true, newstat);
			logger.info("password节点获取的数据值为:{}", new String(data));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	


}
