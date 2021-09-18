package main.kotlin

class ClientThreads {
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            val nameMainThread = Thread.currentThread().name
            println("Thread:$nameMainThread")
            val person:Person = Person("Javier", "Armenta", 27)
            println("Thread:$nameMainThread person:$person")
            person.surname = "garcia"
            println("Thread:$nameMainThread person:$person")
            Thread(object:Runnable{
                lateinit var  nameThread:String
                override fun run() {
                    nameThread = Thread.currentThread().name
                    println("Thread:${nameThread}")
                    println("Thread:$nameMainThread person:$person")
                    println("$nameThread going to sleep...")
                    Thread.sleep(2000L)
                    println("$nameThread comming back from sleeping running again...")
                    person.name = "Nicolas"
                    println("Thread:$nameMainThread person:$person")
                }

            }, "Thread1").start()

            /*
            Here the main thread finishes before the Thread1 which went to sleep for 2 seconds
            but the object both are modifying is still available since there are references to it
            */

            println("Thread:$nameMainThread has finished")
        }
    }
}