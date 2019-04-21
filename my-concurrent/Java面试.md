# 一、JVM调优

---

### 一、JVM参数类型

- **标配参数**

- **x参数**

- **xx参数**


1. ##### **标配参数：**

   ​	java -version，-help等，稳定的参数

2. ##### **x参数：**

   ​	-Xint：解释执行；

   ​	-Xcomp：第一次使用就编译成本地代码

   ​	-Xmixed：混合模式

3. ##### **xx参数:**

   1. **Boolean类型：**

      1. -XX：+或者-某个属性值

         ​	+表示开启；-表示关闭

      2. 

   2. **K-V设置类型（key-value）**

     

### 二、



---







# 二、集合

---

### 一、List

#### 1.特性

- list允许重复
- 可以插入多个null
  - 有序	

#### 2.常用List实现类

- **ArrayList：**

  ​		基于数组实现，非线程安全，由于数组结构，插入或删除影响后面的数据，慢，查询根据索引所以快。查询快而插入和删除慢

  ​		有索引

  ​		遍历：Iterator，Iterator.hasNext（）、for(T t :list)

  ​		排序：Collections.sort（），复杂List<User>实现Comparable<User>重写compareTo（）

  ```java
          //升序
  		@Override
          public int compareTo(User o) {

              return age.compareTo(o.getAge());
          }
  		//删除
  		Iterator<Integer> iterator = s.iterator();
          while (iterator.hasNext()) {
              int i = iterator.next();
              if (5 == i || 7 == i) {
                  iterator.remove();
              }
          }
  ```

- **Vector：**

  ​		基于数组实现

- **LinkedList：**

  ​		底层是双向链表结构，插入或删除只影响位置的前后元素，所以插入快，查询需要从头开始查，慢

### 二、Set

#### 1.特性

- 无序

- 不允许重复

- 允许null（因为不允许重复所以只有一个null） 


#### 2.常用Set实现类

- **HashSet:**底层hashtable实现
- **TreeSet：**有序

### 三、Map

#### 1.特性

- 无序
- key不允许重复，value允许重复
- 允许一个null  key

#### 2.常用Map实现类

- **HashMap：**底层数组和链表实现。key的hashcode（）和equals（）
- **HashTable：**不允许null，线程安全，同步

---





# 三、线程

---

### 一、CAS(compare and swap比较并交换)

##### 什么是CAS

​	比较并交换，是自旋锁和乐观锁的核心操作。

​	CAS 操作包含三个操作数 —— 内存位置/地址（V）、预期原值（A）和新值(B)：如果内存位置的值和预期原值A一致，则内存中的更新成A

​	自身进行循环操作，直到达到所期望的值然后停止循环。

​	AtomicInteger 等这些 并发包下的Atomic*类，底层使用Unsafe类实现。

​	CAS主要是**线程本地变量**和**主内存变量**进行比较，如果是自己期望值，则更新，否则循环直到一致。

##### CAS优缺点

- 循环时间长开销大
- 只能保证一个共享变量的原子操作，多个共享变量必须使用锁
- 引出**ABA**问题（**一个线程将一个共享变量从A改成B,又从B改成A**）可以利用**原子引用类AtomicStampedReference** 增加版本号解决



### 二、自旋锁

##### 	什么是自旋锁

​	当线程A在获取锁的时候，如果这个锁已经被其他线程获取，那么该线程A则循环等待，判断锁是否能被A获取，直到获取成功才会退出循环。

##### 自旋锁优缺点

- **减少线程的上下文切换**：线程一直处于active状态，不会进入阻塞状态
- 如果长时间获取不到锁，会增加CPU消耗



## 三、Java内存模型

​	内存分为主内存和本地内存，每个线程有自己的本地内存，主内存中存放共享变量，本地内存存放共享变量的副本。本地内存更新变量副本后需要同步到主内存中。

##### 	三个特性：

- 可见性
- 原子性
- 可排序性



## 四、乐观锁和悲观锁

##### 	乐观锁：

​			每次拿数据都认为别人不会修改，所以不加锁；但是在更新的时候会进行一个版本的比较，看是否被更改过。适用于多读的应用。

##### 	悲观锁：

​			每次都认为别人会更改数据，所以加锁。传统关系型数据库的行锁，表锁等，读锁，写锁



## 五、AQS（AbstractQueuedSynchronizer队列同步器）

##### 	1、什么是AQS

​		Java并发用来构建锁和其他同步组件的基础框架

​		提供了一种实现阻塞锁和一系列以来FIFO等待队列的同步器的框架

##### **2、AQS实现方式**

​		AQS的主要使用方式是继承，子类通过**继承同步器并实现它的抽象方法**来管理同步状态。

##### **3、AQS的原理**

​		AQS使用一个int类型的成员（全局）变量state来表示同步状态，当state>0表示获取了锁，=0表示释放锁。提供了三个方法：1、getState（），2、setState（），3、compareAndSetState（）来对state进行操作。AQS确保对state的操作是线程安全的。

​		AQS通过内置的**FIFO**同步队列来完成资源获取线程的排队工作。如果当前线程获取同步锁失败时，AQS则会将**当前线程以及等待状态信息构成一个节点(Node)并将其加入同步队列**，同时会阻塞当前线程，当同步锁释放时，则会把节点中的线程唤醒，使其再次尝试获取锁。



### 六、Executors框架

Executors框架其内部采用了线程池机制，他在java.util.cocurrent包下，通过该框架来控制线程的启动、执行、关闭，可以简化并发编程的操作。因此，通过Executors来启动线程比使用Thread的start方法更好，而且更容易管理，效率更好，还有关键的一点：有助于避免this溢出。

Executors框架包括：线程池、Executor，Executors，ExecutorService、CompletionServince，Future、Callable。





### 七、阻塞队列

​	**阻塞队列**是一个支持**两个附加操作**的队列：1、在队列为空时，从队列获取元素时会等待队列变为非空。2、当队列满时，当队列存储元素时会等待队列不满。

​	常用于**生产者-消费者**模式，**生产者**是往队列存储元素的线程，**消费者**是从队列中获取元素的线程。

​	阻塞队列提供了四种处理方法：

​	

| 方法\处理方式 | 抛出异常  | 返回特殊值 | 一直阻塞 | 超时退出           |
| ------------- | --------- | ---------- | -------- | ------------------ |
| 插入方法      | add(e)    | offer(e)   | put(e)   | offer(e,time,unit) |
| 移除方法      | remove()  | poll()     | take()   | poll(time,unit)    |
| 检查方法      | element() | peek()     | 不可用   | 不可用             |

**抛出异常：**当阻塞队列满时，再用add（e）方法插入元素，会抛出IllegalStateException(“Queue full”)异常；当阻塞队列空时，再用remove（）方法移除元素，会抛出NoSuchElementException异常。

**返回特殊值：**offer（e）插入元素返回是否成功true或者false；移除方法poll（）没有返回null

**阻塞：**put（e）无返回值，队列满了继续添加元素线程则会进入阻塞等待，直到添加成功或异常。take（）队列空时线程会进入阻塞等待，直到有元素可删除或异常

**超时退出：**offer(e,time,unit)：成功true，失败false；  poll(time,unit)成功返回元素，失败false









https://blog.csdn.net/cmyperson/article/details/79610870

































