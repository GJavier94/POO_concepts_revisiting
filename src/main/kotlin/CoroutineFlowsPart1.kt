package main.kotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow

class CoroutineFlowsPart1 {
    companion object{
        @JvmStatic fun main(args:Array<String>){
            //let's define a runBlocking Scope which defines a scope block the main thread and creates a coroutine
            runBlocking {
                //We can call  a function tha returns  many values in a list
                val list:List<Int> = function1()
                list.forEachIndexed{ i, value -> println("list[$i]:${value}")}
                // if we want to get the values as soon as they are created we can use Sequence Object
                // BUT THIS BLOCKS THE THREAD!!!
                val sequence:Sequence<Int> = function2()
                sequence.forEachIndexed{ i, value -> println("list[$i]:${value}")}
                /**
                 * There's a better solution
                 * problem: we want an asynchronous method  which produces multiple values and every time a new value is created
                 * it can be obtained from the caller
                 * Solution : Using flows!!!
                 * flows are cold e.g. the flow can only produce a new value iff  the consumer has already consumed the previous value
                 * produce-> emit() function
                 * consume-> colect() function
                 * Flow object -> flow{} builder
                 * let's show the behaviour
                 */
                val job1 = launch {
                    val flow: Flow<Pair<Int,Int>> = getFibonacciUsingFlow()
                    // we can use a timeout to cancel the flow
                    val fib3:Pair<Int,Int>?  = withTimeoutOrNull(2500L){
                        var fib3:Pair<Int,Int>? = null
                        flow.collectIndexed {
                                index, fibPair ->
                            val a1 = fibPair.first
                            val a2 = fibPair.second
                            log("i:$index a1:$a1, a2:$a2")
                            /**
                             * Flows can also be canceled
                             */
                            //by a condition met
                            /*if(index == 3 ){
                                this.coroutineContext.job.cancel()
                            }*/
                            if(index == 3){
                                fib3 = fibPair
                            }
                        }
                        fib3
                    }

                    if(fib3 != null){
                        log("i ran on time and this is my value for fib3 ${fib3.first}, ${fib3.second}")
                    }else{
                        log("it was to slow so there's no value retrieved")
                    }

                }

                //i will use another coroutine to show that the coroutine which is using a flow
                // does not blocked the thread
                val job2 = launch {
                    while(job1.isActive){
                        log("Im the main thread and im not blocked...")
                        delay(1000L)
                    }
                }
            }
        }

        private fun getFibonacciUsingFlow(): Flow<Pair<Int,Int>>  = flow {
            var a1 = 1
            var a2 = 2
            try {
                repeat(20){
                    var aux = a2
                    a2 = a1 + a2
                    a1 = aux
                    emit(Pair(a1,a2))
                    delay(1000L)
                }
            }catch (e:CancellationException){
                log("someone cancelled me")
            }

        }


        private fun function2(): Sequence<Int> = sequence {
            for(i in 1..3){
                log("Im in the sequence $i")
                Thread.sleep(1000L)// the function make the thread go to a BLOCKED state --> not sth desired
                yield(i) // this produces a new values for the sequence
            }
        }

        private fun function1(): List<Int> {
            return listOf(1,2,3)
        }
    }
}