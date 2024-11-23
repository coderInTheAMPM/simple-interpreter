# Scoped Programming Language Interpreter

Implementation of a simple programming language interpreter in Kotlin.
Created as part of a coding assessment.

## Structure
- `InterpreterError.kt`: Error definitions
- `Interpreter.kt`: Main interpreter implementation
- `Main.kt`: Program entry point
- `program`: Example input file

## Running the Program
1. Place your program in a file named `program` in the root directory
2. Run `Main.kt`

## Example Program
```
x = 5
print x
scope {
    x = 2
    print x
    scope {
        x = 3
        y = x
        print x
        print y
    }
    print x
    print y
}
print x
```

## Features
- Variable assignment
- Scoped operations
- Variable printing
