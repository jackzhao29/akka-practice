package code.remoting.server;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * 类WCMapReduceActor.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年9月24日下午6:42:27
 */
public class WCMapReduceActor extends UntypedActor {

  private ActorRef mapRouter;
  private ActorRef aggregateActor;

  public WCMapReduceActor(ActorRef inAggregateActor, ActorRef inMapRouter) {
    mapRouter = inMapRouter;
    aggregateActor = inAggregateActor;
  }

  @Override
  public void preStart() {
    System.out.println("启动WCMapReduceActor:" + Thread.currentThread().getName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
   */
  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof String) {
      /* 如果接收到的是显示结果的请求,那么就调用reduce的Actor */
      if (((String) message).compareTo("DISPLAY_LIST") == 0) {
        System.out.println("Got Display Message");
        aggregateActor.tell(message, getSender());
      }
      if (message.equals("EOF")) {
        // 表示发送完毕
        aggregateActor.tell(true, getSender());
      } else {
        /* 否则给map的Actor进行计算 */
        mapRouter.tell(message, null);
      }
    }
  }

}
