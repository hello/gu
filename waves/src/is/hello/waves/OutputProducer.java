package is.hello.waves;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Optional;
import java.util.stream.DoubleStream;

public final class OutputProducer {
    public enum Type {
        CSV("csv") {
            @Override
            public void writeTo(double[] amplitudes, Writer writer) throws IOException {
                try (final PrintWriter outWriter = new PrintWriter(writer)) {
                    for (double amplitude : amplitudes) {
                        outWriter.printf("%f%n", amplitude);
                    }
                }
            }
        },
        JSON("json") {
            @Override
            public void writeTo(double[] amplitudes, Writer writer) throws IOException {
                try {
                    final JSONObject object = new JSONObject();
                    object.put("min", DoubleStream.of(amplitudes).min().getAsDouble());
                    object.put("max", DoubleStream.of(amplitudes).max().getAsDouble());
                    object.put("amplitudes", new JSONArray(amplitudes));
                    object.write(writer);
                } catch (JSONException e) {
                    throw new IOException(e);
                }
            }
        };

        public final String value;

        Type(String value) {
            this.value = value;
        }

        public abstract void writeTo(double[] amplitudes, Writer writer) throws IOException;

        public static Optional<Type> fromString(String string) {
            for (Type type : values()) {
                if (type.value.equalsIgnoreCase(string)) {
                    return Optional.of(type);
                }
            }
            return Optional.empty();
        }
    }

    private final Type type;

    public OutputProducer(Type type) {
        this.type = type;
    }

    public void write(double[] amplitudes, Writer outWriter) throws IOException {
        type.writeTo(amplitudes, outWriter);
    }
}
