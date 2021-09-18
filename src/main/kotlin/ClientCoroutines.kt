package main.kotlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ClientCoroutines {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            launch {
                val name = "First"
                println("$name coroutine Thread:${Thread.currentThread().name}")
                delay(1000L)
                println("$name coroutine Thread:${Thread.currentThread().name}")
                printMessage(numberOfTimes = 10 , name)
            }
            launch {
                val name = "second"
                println("$name coroutine Thread:${Thread.currentThread().name}")
                delay(1000L)
                println("$name coroutine Thread:${Thread.currentThread().name}")
                printMessage(numberOfTimes = 10, name = name)
            }

            println("Top level non-coroutine code ${Thread.currentThread().name}")
        }

        /**
         * Delay suspending function suspends the coroutine so it releases the thread and  immediately another
         * takes its place to run its own task
         * I proved that by  suspending the first coroutines while it only has completed half of its work
         * so the second coroutine takes the thread and do the same going to suspend state at half of its work
         * so finally the first coroutine comes from suspending state and finishes its work
         * finally the second coroutine comes from suspending state and finishes its work as well
         */
        /*
        output
        Top level non-coroutine code main
            First coroutine Thread:main
            second coroutine Thread:main
            First coroutine Thread:main
            0 First coroutine Thread:main
            1 First coroutine Thread:main
            2 First coroutine Thread:main
            3 First coroutine Thread:main
            4 First coroutine Thread:main
            5 First coroutine Thread:main
            Sleeping for 1 millisecond...
            second coroutine Thread:main
            0 second coroutine Thread:main
            1 second coroutine Thread:main
            2 second coroutine Thread:main
            3 second coroutine Thread:main
            4 second coroutine Thread:main
            5 second coroutine Thread:main
            Sleeping for 1 millisecond...
            6 First coroutine Thread:main
            7 First coroutine Thread:main
            8 First coroutine Thread:main
            9 First coroutine Thread:main
            6 second coroutine Thread:main
            7 second coroutine Thread:main
            8 second coroutine Thread:main
            9 second coroutine Thread:main

         */
        suspend fun printMessage(numberOfTimes: Int, name: String) {
            repeat(numberOfTimes){
                println("$it $name coroutine Thread:${Thread.currentThread().name}")
                if(it == 5) {
                    println("Sleeping for 1 millisecond...")
                    delay(1L)
                }
            }
        }
    }
}