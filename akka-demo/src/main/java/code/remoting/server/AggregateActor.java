package code.remoting.server;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;

/**
 * 类AggregateActor.java的实现描述：聚合Actor
 * 
 * @author coldface
 * @date 2016年9月24日下午6:42:27
 */
public class AggregateActor extends UntypedActor {

  // 最终的结果
  private Map<String, Integer> finalReducedMap = new HashMap<String, Integer>();

  @Override
  public void preStart() throws Exception {
    System.out.println("启动AggregateActor:" + Thread.currentThread().getName());
  }


  /*
   * (non-Javadoc)
   * 
   * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
   */
  @Override
  public void onReceive(Object message) throws Exception {
    /* 如果是Map,那么就进行reduce操作 */
    if (message instanceof Map) {
      Map<String, Integer> reducedList = (Map<String, Integer>) message;
      aggregateInMemoryReduce(reducedList);
    } else if (message instanceof String) {
      /* 如果是String,那么就是打印结果 */
      if (((String) message).compareTo("DISPLAY_LIST") == 0) {
        // getSender().tell(finalReducedMap.toString());
        System.out.println(finalReducedMap.toString());

      }
    } else if (message instanceof Boolean) {
      /* 向客户端发送已经reduce完成的信息 */
      getSender().tell(true, null);
    }
  }

  private void aggregateInMemoryReduce(Map<String, Integer> reducedList) {

    for (String key : reducedList.keySet()) {
      /* 最终的数量的累加 */
      if (finalReducedMap.containsKey(key)) {
        Integer count = reducedList.get(key) + finalReducedMap.get(key);
        finalReducedMap.put(key, count);
      } else {
        finalReducedMap.put(key, reducedList.get(key));
      }
    }
  }



}
