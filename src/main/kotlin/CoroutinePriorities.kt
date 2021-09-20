package main.kotlin

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutinePriorities {
    companion object{
        @JvmStatic fun main(args:Array<String>) {
            runBlocking{
                // it shows that when a coroutine releases a thread and goes to sleep for a really short period of time
                // the thread takes other coroutine and even though the first one has come back from suspending
                // the other coroutine didn't care

                launch(CoroutineName("C1")) {
                    repeat(2){
                        log("Im coroutine 1")
                        delay(1L)
                    }
                }
                launch(CoroutineName("C2")) {
                    repeat(1000){
                        log("Im coroutine 2")
                    }
                }
            }
        }
    }
}