package code.remoting.server;

import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import code.remoting.model.Result;

/**
 * 类ReduceActor.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年9月24日下午6:31:45
 */
public class ReduceActor extends UntypedActor {

  // 管道Actor
  private ActorRef actor = null;

  public ReduceActor(ActorRef inAggregateActor) {
    actor = inAggregateActor;
  }

  @Override
  public void preStart() {
    System.out.println("启动ReduceActor:" + Thread.currentThread().getName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
   */
  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof List) {
      // 强制转换结果
      List<Result> work = (List<Result>) message;
      // 第一次汇总单词表结果
      NavigableMap<String, Integer> reducedList = reduce(work);
      // 把这次汇总的结果发送给最终的结果聚合Actor
      actor.tell(reducedList, null);
    } else if (message instanceof Boolean) {
      // 表示已经计算结果了，把这次汇总的结果发送给最终的结果聚合Actor
      actor.tell(message, null);
    } else {
      throw new IllegalArgumentException("Unknown message [" + message + "]");
    }
  }

  /**
   * 聚合计算本次结果中各个单词的出现次数
   * 
   * @param list
   * @return
   */
  private NavigableMap<String, Integer> reduce(List<Result> list) {

    NavigableMap<String, Integer> reducedMap = new ConcurrentSkipListMap<String, Integer>();

    for (Result result : list) {
      /* 遍历结果,如果在这个小的结果中已经存在相同的单词了,那么数量+1,否则新建 */
      if (reducedMap.containsKey(result.getWord())) {
        Integer value = reducedMap.get(result.getWord());
        value++;
        reducedMap.put(result.getWord(), value);
      } else {
        reducedMap.put(result.getWord(), 1);
      }
    }
    return reducedMap;
  }



}
