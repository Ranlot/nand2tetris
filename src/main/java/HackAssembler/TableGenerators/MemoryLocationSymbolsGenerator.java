package HackAssembler.TableGenerators;

import HackAssembler.ASMtext.ASMtext;
import org.jooq.lambda.Seq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MemoryLocationSymbolsGenerator implements Callable<Map<String, String>>, TableGenerator {

    private Seq<ASMtext> allInstructions;

    public MemoryLocationSymbolsGenerator(Seq<ASMtext> allInstructions) {
        this.allInstructions = allInstructions;
    }

    @Override
    public Map<String, String> createTable(Seq<ASMtext> allInstructions) {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "val1");
        return map;
    }

    @Override
    public Map<String, String> call() throws Exception {
        return createTable(allInstructions);
    }
}