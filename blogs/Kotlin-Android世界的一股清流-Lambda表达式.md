## 什么是Lambda表达式

在[函数的篇章](http://blog.csdn.net/qq1026291832/article/details/73472554)里我们知道了Lambda表达式就是函数，并且也进行了证明。

这篇文章继续探讨Lambda的使用，以及书写规则


先来个最简单的，Lambda是函数，准确的来说是匿名函数

定义一个tell()函数，里面接收一个返回字符串的函数

	/**
	 * 函数名：tell
	 * 参数：f
	 * 参数类型：() 函数
	 * 参数函数的返回值：String
	 *
	 * 函数功能：打印传入函数返回的字符串
	 * */
	fun tell(f: () -> String){
	    println(f())
	}


不用Lambda表达式的调用方式

	tell(fun(): String { return "string" })

显然，fun():String 这些东西都是固定的写法，并且不利于阅读，我们实际关心的应该只是函数体里的东西，那么为了开发方便，Lambda表达式就诞生了


Lambda表达式调用：

	tell({ "string" })


我们发现，不光前面的固定代码被去掉了，return也被省略掉了。Lambda表达式中规定：**如果需要返回值，那么返回值写在代码块的最后一行即可**


Kotlin中的进一步简化，Kotlin中规定如果一个函数仅只有一个函数类型的形参，当传入的是Lambda表达式时，该函数的()小括号可以省略不写。所以最后的代码：

	tell{ "string" }


那么我们加点难度，Lambda中添加参数呢？
	
	fun tell2(f: (str: String) -> String) {
	    println(f("123"))
	}

我们给参数函数中增加了一个参数，也就是说传入的函数是一个需要参数的函数，Lambda表达式该怎么写呢：

 	tell2 { "string:$it" } // 输出：string:123

这里的it在之前函数篇章也有提到过，代表的是当函数仅一个参数时，为了使用lambda表达式方便，Kotlin为我们创建的默认形参名。

一个参数用it,那多个参数呢，我们为形参中的函数再添加一个参数：

	/**
	 * 参数函数中添加多个参数
	 * */
	fun tell3(f: (str1: String, str2: String) -> String) {
	    println(f("123", "abc"))
	}

现在怎么调用呢：

	tell3 { str1, str2 -> "string:$str1 and $str2" } // 输出：string:123 and abc

也就是说，当Lambda表达式中出现多个参数时，我们需要明确的写出参数名，以逗号隔开，函数体和参数之间使用 -> 箭头的形式进行隔开即可。

如果我的Lambda表达式仅使用了一个参数，另一个参数可以不写吗？是不可以的，但是你可以使用下划线来替代它的参数名

	//只使用1个参数
    tell3 { str1, _ -> "string:$str1" }  // 输出：string:123


Kotlin中Lambda表达式的使用就讲到这里，复杂的Lambda表达式万变不离其宗，如果有漏掉的内容之后更新补充。


