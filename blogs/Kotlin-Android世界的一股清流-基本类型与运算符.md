源码地址：[https://github.com/cn-ljb/KotlinBlogs](https://github.com/cn-ljb/KotlinBlogs)

# 基本类型与运算符

本章主要学习Kotlin为我们提供好的基本类型，以及它们之间的运算操作（主要讲解与Java不同的地方）

## 基本类型

类似Java中基本数据类型，Kotlin对Java中8种基本数据类型都做了对应的封装类，我们看看哪些不同部分


### 1、数值类型

进制：不支持8进制

Kotlin官网中说提供了6种数值类的基本类型

	类型		位宽
	
	Double	64
	Float	32
	Long	64
	Int		32
	Short	16
	Byte	8

！！！What? 

Char既然不是数值类型？Char不是可以通过ASCII码直接转换成数值，然后进行运算吗？

是的，Char在Kotlin中不能直接当作数值使用


### 2、字符类型

		//java代码
        char c = 'a';
        System.out.println(c == 97);


也就是说上面的Java代码，在Kotlin里是会报错的，Why？

查看官方这一章节时，并没有看到详细的解释这个问题

那么如果我就是想进行数值判断操作呢，那你必须手动调用toInt()方法才行
	
	//kotlin
 	val c: Char = 'a'
    println(c.toInt() == 97)


那Kotlin中能否直接使用Char类型直接进行运算，很神奇，他既然能...

只是得到的结果类型与Java不同：

	System.out.println(c + 1);	//java 	 返回 98
	println(c + 1)				//kotlin 返回 b

* java中 c+1 返回的是int类型，因为int（32位）大于char(16位)，所以精度由小转为大，最终为int型。
* Kotlin中 Char类型由于不是直接当数值用，所以最后返回依旧是char型（...尼玛，我都不知道怎么解释，望高手赐教）。

### 3、布尔类型

跟Java一样


### 4、类型推导

Kotlin中对于定义的变量，如果能通过初始化的字面值推断出它的类型，那么定义变量时，可以省略声明类型的语句

	//通过后面的1可以推导出是Int型数据，所以可以写成num2的形式
 	val num1 : Int = 1  
    val num2 = 2


### 5、数值类型装箱

首先，Java中的装箱指，将基本数据类型转换为对应的引用数据类型（int -> Integer）,Kotlin中并没有什么Integer等装箱后的类，那么Kotlin中数据类型装箱又指的是什么？

 	val num1：Int = 128
    val num2: Int = num1
    val num3: Int = num1

    println(num2 == num3)   //true
    println(num2 === num3)  //true

（ == 比较值是否相等  ， === 比较内存地址是否相等）

到现在为止，貌似一切都正常，赋值时是以内存地址的形式直接进行操作的，内存地址相同，里面的值自然相同。那么，我们稍微改动下：

    val num1: Int = 128
    val num2: Int? = num1
    val num3: Int? = num1

    println(num2 == num3)   //true
    println(num2 === num3)  //false


我们在num2、num3的定义后面加上了？号，该符号表示当前定义的对象可以为null，显然Int类型只是用来存储数值的，null这种非数值类型的值需要支持，那么就需要装箱操作

装箱后的数据类型除了值之外，内存地值会重新开辟(官方用语：不保留特征)，所以当比较num2 === num3时返回的是false。

对于学习过Java的同学来说，我们可以这样理解，没加？号是java里的int，加上？号就成了java里的Integer

(可能有同学在这里测试时使用了小于128的数字，导致===返回的也是true，这并不是Kotlin语言的问题，而是JVM虚拟机中维护着有符号整形常量池（-128,127），在这个范围里的数值都会直接使用常量池的内存地址，所以这个范围内的数值装箱后比较内存地址依旧是相等的，想更详细了解JVM中的常量池，可自行百度，点到为止（跑题了）。


### 6、高精度转型问题

Java中精度低的数据赋值给精度高的数据是支持的（隐式转换）

		//java 支持
 		int numInt =  1;
        long numLong = numInt;

而Kotlin中不支持这种隐式转换形式的

![](http://i.imgur.com/7CKGZtr.png)

如果你确定可以转换，需要你手动去显示的调用.toLong()进行转型

 	val numInt: Int = 1
    val numLong: Long = numInt.toLong() 

Why? Kotlin不是更高效、更优雅吗？怎么连隐式转换都不支持

官方给出了解释，大概意思是：

	//如果Kotlin支持隐式转换会怎么样（伪代码，实际会报错）
    val a : Int？  = 1
    val b : Long？ = a

    println(a == b) //false

为什么a==b会是false？前面说了Kotlin中的双等于是比较值是否相等，这句话说的并不严谨，其实Kotlin里的双等于在这里实际调用的是Java中Long中equals()方法，我们看看Java中Long方法的实现：

![](http://i.imgur.com/NkBcv0G.png)

原来如果传入比较的参数不是Long类型，那么直接返回false，这样明显违反了我们对==号的定义（比较值是否相等），所以Kotlin认为这种情况是不对的，即禁止了隐式转换这个特性。


最后，每个数值类型都支持下面显示的类型转换：

	toByte(): Byte
	toShort(): Short
	toInt(): Int
	toLong(): Long
	toFloat(): Float
	toDouble(): Double
	toChar(): Char


### 7、字符串类型（String）

与Java相同的部分我们就不说了，说说不同处

1、支持索引访问

 	
	val str = "abcdef"

    println(str[0])

2、支持索引了，当然支持直接遍历，索引遍历就不提了，你可以这样写：（类似Java里的迭代器for循环）

	for (c in str) {
	    println(c)
	}

3、支持段落字符串，在java里字符串中换行必须使用\n或者\r\n，Kotlin直接提供了段落字符串的书写格式

    val lines = """
	line1
	line2
	line3
	"""
    println(lines)

4、字符串模版（Java拼接字符串的繁琐想必大家都有体会吧）

	val a = 1
    val b = 2.5
    val c = true
    println("a=$a , b=$b , c=$c")

这里是直接打印对象，如果想打印对象里的属性或者方法的返回值，使用模版表达式即可：

	val s = "123456"
    println("size=${s.length}")



## 运算符

Kotlin支持标准的算数运算表达式，并且这些运算符被声明为相应类的成员（函数）。

也就是说Kotlin不光支持算数运算符，并且为每种算数运算符都提供一个相应的函数，例如下面的代码：
	
	// plus就是 + 运算对应的函数
	val num : Int  = 10
    println(num +1)
    println(num.plus(1))

那么常用的运算符对应函数有哪些呢？

	a + b	a.plus(b)
	a - b	a.minus(b)
	a * b	a.times(b)
	a / b	a.div(b)
	a % b	a.rem(b), a.mod(b) (deprecated)
	a..b	a.rangeTo(b)


其他的运算符函数，建议直接[查看官网](http://kotlinlang.org/docs/reference/operator-overloading.html)


值得注意的是Kotlin并没有向java那样提供一种特殊符号来表示**位运算**，取而代之的是一种名为**中缀运算符**的形式

 	//java 位运算符（有符号右移）
    System.out.println(2>>1);

    //Kotlin位运算符（有符号右移）
    println(2 shr 1)

位运算符对照表：

	shl(bits) – 有符号左移 (相当于 Java’s <<) 
	shr(bits) – 有符号右移 (相当于 Java’s >>) 
	ushr(bits) – 无符号右移 (相当于 Java’s >>>) 
	and(bits) – 按位与 
	or(bits) – 按位或 
	xor(bits) – 按位异或 
	inv(bits) – 按位翻转



### 中缀运算符究竟是什么？
	
前面我们说普通的算数运算符都有对应的函数，那中缀运算符呢？

其实，中缀运算符就是函数，一种特殊的函数，并且我们还可以自定义中缀符

满足以下条件的函数就可以使用中缀符调用：

>**它们是成员函数或者是扩展函数，只有一个参数，使用infix关键词进行标记**

扩展函数，到函数里我们再进行讲解。

>现在，我们模拟坐标数据类型，并且支持同类型之间添加操作，添加操作结果为对应x,y累加，例如：

	//伪代码
	Point(1,2) add Point(2,2)  得到 Point（3，4）

开始创建这个类型：

	package com.ljb.blogs.base.point
	/**
	 * 坐标数据类
	 */
	data class Point(val x: Int, val y: Int)

是的，Kotlin定义一个数据类就是这么简单，初学者看到这里多多少少会有些蒙圈，没事我们后面再细讲。

数据类有了，为了满足累加功能，我们为它提供一个add()函数

	/**
     * 累加方法，返回累加后的Potint
     */
    fun add(other: Point): Point {
        return Point(this.x+ other.x , this.y+other.y)
    }

方法添加好后，我们测试也没有问题

	val point1 = Point(1, 2)
    val point2 = Point(2, 2)
    val result = point1.add(point2)
    println(result)

但是并不是我们想要的中缀运算符的形式，那我们还差什么？ **infix关键词**

加上infix关键词后，我们再试试

 	val point1 = Point(1, 2)
    val point2 = Point(2, 2)
    val result = point1 add point2
    println(result)

到这里，中缀运算符的由来也就介绍完了，贴上Point完整代码，或者在博客头部的GitHub地址里下载最新源码

	package com.ljb.blogs.base.point
	
	/**
	 * 坐标数据类
	 */
	data class Point(val x: Int, val y: Int) {
	
	    /**
	     * 累加方法，返回累加后的Potint
	     */
	    infix fun add(other: Point): Point {
	        return Point(this.x + other.x, this.y + other.y)
	    }
	
	    override fun toString(): String {
	        return "Point($x, $y)"
	    }
	
	}