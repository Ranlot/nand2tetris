package HackAssembler;

import HackAssembler.CPUinstructions.CPUinstructionFactory;
import HackAssembler.CPUinstructions.Instruction;
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

import static PreDefinedConstants.PreDefinedSymbols.wordLength;
import static PreDefinedConstants.PreDefinedSymbols.zeroBit;
import static org.jooq.lambda.Seq.seq;

public class Main {

    private static String make16bitBinary(long value) {
        String binaryValue = Long.toBinaryString(value);
        int missingBits = wordLength - binaryValue.length();
        return StringUtils.repeat(zeroBit, missingBits) + binaryValue;
    }

    //note that the type is misleading since this function also has the side-effect of closing the input stream
    //this is why we had to duplicate the original stream of instructions
    private static Map<String, String> createLabelTable(Seq<ASMline> allInstructions) {

        Seq<Tuple2<Tuple2<ASMline, Long>, Long>> indexedLabelInstructions = allInstructions
                .zipWithIndex()
                .filter(numberedInstruction -> numberedInstruction.v1.isLabelInstruction())
                .zipWithIndex();

        return indexedLabelInstructions.collect(Collectors.toMap(x -> x.v1.v1.toString(), x -> make16bitBinary(x.v1.v2 - x.v2)));
    }

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/main/resources/Max.asm");

        try (Stream<String> streamOfInstructions = Files.lines(path)) {

            Seq<String> stringStream = seq(streamOfInstructions);

            Tuple2<Seq<ASMline>, Seq<ASMline>> fileLines = stringStream
                    .map(ASMline::new)
                    .map(ASMline::sanitizeString)
                    .filter(ASMline::isNotEmpty)
                    .duplicate();

            Seq<ASMline> allInstructions = fileLines.v1;
            Seq<ASMline> cpuInstructions = fileLines.v2.filter(ASMline::isCPUinstruction);

            Map<String, String> labelTable = createLabelTable(allInstructions);

            for (String label : labelTable.keySet()) {
                System.out.printf("%s\t%s\n", label, labelTable.get(label));
            }

            CPUinstructionFactory cpuInstructionFactory = new CPUinstructionFactory();

            Seq<Instruction> instructions = cpuInstructions
                    .map(ASMline::getCPUinstructionType)
                    .map(cpuInstructionFactory::getCPUinstruction);

            Seq<String> res = instructions.map(Instruction::decodeInstruction);
            res.forEach(System.out::println);

        }

    }

}