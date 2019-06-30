# Online-payment-project-Practice
redis基础与在线支付项目实战
# Redis基础知识
**基础的就不写了,探究性的知识总结一下**

[mysql的ACID(redis没有)](https://blog.csdn.net/justloveyou_/article/details/70312810)
[数据库的四种事务机制](https://www.cnblogs.com/jycboy/p/transaction.html)

>共享锁【S锁】
>又称读锁，若事务T对数据对象A加上S锁，则事务T可以读A但不能修改A，其他事务只能再对A加S锁，而不能加X锁，直到T释放A上的S锁。这保证了其他事务可以读A，但在T释放A上的S锁之前不能对A做任何修改。
>
>排他锁【X锁】
>又称写锁。若事务T对数据对象A加上X锁，事务T可以读A也可以修改A，其他事务不>能再对A加任何锁，直到T释放A上的锁。这保证了其他事务在T释放A上的锁之前不能>再读取和修改A

