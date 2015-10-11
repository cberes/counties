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

By default, the program shows a chart, which it saves to `counties.png` in the current folder.. However, you may specify an option to perform a different function

- -u  
  print unique county names 
- -c
  print county names and frequencies as comma-separated values
- -f  
  remove valid names, and print only invalid names (for testing)

## Examples

Show and save a chart of most frequent county names

```bash
java -jar counties-0.1.0-standalone.jar
```

Print all unique county names

```bash
java -jar counties-0.1.0-standalone.jar -u
```

## License

Copyright © 2015 Corey Beres

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
