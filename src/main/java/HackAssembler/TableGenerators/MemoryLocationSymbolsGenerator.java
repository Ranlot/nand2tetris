package HackAssembler.TableGenerators;

import HackAssembler.ASMtext.ASMtext;
import org.jooq.lambda.Seq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static PreDefinedConstants.PreDefinedTables.originalMemorySymbolContent;

public class MemoryLocationSymbolsGenerator implements Callable<Map<String, String>>, TableGenerator {

    private Seq<ASMtext> addressInstructions;

    private Map<String, String> newMemorySymbols = new HashMap<>();
    private List<String> list = new ArrayList<String>(originalMemorySymbolContent.keySet());
    private int addressForNewSymbol = 16;

    public MemoryLocationSymbolsGenerator(Seq<ASMtext> addressInstructions) {
        this.addressInstructions = addressInstructions;
    }

    //TODO: use distinct
    private void extractMemorySymbol(ASMtext addressInstruction) {
        String memorySymbol = addressInstruction.extractMemorySymbol();
        if( ! list.contains(memorySymbol)) {
            String memorySymbolValue = String.valueOf(addressForNewSymbol);
            newMemorySymbols.put(memorySymbol, memorySymbolValue);
            addressForNewSymbol += 1;
            list.add(memorySymbol);
        }
    }

    @Override
    public Map<String, String> createTable(Seq<ASMtext> addressInstructions) {
        addressInstructions.forEach(this::extractMemorySymbol);
        return newMemorySymbols;
    }

    @Override
    public Map<String, String> call() throws Exception {
        return createTable(addressInstructions);
    }
}