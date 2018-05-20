package main.java.ru.otus.lessons.home27

fun main(args: Array<String>) {
    val quantity = 3_000_000 // Количество элементов массива
    val t = 100 // Количество потоков
    val size = quantity / t

    IntArray(quantity) { it }.asList().shuffled().let { shuffledList ->

        println("Waiting for threads")
        val nlArray = Array(t) { n ->
            shuffledList.subList(n * size, if (n < t - 1) (n + 1) * size else quantity)
        }.map { sortingThread(it).sortedList.toMutableList() }.toTypedArray()
        println("Threads sorted list")

        var count = 0
        val result = mutableListOf<Int>()
        var haveElements = true
        print("\rThe task is performed on 0%")
        while (haveElements) {
            if (count % 10000 == 0) {
                print("\rThe task is performed on ${(count.toDouble() / quantity * 100).toInt()}%")
            }
            count++

            var minValue = 0 to Integer.MAX_VALUE

            for (i in 1..t) {
                if (nlArray[i - 1].isNotEmpty()) {
                    minValue = if (nlArray[i - 1].first() < minValue.second) i to nlArray[i - 1].first() else minValue
                }
            }
            if (minValue.first == 0) {
                haveElements = false
            } else {
                result += nlArray[minValue.first - 1].removeAt(0)
            }
        }

        println("\rThe task is performed on 100%")

        checkResult(quantity, result)
    }
}

private fun checkResult(quantity: Int, result: MutableList<Int>) {
    println()
    println("Easy Check:")
    for (i in 0..9) {
        val rand = (Math.random() * quantity).toInt()
        println("[$rand] = ${result[rand]} : ${rand == result[rand]}")
    }
}

class SortingThread(private val list: List<Int>) : Thread() {
    private lateinit var _sortedList: List<Int>

    val sortedList: List<Int>
        get() {
            return _sortedList
        }

    override fun run() {
        super.run()
        _sortedList = list.sorted()
    }
}

fun sortingThread(list: List<Int>): SortingThread {
    return SortingThread(list).apply {
        start()
        join()
    }
}