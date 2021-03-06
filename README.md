# Quantified Program Reader
The Quantified Program Reader is a converter for different file formats of quantified programs.

## How to build
The steps below show you how to build the entire project. Every module's output is saved in ```<module>/build/libs/```
For the final app, go with the archive with the "-all" postfix so you do not need any runtime dependencies.

### Requirements
- Java 17

### Windows
Open a console and run ````.\gradlew.bat build````

### Linux
Open a console and run ````./gradlew build````

## Project Status
Currently, the project consists only of a skeleton and a lot of work still needs to be done.

### Input file formats
- [QDIMACS](http://www.qbflib.org/qdimacs.html) with less strict syntax

### Output file formats
- [QLP](http://tm-server-2.wiwi.uni-siegen.de/t3-q-mip/index.php?id=24)

## TODO
- Testing
- Documentation
- Optimization
- Model and Parser for Linear Programs
- C/C++ Implementation/Interface