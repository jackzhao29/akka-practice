package code.remoting.client;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * 类ClientActor.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年9月24日下午5:10:40
 */
public class ClientActor extends UntypedActor {

  private ActorRef remoteServer = null;
  private long start;

  public ClientActor(ActorRef inRemoteServer) {
    remoteServer = inRemoteServer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
   */
  @Override
  public void onReceive(Object message) throws Exception {
    // 如果接收到的任务是String的，那么久直接发送给remoteServer这个Actor
    if (message instanceof String) {
      String msg = (String) message;
      if (message.equals("EOF")) {
        // 这个的Sender设置为自己是为了接受聚合完成的消息
        remoteServer.tell(msg, getSelf());
      } else {
        remoteServer.tell(msg, null);
      }
    } else if (message instanceof Boolean) {
      System.out.println("聚合完成");
      // 聚合完成后发送显示结果的消息
      remoteServer.tell("DISPLAY_LIST", null);
      // 执行完毕，关机
      getContext().stop(self());
    }
  }

  @Override
  public void preStart() {
    // 记录开始时间
    start = System.currentTimeMillis();
  }

  @Override
  public void postStop() {
    // 计算用时
    long timeSpent = System.currentTimeMillis() - start;
    System.out.println("Calculation time=" + timeSpent + "ms");
  }

}
