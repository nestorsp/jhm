package benchmark;

import benchmark.template.PresentationBenchmarkTemplate;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SumBenchmark extends PresentationBenchmarkTemplate {

    @Param({"100", "1000"})
    private int n;

    @Benchmark
    public int sumWithLoop() {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    @Benchmark
    public int sumWithFormula() {
        return n * (n + 1) / 2;
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(SumBenchmark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("sum-benchmark-results.json")
               // .addProfiler(GCProfiler.class)
                .build();

        new Runner(opt).run();
    }
}
