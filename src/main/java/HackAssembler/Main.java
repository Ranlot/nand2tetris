package HackAssembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static PreDefinedConstants.PreDefinedSymbols.COMMENT_SYMBOL;

public class Main {

    //https://github.com/aalhour/Assembler.hack

    public static void main(String[] args) throws IOException {

        //Path path = Paths.get("src/main/resources/Add.asm");
        Path path = Paths.get("src/main/resources/Max.asm");

        try(Stream<String> streamOfInstructions = Files.lines(path)) {

            List<Instruction> instructionList = streamOfInstructions
                    .map(line -> line.split(COMMENT_SYMBOL)[0])
                    .filter(line -> line.length() > 0)
                    .map(line -> line.replaceAll("\\s+", ""))
                    .map(Instruction::new)
                    .collect(Collectors.toList());

            List<TupledInstruction> tupledInstructions = IntStream.range(0, instructionList.size())
                    .mapToObj(index -> new TupledInstruction(index, instructionList.get(index)))
                    .collect(Collectors.toList());

            //pass1 to collect all instructions that are just defining label
            //(these are not real instructions to be executed on the CPU
            //but simply a higher level construct in the assembly language

            tupledInstructions.forEach(System.out::println);

            //instructionList.forEach(System.out::println);

            //instructionStream.forEach(System.out::println);
            //labelStream.forEach(System.out::println);

        }

    }

}