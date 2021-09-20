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
                job4.join()


                //any iterable which is a list of pairs can be turn to map by toMap() function
                /*val list = listOf(
                    Pair(1, "uno"),
                        Pair(2,"dos"),
                        Pair(3,"tres"))
                list.toMap()*/

                // we can create two sequences
                val sequence1 = sequenceOf(1,2,3)
                val sequence2 = sequenceOf("uno","dos","tres")
                sequence1.zip(sequence2).toMap().forEach{ log("key:${it.key}, value:${it.value}")}
                /**
                 * if we made this with sequence we can also do it with
                 * flows
                 */

                /**
                 * COMPOSING MULTIPLE FLOWS
                 * zip , combine
                 */
                val job5 = launch {
                    val flow1:Flow<Int> = produceSomething(170L,3 )
                    val flow2:Flow<Int> = produceSomething(160L,3 )
                    //Zip needs the other flow and the transform functiosn wich will produces the values by using the flows
                    //and then will convert them
                    flow1.zip(flow2){
                        v1,v2 ->
                        "v1:$v1, v2:$v2" // it is producing a list of strings
                    }.toList().forEach { log(it) }
                    /**
                     * Let's define F as the set of flows
                     * zip is a function such that
                     *      zip: F -> F
                     *          f1.zip(f2) -> f3
                     *  then you can apply a final operation
                     */
                    // zip makes synchronized it waits for the other value to ve collected
                    // but what if we want to use conflated -> we use combined so every time there's an update we get a new pair
                    flow1.combine(flow2){
                            v1,v2 ->
                        "v1:$v1, v2:$v2"
                    }.collect { log(it) }
                }
                job5.join()
                /**
                 * [main @coroutine#16] v1:0, v2:0
                    [main @coroutine#16] v1:0, v2:1
                    [main @coroutine#16] v1:1, v2:1
                    [main @coroutine#16] v1:1, v2:2
                    [main @coroutine#16] v1:2, v2:2
                 */

                /**
                 * We flows basically have the pattern request response
                 * we can nest flows ( make flow of flows )  request response request
                 */

                val flowNames:Flow<String> = generateNames()

                flowNames.flatMapConcat {
                    generateNumbers(it)
                }.collect {
                    value ->
                    log("concatenated flatmap value $value")
                }
                // we had a flow of flows

            }
        }

        private fun generateNames(): Flow<String> = flow{
            repeat(5){
                i ->
                delay(1000L)
                emit ( when(i){
                    0 -> "Javier"
                    1 -> "Yvett"
                    2 -> "Nicolas"
                    3 -> "Daniel"
                    else -> "Ernesto"
                })
            }
        }
        private fun generateNumbers(name: String):Flow<String> = flow{
            repeat(5){
                delay(900L)
                emit("$it: Name $name" )
            }
        }

        private fun produceSomething(timeEmiter: Long, size:Int = 5): Flow<Int> = flow {
            repeat(size){
                log("$it emit(): producing..")
                delay(timeEmiter)
                log("$it emit(): production done... Sending production")
                emit(it)
            }
        }.flowOn(Dispatchers.Default)

    }
}