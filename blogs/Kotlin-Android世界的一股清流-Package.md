源码地址：[https://github.com/cn-ljb/KotlinBlogs](https://github.com/cn-ljb/KotlinBlogs)

# Package

### 命名规则（同Java）

>由小写字母、下划线、数字组成，必须由小写字母或者下划线开头
>
>行业规范，同Java，例如：com.baidu.xxx


### 与Java不同

* 1、Kotlin中包名可以和实际的文件夹不一致

* 2、没有指定包名，属于没有名字的默认包上

* 3、如果有重名包可以使用 as 关键字设置别名


#### 1、Kotlin中包名可以和实际的文件夹不一致

这条很好理解，我们知道Java中包名和实际的文件目录是保持一致，如果不一致，编译器会报错并提醒你修改包名或者移动到正确的包下。那么在Kotlin中就没有这么严格的限制，包名和实际的物理地址是可以不一致的。

![](http://i.imgur.com/CSgzMay.png)

a包下，存在一个Java文件和Kotlin文件，如果我们手动将文件的package修改为

	com.ljb.blogs.packages.b

Java文件通不过编译，需要你修改包名或者移动到正确的包下

![](http://i.imgur.com/OgSeTXm.png)

而Kotlin，编译器会友善的给个提醒找不到实际目录，当然你可以选择忽视

![](http://i.imgur.com/G8ktu2s.png)


（注：虽然可以包名与目录不一致，但开发者们了解下即好，以前该怎么写还是怎么写）


#### 2、没有指定包名，属于没有名字的默认包上

这一条其实也好理解，Kotlin不声明包行吗？答案是行的
	
	/**
	 * 这是一个没有指定包名的Kotlin类
	 */
	class DefaultKotlin{
	
	    fun tell(){
	        println("我是没有定义包名的DefaultKotlin")
	    }
	}


上面这个DefaultKotlin类，把他放到任意包下是不会报错的，即便它没有指定包。

如何使用它呢？跟Java一样，通过import的关键字直接导入即可

	package com.ljb.blogs.packages.b //包名和实际的文件目录可以不同
	
	import DefaultKotlin
	
	fun main(args: Array<String>) {

		val def = DefaultKotlin()
		def.tell()

	}


	   
#### 3、如果有重名包可以使用 as 关键字设置别名

在Java中，如果你要同时使用类名相同，包名不同的两个类，你必须明确的指定出你使用的是哪个包下的类（以包名.类名的形式声明，至少申明其中一个）。


我们在c包，和d包下都定义了Person的Java类

![](http://i.imgur.com/7DLHvDK.png)
	

c包下：	

	package com.ljb.blogs.packages.c;
	
	public class Person {
	
	    public void tell(){
	        System.out.println("我来自c包");
	    }
	}

d包下：

	package com.ljb.blogs.packages.d;
	
	public class Person {
	
	    public void tell(){
	        System.out.println("我来自d包");
	    }
	}



那么Java中是这样使用：


	package com.ljb.blogs.packages.a;   //包名和实际的文件目录必须相同
	
	import com.ljb.blogs.packages.c.Person;
	
	/**
	 * Package Java测试类
	 */
	public class PackageJava {
	
	    public static void main(String[] args){
	
	        //两个同名类，至少你得写一个完成包名的类来进行区分
	        Person c = new Person();
	        com.ljb.blogs.packages.d.Person d = new com.ljb.blogs.packages.d.Person();
	
	        c.tell();
	        d.tell();
	    }
	
	}

首先，Java的这种形式Kotlin也是支持的。

其次，Kotlin提供了一种更好的解决方案，让代码看起来更优雅，使用as关键字为包设置别名

	package com.ljb.blogs.packages.b //包名和实际的文件目录可以不同
	
	import com.ljb.blogs.packages.c.Person as CPerson	//设置别名
	import com.ljb.blogs.packages.d.Person as DPerson
	
	/**
	 * Package Kotlin测试类
	 */
	fun main(args: Array<String>) {
	 	val c = CPerson()
	    val d = DPerson()
	
	    c.tell()
	    d.tell()
	}
	
细心的朋友可能已经发现，我们直接通过Kotlin代码，造了两个Java类的对象（Person），是的，你没猜错，Kotlin可以直接调用Java代码。
	

### 其他细节

前面提到了improt关键字，跟Java一样都用于导入包，那么java中会默认导入java.lang包，Kotlin又导入哪些包呢？

Kotlin默认导入的包：

>-- kotlin.*
>
>-- kotlin.annotation.*
>
>-- kotlin.collections.*
>
>-- kotlin.comparisons.* (since 1.1)
>
>-- kotlin.io.*
>
>-- kotlin.ranges.*
>
>-- kotlin.sequences.*
>
>-- kotlin.text.*

根据平台的不同，导入的平台包：

JVM:
>---- java.lang.*
>
>---- kotlin.jvm.*

 JS:
>---- kotlin.js.*

这些包都分别是什么，有什么用呢？其实看名字我们也能猜出部分包的功能（笔者暂时也没具体的研究这些包的功能，先一笔带过）。





