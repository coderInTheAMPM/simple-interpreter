class Interpreter
{
    private val scopes = mutableListOf(mutableMapOf<String, Int>())

    private fun getVariable(name: String): Int? =
        scopes.asReversed().firstOrNull { it.containsKey(name) }?.get(name)

    private fun setVariable(name: String, value: Int)
    {
        scopes.last()[name] = value
    }

    private fun enterScope() =
        scopes.add(mutableMapOf())

    private fun exitScope()
    {
        if (scopes.size <= 1)
            throw InterpreterError.ScopeError("Cannot exit global scope")

        scopes.removeAt(scopes.lastIndex)
    }

    private fun validateVariableName(name: String)
    {
        if (!name.matches(Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")))
            throw InterpreterError.InvalidSyntax(name, "Invalid variable name")
    }

    fun executeLine(line: String)
    {
        val trimmedLine = line.trim()
        if (trimmedLine.isEmpty()) return

        try
        {
            when
            {
                trimmedLine == "scope {" -> enterScope()

                trimmedLine == "}" -> exitScope()

                trimmedLine.startsWith("print ") ->
                {
                    val varName = trimmedLine.substring(6).trim()
                    validateVariableName(varName)
                    val value = getVariable(varName)
                    println(value ?: "null")
                }

                trimmedLine.contains("=") ->
                {
                    val (name, valueStr) = trimmedLine.split("=", limit = 2).map { it.trim() }
                    validateVariableName(name)

                    try
                    {
                        // Try parsing as integer
                        val intValue = valueStr.toInt()
                        setVariable(name, intValue)
                    }
                    catch (e: NumberFormatException)
                    {
                        // If not an integer, treat as variable reference
                        validateVariableName(valueStr)
                        val referencedValue =
                            getVariable(valueStr)
                                ?: throw InterpreterError.VariableError("Referenced variable '$valueStr' does not exist")
                        setVariable(name, referencedValue)
                    }
                }

                else -> throw InterpreterError.InvalidSyntax(trimmedLine, "Invalid statement")
            }
        }
        catch (e: InterpreterError)
        {
            println("Error on line '$trimmedLine': ${e.message}")
        }
        catch (e: Exception)
        {
            println("Unexpected error on line '$trimmedLine': ${e.message}")
        }
    }
}
