package HackAssembler.Utils;

import HackAssembler.ASMtext.ASMtext;
import HackAssembler.TableGenerators.LabelTableGenerator;
import HackAssembler.TableGenerators.MemoryLocationSymbolsGenerator;
import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static PreDefinedConstants.PreDefinedSymbols.wordLength;
import static PreDefinedConstants.PreDefinedSymbols.zeroBit;

public class Utils {

    public static String make16bitBinary(long value) {
        String binaryValue = Long.toBinaryString(value);
        int missingBits = wordLength - binaryValue.length();
        return StringUtils.repeat(zeroBit, missingBits) + binaryValue;
    }

    public static List<Map<String, String>> createRelevantTables(Seq<ASMtext> allInstructions) throws ExecutionException, InterruptedException {

        List<Map<String, String>> listOfTables = new ArrayList<>();

        Tuple2<Seq<ASMtext>, Seq<ASMtext>> duplicatedAllInstructions = allInstructions.duplicate();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Map<String, String>> labelTableTask = new LabelTableGenerator(duplicatedAllInstructions.v1);
        Callable<Map<String, String>> memorySymbolsTask = new MemoryLocationSymbolsGenerator(duplicatedAllInstructions.v2);

        Future<Map<String, String>> labelTableContentFuture = executor.submit(labelTableTask);
        Future<Map<String, String>> memorySymbolsContentFuture = executor.submit(memorySymbolsTask);
        executor.shutdown();

        Map<String, String> labelTableContent = labelTableContentFuture.get();
        Map<String, String> memorySymbolsContent = memorySymbolsContentFuture.get();

        listOfTables.add(labelTableContent);
        listOfTables.add(memorySymbolsContent);

        return listOfTables;

    }

}