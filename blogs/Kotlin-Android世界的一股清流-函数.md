源码地址：[https://github.com/cn-ljb/KotlinBlogs](https://github.com/cn-ljb/KotlinBlogs)


## 函数定义

什么是函数...不用解释了吧...

###  函数范围

Kotlin 中可以在文件顶级声明函数，这就意味者你不用像在Java一样创建一个类来持有函数。除了顶级函数，Kotlin 函数自然也可以声明为局部的，作为成员函数或扩展函数。

### 定义一个函数

一个简单的方法定义：

	fun add(n1: Int, n2: Int): Int {
	
	    return n1 + n2
	}

基本格式：

	fun 函数名(形参1: 参数类型 , 形参2: 参数类型...) <: 返回值类型>{
	    函数体...
	}


>fun关键字定义
>
>参数变量与参数类型之间用冒号隔开，多个参数逗号隔开；
>
>无返回值的话，返回值类型可以省略；否则紧跟函数()括号之后，冒号隔开

### 与Java不同点：

#### 1、形参可以设置默认值


	fun add(n1: Int = 0, n2: Int = 0, n3: Int = 0): Int {
	    return n1 + n2 + n3
	}

好处：优雅的实现了Java方法重载特性

我们可以直接这样调用代码

 	println(add(1, 2))
    println(add(1, 2, 3))

不用像Java那样重写个方法，给某个参数传个默认值或者null了。



#### 2、指定命名参数

如果，我只想传入第1个参数和第3个参数呢？

Kotlin中提供了命名参数的形式，来传递指定的参数

 	println(add(1, n3 = 4))	 //跳过第2个参数

我们可以直接指定形参变量名传递参数，从而准确的传递参数到对应形参上

#### 3、Unit 无返回值

同Java void一样，Kotlin提供Unit表示无返回值，只不过一般都省略不写

	fun tell(str: String): Unit {
	    println(str)
	}
	
	//Unit省略不写
	fun tell(str: String) {
	    println(str)
	}

#### 4、可变参数

类似java中的可变参数，通过vararg关键字修饰

	fun addMore(vararg arr: Int): Int {
	    var result = 0
	    for (num in arr) {
	        result += num
	    }
	    return result
	}

调用

	 println(addMore(1,2,3,4,5))  //15

## 函数的调用

同Java


## 函数分类

### 1、单表达式函数

当函数只返回单个表达式时，大括号可以省略并在 = 后面定义函数体

诺编译器能推断出表达式返回的数据类型，返回值类型也可以省略

上面的add方法，就可改为:

	fun add(n1: Int = 0, n2: Int = 0, n3: Int = 0) = n1 + n2 + n3


### 2、成员函数

没什么好讲的，同Java定义在类成员上的函数

	class Person{
	
	    fun tell(){
	        println(".......")
	    }
	}


### 3、局部函数

Kotlin支持函数中定义函数

	fun outFun(): Int {
	    var n = 1
	
	    fun inFun(): Int {
	        n += 1
	        return n
	    }
	
	    return inFun()
	}


局部函数有什么用？实现闭包

什么是闭包：函数内包含子函数，并最终return子函数。


### 4、中缀函数

在[运算符章节](http://blog.csdn.net/qq1026291832/article/details/73326200)已经提到过，infix关键字修饰，这里不再细讲


### 5、泛型函数

同Java不介绍了

	fun sigletonArray<T>(item: T): Array<T> {
	    return Array<T>(1, {item})
	}


### 6、扩展函数

在某些情况下，某个类缺少部分功能，Java中的做法可以通过继承去扩展功能，在Kotlin中可以直接通过扩展函数的形式来解决该问题。 


	//扩展函数,给Int类添加一个add()函数
	fun Int.add(num: Int): Int {
	    return this.plus(num)
	}

那么只要是个Int对象都可以调用该函数：

 	val num :Int = 10
    //调用我们自定义的扩展函数
    println(num.add(1))


### 7、尾递归函数

>什么是伪递归？

Kotlin 支持函数式编程的尾递归。这个允许一些算法可以通过循环而不是递归解决问题，从而**避免栈溢出**。


>tailrec关键字

当函数被标记为 tailrec 时，编译器会优化递归，并用高效迅速的循环代替它。

>举例：求阶乘

Java中递归如何求阶乘？

	 private static long factorial(int num) {
	        if (num == 0 || num== 1) {
	            return 1;
	        } else {
	            return num * factorial(num-1);
	        }
	 }

上面这个方法看似是没有问题的，并且我们输入一个较小的数，阶乘也是完全能算出来的，但当我们计算10000(或者更大)的数的阶乘，Java抛出了栈溢出异常：

![](http://i.imgur.com/EiegilU.png)


Kotlin中呢？会有同样的问题吗？

	fun factorial1(num: Int): Long {
	    if (num == 1 || num == 0) {
	        return 1
	    } else {
	        return num * factorial1(num - 1)
	    }
	}

改好后，继续求10000的阶乘，发现Kotlin中同Java一样抛出来栈溢出异常

![](http://i.imgur.com/FCc1S3y.png)


怎么解决？

Kotlin中提供tailrec关键字来解决递归嵌套层次过多导致的栈异常问题


tailrec关键字使用要求：
	
* 1、tailrec关键字修饰递归方法
* 2、递归函数内部调用自身之后，不能再有代码或者运算（上面的代码不满足这一条）

显然第一条很容易满足，但第二条之前的代码在调用自身后还有乘法运算和返回数据这两步，显然是不满足条件的，我们尝试多加一个参数，通过该参数的引用来获取返回值。

最终修改后的代码：

	tailrec fun factorial2(num: Int, end: Result) {
	    if (num == 1 || num == 0) {
	        return
	    } else {
	        end.value *= BigInteger.valueOf(num.toLong())
	        factorial2(num - 1, end)
	    }
	}


创建了一个Result类，来接收最后的计算结果：

	/**
	 * 该类用于接收最后伪递归返回的结果
	 * Created by L on 2017/6/19.
	 */
	class Result(var value: BigInteger = BigInteger.valueOf(1L))


这时候，计算10000的阶乘，发现很快就给出了运行结果：

![](http://i.imgur.com/8iIhOZV.png)


那么，tailrec关键字究竟是怎么做到的？我们的代码中依旧还是有嵌套调用自身这个方法N多次,为什么没有出现异常？


前面已经提到，Kotlin发现如果是tailrec关键词修饰的函数，会将其递归替换为高效的循环。
我们把编译后字节码文件反编译回来后，就会发现factorial2()函数的确被编译器用循环的形式重新实现了：

![](http://i.imgur.com/NMnHtnG.png)


### 8、高阶函数

>什么是高阶函数？

高阶函数就是可以接受函数作为参数的函数。

函数作为参数？

炸一听好像很神奇，我们还是通过Java代码和Kotlin代码的对比，来了解所谓的高阶函数


首先，大家不妨想一想我们在Java中是怎么传递函数的？

传递不了？是的，Java的参数不支持函数（方法）类型，但实际开发中，比如Android中为了给一个Button设置onClick事件，通常我们会创建一个匿名内部类**对象**来传递这个onClick方法不是吗？


也就是说Java是通过对象的形式来传递方法的，那我们开始写代码：

需求：创建一个按钮对象，该对象提供一个downClick()方法用来触发点击事件，事件的具体内容由调用者决定。

Java版：

	public class ButtonJ {
	
	    public void downClick(OnClick click) {
	        System.out.println("---start---");
	        if (click != null) {
	            click.onClick();
	        }
	        System.out.println("---end---");
	    }
	
	    public interface OnClick {
	        void onClick();
	    }
	
	}

	
Kotlin版：

	class Button {
	
	    fun downClick(click: () -> Unit) {
	        println("---start---")
	        click()
	        println("---end---")
	    }
	
	}


调用：

	
	//java
    ButtonJ buttonJ = new ButtonJ();
	//需要传个OnClick的子类对象，才能间接调用
    buttonJ.downClick(new ButtonJ.OnClick() {
        @Override
        public void onClick() {
            System.out.println("Java按钮被点击");
        }
    });

	//Kotlin
	val buttonK = ButtonK()
	//直接传方法
    buttonK.downClick(fun() {
        println("Kotlin按钮被点击")
    })
	

通过上面的比对代码，想必大家对Kotlin中高阶函数有个基本认识了吧。

注：Kotlin中如果参数只有一个参数，并且它是一个方法，那么可以直接写成下面这种形式：

	//省略写法
    buttonK.downClick{
        println("Kotlin按钮被点击2")
    }

那如果多个参数中有一个方法，又该怎么写？（假设点击事件需要个额外参数）


	/**
     * 添加了一个额外参数，并将参数传递给点击事件处理
     * */
    fun downClick(arg: Int, click: (Int) -> Unit) {
        println("---start---")
        click(arg)
        println("---end---")
    }

也就是说，你只需要把传递的方法定义为最后一个参数即可，调用如下:
   
	//增加额外参数
    buttonK.downClick(666, fun(arg: Int) {
        println("这是你传入的参数：$arg")
    })

总觉的这个fun关键字比较碍眼，内否简写？

当然是可以的，简化后：

	buttonK.downClick(777, { println("这是你传入的参数：$it") })

是不是很神奇？我们直接把fun和参数定义一起干掉了，编译器既然没报错，那么这是为什么？

因为我们在不经意间写了个Lambda表达式（{...}大括号里就是lambda表达式，it是Kotlin中为了开发人员访问方法参数而设置的默认的形参名，也就是我们实际传入的参数）


## Lambda表达式

>什么是Lamdba表达式？

其实Lambda表达式就是函数，并且我们都已经证明过了，不是吗？