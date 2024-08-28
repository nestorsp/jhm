package benchmark;

import benchmark.template.PresentationBenchmarkTemplate;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MapPerformance extends PresentationBenchmarkTemplate {

    public static final int MAX = 10_000;

    static class Bean {

        int i;

        public Bean(int i) {
            this.i = i;
        }

        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;

            Bean bean = (Bean) object;

            return i == bean.i;
        }

        public int hashCode() {
            return 1;
        }
    }

    public static class BeanComparable implements Comparable<BeanComparable> {

        int i;

        public BeanComparable(int i) {
            this.i = i;
        }

        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;

            BeanComparable bean = (BeanComparable) object;

            return i == bean.i;
        }

        public int hashCode() {
            return 1;
        }


        @Override
        public int compareTo(BeanComparable o) {
            return Integer.compare(i, o.i);
        }
    }

    @Benchmark
    public Integer insertBean() {
        HashMap<Bean, Integer> map = new HashMap<>();
        IntStream.range(0, MAX).mapToObj(Bean::new).forEach(k -> map.put(k, k.i));
        return map.get(new Bean(MAX - 1));
    }

    @Benchmark
    public Integer insertBeanComparable() {
        HashMap<BeanComparable, Integer> map = new HashMap<>();
        IntStream.range(0, MAX).mapToObj(BeanComparable::new).forEach(k -> map.put(k, k.i));
        return map.get(new BeanComparable(MAX - 1));
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(new String[]{MapPerformance.class.getName()});
    }
}