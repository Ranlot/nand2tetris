package HackAssembler;

import HackAssembler.Utils.ASMline;
import HackAssembler.CPUinstructions.CPUinstruction;
import HackAssembler.Utils.RelevantTables;
import HackAssembler.PreDefinedConstants.DynamicalTables;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import static HackAssembler.Utils.GeneralFunctions.createRelevantTables;
import static HackAssembler.Utils.GeneralFunctions.writeToFile;
import static HackAssembler.PreDefinedConstants.DynamicalTables.labelSymbols;
import static HackAssembler.PreDefinedConstants.PreDefinedTables.*;
import static org.jooq.lambda.Seq.seq;

public class Main {

    public static void main(String[] args) throws Exception {

        long timeIN = System.nanoTime();

        String filePath = args[0];

        Path path = Paths.get(filePath);

        String rootDir = path.getParent().toString();
        String ASMfileName = path.getFileName().toString().split("\\.")[0];
        String HackFileOut = ASMfileName + ".hack";
        String hackOUT = rootDir + "/" + HackFileOut;

        try (Stream<String> streamOfLines = Files.lines(path)) {

            Seq<String> sequentialStreamOfLines = seq(streamOfLines);

            final Tuple2<Seq<ASMline>, Seq<ASMline>> duplicatedASMtext = sequentialStreamOfLines
                    .map(ASMline::new)
                    .map(ASMline::sanitizeString)
                    .filter(ASMline::isNotEmpty)
                    .duplicate();

            // consume first copy of assembly code
            // dynamically create tables of symbols for labels and memory locations
            Map<DynamicalTables, Map<String, String>> dynamicalSymbolTables = createRelevantTables(duplicatedASMtext.v1);
            Map<String, String> dynamicalLabelSymbols = dynamicalSymbolTables.get(labelSymbols);
            memorySymbolsMap.putAll(dynamicalSymbolTables.get(DynamicalTables.memorySymbols));
            RelevantTables relevantTables = new RelevantTables(
                    dynamicalLabelSymbols, memorySymbolsMap, computeTableMap, jumpTableMap, destinationTableMap);

            //consume second copy of assembly code to handle CPU instructions (address & compute)
            Seq<CPUinstruction> cpuInstructions = duplicatedASMtext.v2
                    .filter(ASMline::isCPUinstruction)
                    .map(ASMline::makeCPUinstruction);

            FileWriter fileOut = new FileWriter(hackOUT);

            cpuInstructions
                    .map(cpuInstruction -> cpuInstruction.decodeCPUInstruction(relevantTables))
                    .forEach(binaryInstruction -> writeToFile(fileOut, binaryInstruction));

            fileOut.close();

            long timeOUT = System.nanoTime();
            System.out.printf("\nCreated %s in %f seconds\n\n", HackFileOut, (timeOUT - timeIN) / Math.pow(10, 9));

        }

    }

}