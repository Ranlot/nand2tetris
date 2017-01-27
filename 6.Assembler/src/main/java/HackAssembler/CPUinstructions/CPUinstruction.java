package HackAssembler.CPUinstructions;

import HackAssembler.Utils.RelevantTables;

public interface CPUinstruction {

    String decodeCPUInstruction(RelevantTables relevantTables);

}