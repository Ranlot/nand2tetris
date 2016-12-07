package HackAssembler;

import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static PreDefinedConstants.PreDefinedSymbols.*;
import static org.jooq.lambda.Seq.seq;

public class Main {


    private static String make16bitBinary(long value) {
        String binaryValue = Long.toBinaryString(value);
        int missingBits = wordLength - binaryValue.length();
        return StringUtils.repeat(zeroBit, missingBits) + binaryValue;
    }

    //note that the type is misleading since this function also has the side-effect of closing the input stream
    //this is why we had to duplicate the original stream of instructions
    private static Map<String, String> createLabelTable(Seq<Instruction> allInstructions) {

        Seq<Tuple2<Tuple2<Instruction, Long>, Long>> indexedLabelInstructions = allInstructions
                .zipWithIndex()
                .filter(numberedInstruction -> numberedInstruction.v1.isLabelInstruction())
                .zipWithIndex();

        return indexedLabelInstructions.collect(Collectors.toMap(x -> x.v1.v1.getLabelAsString(), x -> make16bitBinary(x.v1.v2 - x.v2)));
    }

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/main/resources/Max.asm");

        try (Stream<String> streamOfInstructions = Files.lines(path)) {

            Seq<String> instructionSeq = seq(streamOfInstructions);

            Tuple2<Seq<Instruction>, Seq<Instruction>> instructionSequence = instructionSeq
                    .map(line -> line.split(commentSymbol)[0])
                    .filter(line -> line.length() > 0)
                    .map(line -> line.replaceAll(emptySpace, byNothing))
                    .map(Instruction::new)
                    .duplicate();

            Seq<Instruction> allInstructions = instructionSequence.v1;
            Seq<Instruction> cpuInstructions = instructionSequence.v2.filter(instruction -> ! instruction.isLabelInstruction());

            Map<String, String> labelTable = createLabelTable(allInstructions);

            for (String label : labelTable.keySet()) {
                System.out.printf("%s\t%s\n", label, labelTable.get(label));
            }

            cpuInstructions.forEach(System.out::println);

        }

    }

}