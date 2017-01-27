package HackAssembler.ASMtext;

import HackAssembler.CPUinstructions.AddressInstruction;
import HackAssembler.CPUinstructions.CPUinstruction;
import HackAssembler.CPUinstructions.ComputeInstruction;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import static PreDefinedConstants.PreDefinedSymbols.addressInstruction;
import static PreDefinedConstants.PreDefinedSymbols.computeInstruction;
import static PreDefinedConstants.PreDefinedSymbols.shtrudelSymbol;

//TODO: maybe should inherit from ASMtext????
public class ASMtextCPU {

    private String cpuInstruction;
    private boolean isAddressInstruction(String cpuInstruction) {
        return cpuInstruction.startsWith(shtrudelSymbol);
    }

    ASMtextCPU(String cpuInstruction) {
        this.cpuInstruction = cpuInstruction;
    }

    /*public Tuple2<String, String> parseCPUinstructionType() {
        return Tuple.tuple(isAddressInstruction(cpuInstruction) ? addressInstruction : computeInstruction, cpuInstruction);
    }*/

    public CPUinstruction makeCPUinstruction() {

        return isAddressInstruction(cpuInstruction) ?
                new AddressInstruction(cpuInstruction) :
                new ComputeInstruction(cpuInstruction);

    }

}

