源码地址：[https://github.com/cn-ljb/KotlinBlogs](https://github.com/cn-ljb/KotlinBlogs)


# 委托

## 一、委托类

什么是委托类？

代理设计模式，在Java中实现一个简单的代理模式如下：
	
	//抽象功能
	public interface Base {
	
	    void doSome();
	
	}
	
	//实际操作类
	public class BaseImpl implements Base {
	
	    @Override
	    public void doSome() {
	        System.out.println("java do some thing.");
	    }
	}
	
	//代理类
	public class BaseProxy implements Base {
	
	    private Base impl;
	
	    public BaseProxy(Base impl) {
	        this.impl = impl;
	    }
	
	    @Override
	    public void doSome() {
	        impl.doSome();
	    }
	}

Kotlin在语法上提供by关键字，所以实现以上代码，可以更简洁点：

	interface Base {
	    fun doSome()
	}
	
	
	class BaseImpl : Base {
	
	    override fun doSome() {
	        println("do some thing...")
	    }
	
	}
	
	class BaseProxy(val b: Base) : Base by b


当然，你可以在代理中扩展doSome函数，只需覆写它即可

	class BaseProxy(val b: Base) : Base by b{
	
	    override fun doSome() {
	        println("pre")
	        b.doSome()
	        println("next")
	    }
	}


## 二、委托属性

委托类并不是重点，主要看看委托属性。

什么是委托属性？

就如同代理设计模式一样 在调用属性或者赋值属性值时，我们希望做些额外操作，Kotlin基本语法如下：

	val/var <property name>: <Type> by <expression>

 by 后面的表达式就是代理，因为get() set() 对应的属性会被 getValue() setValue() 方法代理。属性代理不需要任何接口的实现，但必须要提供 getValues() 函数(如果是 var 还需要 setValue())。


例如：
	
	class Person() {
	
	    var name: String by PrefixName()
	
	    constructor(name: String) : this() {
	        this.name = name
	    }
	
	}
	
	
	class PrefixName {
	
	    private var proxyName: String = "admin_"
	
	    operator fun getValue(person: Person, property: KProperty<*>): String {
	        return "admin_$proxyName"
	    }
	
	    operator fun setValue(person: Person, property: KProperty<*>, s: String) {
	        proxyName = s
	    }
	
	}

我们将Person的name属性委托给了PrefixName类，该类提供了该属性新的getValue()和setValue()函数，将来在使用Person的name属性时，实际访问的是该类中的代理。

    //代理属性
    val per = Person("Jake")
    println(per.name)
    per.name = "haha"
    println(per.name)

输出：

	admin_Jake
	admin_haha

很多同学可能会纳闷，输出结果中可以看出一直使用的是PrefixName中的返回的属性，那Person中定义的name成员有什么意义，不能直接操作吗？

其实，Person中是没有name属性的，定义语句只是Kotlin中的语法格式罢了。不信？我们反编译字节码，看看Java代码中到底有些什么东西。

反编译为Java代码：

![](http://i.imgur.com/9nPrS2i.png)

虽然Java代码里存在setter和getter方法，但是并没有name属性。也就是说我们的猜想是没有错的，Person中的name属性被代理后，实际Person类中并不存在该属性，从而替代它的是**代理对象**。

无论是Person中的setter还是getter，实际调用的都是代理对象中setValue，getValue ，不妨看下反编译后的代理属性类是什么样子的：

![](http://i.imgur.com/80sMZMC.png)

发现除了我们自己写的代码外，只多了几行检查参数的代码，也就是说Kotlin强制要求代理属性里的参setValue()\getValue()参数数必须不为null，如果参数为null，则抛出异常。

![](http://i.imgur.com/kowXTAT.png)


注意：**被定义为代理的属性，该类中实际是不存在的**

理解这句话，不要被Kotlin的语法所迷惑。

### 标准委托属性

我们除了可以自定义委托属性外，Kotlin也为我们提供了集中常用的委托属性。


#### 1、延迟属性

lazy()

该委托可以保证属性在使用是才被初始化出来

例如：

    val str: String by lazy { "abc" }
    

定义时，在lazy后面的Lambda表达式进行初始化操作即可。细心的朋友也已经注意到lazy其实就是一个接收函数的函数（可以直接传递Lambda表达式）。

![](http://i.imgur.com/1I6i5nN.png)


貌似有点不对啊，委托属性by关键字之后写的对象类，不是应该提供setValue()\getValue()函数吗？
那么我们不妨继续看下lazy()函数，发现其返回的是Lazy的一个对象。

![](http://i.imgur.com/T7D79uJ.png)

然而Lazy是个接口，那么实际SynchronizedLazyImpl应该是Lazy中的子类对象，那么这个子类对象有setValue（）\getValue()函数吗

![](http://i.imgur.com/8mTHHI5.png)

我们发现子类对象，也没有这两个函数，这不科学啊，不满足定义一个委托属性的规则啊。先别急，别忘了，Kotlin中有个神奇的特性：扩展函数。

![](http://i.imgur.com/sFBIp1x.png)

原来Lazy的getValue()是通过扩展函数实现的，那么setValue()呢？很遗憾，并没有，也就是说lazy委托只能给只读属性（val）使用：**使用时初始化，初始化之后不能再进行赋值**（当然你可以手动给Lazy添加一个扩展setValue()函数，但是如果这样便曲解了lazy委托的意义）。

既然这样，我们不妨在看看lazy是怎么实现的，其实让我们自己实现也完全没问题吧。

继续看实际的Lazy实现类 SynchronizedLazyImpl类， 注意里面的value属性的getter()函数，扩展的getValue()函数返回的就是这个属性。

![](http://i.imgur.com/GJyzjgK.png)

使用起来也跟普通的属性没有任何区别

	//...没使用前是不会被初始化的
    println(str)  //输出：abc


如果该属性需要多线程访问，为了保证线程安全，lazy()函数提供了两个参数的重载函数：

	//多线程安全
    val str2: String by lazy(LazyThreadSafetyMode.PUBLICATION) { "bcd" }



#### 2、可观察属性

Delegates.observable() 

有点类似于观察者设计模式，当属性值发生改变时，发出事件并做出响应事件。

怎么用呢？
	
	class Person() {
	
	    var name: String by PrefixName()
	
	    var age: Int by Delegates.observable(0, {
	        property, oldValue, newValue ->
	        println("属性名：${property.name} , 旧值：$oldValue ,  新值：$newValue")
	    })
	
	    constructor(name: String) : this() {
	        this.name = name
	    }
	
	}

我们为Person类添加一个age属性，该属性使用了可观察委托，第一个参数是属性初始值，一但属性值发生改变，第二个参数里的Lambda（函数）就会被调用。

调用：

	per.age = 10

输出：

	属性名：age , 旧值：0 ,  新值：10


那么底层的实现代码过程就不带大家看了，套路是一样，by之后的对象类提供setValue()\getValue()函数，贴出主要部分，具体的实现感兴趣的同学按照这个套路看源码即可，不难~

![](http://i.imgur.com/VdXjgqV.png)


#### 3、Map存储属性

map 

该委托用于将map中存储的属性读取到Mode中，Kotlin官方考虑到如果存在一个Map集合，并且该集合的键值对对应Mode中的属性，那么可以使用该委托（实用性有待考察）。

这个就不讲了，个人感觉有点鸡肋，Json解析有各种解析工具（Gson、fastjson等），可能是个人使用场景使用不到吧，上一个官方demo结束。

	
	class User(val map: Map<String, Any?>) {
	    val name: String by map
	    val age: Int     by map
	}

在这个例子中，构造函数接受一个 map :

	val user = User(mapOf(
	    "name" to "John Doe",
	    "age"  to 25
	))


var 属性可以用 MutableMap 代替只读的 Map：

	class MutableUser(val map: MutableMap<String, Any?>) {
	    var name: String by map
	    var age: Int     by map
	}



## 总结

除了了解常用的委托外，最主要的还是理解一个委托它究竟做了什么，怎么实现的？哪里不明白，反编译看Java源码可能会更直接，不得不承认Kotlin的语法糖太多，有些地方真的阅读起来比较晦涩，不过没事，谁让咱们懂Java。

