package HackAssembler.CPUinstructions;

import HackAssembler.RelevantTables;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static HackAssembler.Utils.Utils.extractMemorySymbol;
import static PreDefinedConstants.PreDefinedSymbols.shtrudelSymbol;
import static PreDefinedConstants.PreDefinedSymbols.wordLength;
import static PreDefinedConstants.PreDefinedSymbols.zeroBit;

public class AddressInstruction implements CPUinstruction {

    private String instruction;

    public AddressInstruction(String instruction) {
        this.instruction = instruction;
    }

    /*private String extractMemorySymbol(String instruction) {
        return instruction.split(shtrudelSymbol)[1];
    }*/

    private static String make16bitBinary(String string) {
        int intValue = Integer.valueOf(string);
        String binaryValue = Integer.toBinaryString(intValue);
        int missingBits = wordLength - binaryValue.length();
        return StringUtils.repeat(zeroBit, missingBits) + binaryValue;
    }

    @Override
    public String decodeCPUInstruction(RelevantTables relevantTables) {

        String addressValue = extractMemorySymbol(instruction);

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