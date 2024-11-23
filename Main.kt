import java.io.File
import java.io.FileNotFoundException

fun main()
{
    // Looks for file named "program" with the code inside
    val programFile = File("program")
    if (!programFile.exists())
    {
        println("Error: File 'program' not found in the current directory")
        return
    }

    println("Executing program from file 'program'...")
    println("----------------------------------------")
    runProgram(programFile)
}

fun runProgram(programFile: File) {
    val interpreter = Interpreter()
    try
    {
        programFile.readLines().forEachIndexed { index, line ->
            try
            {
                interpreter.executeLine(line)
            }
            catch (e: Exception)
            {
                println("Error on line ${index + 1}: ${e.message}")
            }
        }
    }
    catch (e: FileNotFoundException)
    {
        println("Error: Program file not found")
        return
    }
    catch (e: Exception)
    {
        println("Error reading program file: ${e.message}")
        return
    }
}
