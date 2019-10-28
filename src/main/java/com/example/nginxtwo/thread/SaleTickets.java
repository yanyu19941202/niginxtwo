package com.example.nginxtwo.thread;

public class SaleTickets implements  Runnable{

   private int ticketCount = 10;// 总的票数，这个是共享资源，多个线程都会访问
   Object aaa = new Object();// 锁，自己定义的，或者使用实例的锁

    @Override
    public void run() {


           while (ticketCount > 0) {
               sellTicket();
               try {
                   Thread.sleep(5000);

               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

    }

    public void sellTicket()
    {
        synchronized (this)// 当操作的是共享数据时,
        // 用同步代码块进行包围起来,执行里面的代码需要mutex的锁，但是mutex只有一个锁。这样在执行时,只能有一个线程执行同步代码块里面的内容
        {
            if (ticketCount > 0)
            {
                ticketCount--;
                System.out.println(Thread.currentThread().getName()
                        + "正在卖票,还剩" + ticketCount + "张票");
            }
            else
            {
                System.out.println("票已经卖完！");
                return;
            }
        }
    }

        public static void main(String[] args) {

            SaleTickets runTicekt = new SaleTickets();//只定义了一个实例，这就只有一个Object mutex = new Object();即一个锁。
            Thread th1 = new Thread(runTicekt, "窗口1");//每个线程等其他线程释放该锁后，才能执行
            Thread th2 = new Thread(runTicekt, "窗口2");
            Thread th3 = new Thread(runTicekt, "窗口3");
            Thread th4 = new Thread(runTicekt, "窗口4");
            th1.start();
            th2.start();
            th3.start();
            th4.start();
        }

}
