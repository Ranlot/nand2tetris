package HackAssembler;

import HackAssembler.ASMtext.ASMtext;
import HackAssembler.ASMtext.ASMtextCPU;
import HackAssembler.CPUinstructions.CPUinstruction;
import HackAssembler.CPUinstructions.CPUinstructionFactory;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static HackAssembler.Utils.Utils.createRelevantTables;
import static HackAssembler.Utils.Utils.writeToFile;
import static PreDefinedConstants.PreDefinedTables.*;
import static org.jooq.lambda.Seq.seq;

public class Main {

    public static void main(String[] args) throws Exception {

        Path path = Paths.get("src/main/resources/Pong.asm");

        try (Stream<String> streamOfLines = Files.lines(path)) {

            Seq<String> sequentialStreamOfLines = seq(streamOfLines);

            Tuple2<Seq<ASMtext>, Seq<ASMtext>> duplicatedASMtext = sequentialStreamOfLines
                    .map(ASMtext::new)
                    .map(ASMtext::sanitizeString)
                    .filter(ASMtext::isNotEmpty)
                    .duplicate();

            //go over the assembly code and create tables of symbols for labels and memory locations
            List<Map<String, String>> relevantTables = createRelevantTables(duplicatedASMtext.v1);

            //TODO: get rid of explicit indices
            Map<String, String> labelTableContent = relevantTables.get(0);
            Map<String, String> programSpecificSymbols = relevantTables.get(1);
            memorySymbolContent.putAll(programSpecificSymbols);

            final RelevantTables finalRelevantTables = new RelevantTables(labelTableContent,
                    memorySymbolContent,
                    computeTableContent,
                    jumpTableContent,
                    destinationTableContent);

            CPUinstructionFactory cpuInstructionFactory = new CPUinstructionFactory();

            Seq<CPUinstruction> cpuInstructions = duplicatedASMtext.v2
                    .filter(ASMtext::isCPUinstruction)
                    .map(ASMtext::issueCPUinstruction)
                    .map(ASMtextCPU::parseCPUinstructionType)
                    .map(cpuInstructionFactory::makeCPUinstruction);

            Stream<String> res = cpuInstructions.map(cpuInstruction -> cpuInstruction.decodeInstruction(finalRelevantTables));

            final FileWriter fw = new FileWriter("src/main/resources/Pong.hack");
            res.forEach(x -> writeToFile(fw, x));
            fw.close();

        }

    }

}