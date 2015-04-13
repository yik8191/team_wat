CLASSPATH = .:./lib/jdom-1.1.3.jar:./lib/json-simple-1.1.1.jar
TARGET = src/matachi/mapeditor/Main
# Java Compiler
JC = javac

build:
	mkdir -p bin  # only create directory if it does not exist yet
	$(JC) -cp $(CLASSPATH) -sourcepath src -d bin $(TARGET).java

run:
	java -classpath $(CLASSPATH):./bin matachi.mapeditor.Main

clean:
	rm -f *~ *.class

jar:
	jar cvf rk-lvl-editor.jar bin lib data
