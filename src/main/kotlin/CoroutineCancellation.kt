package main.kotlin

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class CoroutineCancellation {
    companion object{
        @JvmStatic fun main(args:Array<String>) {
            runBlocking {
                //don't forget runBlocking gives a scope which block the thread in which it was declared,
                // so it's ok to use it into top level declarations


                //let's create a parent coroutine // when no specifying a constructor it will be run in the thread in which it was declared
                launch {
                    //This coroutine wants a child coroutine to run a job
                    // in background coroutines we can have fine-grained control over them so that
                    // if the coroutine is running in a background thread and it has taken so long
                    // we can just cancel the coroutine
                    println("Running a background job...")
                    //let's launch a coroutine
                    val job = launch(Dispatchers.Default) {
                        repeat(1000){
                            println("job i will be sleep to simulate processing...")
                            delay(500L)
                        }

                    }
                    // because the task is running in other thread the next line will be executed earlier or later
                    println("Im the parent waiting for the background job ")
                    println("Im the parent i will just stablish a 2 seconds timeout")
                    val timeOut:Long = 2000L
                    delay(timeOut)
                    try {
                        job.cancelAndJoin()
                    }catch (e:CancellationException){
                        println("The background job took more than timeout$timeOut milliseconds")
                    }
                    println("The background job has been finished...")


                    // you didn't notice sth -> cancellation has to be cooperative e.g.
                    // the coroutine code invoked delay() so -> All the suspending functions in kotlinx.coroutines are cancellable
                    // by omiting delay() in coroutines code and making some other processing
                    // the coroutine will not be cancellable

                    //let's see it

                    val firsJob = launch {
                        val currentMillis = System.currentTimeMillis()
                        val job = launch(Dispatchers.Default) {
                            var nextTime = currentMillis
                            var i = 0
                            while(i < 10){
                                // printing messages periodically
                                if( System.currentTimeMillis() >= nextTime){
                                    println("I will be sleeping for ${++i} time ")
                                    nextTime += 500L
                                }
                            }

                        }
                        // we will try to cancel the operations after one second and half
                        delay(1000L)
                        println("Tired of waiting... I will try to cancel")
                        job.cancelAndJoin()
                        println("I think i ran immediately after canceling but --->( unaware coroutine code didn't cooperate)")

                    }

                    // how can we make a coroutine cancellable
                    // by invoking a suspend function or explicitly check the cancellation status
                    firsJob.join()

                    val secondJob = launch {
                        println("running a cancellable  background job...")
                        val currentMillis = System.currentTimeMillis()
                        val job = launch(Dispatchers.Default) {
                            var nextTime = currentMillis
                            var i = 0
                            try {
                                while(i < 10){
                                    //checking periodically if it hasn't been cancelable
                                    yield()
                                    // i could also used isActive on the while to check it explicitly
                                    if( System.currentTimeMillis() >= nextTime){
                                        println("CANCELABLE COROUTINE I will be sleeping for ${++i} time ")
                                        nextTime += 500L
                                    }
                                    /*if(i == 7 ){
                                        delay(1L)  even though I cancel almost immediately the job I now know that coroutines checks if someone cancels it until a suspending function checks it
                                    }*/
                                }
                            }finally {
                                // check if you finished your job =)
                                if(i < 10 ){
                                    println("I couldn't finish my job on time =( ...")
                                }
                            }
                        }
                        // we will try to cancel the operations after one second and half
                        delay(1000L)
                        println("Tired of waiting... I will try to cancel")
                        job.cancelAndJoin()
                        println("I think i could cancel the coroutine so not the 10 times were executed ")

                    }
                    /**
                     * There are some ready to use functions
                     * to handle timeouts and canceling
                     * withTimeout(millis)
                     * withTimeoutOrNull(millis)
                     */
                    secondJob.join()
                    println("i waited for the second job...")

                    val thirdJob = launch(Dispatchers.Default) {
                        val result:Int? = withTimeoutOrNull(3000L){
                            var i = 0
                            repeat(1000){
                                i = it
                                println("$it times")
                                delay(500L)
                            }
                            i
                        }
                        if(result == null){
                            println("couldn't finish")
                        }

                    }
                    thirdJob.join()
                }

            }
        }

    }
}


/*
Running a background job...
Im the parent waiting for the background job
Im the parent i will just stablish a 2 seconds timeout
job i will be sleep to simulate processing...
job i will be sleep to simulate processing...
job i will be sleep to simulate processing...
job i will be sleep to simulate processing...
The background job has been finished...
I will be sleeping for 1 time
I will be sleeping for 2 time
I will be sleeping for 3 time
Tired of waiting... I will try to cancel
I will be sleeping for 4 time
I will be sleeping for 5 time
I will be sleeping for 6 time
I will be sleeping for 7 time
I will be sleeping for 8 time
I will be sleeping for 9 time
I will be sleeping for 10 time
I think i ran immediately after canceling but --->( unaware coroutine code didn't cooperate)
running a cancellable  background job...
CANCELABLE COROUTINE I will be sleeping for 1 time
CANCELABLE COROUTINE I will be sleeping for 2 time
CANCELABLE COROUTINE I will be sleeping for 3 time
Tired of waiting... I will try to cancel
I think i could cancel the coroutine so not the 10 times were executed

Process finished with exit code 0

 */