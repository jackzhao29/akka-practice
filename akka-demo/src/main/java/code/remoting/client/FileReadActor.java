package code.remoting.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import akka.actor.UntypedActor;

/**
 * 类FileReadActor.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年9月24日下午5:10:40
 */
public class FileReadActor extends UntypedActor {

  /*
   * (non-Javadoc)
   * 
   * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
   */
  @Override
  public void onReceive(Object message) throws Exception {
    // TODO Auto-generated method stub
    if (message instanceof String) {
      // 如果消息是String类型的
      String fileName = (String) message;
      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
            Thread.currentThread().getContextClassLoader().getResource(fileName).openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
          // 遍历一行一个消息反馈给消息发送方
          getSender().tell(line, null);
        }
        System.out.println("All lines send!");
        getSender().tell(String.valueOf("EOF"), null);
      } catch (Exception ex) {
        System.err.format("IOException:%s%n", ex);
      }
    } else {
      throw new IllegalArgumentException("Unknown message [" + message + "]");
    }

  }

}
