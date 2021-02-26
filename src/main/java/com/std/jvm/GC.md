https://www.cnblogs.com/onlinemf/p/7040378.html  
https://blog.csdn.net/lwang_IT/article/details/78650168  
**1.那些对象需要回收**  
　　java内存模型中分为五大区域已经有所了解。我们知道程序计数器、虚拟机栈、本地方法栈，由线程而生，随线程而灭，其中栈中的栈帧随着
方法的进入顺序的执行的入栈和出栈的操作，一个栈帧需要分配多少内存取决于具体的虚拟机实现并且在编译期间即确定下来【忽略JIT编译器做
的优化，基本当成编译期间可知】，当方法或线程执行完毕后，内存就随着回收，因此无需关心。而Java堆、方法区则不一样。方法区存放着类加
载信息，但是一个接口中多个实现类需要的内存可能不太一样，一个方法中多个分支需要的内存也可能不一样【只有在运行期间才可知道这个方法
创建了哪些对象没需要多少内存】，这部分内存的分配和回收都是动态的，gc关注的也正是这部分的内存。  
　　Java堆是GC回收的“重点区域”。堆中基本存放着所有对象实例，gc进行回收前，第一件事就是确认哪些对象存活，哪些死去[即不可能再被引用]  
　　判断对象是否存活算法：  
　　1.引用计数算法  
　　早期判断对象是否存活大多都是以这种算法，这种算法判断很简单，简单来说就是给对象添加一个引用计数器，每当对象被引用一次就加1，
引用失效时就减1。当为0的时候就判断对象不会再被引用。  
　　优点:实现简单效率高，被广泛使用与如python何游戏脚本语言上。  
　　缺点:难以解决循环引用的问题，就是假如两个对象互相引用已经不会再被其它其它引用，导致一直不会为0就无法进行回收。  　  
　　2.可达性分析算法  
　　目前主流的商用语言[如java、c#]采用的是可达性分析算法判断对象是否存活。这个算法有效解决了循环利用的弊端。  
　　它的基本思路是通过一个称为“GC Roots”的对象为起始点，搜索所经过的路径称为引用链，当一个对象到GC Roots没有任何引用跟它连接则
证明对象是不可用的。  　　
　　
　　可作为GC Roots的对象有四种:  
　　1. 虚拟机栈（栈帧中的本地变量表）中的引用对象。  
　　2. 方法区中的类静态属性引用的对象，一般指被static修饰的对象，加载类的时候就加载到内存中。  
　　3. 方法区中的常量引用的对象。  
　　4. 本地方法栈中JNI（native方法）引用的对象。  
　　要真正宣告对象死亡需经过两个过程。
　　1.可达性分析后没有发现引用链
　　2.查看对象是否有finalize方法，如果有重写且在方法内完成自救[比如再建立引用]，还是可以抢救一下，注意这边一个类的finalize只执行
一次，这就会出现一样的代码第一次自救成功第二次失败的情况。[如果类重写finalize且还没调用过，会将这个对象放到一个叫做F-Queue的
序列里，这边finalize不承诺一定会执行，这么做是因为如果里面死循环的话可能会时F-Queue队列处于等待，严重会导致内存崩溃，这是我们
不希望看到的。]   
　　JVM运行时内存：heap  
　　Young 1/3  【Eden 8/10  survivorFrom 1/8  survivorTo 1/8】  
　　Old　 2/3  

**2.什么时候需要回收**  
　　safepoint(安全点)：  
　　1:循环的末尾   
　　2:方法临返回前/调用方法的call指令后   
　　3:可能抛异常的位置   
**3.怎么回收**  
https://www.cnblogs.com/fangfuhai/p/7203468.html?utm_source=itdadao&utm_medium=referral  
　　垃圾回收算法：  
　　1. 标记/清除算法 mark-sweep 
　　2. 复制算法  copying
　　3. 标记/整理算法 mark-compact  
　　4. 分代收集算法  
　　垃圾回收器：  
　　1.年轻代收集器：Serial,ParNew,Parallel Scavenge  
　　2.老年代收集器：Serial Old,Parallel Old,CMS收集器  
　　3.特殊收集器：G1收集器  
　　几个概念：  
　　1.Minor GC：在年轻代Young space(包括Eden区和Survivor区)中的垃圾回收称之为 Minor GC,Minor GC只会清理年轻代。  
　　2.Major GC:Major GC清理老年代(old GC)，但是通常也可以指和Full GC是等价，因为收集老年代的时候往往也会伴随着升级年轻代，
收集整个Java堆。所以有人问的时候需问清楚它指的是full GC还是old GC。  
　　3.Full GC:full gc是对新生代、老年代、永久代【jdk1.8后没有这个概念了】统一的回收。【知乎R大的回答:收集整个堆，包括young gen
、old gen、perm gen（如果存在的话)、元空间(1.8及以上)等所有部分的模式】  
　　4.mixed GC【g1特有】:混合GC,收集整个young gen以及部分old gen的GC。只有G1有这个模式。  

**几个疑问**  
  
1.GC是怎么判断对象是被标记的  
  
　　通过OOPMap把栈上代表引用的位置全部记录下来，避免全栈扫描，加快枚举根节点的速度，除此之外还有一个极为重要的作用，可以帮HotSpot
实现准确式GC【这边的准确关键就是类型，可以根据给定位置的某块数据知道它的准确类型，HotSpot是通过oopMap外部记录下这些信息，存成映射
表一样的东西】  
  
2.什么时候触发GC  
  
　　minor GC(young GC):当年轻代中eden区分配满的时候触发[值得一提的是因为young GC后部分存活的对象会已到老年代(比如对象熬过15轮)，
所以过后old gen的占用量通常会变高]  
　　full GC:  
　　①手动调用System.gc()方法 [增加了full GC频率，不建议使用而是让jvm自己管理内存，可以设置-XX:+ DisableExplicitGC来禁止RMI调用System.gc]  
　　②发现perm gen（如果存在永久代的话)需分配空间但已经没有足够空间  
　　③老年代空间不足，比如说新生代的大对象大数组晋升到老年代就可能导致老年代空间不足。  
　　④CMS GC时出现Promotion Faield[pf]  
　　⑤统计得到的Minor GC晋升到旧生代的平均大小大于老年代的剩余空间。这个比较难理解，这是HotSpot为了避免由于新生代晋升到老年代导
致老年代空间不足而触发的FUll GC。比如程序第一次触发Minor GC后，有5m的对象晋升到老年代，姑且现在平均算5m，那么下次Minor GC发生
时，先判断现在老年代剩余空间大小是否超过5m，如果小于5m，则HotSpot则会触发full GC(这点挺智能的)  
　　Promotion Faield:minor GC时 survivor space放不下[满了或对象太大]，对象只能放到老年代，而老年代也放不下会导致这个错误。 
　　Concurrent Model Failure:cms时特有的错误，因为cms时垃圾清理和用户线程可以是并发执行的，如果在清理的过程中  
　　可能原因：  
　　①.cms触发太晚，可以把XX:CMSInitiatingOccupancyFraction调小[比如-XX:CMSInitiatingOccupancyFraction=70 是指设定CMS在对内
存占用率达到70%的时候开始GC(因为CMS会有浮动垃圾,所以一般都较早启动GC)]  
　　②.垃圾产生速度大于清理速度，可能是晋升阈值设置过小，Survivor空间小导致跑到老年代，eden区太小，存在大对象、数组对象等情况  
　　③.空间碎片过多，可以开启空间碎片整理并合理设置周期时间  
　　full gc导致了concurrent mode failure，而不是因为concurrent mode failure错误导致触发full gc，真正触发full gc的原因可能是
ygc时发生的promotion failure。  
  
3.cms垃圾收集器是否会扫描年轻代  
　　会，在初始标记的时候会扫描新生代。虽然cms是老年代收集器，但是我们知道年轻代的对象是可以晋升为老年代的，为了空间分配担保，还
是有必要去扫描年轻代。  
  
4.什么是空间分配担保  
　　在minor gc前，jvm会先检查老年代最大可用空间是否大于新生代所有对象总空间，如果是的话，则minor gc可以确保是安全的，如果担保失
败,会检查一个配置(HandlePromotionFailire),即是否允许担保失败。如果允许:继续检查老年代最大可用可用的连续空间是否大于之前晋升的平
均大小，比如说剩10m，之前每次都有9m左右的新生代到老年代，那么将尝试一次minor gc(大于的情况)，这会比较冒险。如果不允许，而且还小
于的情况，则会触发full gc。【为了避免经常full GC 该参数建议打开】这边为什么说是冒险是因为minor gc过后如果出现大对象，由于新生代
采用复制算法，survivor无法容纳将跑到老年代，所以才会去计算之前的平均值作为一种担保的条件与老年代剩余空间比较，这就是分配担保。
这种担保是动态概率的手段，但是也有可能出现之前平均都比较低，突然有一次minor gc对象变得很多远高于以往的平均值，这个时候就会导致担
保失败【Handle Promotion Failure】，这就只好再失败后再触发一次FULL GC。  
  
5.为什么复制算法要分两个Survivor，而不直接移到老年代  
　　这样做的话效率可能会更高，但是old区一般都是熬过多次可达性分析算法过后的存活的对象，要求比较苛刻且空间有限，而不能直接移过去，
这将导致一系列问题(比如老年代容易被撑爆)分两个Survivor(from/to)，自然是为了保证复制算法运行以提高效率。  

  
**垃圾收集相关常用参数**  
UserSerialGC:虚拟机运行在client模式下的默认值，打开此开关后，使用Serial+Serial Old的收集器组合进行内存回收  
UserParNewGC：打开此开关后，使用ParNew+Serial Old的收集器组合进行内存回收
UserConcMarkSweepGC：打开此开关后，使用ParNew+CMS+Serial Old的收集器组合进行内存回收。Serial Old将作为CMS出现Concurrent Mode 
Failure失败后的备用收集器使用
UseParallelGC：虚拟机运行在Server模式下的默认值，打开开关后，使用Parallel Scavenge + Serial Old（ps Mark-Sweep）的收集器进行
内存回收
UseParallelOldGC:打开此开关后，使用Parallel Scavenge + Parallel Old的收集器组合进行内存回收
  
SurvivorRadio:新生代中Eden区域与Survivor区域的容量的比例，默认为8，代表Eden：Survivor=8:1  
PretenureSizeThreshold：直接晋升到老年代的对象大小，设置这个参数后，大于这个参数的对象将直接在老年代分配  
MaxTenuringThreshold:晋升到老年代的对象大小，设置这个参数后，大于这个参数的对象将直接在老年代分配  
UseAdaptiveSizePolicy:动态调整java堆中各个区域的大小以及进入老年代的年龄   
HandlePromotionFailure：是否允许分配担保失败，即老年代的剩余空间不足以应对新生代的整个Eden和Survivor区的所有对象都存活的极端现象  
ParallelGCThreads:设置并行GC时进行内存回收的线程数  
  
GCTimeRadio:GC时间占总时间的比例，默认值为99，即允许1%的GC时间。仅在使用parallel Scavenge收集器时生效  
MaxGCPauseMills：设置GC的最大停顿时间，仅在使用Parallel Scavenge收集器时生效  
CMSInitiatingOccupyFraction：设置CMS收集器在老年代空间被使用多少次以后触发垃圾收集，默认值为68%，只在使用CMS收集器时生效  
UseCMSCompactAtFullCollection：设置在CMS收集器在完成垃圾收集后是都要进行一次内存碎片整理，仅在使用CMS收集器时生效  
CMSFullGCsBeforeCompact：设置CMS收集器在进行若干次垃圾收集后再启动一次内存碎片整理。仅在使用CMS收集器时生效  

   
**7.stop the world具体是什么，有没有办法避免**  
　　stop the world简单来说就是gc的时候，停掉除gc外的java线程。无论什么gc都难以避免停顿，即使是g1也会在初始标记阶段发生，stw并
不可怕，可以尽可能的减少停顿时间。　　
  
**8.新生代什么样的情况会晋升为老年代**  
　　对象晋升规则  
　　1.长期存活的对象进入老年代，对象每熬过一次GC年龄+1(默认年龄阈值15，可配置)。  
　　2.对象太大新生代无法容纳则会分配到老年代  
　　3.eden区满了，进行minor gc后，eden和一个survivor区仍然存活的对象无法放到(to survivor区)则会通过分配担保机制放到老年代，这种
情况一般是minor gc后新生代存活的对象太多。  
　　4.动态年龄判定，为了使内存分配更灵活，jvm不一定要求对象年龄达到MaxTenuringThreshold(15)才晋升为老年代，若survior区相同年龄对
象总大小大于survior区空间的一半，则大于等于这个年龄的对象将会在minor gc时移到老年代。  
  
**8.怎么理解g1，适用于什么场景**  
　　G1 GC 是区域化、并行-并发、增量式垃圾回收器，相比其他 HotSpot 垃圾回收器，可提供更多可预测的暂停。增量的特性使 G1 GC 适用于
更大的堆，在最坏的情况下仍能提供不错的响应。G1 GC 的自适应特性使 JVM 命令行只需要软实时暂停时间目标的最大值以及 Java 堆大小的最
大值和最小值，即可开始工作。  
　　g1不再区分老年代、年轻代这样的内存空间，这是较以往收集器很大的差异，所有的内存空间就是一块划分为不同子区域，每个区域大小为
1m-32m，最多支持的内存为64g左右，且由于它为了的特性适用于大内存机器。 
　　适用场景:  
　　1.像cms能与应用程序并发执行，GC停顿短【短而且可控】，用户体验好的场景。  
　　2.面向服务端，大内存，高cpu的应用机器。【网上说差不多是6g或更大】  
　　3.应用在运行过程中经常会产生大量内存碎片，需要压缩空间【比cms好的地方之一，g1具备压缩功能】。  
 
　　

