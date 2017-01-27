package HackAssembler.CPUinstructions;

import HackAssembler.Utils.ASMlineCPU;
import HackAssembler.Utils.RelevantTables;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static HackAssembler.PreDefinedConstants.PreDefinedSymbols.wordLength;
import static HackAssembler.PreDefinedConstants.PreDefinedSymbols.zeroBit;

public class AddressInstruction implements CPUinstruction {

    private ASMlineCPU instruction;

    public AddressInstruction(ASMlineCPU instruction) {
        this.instruction = instruction;
    }

    private static String make16bitBinary(String string) {
        int intValue = Integer.valueOf(string);
        String binaryValue = Integer.toBinaryString(intValue);
        int missingBits = wordLength - binaryValue.length();
        return StringUtils.repeat(zeroBit, missingBits) + binaryValue;
    }

    @Override
    public String decodeCPUInstruction(RelevantTables relevantTables) {

        String addressValue = instruction.extractMemorySymbol();

        Map<String, String> labelTable = relevantTables.getLabelTable();
        Map<String, String> memorySymbolTable = relevantTables.getMemorySymbol();

        if(memorySymbolTable.containsKey(addressValue)) {
            return make16bitBinary(memorySymbolTable.get(addressValue));
        } else if(labelTable.containsKey(addressValue)) {
            return make16bitBinary(labelTable.get(addressValue));
        } else {
            return make16bitBinary(addressValue);
        }

    }

}