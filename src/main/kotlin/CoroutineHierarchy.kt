package main.kotlin

import kotlinx.coroutines.*


class CoroutineHierarchy {
    companion object{
        @JvmStatic fun main(arg:Array<String>){
            /**
             * This show how we can propagate a cancellation between a coroutines hierarchy
             */
            runBlocking {
                val parentJob =launch {
                    log("Starting parent job")
                    val childJob = launch {
                        log("Starting childJob job")
                        try {
                            val granChildJob = launch {
                                log("Starting granChildJob job")
                                try {
                                    log("I will sleep for 3 seconds")
                                    delay(3000L)// let's make a delay for 3 seconds
                                    log("wake up..")
                                }catch (e:CancellationException){
                                    log("I got cancelled by some parent")
                                }
                            }
                            log("I will sleep for 3 seconds")
                            delay(3000L)// let's make a delay for 3 seconds
                            log("wake up..")
                        }catch (e:CancellationException){
                            log("I got cancelled by some parent")
                        }
                    }
                    log("I will sleep for 1.3 seconds")
                    delay(1300L)
                    log("now i will cancel")
                    childJob.cancel()
                    childJob.join()
                    log("as you can see cancelling is propagated")
                }
            }

        }
    }
}

/**
 * /usr/lib/jvm/java-11-amazon-corretto/bin/java -Dkotlinx.coroutines.debug -javaagent:/snap/intellij-idea-community/323/lib/idea_rt.jar=37779:/snap/intellij-idea-community/323/bin -Dfile.encoding=UTF-8 -classpath /home/javier/Desktop/udemy_courses/java/build/classes/java/main:/home/javier/Desktop/udemy_courses/java/build/classes/kotlin/main:/home/javier/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-jdk8/1.5.30/5fd47535cc85f9e24996f939c2de6583991481b0/kotlin-stdlib-jdk8-1.5.30.jar:/home/javier/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlinx/kotlinx-coroutines-core-jvm/1.5.2/f4cc07a50437659e0043e7da762809a46932b6a0/kotlinx-coroutines-core-jvm-1.5.2.jar:/home/javier/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-jdk7/1.5.30/525f5a7fa6d7790a571c07dd24214ed2dda352fe/kotlin-stdlib-jdk7-1.5.30.jar:/home/javier/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.5.30/d68efdea04955974ac1020f8f66ef8176bfbce1f/kotlin-stdlib-1.5.30.jar:/home/javier/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.5.30/649ffab7767038323fec0cc41e2d7b0a8f65a378/kotlin-stdlib-common-1.5.30.jar:/home/javier/.gradle/caches/modules-2/files-2.1/org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar main.kotlin.CoroutineHierarchy
    [main @coroutine#2] Starting parent job
    [main @coroutine#2] I will sleep for 1.3 seconds
    [main @coroutine#3] Starting childJob job
    [main @coroutine#3] I will sleep for 3 seconds
    [main @coroutine#4] Starting granChildJob job
    [main @coroutine#4] I will sleep for 3 seconds
    [main @coroutine#2] now i will cancel
    [main @coroutine#4] I got cancelled by some parent
    [main @coroutine#3] I got cancelled by some parent
 */