@file:OptIn(DelicateCoroutinesApi::class)

package integration

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    run()
}

fun run() = runBlocking {
    val appTest = AppTest()
    val scope = GlobalScope.launch { appTest.canRun() }
    scope.join()
}
