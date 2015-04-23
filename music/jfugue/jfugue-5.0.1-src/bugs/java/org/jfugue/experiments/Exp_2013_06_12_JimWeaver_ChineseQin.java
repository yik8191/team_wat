package org.jfugue.experiments;

import org.jfugue.player.Player;
import org.staccato.Instruction;
import org.staccato.InstructionPreprocessor;

public class Exp_2013_06_12_JimWeaver_ChineseQin 
{
    public static void main(String[] args) {
        Player player = new Player();

        InstructionPreprocessor ipp = InstructionPreprocessor.getInstance();
        ipp.addInstruction("turn portamento", new Instruction.Switch(":CON(65,$)", 0, 127));
        ipp.addInstruction("set portamento time", new Instruction.LastIsValue(":CON(5,$)"));
        
        // Portamento with notes
        player.play("{turn portamento on} {set portamento time to 70} C5h D5h E5h "); 
        
        // Portamento with microtones (frequencies given in Hertz), which are converted to note and pitch wheel events
        player.play("{turn portamento on} {set portamento time to 70} m440.5h m550.1h m660.2h "); 
    }
}
