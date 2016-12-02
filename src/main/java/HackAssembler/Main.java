package HackAssembler;

import PreDefinedConstants.PreDefinedSymbols;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    //https://github.com/aalhour/Assembler.hack

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/main/resources/Add.asm");

        try(Stream<String> streamOfInstructions = Files.lines(path)) {

            Stream<Instruction> instructionStream = streamOfInstructions
                    .map(line -> line.split(PreDefinedSymbols.COMMENT_SYMBOL)[0])
                    .filter(line -> line.length() > 0)
                    .map(line -> line.replaceAll("\\s+", ""))
                    .map(Instruction::new);

            instructionStream.forEach(System.out::println);

        }

    }

}