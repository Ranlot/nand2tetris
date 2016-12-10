package HackAssembler.TableGenerators;

import HackAssembler.ASMtext.ASMtext;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static HackAssembler.Utils.Utils.make16bitBinary;

public class LabelTableGenerator implements Callable<Map<String, String>>, TableGenerator {

    private Seq<ASMtext> allInstructions;

    public LabelTableGenerator(Seq<ASMtext> allInstructions) {
        this.allInstructions = allInstructions;
    }

    public Map<String, String> createTable(Seq<ASMtext> allInstructions) {
        Seq<Tuple2<Tuple2<ASMtext, Long>, Long>> indexedLabelInstructions = allInstructions
                .zipWithIndex()
                .filter(numberedInstruction -> numberedInstruction.v1.isLabelInstruction())
                .zipWithIndex();
        return indexedLabelInstructions.collect(Collectors.toMap(x -> x.v1.v1.toString(), x -> make16bitBinary(x.v1.v2 - x.v2)));
    };

    @Override
    public Map<String, String> call() throws Exception {
        return createTable(allInstructions);
    }

}
