
源码地址：[https://github.com/cn-ljb/KotlinBlogs](https://github.com/cn-ljb/KotlinBlogs)


## 类的定义

Kotlin中的类也是使用class关键字定义

但整个类结构与Java有所不同：Kotlin中类的定义主要由三部分组成：类名、类头、类主体。

	//		类名		类头
	class Person(var name: String) {
	
	    //...类主体
	
	}


### 一、类名

同Java紧跟class关键字之后，空格隔开


### 二、类头

两部分组成：

* 成员属性
* 主构造函数

代码示例：

	class Person(var name: String) {
	
	    init {
	        println("属性已被初始化：$name")
	        println("主构造额外操作")
	    }
	
	}

name是Person类的一个成员属性，小括号()是Person类的主构造方法。也就是说Kotlin中在创建Person类对象时，必须传入name属性值，并且会自动为其初始化，帮我们做了类似Java里this.name = name的操作。

如果我们的主构造除了初始化属性之外，还有别的代码操作，需要把这些操作放到init()函数中，Kotlin的主构造函数在初始化属性之后，会调用该函数。

调用：
	
	Person("Jack")

输出：

	属性已被初始化：Jack
	主构造额外操作


### 三、类主体

类主体部分类似于Java（大括号里的东西），但也有所不同，前面的主构造额外函数init()也属于类主体区域内。

类主体可以包含：

* 构造函数和初始代码块
* 成员属性
* 成员函数
* 内部类
* 伴生对象
* 对象声明

#### 1、构造函数

除之前的主构造函数和init()函数外，Kotlin也支持多构造函数，其它的构造函数称为**二级构造函数**

 	constructor (name: String, age: Int) : this(name) {
        this.age = age
    }

这里，Kotlin定义二级构造函数与Java不同：

* 仅需constructor关键字修饰，不需要函数名
* 所有的二级构造必须先调用（代理）主构造，主构造执行完后，会执行二级构造中的代码

所以我们仅在二级构造中初始化了age属性，name属性已由之前的主构造初始化。


如果我们没有定义构造函数（主\二级），JVM虚拟机依旧会为我们提供一个无参构造函数，这和Java一样；但是，如果我们的主构造参数设置了默认值，Jvm虚拟机也会为我们提供一个无参构造。

	//主构造存在默认参数，JVM依旧会提供一个无参构造
	class Person(var name: String = "无名") {
		//...
	｝

所以我们可以使用默认值直接创建Person对象

	val wuMing = Person()

（注：虽然我们已经创建过很多次对象了，但还是提下**Kotlin创建对象是不需要new关键字的**）

#### 2、成员属性

首先概念上是同Java的成员变量一样的，我们还是单独来说说它的区别和特点

Kotlin支持可变属性和只读属性，什么意思呢？类似于Java中常量和变量的概念

* val关键字：只读属性，初始化后，只能访问，不能赋值

* var关键字：可变属性，可读可写

**getter()\setter()**


讲到属性，不能少了JavaBean里的getter()\setter()方法，虽然Java的IDE基本都提供了自动生成代码的快捷选项，但Kotlin将其再一步简化

Kotlin中如果属性是var可变属性，那么默认就提供getter()\setter()函数，val则提供getter()函数

如果我们需要覆写这些getter()\setter()方法只需遵循以下格式： 

	var <propertyName>: <PropertyType> [ = <property_initializer> ]
	    <getter>
	    <setter>

我们尝试修改age属性的getter()方法，不同年龄段，返回不同的数字：

    var age: Int = 0
        get() {
            if (field < 16) {
                return 0
            } else if (field in 16..30) {
                return 1
            } else return 2
        }


调用：

	 val per = Person("Jack", 10)
	 println(per.age) //输出0

等等，getter()里的field是什么？**备用字段**

为什么我们不直接比较age而是用field关键字呢？

因为在Kotlin中规定，**如果直接使用属性名，那么就相当于调用它的getter()函数，所以如果我们在getter函数里直接使用age属性，那么这将是一个无限的递归，JVM自然会抛出 栈溢出**。

为了解决这个问题Kotlin在getter()和setter()函数中为我们提供了这个备用字段**field**


#### 3、成员函数

在函数章节提到过，无论是定义，使用，作用域都同Java，暂一笔带过。

#### 4、内部类

Kotlin中对于**内部类**的定义有所不同，在Kotlin中内部类除了必须定义在类的内部外，还必须通过inner关键字修饰，否则它只是个**嵌套类**。

探讨内部类和嵌套类各自的特点：

![](http://i.imgur.com/ggkCX2L.png)

A是外部类，B是A的嵌套类，C是A的内部类，我们尝试互相访问对方的属性以及函数。

外部类访问嵌套类以及内部类中的资源，我们在A类中添加tell()函数测试：

![](http://i.imgur.com/uuVGYTr.png)

以上代码中可以得出结论：**外部类是无法直接访问嵌套类或者内部类中的任何资源**


那么嵌套类能访问外部类资源吗？我们给嵌套类也加上tell()函数

![](http://i.imgur.com/tJa7cKC.png)

很显然**嵌套类是不能直接调用外部类资源以及内部类资源**


再来看看内部类：

![](http://i.imgur.com/GUDUbsz.png)


Kotlin中的**内部类跟Java一样是可以直接访问外部类资源，但不能访问嵌套类资源**



最后，嵌套类、外部类是如何创建对象的:

	//嵌套类创建对象
    val b = A.B()

    //内部类创建对象
    val c =A().C()


**嵌套类创建对象，需要指定外部类前缀；而内部类创建对象则需要通过外部类对象来创建**

细心的朋友可能已经发现这两种书写格式在Java中很眼熟，内部类的创建格式与Java相同就不说了，这个嵌套类与Java的（static）静态内部类创建语法是一样的。

如果真如我们猜想的那样，Java中的静态内部类，可以访问直接外部类中static修饰的全局变量；Kotlin中的嵌套类我们不妨测试一下。（Kotlin中的全局变量通过伴生对象定义，这个之后会讲，先知道它就是全局变量即可）

![](http://i.imgur.com/Nu2FT0n.png)

我们发现嵌套类可以调用该变量

![](http://i.imgur.com/bTDIsOM.png)

所以可以得出：Kotlin中的嵌套类的使用场景是类似与Java中的静态内部类的使用场景。


#### 5、伴生对象

前面也提到了Kotlin中是没有static关键字的，但是总得有东西来替代它，那么它就是伴生对象。

从名字来看它貌似是随着类一起出生的，它和Java中static的定义也很相似（当类加载时static修饰的属性和方法就直接加载到内存中）。


使用起来也和Java中是一样的，可以直接通过类名调用，还有伴生对象只能使用伴生对象中的属性及方法，就如同static修饰的方法只能调用static修饰的属性和方法一样。

![](http://i.imgur.com/iZVC1jG.png)


#### 6、对象声明与表达式

单例设计模式大家一定再熟悉不过，如果在Java中，肯定首要是创建一个类，然后私有化构造方法等等，Kotlin中提供了更简便的实现方案。你不是仅需一个对象吗，那么干嘛要创建类，直接创建一个对象不是更好。

Kotlin中支持直接创建对象，称为：**对象声明** ，使用object关键字修饰。

我们在之前的Person类中声明一个obj对象

![](http://i.imgur.com/xiu7ii4.png)

那么这个对象对于Person来说就像一个全局的只读变量一样

![](http://i.imgur.com/oz6aRSg.png)

当然你完全可以不用写在类的内部，只是要知道类的主体里可以写对象声明的


**对象表达式**又是什么？

Java中的匿名内部类，想必做android开发的同学再熟悉不过，那么Kotlin中的匿名内部类就叫做对象表达式。

既然类似Java中的匿名内部类，那么使用场景肯定也是类似的了。

![](http://i.imgur.com/81uXwsB.png)

这段代码想必大家都很熟悉，为Button设置点击事件的回调，用Java代码实现最直接的方法便是创建个匿名内部类对象，那么我们来看看Kotlin的对象表达式

![](http://i.imgur.com/c64vF17.png)

我只能说实在是不能再像了（与Java匿名内部类相比）。

唯一的区别就在于，对象表达式可以直接访问局部作用域里的变量，不需要像java那样final修饰才行。

### 四、数据类

最后再讲讲一种特殊的类：**数据类**

前面属性时，提到了JavaBean的概念，提供getter()和setter()函数，除了这两个函数之外equals()、toString()等也是几个常用函数，那么Kotlin中为了开发方便，直接提供data关键字来修饰这些数据类。

使用data修饰的类，除了之前var\val提供的setter()\getter()函数之外，还默认实现了

	equals()/hashCode 函数
	toString 格式是 "User(name=john, age=42)"
	[compontN()] 
	copy() 函数	

如果在类中明确声明或从基类继承了这些方法，编译器不会自动生成。

为确保这些生成代码的一致性，并实现有意义的行为，数据类要满足下面的要求：

* 主构造函数应该至少有一个参数；
* 主构造函数的所有参数必须标注为 val 或者 var ；
* 数据类不能是 abstract，open，sealed，或者 inner ；
* 在 JVM 中如果构造函数是无参的，则所有的属性必须有默认的值。	

例如：

	data class Data(var data1: String, var data2: String)

