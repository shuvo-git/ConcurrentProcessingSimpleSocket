package socket.server.manager;

import java.util.concurrent.RecursiveAction;

public class ParallelPrimeCalculationManager extends RecursiveAction {
    private final Integer lo;
    private final Integer hi;
    private final Integer THRESHOLD = 50000;
    private Integer value;

    public ParallelPrimeCalculationManager(Integer lo, Integer hi) {
        this.lo = lo;
        this.hi = hi;
    }

    public static Integer findPrimes(Integer n) {
        ParallelPrimeCalculationManager manager = new ParallelPrimeCalculationManager(0, n);
        manager.compute();

        return manager.getValue();
    }


    private boolean isPrime(Integer n) {
        if (n <= 1)
            return false;
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;
        return true;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    protected void compute() {

        if ((hi - lo) <= THRESHOLD) {
            System.out.println("In ForK Join: -- " + Thread.currentThread().getName());
            Integer sum = 0;
            for (Integer i = lo; i < hi; ++i) {
                sum += this.isPrime(i) ? 1 : 0;
            }
            this.value = sum;

        } else {

            Integer mid = (lo + hi) / 2;
            ParallelPrimeCalculationManager left = new ParallelPrimeCalculationManager(lo, mid);
            ParallelPrimeCalculationManager right = new ParallelPrimeCalculationManager(mid, hi);

//            Integer chunk = (hi+lo)/2+1,i=0;
//            Integer chunkLow = lo;
//            Integer end = (chunkLow+chunk)>hi? hi:chunkLow;

            left.fork();
            right.compute();
            left.join();

            this.value = left.getValue() + right.getValue();

        }

    }
}
