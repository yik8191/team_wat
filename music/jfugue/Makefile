CLASSPATH = .:./jfugue-5.0.1.jar:./gervill.jar

# Java Compiler
JC = javac
JRE = java

test:
	$(JC) -cp $(CLASSPATH) TestTrack.java

playtest:
	$(JRE) -cp $(CLASSPATH) TestTrack

tuttracks:
	$(JC) -cp $(CLASSPATH) TutorialTrack.java

savetuts:
	$(JRE) -cp $(CLASSPATH) TutorialTrack

w1tracks:
	$(JC) -cp $(CLASSPATH) World1Track.java

savew1s:
	$(JRE) -cp $(CLASSPATH) World1Track

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

track4:
	$(JC) -cp $(CLASSPATH) GameTrack4.java

play4:
	$(JRE) -cp $(CLASSPATH) GameTrack4

reader:
	$(JC) -cp $(CLASSPATH) MusicReader.java

runreader:
	$(JRE) -cp $(CLASSPATH) MusicReader

seemidi:
	$(JC) -cp $(CLASSPATH) SeeMidi.java

runseemidi:
	$(JRE) -cp $(CLASSPATH) SeeMidi

clean:
	rm -f *~ *.class
