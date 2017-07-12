package com.dyp.tzk;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Watcher watcher = new Watcher() {
			
			public void process(WatchedEvent event) {
				String path = event.getPath();
				System.out.println("event path: "+ path);
				EventType type = event.getType();
				System.out.println("event tyep: "+ type.name());
				KeeperState state = event.getState();
				System.out.println("event state : "+ state.name());
				WatcherEvent wrapper = event.getWrapper();
				System.out.println("event wrapper : path:"+ wrapper.getPath() + "  type:"+wrapper.getType()+" state:"+wrapper.getState());
			}
		};
    	String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    	int sessionTimeout = 60000;
        try {
			ZooKeeper zk = new ZooKeeper(connectString, sessionTimeout, watcher);
			System.out.println("获取连接：" + zk);
			/*//get
			Stat stat = new Stat();
			byte[] data = zk.getData("/root", watcher, stat);
			System.out.println("读取信息：" + new String(data));
			int version = stat.getVersion();
			System.out.println("stat : "+ stat.getVersion()+ " ctime:" + stat.getCtime() + " pzxid:" + stat.getPzxid());
			//set
			Stat nStat = zk.setData("/root", "hello new1".getBytes(), version);
			System.out.println("nstat : "+ nStat.getVersion()+ " ctime:" + nStat.getCtime() + " pzxid:" + nStat.getPzxid());
			*/
			/*
			 //create
		    //new一个acl
	        List<ACL> acls = new ArrayList<ACL>();
	        //添加第一个id，采用用户名密码形式
	        Id id1 = new Id("digest",
	                DigestAuthenticationProvider.generateDigest("admin:admin"));
	        ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);
	        acls.add(acl1);
	        //添加第二个id，所有用户可读权限
//	        Id id2 = new Id("world", "anyone");
	        Id id2 = new Id("digest", DigestAuthenticationProvider.generateDigest("guest:guest123"));
	        ACL acl2 = new ACL(ZooDefs.Perms.READ, id2);
	        acls.add(acl2);
			String rst = zk.create("/root/nnode1", "new node".getBytes(), acls, CreateMode.PERSISTENT);
			System.out.println("create result:" + rst);
			*/
			zk.addAuthInfo("digest", "guest:guest123".getBytes());
			Stat stat1 = new Stat();
			byte[] nvalue = zk.getData("/root/nnode1", watcher, stat1);
			System.out.println("nnode value:"+ new String(nvalue));
			
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
