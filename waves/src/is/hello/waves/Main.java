package is.hello.waves;

import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.io.Writer;

public class Main {
    public static void main(String[] args) {
        try {
            ProgramOptions options = ProgramOptions.parse(args);

            final double[] amplitudes = SpectrogramProducer.produce(options.inputFile, options.samplesPerSecond);
            final OutputProducer output = new OutputProducer(options.outputType);
            try (final Writer outWriter = options.createOutputWriter()) {
                output.write(amplitudes, outWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (ParseException e) {
            ProgramOptions.printHelp();
            System.exit(-1);
        }
    }
}
