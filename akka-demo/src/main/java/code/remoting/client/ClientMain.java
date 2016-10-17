package code.remoting.client;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * 类ClientMain.java的实现描述：客户端
 * 
 * @author coldface
 * @date 2016年9月24日下午5:10:40
 */
public class ClientMain {
  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    // 文件名
    final String fileName = "data.txt";
    // 根据配置，找到System
    ActorSystem actorSystem = ActorSystem.create("ClientApplication",
        ConfigFactory.load("remoting-client-application").getConfig("WCMapReduceClientApp"));
    // 实例化远程Actor
    final ActorRef remoteActor =
        actorSystem.actorFor("akka.tcp://WCMapReduceApp@127.0.0.1:2552/user/WCMapReduceActor");
    // 实例化FileReadActor的管道
    final ActorRef fileReadActor = actorSystem.actorOf(Props.create(FileReadActor.class));
    // 实例化Client的Actor管道
    final ActorRef clientActor = actorSystem.actorOf(Props.create(ClientActor.class, remoteActor));
    // 发送文件名给fileReadActor设置sender或者说回调的Actor为clientActor
    fileReadActor.tell(fileName, clientActor);
  }

}
