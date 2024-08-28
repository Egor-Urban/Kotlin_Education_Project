import java.io.File

val data_path = "data/data.txt"
val data_file = File(data_path)

fun main() {
    println("Список дел")

    if (!data_file.exists()) {
        println("Файл не найден")
        return
    }

    val data_lines: List<String> = data_file.readLines()

    if (data_lines.isEmpty()) {
        println("Сохранённых задач не найдено, вы хотите записать новую? (y/n)")
        val response = readLine()
        if (response == "y" || response == "Y") {
            println("Введите новую задачу: ")
            val zadacha = readLine().toString()
            data_file.appendText(zadacha + "\n")
        }
        return
    }

    println("Сохранённые задачи: ")
    for ((index, line) in data_lines.withIndex()) {
        println("${index + 1}) $line")
    }

    println("\nВы можете работать с задачей, для этого введите её индекс. Для добавления новой задачи - введите new: ")
    val mode = readLine().toString()
    if (mode == "new") {
        println("Введите новую задачу: ")
        val zadacha = readLine().toString()
        data_file.appendText(zadacha + "\n")
    } else {
        try {
            val needIndex = mode.toInt() - 1
            if (needIndex in data_lines.indices) {
                println("Выберите действие: 1 - Удалить задачу, 2 - Редактировать задачу")
                when (readLine().toString()) {
                    "1" -> {
                        val updatedData = data_lines.toMutableList().apply { removeAt(needIndex) }
                        data_file.writeText(updatedData.joinToString("\n") + "\n")
                        println("Задача удалена.")
                    }
                    "2" -> {
                        println("Введите новое описание задачи: ")
                        val updatedTask = readLine().toString()
                        val updatedData = data_lines.toMutableList().apply { set(needIndex, updatedTask) }
                        data_file.writeText(updatedData.joinToString("\n") + "\n")
                        println("Задача обновлена.")
                    }
                    else -> println("Неверная команда.")
                }
            } else {
                println("Задача с таким индексом не найдена.")
            }
        } catch (e: NumberFormatException) {
            println("Неверный ввод. Ожидался индекс задачи.")
        }
    }
}
