CLASSPATH = .:./jfugue-5.0.1.jar:./gervill.jar

# Java Compiler
JC = javac
JRE = java

test:
	$(JC) -cp $(CLASSPATH) TestTrack.java

playtest:
	$(JRE) -cp $(CLASSPATH) TestTrack

tuttrack1:
	$(JC) -cp $(CLASSPATH) TutorialTrack1.java

playtut1:
	$(JRE) -cp $(CLASSPATH) TutorialTrack1

track1:
	$(JC) -cp $(CLASSPATH) GameTrack1.java

play1:
	$(JRE) -cp $(CLASSPATH) GameTrack1

track2:
	$(JC) -cp $(CLASSPATH) GameTrack2.java

play2:
	$(JRE) -cp $(CLASSPATH) GameTrack2

track3:
	$(JC) -cp $(CLASSPATH) GameTrack3.java

play3:
	$(JRE) -cp $(CLASSPATH) GameTrack3

reader:
	$(JC) -cp $(CLASSPATH) MusicReader.java

runreader:
	$(JRE) -cp $(CLASSPATH) MusicReader



clean:
	rm -f *~ *.class
