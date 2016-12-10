package HackAssembler.TableGenerators;

import HackAssembler.ASMtext.ASMtext;
import org.jooq.lambda.Seq;

import java.util.Map;

public interface TableGenerator {

    //note that createTable closes the input stream
    Map<String, String> createTable(Seq<ASMtext> allInstructions);

}
