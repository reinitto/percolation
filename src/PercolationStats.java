import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0 || trials <=0){
            throw new IllegalArgumentException();
        }

    }

    // sample mean of percolation threshold
    public double mean(){
        return 0.0;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return 0.0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return 0.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return 0.0;
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        // this is where the magic happens!
        // init grid
        Percolation perk = new Percolation(n);

        while(!perk.percolates()){
            // open random node
            int row = StdRandom.uniformInt(1,n+2);
            int col = StdRandom.uniformInt(1,n+2);
            // skip opened ones
            while(perk.isOpen(row,col)){
                row = StdRandom.uniformInt(1,n+2);
                col = StdRandom.uniformInt(1,n+2);
            }
            perk.open(row,col);
            //
        }

    }

}
