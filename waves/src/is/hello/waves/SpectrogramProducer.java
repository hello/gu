package is.hello.waves;

import com.musicg.wave.Wave;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class SpectrogramProducer {
    public static final float DEFAULT_SAMPLES_PER_SECOND = 0.01f;

    private final Wave wave;
    private final float normalizeScale;
    private final float normalizeMax;

    //region Creation

    public static double[] produce(File file, float samplesPerSecond) throws IOException {
        try (FileInputStream waveStream = new FileInputStream(file)) {
            Wave wave = new Wave(waveStream);
            SpectrogramProducer producer = new SpectrogramProducer(wave);
            return producer.analyze(samplesPerSecond);
        }
    }

    public SpectrogramProducer(Wave wave) {
        this.wave = wave;
        if (wave.getWaveHeader().getBitsPerSample() == 8) {
            this.normalizeScale = 0f;
            this.normalizeMax = 1f;
        } else {
            this.normalizeScale = 1f;
            this.normalizeMax = 2f;
        }
    }

    //endregion


    //region Analysis

    public double[] analyze(float samplesPerSecond) {
        final double[] amplitudes = wave.getNormalizedAmplitudes();
        final int totalSampleCount = amplitudes.length;
        final float sampleRate = wave.getWaveHeader().getSampleRate();
        final int frameCount = calculateFrameCount(totalSampleCount, sampleRate, samplesPerSecond);
        if (frameCount > 0) {
            final int samplesPerFrame = totalSampleCount / frameCount;
            final double[] frames = new double[frameCount];
            for (int frame = 0; frame < frameCount; frame++) {
                double sum = 0.0;
                for (int sample = 0; sample < samplesPerFrame; sample++) {
                    final double amplitude = normalizeSampleAmplitude(amplitudes[frame * samplesPerFrame + sample]);
                    sum += amplitude;
                }
                frames[frame] = (sum / samplesPerFrame);
            }
            return frames;
        } else {
            return new double[0];
        }
    }

    private int calculateFrameCount(int totalSampleCount, float sampleRate, float samplesPerSecond) {
        if (samplesPerSecond <= 0f) {
            throw new IllegalArgumentException("samplesPerSecond not > 0f");
        }

        if (sampleRate <= 0f) {
            throw new IllegalArgumentException("sampleRate not > 0f");
        }

        return Math.round(totalSampleCount / sampleRate / samplesPerSecond);
    }

    private double normalizeSampleAmplitude(double sample) {
        return (sample + normalizeScale) / normalizeMax;
    }

    //endregion
}
