import java.util.*

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("\nHello from Kotlin")
        println()

        TestStand().launch()

        println("\nДо свидания")
    }
}

class TestStand {
    private var process = true
    lateinit var scanner: Scanner

    fun launch() {
        initialize()
        mainLoop()
        stop()
    }

    private fun initialize() {
        scanner = Scanner(System.`in`)
    }

    private fun mainLoop() {
        while (process) {
            println("""
                |===================================
                |Сделайте выбор:
                |  1 - для проверки размера Object
                |  2 - для проверки размера пустой строки
                |  3 - для проверки размера пустого массива
                |  4 - для наблюдения за изменением размера динамического массива
                |  q - выход
                |===================================
            """.trimMargin())
            val input = scanner.nextLine()
            when (input.trim().toLowerCase()) {
                "1" -> checkSimpleObject()
                "2" -> checkEmptyString()
                "3" -> checkEmptyArray()
                "4" -> checkDynamicSizeArray()
                "q" -> finish()
                else -> {
                    println("Проверьте корректность ввода")
                }
            }
        }
    }

    private fun getMemory(): Long {
        val runtime = Runtime.getRuntime()
        return runtime.totalMemory() - runtime.freeMemory()
    }

    private fun tryCleanMemory() {
        System.gc()
        Thread.sleep(10)
    }

    private fun checkSimpleObject() {
        println("Выполняем задание 1")

        val size = 20_000_000

        repeat(5) {
            print("Ждем-с...")
            tryCleanMemory()
            val shot1 = getMemory()
            Array(size) { _ -> Object() }
            val shot2 = getMemory()
            println("\rMemory for one simple Object: ${(shot2 - shot1) / size}")
        }

        println("Завершаем")
        println()
    }

    private fun checkEmptyString() {
        println("Выполняем задание 2")

        val size = 20_000_000

        repeat(5) {
            print("Ждем-с...")
            tryCleanMemory()
            val shot1 = getMemory()
            Array(size) { _ -> String(ByteArray(0)) }
            val shot2 = getMemory()
            println("\rMemory for empty String: ${(shot2 - shot1) / size}")
        }

        println("Завершаем")
        println()
    }

    private fun checkEmptyArray() {
        println("Выполняем задание 3")

        val size = 20_000_000

        repeat(5) {
            print("Ждем-с...")
            tryCleanMemory()
            val shot1 = getMemory()
            Array(size) { _ -> emptyArray<String>() }
            val shot2 = getMemory()
            println("\rMemory for empty Array: ${(shot2 - shot1) / size}")
        }

        println("Завершаем")
        println()
    }

    private fun checkDynamicSizeArray() {
        println("Выполняем задание 4")

        val size = 20_000

        println("Введите размер массива: ")
        val arrayLength = scanner.nextLine().trim().toInt()

        repeat(5) {
            print("Ждем-с...")
            tryCleanMemory()
            val shot1 = getMemory()
            Array(size) { _ -> IntArray(arrayLength) { _ -> 5 } }
            val shot2 = getMemory()
            println("\rMemory for Array length $arrayLength: ${(shot2 - shot1) / size}")
        }

        println("Завершаем")
        println()
    }

    private fun finish() {
        process = false
    }

    private fun stop() {
        scanner.close()
    }
}
