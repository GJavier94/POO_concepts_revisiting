package main.kotlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ClientCoroutines {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            launch {
                delay(1000L)
                println("world")
            }
            println("hello")
        }
    }
}