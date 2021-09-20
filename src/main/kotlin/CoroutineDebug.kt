package main.kotlin
import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**
 * Coroutine Debugging can be made in two ways
 * 1) intellij tab from debug tab idea
 * 2) by adding -Dkotlinx.coroutines.debug JVM option and run the debugger  and using logging
 * Let's try the second one
 */
fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

class CoroutineDebug {
    companion object{
        @JvmStatic fun main(args:Array<String>){
            runBlocking {
                val parentContext = this.coroutineContext
                log("Im the parent coroutine")
                val job1 = launch(Dispatchers.Unconfined) {
                    log("Im computing a piece of the answer")
                    delay(1L)
                    log("Now im running into another thread")
                }
                val job2 = launch {
                    log("Im computing another piece of the answer")
                }

                job2.join()

                val executorCoroutinesDispatcher = newSingleThreadContext("Ctx1")
                val job3 = launch(executorCoroutinesDispatcher){
                    println("My job is ${coroutineContext[Job]}")
                    log("<----Im running ")// here it runs in thread Ctx1
                    withContext( parentContext){
                        log("<----now Im running ")
                    }
                    log("<--This is sequential it will run in third place")
                }
                /**
                 * Output:
                 * [Ctx1 @coroutine#4] <----Im running
                 * [main @coroutine#1] <----now Im running
                 * [Ctx1 @coroutine#4] <--This is sequential it will run in third place
                 */

                /**
                 * WE CAN NAME COROUTINES by using CoroutineName(<name>) object
                 */
                job3.join()
                // to define multiple elements for the context coroutine use +

                val result = async( Dispatchers.Default + CoroutineName("coroutineFibonacci")) {
                    var a1 = 1
                    var a2 = 1
                    repeat(10){
                        var aux = a2
                        a2 = a1 + a2
                        a1 = aux
                    }
                    log("The result is: $a2")
                    a2
                }
                log("result received is ${result.await()}")

            }


        }
    }
}