package code.simple;

import akka.actor.UntypedActor;

/**
 * 类GreetPrinter.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年9月24日下午3:11:11
 */
public class GreetPrinter extends UntypedActor {

  /*
   * (non-Javadoc)
   * 
   * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
   */
  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof Greeting) {
      System.out.println(((Greeting) message).message);
    }

  }

}
