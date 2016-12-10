package HackAssembler;

import HackAssembler.ASMtext.ASMtext;
import HackAssembler.ASMtext.ASMtextCPU;
import HackAssembler.CPUinstructions.CPUinstruction;
import HackAssembler.CPUinstructions.CPUinstructionFactory;
import PreDefinedConstants.SymbolTableNames;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static HackAssembler.Utils.Utils.createRelevantTables;
import static PreDefinedConstants.PreDefinedTables.getPreDefinedMaps;
import static PreDefinedConstants.SymbolTableNames.labelTable;
import static org.jooq.lambda.Seq.seq;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        Path path = Paths.get("src/main/resources/Rect.asm");

        Map<SymbolTableNames, Map<String, String>> allSymbolMaps = getPreDefinedMaps();

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
            Map<String, String> memorySymbolsContent = relevantTables.get(1);
            allSymbolMaps.put(labelTable, labelTableContent);

            for (String label : labelTableContent.keySet()) {
                System.out.printf("%s\t%s\n", label, labelTableContent.get(label));
            }

            CPUinstructionFactory cpuInstructionFactory = new CPUinstructionFactory();

            Seq<CPUinstruction> cpuInstructions = duplicatedASMtext.v2
                    .filter(ASMtext::isCPUinstruction)
                    .map(ASMtext::issueCPUinstruction)
                    .map(ASMtextCPU::parseCPUinstructionType)
                    .map(cpuInstructionFactory::makeCPUinstruction);

            Seq<String> res = cpuInstructions.map(cpuInstruction -> cpuInstruction.decodeInstruction(allSymbolMaps));
            res.forEach(System.out::println);

        }

    }

}