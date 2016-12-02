package HackAssembler;

public class Instruction {

    private String instruction;

    public Instruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isOfTypeLabel() {
        return true;
    }

    public boolean isOfTypeA() {
        return true;
    }

    public boolean isOfTypeC() {
        return true;
    }

    @Override
    public String toString() {
        return "Instruction = " + instruction;
    }

}
