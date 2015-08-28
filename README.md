# County counter

Counts counties in the USA. Also prints counties that have unique names. This is done by parsing the "List of United States counties and county equivalents" article on Wikipedia.

## Installation

Clone the repo, and run a build with Leiningen.

```bash
lein uberjar
```

## Usage

Run the standalone JAR file with optional arguments

```bash
java -jar counties-0.1.0-standalone.jar [args]
```

### Options

By default, the program prints county names and their frequency counts. However, you may specify an option to perform a different function

- -f  
  remove valid names, and print only invalid names (for testing)
- -u  
  print unique county names 

## Examples

Print all county names and their frequency

```bash
java -jar counties-0.1.0-standalone.jar
```

Print unique county names

```bash
java -jar counties-0.1.0-standalone.jar -u
```

## License

Copyright © 2015 Corey Beres

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
