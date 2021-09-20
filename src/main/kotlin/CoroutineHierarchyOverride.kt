package main.kotlin

import kotlinx.coroutines.*

class CoroutineHierarchyOverride {
    companion object{
        @OptIn(DelicateCoroutinesApi::class)
        @JvmStatic fun main(args:Array<String>){
            /**
             * Can we override the coroutine hierarchy ???
             * when launching a coroutine it inherits the context of the outer scope coroutine
             * in this the parent child relationship is established
             * how to change the relationship??
             * two ways:
             *
             * 1) by using another scopre reference <anotherScopeReference>.launch
             * 2) by using another job in the constructor launch( anotherJobReference)
             * so if cancelling this relationship makes it not to cancel
             *
             */

            runBlocking {
                log("Im the  grand parent...")
                val job1 = launch {
                    log("Im the parent...")
                    val parentJob = this.coroutineContext.job
                    val childJob = launch {
                        repeat(3){
                            launch {
                                try{
                                    log("Im child $it ")
                                    delay(3000L)
                                    log("comming back from sleep child $it ")
                                }catch (e:CancellationException){
                                    log("Someone cancelled me...")
                                }
                            }
                        }

                        GlobalScope.launch() {
                            log("I have another dsdsa i don't get affected by cancellation..")
                            delay(3000L)
                            log("I told you!! I DON'T GET AFFECTED BY CANCELLATION")
                        }
                        launch(parentJob) {
                            log("I have another parent i don't get affected cancellation..")
                            delay(3000L)
                            log("I told you!! I DON'T GET AFFECTED BY CANCELLATION")
                        }
                    }
                    log("im the parent job i will sleep 1.3 seconds")
                    delay(1300L)
                    log("Now i will cancel the child job")
                    childJob.cancel()
                    childJob.join()
                    log("I cancel them and it seems there are some coroutines are not mine so maybe more code is coming")


                }
                job1.join()
                /**
                 * There exists parental responsibilities
                 * parent Coroutine waits for its children to finish itself
                 */

                launch {
                    repeat(3){
                        launch {
                            log("Hi im child $it")
                            delay(1300L)
                            log("Child $it now i will die") // this line will be printed
                        }
                    }
                }


            }


        }
    }
}