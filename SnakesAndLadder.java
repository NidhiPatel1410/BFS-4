// In this problem, we are flattening the given board to the 1d array first, and then performing bfs on the 1d array. Keeping the 
// moves variable which acts as a level in BFS, then whenever any cell value equals n*n-1 returning moves. Else, exploring it's 
// 6 childs or we can say 6 positions that is possible when we roll a dice. One by one adding them to the queue, if they are not
// visited yet and performing bfs on them.
// Time Complexity : O(n^2)
// Space Complexity : O(n^2)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
class Solution {
    public int snakesAndLadders(int[][] board) {
        // Base case
        if (board == null || board.length == 0) {
            return 0;
        }
        int n = board.length;
        // Array for flattening the board
        int[] nums = new int[n * n];
        // Moves acts as level in bfs
        int moves = 0;
        // Row and col for board
        int row = n - 1;
        int col = 0;
        // Index for the flattened array
        int index = 0;
        // Since we have alternate directions of numbers in board on each row, we keep a
        // even variable for that
        int even = 0;
        // Queue for BFS
        Queue<Integer> q = new LinkedList<>();
        // For flattening the board into the 1d array
        while (index < n * n) {
            // Check if cell is not -1, i.e. either snake or ladder
            if (board[row][col] != -1) {
                // In that case, give the value in 1d array as the corresponding same value - 1,
                // because it is 0-indexed
                nums[index] = board[row][col] - 1;
            } else {
                // Else simply add -1
                nums[index] = -1;
            }
            // Do index++
            index++;
            // Check which row you are on, if even row(1-indexed) than moving left to right
            // so do col++
            if (even % 2 == 0) {
                col++;
                // Now check if the col has become = to n, in that case, move to next row, that
                // is one above it
                if (col == n) {
                    row--;
                    // change the col to valid col value
                    col = n - 1;
                    // And also do even++ for odd row, right to left
                    even++;
                }
            } else {
                // If odd row, right to left, do col--
                col--;
                // If col reaches -1, time to flip
                if (col == -1) {
                    // make col as valid index
                    col = 0;
                    // go to next row
                    row--;
                    // And do even++ indicating even row, left to right
                    even++;
                }
            }
        }
        // Add the index 0 to the queue
        q.add(0);
        // Start bfs
        while (!q.isEmpty()) {
            // Take the size variable
            int size = q.size();
            // Loop till size
            for (int i = 0; i < size; i++) {
                // Poll current
                int curr = q.poll();
                // Check if the current cell is the last cell, just return moves
                if (curr == n * n - 1) {
                    return moves;
                }
                // If not then roll the dice i.e. explore 6 childrens
                for (int j = 1; j <= 6; j++) {
                    // Compute child position
                    int child = j + curr;
                    // Check if the child index is greater than or equal to the n^2 , than continue
                    if (child >= n * n) {
                        continue;
                    }
                    // Else, check if it is not already visited
                    if (nums[child] != -2) {
                        // Then 2 possibilities, either -1 or some positive value indicating snake or
                        // ladder over their
                        if (nums[child] == -1) {
                            // If -1, then add the index of it i.e child to the queue
                            q.add(child);
                            // And mark the cell visited
                            nums[child] = -2;
                        } else {
                            // Else if it is positive number than add that value to the queue
                            q.add(nums[child]);
                            // Mark it as visited
                            nums[child] = -2;
                        }
                    }

                }
            }
            // Do moves++ after one level
            moves++;
        }
        // Return -1, if not possible
        return -1;
    }
}