package HackAssembler.ASMtext;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import static PreDefinedConstants.PreDefinedSymbols.addressInstruction;
import static PreDefinedConstants.PreDefinedSymbols.computeInstruction;
import static PreDefinedConstants.PreDefinedSymbols.shtrudelSymbol;

public class ASMtextCPU {

    private String cpuInstruction;

    private boolean isAddressInstruction(String cpuInstruction) {
        return cpuInstruction.startsWith(shtrudelSymbol);
    }

    ASMtextCPU(String cpuInstruction) {
        this.cpuInstruction = cpuInstruction;
    }

    public Tuple2<String, String> parseCPUinstructionType() {
        return Tuple.tuple(isAddressInstruction(cpuInstruction) ? addressInstruction : computeInstruction, cpuInstruction);
    }
}

