package main.kotlin

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CoroutinesConcurrencyPrinciple {

    companion object{
        @JvmStatic fun main(args: Array<String>) {
            runBlocking {
                val topCoroutineScope = this
                //This is a parent coroutine
                println(this.hashCode())
                //runBlocking defines a CoroutineScope object which defines an scope for coroutines
                //but the main difference with other builders is that it blocks the thread in which it
                // was declared

                /**
                 * Concurrency Structured Principle
                 * Basically within a coroutine scope we can declare coroutines
                 * and within these coroutines we can also define coroutines scopes
                 * the principles says that the scopes take into account small scopes so
                 * and scope will not finish until all its descendants coroutines finishes
                 */
                // the method launch -> launches a coroutine immediately
                //Launches a new coroutine without blocking the current thread and returns a reference to the coroutine as a Job.
                // The coroutine is cancelled when the resulting job is cancelled.

                /*
                this.launch {
                    val numberCoroutine:String = "0"
                    val level = 0
                    println("coroutine: $numberCoroutine ")
                    CoroutineScope(this.coroutineContext).launch {
                        val numberCoroutine:String = "0.1"
                        val level = 1
                        println("coroutine: $numberCoroutine ")
                        delay(1000L)
                        println("coroutine: $numberCoroutine  FINISHES...")
                    }
                    CoroutineScope(this.coroutineContext).launch {
                        val numberCoroutine:String = "0.2"
                        val level = 1
                        println("coroutine: $numberCoroutine ")
                        delay(1000L)
                        println("coroutine: $numberCoroutine  FINISHES...")

                    }
                    delay(1000L)
                    println("coroutine: $numberCoroutine  FINISHES...")
                }

            }*/
                // we send the previoues lines to comments because we want to re implement them by using
                // This is a child coroutine
                val job = this.launch{
                    println(this.hashCode())
                    doWork(this.coroutineContext)
                }
                println("Im a line that doesn't wait for the the coroutines executed in this scope...")
                /**
                 * this launch is a builder for coroutines
                 * launche method returns a Job object instances which we can use to
                 * to make this scope with for the completion of the coroutine
                 */

                job.join() // suspends the parent coroutine for the completion of the other coroutine
                println("The job has finished")

                //Also, coroutines are light-weight
                /**
                 * DOING THIS WITH THREADS COULD BE IMPOSSIBLE
                 * IT WOULD RUN OUT OF MEMORY OR STH...
                 */
                repeat(100_000){
                    topCoroutineScope.launch {
                        delay(5000L)
                        print(".")
                    }
                }

            }


            println("Hello i was the last line executed") //this the last line executed because the thread is blocke by the runBlocking scope
        }

        //another builder for scopes for coroutines is -> coroutineScope
        private suspend fun doWork(coroutineContext: CoroutineContext) = coroutineScope{
            println(this.hashCode())
                                                                                // ^
            //you can launch many concurrent courtines within an scope define by  -|
            launch {
                println(this.hashCode())
                var text:String = ""
                repeat(100){
                    text += "Hello"
                }
            }
        }
    }

}