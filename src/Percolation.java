import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Percolation {
    int [] grid;
    int [] treeSize;
    int [] siteIsOpen;
    int openSites = 0;
    int N;
    // creates n-by-n grid, with all sites initially blocked
    // points to itself == blocked
    public Percolation(int n) {

        if(n<=0){
            throw new IllegalArgumentException();
        }
        grid = new int[n*n];
        treeSize = new int[n*n];
        siteIsOpen = new int[n*n];
        N=n;
//        StdOut.println("grid");
//        StdOut.println(Arrays.toString(grid));



        // initialize each cell to its index
        for(int i =0;i<grid.length;i++){
            grid[i]=i;
            treeSize[i] = 1;
            siteIsOpen[i] = -1;
        }
        // instead of virtual site, connect all top row sites
        for(int i=0;i<n ;i++){
            grid[i]=0;
        }
        // last row of nodes points to last node
        for(int i = n*n-n ; i < grid.length;i++){
            grid[i]=n*n;

        }
    }


    private  int getPos(int row, int col) {
        if(row < 0 || row > N - 1){
//            StdOut.println(row);
            throw new IllegalArgumentException("row out of bounds");
        }
        if(col < 0 || col >  N - 1){
//            StdOut.println(col);
            throw new IllegalArgumentException("col out of bounds");

        }
        return row*N+col;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int pos = getPos(row,col);
//        boolean isSiteOpen = isOpen(row,col);
//        StdOut.println("isSiteOpen");
//        StdOut.println(isSiteOpen);
        if(!isOpen(row,col)){
            grid[pos]=1;
            openSites+=1;
            // connect to it's neighbours
            connect(row,col);

        }
    }

    private int getRoot(int row, int col){
        int currPos = getPos(row,col);
        while(grid[currPos] != currPos){
            currPos = grid[currPos];
        }
        return currPos;
    }


    private void connect(int row, int col){
        // TODO: connect site to neighbouring open sites
        // get root of current node
        int nodeRoot = getRoot(row,col);
        // get 4-directional sites
//        StdOut.println("connect");
//
//
//        StdOut.println(row);
//        StdOut.println(col);
//        StdOut.println(nodeRoot);

        int leftr = row;
        int leftc = col == 0 ? -1 : col-1;
        int rightr = row;
        int rightc = col == N -1 ? -1 : col+1;
        int topr = row == 0 ? -1 : row -1;
        int topc = col;
        int bottomr = row == N -1 ? -1 : row + 1;
        int bottomc = col;
        int[][]sites = {{leftr, leftc}, {rightr, rightc}, {topr, topc}, {bottomr, bottomc}};
        // if sites are open, connect them

//        StdOut.println("sites");
//        for(int g=0; g < sites.length;g++){
//            StdOut.println(Arrays.toString(sites[g]));
//        }
        for(int i = 0; i< sites.length;i++){
            int crow = sites[i][0];
            int ccol = sites[i][1];
            if((crow >= 0 && crow < N) && (ccol > -1 && ccol < N)){
                if(isOpen(crow,ccol)){
                    // get root of left node
                    int cRoot = getRoot(crow,ccol);
                    // connect smaller tree to larger tree
                    if(treeSize[nodeRoot] > treeSize[cRoot]){
                        treeSize[nodeRoot] +=treeSize[cRoot];
                        grid[cRoot] = grid[nodeRoot];
                    }else{
                        treeSize[cRoot] +=treeSize[nodeRoot];
                        grid[nodeRoot] = grid[cRoot];
                    }
                }
            }



        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int pos = getPos(row,col);
        return siteIsOpen[pos] != -1;
    }

    // is the site (row, col) full?\
    // full==is connected to top
    public boolean isFull(int row, int col) {
        // follow node pointers to top
        int pos = getPos(row,col);
        while(grid[pos] != pos){
            pos = grid[pos];
        }
        return pos == 0;
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }


    // does the system percolate?
    public boolean percolates() {
        return isFull(N-1,N-1);
    }


    // test client (optional)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Percolation perk = new Percolation(N);
        // print only grid without virtual nodes
        for(int a =0; a<perk.grid.length ;a++){
            StdOut.println(perk.grid[a]);
        }

        // open some random sites
        for(int i = 0; i < perk.N*2; i++){
//            StdOut.println("opening rand site");
            int randRow = StdRandom.uniformInt(0,N);
            int randCol = StdRandom.uniformInt(0,N);
            perk.open(randRow,randCol);
            StdOut.println(Arrays.toString(perk.grid));
        }

    }
}
