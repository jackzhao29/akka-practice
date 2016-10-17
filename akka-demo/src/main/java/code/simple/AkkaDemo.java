package code.simple;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.FiniteDuration;
import code.simple.Greeting;

/**
 * 类AkkaDemo.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年9月24日下午3:12:41
 */
public class AkkaDemo {
  public static void main(String[] args) {
    final ActorSystem actorSystem = ActorSystem.create("helloakka");
    // 创建一个到greeter Actor的管道
    final ActorRef greeter = actorSystem.actorOf(Props.create(Greeter.class), "greeter");
    // 创建邮箱
    final Inbox inbox = Inbox.create(actorSystem);
    // 先发第一个消息，消息类型为WhoToGreet
    greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());

    // 真正的发送消息，消息体为Greet
    inbox.send(greeter, new Greet());
    inbox.send(greeter, "小李");
    // 等待5秒尝试接受Greeter返回的消息
    Greeting greeting1 = (Greeting) inbox.receive(FiniteDuration.create(5, TimeUnit.SECONDS));
    System.out.println("Greeting:" + greeting1.message);

    // 发送第三个消息，修改名字
    greeter.tell(new WhoToGreet("typesafe"), ActorRef.noSender());

    // 发送第四个消息
    inbox.send(greeter, new Greet());
    // 等待5秒尝试接受Greeter返回的消息
    Greeting greeting2 = (Greeting) inbox.receive(FiniteDuration.create(5, TimeUnit.SECONDS));
    System.out.println("Greeting:" + greeting2.message);

    // 新创建一个Actor的管道
    ActorRef greetPrinter = actorSystem.actorOf(Props.create(GreetPrinter.class));
    // 使用schedule每秒发送一个Greet消息给greeterActor,然后把greeterActor的消息返回给greetPrinterActor
    actorSystem.scheduler().schedule(FiniteDuration.Zero(),
        FiniteDuration.create(1, TimeUnit.SECONDS), greeter, new Greet(), actorSystem.dispatcher(),
        greetPrinter);

  }

}
