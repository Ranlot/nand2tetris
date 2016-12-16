package HackAssembler.CPUinstructions;

import HackAssembler.RelevantTables;

public interface CPUinstruction {

    String decodeInstruction(RelevantTables relevantTables);

}
