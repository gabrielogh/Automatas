Welcome to DFAPila
================

Este es un proyecto de implementacion de Automatas Pila Deterministicos.

Asignatura: Autómatas y Lenguajes.

Grupo:
    |-- Gaido Maria Laureana.
    |-- Gonzalez Gabriel.


Getting Started
---------------

Herramientas utilizadas:

# Maven (http://maven.apache.org/).
# Junit.

* Se utiliza un Pom.xml para la descripción de las dependencias

* comando
>  `$ mvn package`
>  toma el código compilado y lo empaqueta en un formato distrubuible (como JAR).
>
>  `$ mvn compile`
>  compila el código fuente del proyecto.
>
>  `$ mvn test`
>  Corre la test suite
>
>  `$ ./run.sh`
>   Compila, ejecuta y corre los test del proyecto.

Estructura del proyecto

```
DFAPila
|-- pom.xml
`-- src
    |-- main
    |   `-- java
    |       `-- App.java
    |        -- DFAPila.java
    |        -- Quintupla.java
    |        -- Tupla.java
    |        -- State.java
    `-- test
        `-- java
            `-- AppTest.java
             -- DFAPilaTest.java
`-- files
    |`-- automata1.dot
    |`-- automata2.dot
    |`-- automata3.dot
    |`-- automata4.dot
```

