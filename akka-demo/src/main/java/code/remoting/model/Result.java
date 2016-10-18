package code.remoting.model;

import java.io.Serializable;

/**
 * 类Result.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年9月24日下午6:42:27
 */
public class Result implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5513202512746440696L;
  // 单词
  private String word;
  // 数量
  private int num;

  public Result(String word, int num) {
    this.word = word;
    this.num = num;
  }



  /**
   * @return the word
   */
  public String getWord() {
    return word;
  }



  /**
   * @param word the word to set
   */
  public void setWord(String word) {
    this.word = word;
  }



  /**
   * @return the num
   */
  public int getNum() {
    return num;
  }



  /**
   * @param num the num to set
   */
  public void setNum(int num) {
    this.num = num;
  }



  @Override
  public String toString() {
    return word + ":" + num;
  }

}
