CLASSPATH = .:./jfugue-5.0.1.jar:./gervill.jar

# Java Compiler
JC = javac
JRE = java


track1:
	$(JC) -cp $(CLASSPATH) GameTrack1.java

play1:
	$(JRE) -cp $(CLASSPATH) GameTrack1

track2:
	$(JC) -cp $(CLASSPATH) GameTrack2.java

play2:
	$(JRE) -cp $(CLASSPATH) GameTrack2

reader:
	$(JC) -cp $(CLASSPATH) MusicReader.java

runreader:
	$(JRE) -cp $(CLASSPATH) MusicReader



clean:
	rm -f *~ *.class
