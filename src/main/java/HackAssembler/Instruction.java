package HackAssembler;

import static PreDefinedConstants.PreDefinedSymbols.shtrudelSymbol;

//TODO: big mess to organize better: multiple dispatch???
public class Instruction {

    private String instruction;

    public Instruction(String instruction) {
        this.instruction = instruction;
    }

    private boolean instructionIsOfTypeLabel(String instruction) {
        return instruction.contains("(") && instruction.contains(")");
    }

    public boolean isLabelInstruction() {
        return instruction.contains("(") && instruction.contains(")");
    }

    //!!! valid only for label instrucion
    public String getLabelAsString() {
        return instruction.replace("(", "").replace(")", "");
    }

    private static boolean isAinstruction(String instruction) {
        return instruction.startsWith("@");
    }

    public String stripLabelInstruction() {
        return instruction.split(shtrudelSymbol)[0];
    }

    @Override
    public String toString() {
        return instruction;
    }

}
