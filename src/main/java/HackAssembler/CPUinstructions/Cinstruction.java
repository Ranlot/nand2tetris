package HackAssembler.CPUinstructions;

public class Cinstruction implements Instruction {

    private String instruction;

    public Cinstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String decodeInstruction() {
        return "C-instruction" + instruction;
    }

}
