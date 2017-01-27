package HackAssembler.Utils;

import HackAssembler.CPUinstructions.AddressInstruction;
import HackAssembler.CPUinstructions.CPUinstruction;
import HackAssembler.CPUinstructions.ComputeInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static HackAssembler.PreDefinedConstants.PreDefinedSymbols.jumpSeparator;

public class ASMlineCPU extends ASMline {

    private String ASMlineCPU;

    ASMlineCPU(String ASMlineCPU) {
        super(ASMlineCPU);
        this.ASMlineCPU = ASMlineCPU;
    }

    public List<String> instructionSplitter(String splitCharacter) {
        String[] splitJump = ASMlineCPU.split(splitCharacter);
        return new ArrayList<>(Arrays.asList(splitJump[0], splitJump[1]));
    }

    public boolean isJumpInstruction() {
        return ASMlineCPU.contains(jumpSeparator);
    }

    public CPUinstruction makeCPUinstruction() {
        return isAddressInstruction(ASMlineCPU) ?
                new AddressInstruction(new ASMlineCPU(ASMlineCPU)) :
                new ComputeInstruction(new ASMlineCPU(ASMlineCPU));
    }

}