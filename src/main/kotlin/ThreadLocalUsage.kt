package main.kotlin

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ThreadLocalUsage {
    companion object{
        @JvmStatic fun main(args:Array<String>){
            runBlocking {
                launch {
                    val threadLocal = ThreadLocal<Int>()
                    val coroutineContext = newSingleThreadContext("TheBestThread")

                    /*
                    we define a variable for a coroutine depending on its context
                    so for example the coroutine with the Job "job"
                    when running in context main is 1
                    and when running in context "TheBestThread" is 1000
                     */
                    val job = launch(threadLocal.asContextElement(1)) {
                        repeat(3){
                            log("threadLocal:${threadLocal.get()}")
                            threadLocal.set(threadLocal.get() + 1)
                            delay(1000L)
                            withContext(coroutineContext + threadLocal.asContextElement(1000)){
                                log("threadLocal:${threadLocal.get()}")
                            }
                        }
                    }
                }
            }
        }
    }
}