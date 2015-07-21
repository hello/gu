package is.hello.waves;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Optional;

public final class ProgramOptions {
    private static final Options PROGRAM_OPTIONS = new Options()
            .addOption("i", "input", true, "[Required] A wav file to analyze")
            .addOption("o", "output", true, "Where to place the output")
            .addOption("t", "output-type", true, "The type of output to produce, json or csv")
            .addOption("s", "samples-per-second", true, "The samples per second to use");

    public final File inputFile;
    public final Optional<File> outputFile;
    public final OutputProducer.Type outputType;
    public final float samplesPerSecond;

    public static ProgramOptions parse(String[] args) throws ParseException {
        final DefaultParser parser = new DefaultParser();
        final CommandLine line = parser.parse(PROGRAM_OPTIONS, args, true);

        if (!line.hasOption("i")) {
            throw new MissingArgumentException("input");
        }



        final File inputFile = new File(line.getOptionValue("i"));
        final Optional<File> outputFile;
        if (line.hasOption("o")) {
            outputFile = Optional.of(new File(line.getOptionValue("o")));
        } else {
            outputFile = Optional.empty();
        }
        final String rawOutputType = line.getOptionValue("t");
        final Optional<OutputProducer.Type> outputType = OutputProducer.Type.fromString(rawOutputType);
        final float samplesPerSecond = line.hasOption("s")
                ? Float.parseFloat(line.getOptionValue("s"))
                : SpectrogramProducer.DEFAULT_SAMPLES_PER_SECOND;
        return new ProgramOptions(inputFile, outputFile, outputType.orElse(OutputProducer.Type.JSON), samplesPerSecond);
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("waves", PROGRAM_OPTIONS);
    }

    private ProgramOptions(File inputFile,
                           Optional<File> outputFile,
                           OutputProducer.Type outputType,
                           float samplesPerSecond) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.outputType = outputType;
        this.samplesPerSecond = samplesPerSecond;
    }

    public Writer createOutputWriter() throws IOException {
        if (outputFile.isPresent()) {
            return new FileWriter(outputFile.get());
        } else {
            return new PrintWriter(System.out);
        }
    }
}
