package code.simple;

import java.io.Serializable;

/**
 * 类WhoToGreet.java的实现描述：打招呼的人
 * 
 * @author coldface
 * @date 2016年9月24日下午3:04:07
 */
public class WhoToGreet implements Serializable {
  private static final long serialVersionUID = -1568078485261038435L;
  public final String who;

  public WhoToGreet(String who) {
    this.who = who;
  }

}
