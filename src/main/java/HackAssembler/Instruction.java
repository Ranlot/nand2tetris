package HackAssembler;

import static PreDefinedConstants.PreDefinedSymbols.A_INSTRUCTION;
import static PreDefinedConstants.PreDefinedSymbols.LABEL_INDICATOR;

public class Instruction {

    private String instruction;

    public Instruction(String instruction) {
        this.instruction = instruction;
    }

    private boolean instructionIsOfTypeLabel(String instruction) {
        return instruction.contains("(") && instruction.contains(")");
    }

    public String stripLabelInstruction() {
        return instruction.split("@")[0];
    }

    public String getInstructionType() {
        if(instructionIsOfTypeLabel(instruction)) {
            return LABEL_INDICATOR;
        }
        else {
            return A_INSTRUCTION;
        }
    }

    @Override
    public String toString() {
        return "Instruction = " + instruction;
    }

}
