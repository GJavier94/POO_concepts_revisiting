package main.kotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CoroutineFlowsPart2 {
    companion object{
        @JvmStatic fun main(args:Array<String>){
            runBlocking {
                //there are many flow builders
                // flow{} block, asFlow() applied in collections, flowOf() and so on ...
                // we can create a flow from a collection
                // e.g.
                //(1..3).asFlow()
                // flow objects also have transformations like collection e.g.  filter, map, tranform, reduce
                // but these transformations are applied element by element because it is a flow,
                // and they will be applied until collect is called

                (1..3).asFlow().map {
                    number ->
                    log("applying a transformation to this flow collection")
                    "response $number"
                }.collectIndexed{
                    index, value ->
                    log("index:$index value: $value")
                }
                // transform is the most general operator for a flow transformation
                val flow:Flow<Int> = (1..3).asFlow()
                flow.transform{
                    request ->
                    emit("making a request $request")
                    emit( makeRequest(request))
                }.collectIndexed{
                    index, value ->
                    log("index:$index value:$value")
                }

                // the take operator limitates the number of values get from the flow
                (1..20).asFlow().take(10).map { "value: $it" }.collect { log(it) }

                /**
                 * as we can see collect starts the flow working
                 * so it is a terminal operator  so what
                 * emit()
                 * map() or other function sends
                 * it is startet by collect
                 * There are other terminal operators
                 *
                 * toList()
                 * toSet()
                 * first()
                 * single()
                 * reduce()
                 */
                // problem make the sum of the first 20 numbers squared
                val sum:Int = (1..20).asFlow().map { it*it }.reduce{ acum, x -> acum + x }
                log("sum:$sum")

                /**
                 * the functions applied to the flow are sequential
                 * you have a flow
                 * you take one value of the floe
                 * you applied many operator over the value
                 * you collect the value with a terminal operator
                 *
                 */
                (1..5).asFlow()
                    .filter {
                        println("Filter $it")
                        it % 2 == 0
                    }
                    .map {
                        println("Map $it")
                        "string $it"
                    }.collect {
                        println("Collect $it")
                    }
                /**
                 * Flow Context
                 * The flow runs on the coroutine and thread where it was called
                 *  Context preservation
                 */

                val coroutineExecutorScope = newSingleThreadContext("Thread4Flow")
                val job1 = launch(coroutineExecutorScope + CoroutineName("coroutine2")) {
                    val flow:Flow<Int> = simple()
                    flow.collect {
                        value ->
                        log("collecting..the value is $value")
                    }
                }
                /**
                 * What if we want to change the thread of the flow execution != thread of collection
                 * context collection() != context emit()
                 * uses cases
                 * UI Thread        != Dispatcher default
                 * Solution use .flowOn(<Dispatcher>)
                 */
                job1.join()
                // this coroutine job is running on the main thread
                launch {
                    val flow:Flow<Int> = processNumbers()
                    flow.collect {
                        log("recollecting in another context value:$it")
                    }
                }
            }

        }

        private fun processNumbers(): Flow<Int> = flow<Int> {
            repeat(3){
                log("Emitting $it from another context")
                emit(it)
                delay(1000L)
            }
        }.flowOn(Dispatchers.Default)

        private fun simple(): Flow<Int> = flow{
            log("check how i run on the same thread")
            for( i in 1..3 ){
                emit(i)
            }
        }

        private fun makeRequest(request: Int): String {

            return "response of the request:$request"
        }
    }
}