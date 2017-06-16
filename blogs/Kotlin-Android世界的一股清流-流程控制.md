源码地址：[https://github.com/cn-ljb/KotlinBlogs](https://github.com/cn-ljb/KotlinBlogs)

## 流程控制语句

### if语句

基本用法同Java

唯一不同点，Kotlin中没有三目运算符（a==xxx?b:c），取而代之的是if表达式

	//Java
	String result = num % 2 == 0 ? "偶数" : "奇数";

	//Kotlin
	 val result = if (num % 2 ==0 ) "偶数" else "奇数"

注：if表达式是支持块级代码的，最后返回值由块级代码最后一行代码决定

	val result = if (num % 2 == 0) {
	        println("哈哈哈哈")
	        "偶数"
	    } else {
	        println("啦啦啦啦")
	        "奇数"
	    }

所以，这样写也是没问题的。

### When语句

貌似是个新东西，其实是Java中switch语句的替代品

  	val num = 3
    when(num){
        1 -> println("num=1")
        2 -> println("num=2")
        3 -> println("num=3")
		4,5,6 -> println("num = 4 or 5 or 6")
        else -> println("不认识这个数")
    }

乍一看，跟Java的switch语句的确没什么区别，只是换了一层皮。

那when到底强在哪？它可以作为表达式，我们把上面的代码改一改

 	val num = 3
    val result = when (num) {
        1 -> "num=1"
        2 -> "num=2"
        3 -> "num=3"
        4, 5, 6 -> "num = 4 or 5 or 6"
        else -> "不认识这个数"
    }
    println(result)

可以看到when语句是直接支持返回数据的。

这里要注意当when作为表达式时，一般是要求必须要有else来处理未知情况，但如果你的代码能显示的表明你已经考虑到了所有情况，else也是可以不写的。

当然它和if表达式一样支持块级代码，返回最后一行代码的值。


### for循环

Kotlin简化了for循环的使用，也就是说，你不用再顽固的写（i=0；i<size;i++）这种东西了。

	//获取item
    for (item: Int in arr) {
        println(item)
    }

    //获取索引
    for (index in arr.indices) {
        println(arr[index])
    }

    //即获取对象，又获取索引
    for ((index, item) in arr.withIndex()) {
        println("$index :: $item")
    }

并且只要数据类型是支持迭代器的，那么都支持这种for循环形式

### while 以及 do...while

同Java一样


### continue、break 以及 return

基本使用方法同Java没有变

但是Kotlin支持标签跳转了，怎么玩呢？

假设有一个三位数组arr[3][3][3]，每个大纬度里只存一个获奖数，需要我们拿程序找到它们，传统的java代码肯定是暴力循环，找到一个数后加标记，一层层跳回，直到返回最外层进行下一次循环，从而提高效率。然而Kotlin有了标签就不用这么麻烦了：


 	var arr = arrayOf(
            arrayOf(arrayOf(1, 2, 3), arrayOf(1, 88, 3), arrayOf(1, 2, 3)),
            arrayOf(arrayOf(1, 2, 3), arrayOf(1, 2, 3), arrayOf(1, 88, 3)),
            arrayOf(arrayOf(1, 88, 3), arrayOf(1, 2, 3), arrayOf(1, 2, 3))
    )

	loop@ for (item in arr) {
        for (item2 in item) {
            for (item3 in item2) {
                if (item3 == 88) {
                    println("找到了：$item3")
                    continue@loop
                }
            }
        }
    }


代码会输出3次“找到了88”，无论是continue还是break他们的语义是不变的

	continue会跳转到标签所在位置的下一次循环
	
	break会结束掉标签所在位置的循环

上面的代码用break实现，只需要把loop@标签放到第二层循环即可。


return呢？return支持标签返回吗？

答案肯定是支持的，return多半在函数跳转中搭配标签使用，在函数篇章里我们再进行详细讲解。

 