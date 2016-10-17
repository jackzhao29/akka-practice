package code.simple;

import java.io.Serializable;

/**
 * 类Greeting.java的实现描述：招呼体，里面有打的什么招呼
 * 
 * @author coldface
 * @date 2016年9月24日下午3:02:45
 */
public class Greeting implements Serializable {
  private static final long serialVersionUID = 6677821317696783482L;
  public final String message;

  public Greeting(String message) {
    this.message = message;
  }

}
