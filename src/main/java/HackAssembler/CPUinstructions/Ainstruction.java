package HackAssembler.CPUinstructions;

public class Ainstruction implements Instruction {

    private String instruction;

    public Ainstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String decodeInstruction() {
        return "A-instruction" + instruction;
    }

}
