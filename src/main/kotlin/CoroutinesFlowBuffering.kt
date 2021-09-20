package main.kotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

class CoroutinesFlowBuffering {

    companion object{

        /**
         * in flows to emit() and collect are sequential or synchronous
         * it means
         * in order to emit() one more time needs that collect() has already received the previous emit()
         * and
         * in order to collect() one more time it needs emit() has emitted a new value
         * Total time = Temition + Tcollection
         */
        @JvmStatic fun main(args:Array<String>){
            runBlocking {

                /**
                 * When synchronized...
                 */
                val job1 = launch(){
                    val timeWhenSync = measureTimeMillis {
                        log("\n\rJOB1")
                        val flow: Flow<Int> = produceSomething(timeEmiter = 160L)
                        flow.collectIndexed {
                                index, value ->
                            log("$index Collect(): Production received ... consuming production")
                            delay(1000L)
                            log("$index Collect(): consumed.... The value produced is $value")
                        }
                    }
                    log("timeWhenSync:$timeWhenSync")
                }
                job1.join()
                /**
                 *By adding a buffer with some limit obviously
                 */
                val job2 = launch(){
                    val timeWhenBuffered = measureTimeMillis {
                        log("JOB2")
                        val flow: Flow<Int> = produceSomething(timeEmiter = 160L)
                        flow.buffer(10 , onBufferOverflow = BufferOverflow.DROP_OLDEST).collectIndexed {
                                index, value ->
                            log("$index Collect(): Production received ... consuming production")
                            delay(1000L)
                            log("$index Collect(): consumed.... The value produced is $value")
                        }
                    }
                    log("timeWhenBuffered:$timeWhenBuffered")
                }
                job2.join()
                /**
                 *By adding only caring about the last update  -> if the collector last longer than the coroutine to emit
                 * basically it holds a buffer of size 1
                 */
                val job3 = launch(){
                    log("JOB3")
                    val timeWhenConflated = measureTimeMillis {
                        val flow: Flow<Int> = produceSomething(timeEmiter = 160L)
                        flow.conflate().collectIndexed {
                                index, value ->
                            log("$index Collect(): Production received ... consuming production")
                            delay(1000L)
                            log("$index Collect(): consumed.... The value produced is $value")
                        }
                    }
                    log("timeWhenConflated:$timeWhenConflated")
                }
                job3.join()

                /**
                 * What if only want the latest value
                 *
                 */
                val job4 = launch(){
                    log("JOB4")
                    val timeWhenLatest = measureTimeMillis {
                        val flow: Flow<Int> = produceSomething(timeEmiter = 160L)
                        flow.collectLatest {
                            value ->
                            log("Collect(): Production received ... consuming production")
                            delay(1000L)
                            log("Collect(): consumed.... The value produced is $value")
                        }
                    }
                    log("timeWhenLatest:$timeWhenLatest")
                }
            }
        }

        private fun produceSomething(timeEmiter: Long): Flow<Int> = flow {
            repeat(5){
                log("$it emit(): producing..")
                delay(timeEmiter)
                log("$it emit(): production done... Sending production")
                emit(it)
            }
        }.flowOn(Dispatchers.Default)

    }

}