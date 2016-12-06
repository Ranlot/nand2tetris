package HackAssembler;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static PreDefinedConstants.PreDefinedSymbols.*;

public class Main {

    //https://github.com/aalhour/Assembler.hack

    private static HashMap<String, String> makeLabelTable(List<Instruction> instructionList) {
        HashMap<String, String> labelTable = new HashMap<String, String>();
        int lineCounter = 0;
        for (Instruction instruction : instructionList) {
            lineCounter += 1;
            final String instructionType = instruction.getInstructionType();
            if (instructionType.equals(LABEL_INDICATOR)) {
                lineCounter -= 1;
                labelTable.put(instruction.stripLabelInstruction(), make16bitBinary(lineCounter));
            }
        }
        return labelTable;
    }

    private static String make16bitBinary(int value) {
        String binaryValue = Integer.toBinaryString(value);
        int missingBits = WORD_LENGTH - binaryValue.length();
        String repeat0 = StringUtils.repeat('0', missingBits);
        return repeat0 + binaryValue;
    }

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/main/resources/Max.asm");

        try (Stream<String> streamOfInstructions = Files.lines(path)) {

            List<Instruction> instructionList = streamOfInstructions
                    .map(line -> line.split(COMMENT_SYMBOL)[0])
                    .filter(line -> line.length() > 0)
                    .map(line -> line.replaceAll("\\s+", ""))
                    .map(Instruction::new)
                    .collect(Collectors.toList());

            //pass1 to collect all instructions that are just defining label
            //(these are not real instructions to be executed on the CPU
            //but simply a higher level construct in the assembly language

            HashMap<String, String> labelTable = makeLabelTable(instructionList);

            for (String key : labelTable.keySet()) {
                System.out.printf("%s\t%s\n", key, labelTable.get(key));
            }

        }

    }

}