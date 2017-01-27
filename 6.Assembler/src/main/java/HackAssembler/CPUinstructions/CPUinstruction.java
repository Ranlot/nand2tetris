package HackAssembler.CPUinstructions;

import HackAssembler.RelevantTables;

public interface CPUinstruction {

    String decodeCPUInstruction(RelevantTables relevantTables);

}