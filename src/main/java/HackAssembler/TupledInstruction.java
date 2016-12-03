package HackAssembler;

public class TupledInstruction {

    private Integer lineNumber;
    private Instruction instruction;

    public TupledInstruction(Integer lineNumber, Instruction instruction) {
        this.lineNumber = lineNumber;
        this.instruction = instruction;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    @Override
    public String toString() {
        return "lineNumber = " + lineNumber + ", instruction = " + instruction;
    }
}
