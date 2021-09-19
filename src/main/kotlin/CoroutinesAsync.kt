package main.kotlin

import kotlinx.coroutines.*
import java.lang.ArithmeticException
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

/**
 *
 * A coroutine executes its own code in a sequential way by default
 * I mean
 * launch{
 *  firstSuspendedFunction() // this function is executed first
 *  secondSuspendedFunction() // this function is executed after the first function is executed
 *  // executing code sequentially by default
 * }
 * The previous noteworthy point is  useful when functions depend on each other
 * but what if we already know that these functions do not depend on each other =>
 * They are independent => they can be run concurrently
 * response => use coroutines => BUT launch doesn't return the value in case these functions are returning values what
 * we can do ===> ANSWER ====> async()
 *
 * async()-> runs a coroutine returns Deferred object which is also a job, but it has a field await() with the result value of the coroutine
 *
 */
class CoroutinesAsync {

    companion object{
        @JvmStatic fun main(args:Array<String>){
            runBlocking {
                /**
                 * Showing how the coroutine code is executed sequentially
                 */
                val job1 = launch {
                    val startTime = System.currentTimeMillis()

                    val result1:Int = firstSuspendFunction()
                    val result2:Int = secondSuspendFunction()
                    val finalResult  = result1 + result2
                    val time = System.currentTimeMillis() - startTime
                    println("The result is $finalResult in time:$time millis")
                }

                job1.join()


                /**
                 * Now lets make the two methods run concurrently by calling them within async{}
                 * which will return a deferred object
                 * IN THIS CASE BOTH
                 *      1)DECLARATION
                 *      2)RUNNING
                 * of sync{} are made at the same time
                 */
                val job2 = launch {
                    val startTime = System.currentTimeMillis()
                    val deferred1 = async{
                        firstSuspendFunction()
                    }
                    val deferred2 = async{
                        secondSuspendFunction()
                    }
                    // the previous coroutines are running asynchronously -> concurrently
                    // and return a Deferred object
                    val finalResult = deferred1.await() + deferred2.await()
                    val time = System.currentTimeMillis() - startTime
                    println("The result is $finalResult in time:$time millis")
                }
                job2.join()
                /**
                 * BUT we can make a lazy initialization
                 * so that we ust declaring the async coroutines without running them
                 * and we can run them either by:
                 *      1) calling start()
                 *      2) calling await() but it would make sequential  behaviour
                 *
                 */

                val job3 = launch {
                    val startTime = System.currentTimeMillis()
                    val deferred1 = async(start = CoroutineStart.LAZY){
                        firstSuspendFunction()
                    }
                    val deferred2 = async(start = CoroutineStart.LAZY){
                        secondSuspendFunction()
                    }

                    // the previous coroutines are just only declared now we can run them
                    deferred1.start()
                    deferred2.start()

                    val finalResult = deferred1.await() + deferred2.await()
                    val time = System.currentTimeMillis() - startTime
                    println("The result is $finalResult in time:$time millis")
                }

                job3.join()

                /**
                 * We can also define async methods -> a methods which executes a task asynchronously
                 * What will they need ?
                 * because they are going to execute an asynchronous task => use async => needs scope => coroutineScope => can be GlobalScope
                 * they are normal methods => instead of suspend keyword : @OptIn annotation to marked as asyn...
                 */

                val job4 = launch {
                    // These functions are async-> they can be run concurrently take care of it
                    val time = measureTimeMillis {
                        val def1 = methodFirstAsync()
                        val def2 = methodSecondAsync()
                        val finalResult = def1.await() + def2.await()
                        println("The result is $finalResult ")
                    }
                    println("in time:$time millis")
                }
                job4.join()

                /**
                 * WHAT IF...
                 * you run first and second,
                 * and you receive an exception in first
                 * so second will be still running ? =S=S=S=S=S=
                 * wee need to make them aware of the other one to cancel all of them
                 *
                 * coroutine cancellation propagates hierarchically =D!!!!
                 * how to make them aware ??? R = USE THE SAME SCOPE
                 *
                 * Create a new function which has one scope
                 * within that scope makes many async tasks
                 * if one of them gets an exception all of them can receive the exception
                 */

                launch {
                    try {
                        val finalResult:Int = makeProcessing()
                        println("finalResult....")
                    }catch (e:ArithmeticException){
                        println("ParentJob:: There was an error somewhere...")
                    }
                }
                /**
                   Cancellation gets propagated
                    Async1:::
                    Async2:::
                    Async1:::There was an exception in the scope which was propagated here
                    ParentJob:: There was an error somewhere...
                 */


            }
        }

        private suspend fun makeProcessing(): Int = coroutineScope {
            val def1 = async<Int> {
                try{
                    println("Async1:::")
                    delay(Long.MAX_VALUE)
                    42
                }finally {
                    println("Async1:::There was an exception in the scope which was propagated here ")
                }
            }
            val def2 = async {
                println("Async2:::")
                throw ArithmeticException()
                3
            }
            def1.await() + def2.await()
        }

        private suspend fun concurrentSum(): Int  = coroutineScope {
            val deferred1 = async{
                firstSuspendFunction()
            }
            val deferred2 = async{
                secondSuspendFunction()
            }
            val result1 = deferred1.await()
            val result2 = deferred2.await()
            result1 + result2
        }


        private suspend fun firstSuspendFunction(): Int {
            println("firstSuspendFunction: producing an integer result...")
            var a1:Int = 1
            var a2:Int = 1
            delay(1000L)
            repeat(10){
                var aux = a2
                a2 = a1 + a2
                a1 = aux
            }
            println("firstSuspendFunction:$a2")
            return a2
        }
        private suspend fun secondSuspendFunction(): Int {
            println("secondSuspendFunction: producing an integer result...")
            var a1:Int = 1
            var a2:Int = 1
            delay(1000L)
            repeat(20){
                var aux = a2
                a2 = a1 + a2
                a1 = aux
            }
            println("secondSuspendFunction:$a2")
            return a2
        }

        @OptIn(DelicateCoroutinesApi::class) // use it with care
        private fun methodFirstAsync() = GlobalScope.async {
            // it will execute the suspend method
            val result = firstSuspendFunction()
            result
        }

        @OptIn(DelicateCoroutinesApi::class) // use it with care
        private fun methodSecondAsync() = GlobalScope.async {
            // it will execute the suspend method
            val result = secondSuspendFunction()
            result
        }
    }

}