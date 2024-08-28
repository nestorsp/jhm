package benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2 , time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2 , time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class BasicBenchmark {

    private long benchmarkExecutions = 0;
    private long benchmarkIterations = 0;

    @Setup(Level.Iteration)
    public void setup() {
        benchmarkIterations += benchmarkExecutions;
        benchmarkExecutions = 0;
    }

    @Benchmark
    public void basicMethod() throws InterruptedException {
        benchmarkExecutions++;
        //TimeUnit.MILLISECONDS.sleep(1);
    }

    @TearDown(Level.Iteration)
    public void tearDownIteration() {
        System.out.printf("Benchmark executions: %,d.%n", benchmarkExecutions);
    }

    @TearDown(Level.Trial)
    public void tearDownTrial() {
        System.out.printf("Benchmark iterations: %,d.%n", benchmarkIterations);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(new String[]{BasicBenchmark.class.getName()});
    }
}
