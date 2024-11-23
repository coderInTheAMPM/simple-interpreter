sealed class InterpreterError : Exception()
{
    data class InvalidSyntax(val line: String, override val message: String) : InterpreterError()
    data class ScopeError(override val message: String) : InterpreterError()
    data class VariableError(override val message: String) : InterpreterError()
    data class FileError(override val message: String) : InterpreterError()
}
