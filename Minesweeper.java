// In this problem, calling a dfs on the initial click position cell, counting the number of mines in its neighbors, if 0 then, 
// changing the cell value to 'B' and recursively calling dfs on it's neighbors. If not 0, then simply changing the cell value to 
// the number of mines in its vicinity. Base case if the cell position is not valid or cell value not equal to 'E' then returning.

// Time Complexity : O(m*n)
// Space Complexity : O(m*n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
// DFS
class Solution {
    int m, n;
    int[][] dirs;

    public char[][] updateBoard(char[][] board, int[] click) {
        // Base case
        if (board == null || board.length == 0) {
            return board;
        }
        // If the starting position is only Mine then change to X and return board
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        m = board.length;
        n = board[0].length;
        // Dirs array for looking at it's 8 neighbors U D L R UR UL LL LR
        dirs = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 }, { 1, 1 } };
        // Call dfs
        dfs(board, click);
        return board;
    }

    private void dfs(char[][] board, int[] click) {
        // Base case if the cell position is out of bound or if the cell value is not
        // 'E', return
        if (click[0] < 0 || click[0] >= m || click[1] < 0 || click[1] >= n || board[click[0]][click[1]] != 'E') {
            return;
        }
        // Logic
        // Else check the mines in vicinity
        int countMines = countMines(board, click);
        // If 0,
        if (countMines == 0) {
            // Change the value of the cell to 'B'
            board[click[0]][click[1]] = 'B';
            // And call dfs for it's 8 neighbors
            for (int[] dir : dirs) {
                int nr = click[0] + dir[0];
                int nc = click[1] + dir[1];
                dfs(board, new int[] { nr, nc });
            }
        }
        // Else if number of mines greater than 0, then simply change the cell value to
        // that and dont call dfs
        else {
            board[click[0]][click[1]] = (char) (countMines + '0');
        }

    }

    private int countMines(char[][] board, int[] click) {
        int count = 0;
        for (int[] dir : dirs) {
            int nr = click[0] + dir[0];
            int nc = click[1] + dir[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'M') {
                count++;
            }
        }
        return count;
    }

}

// DFS - just little change, instead of checking in base case, checking before
// making dfs call only
class Solution {
    int m, n;
    int[][] dirs;

    public char[][] updateBoard(char[][] board, int[] click) {
        if (board == null || board.length == 0) {
            return board;
        }
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        m = board.length;
        n = board[0].length;
        dirs = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 }, { 1, 1 } };
        dfs(board, click);
        return board;
    }

    private void dfs(char[][] board, int[] click) {
        // Base
        // if(click[0]<0 || click[0]>=m || click[1]<0 || click[1]>=n ||
        // board[click[0]][click[1]]!='E'){
        // return;
        // }
        // Logic
        int countMines = countMines(board, click);
        if (countMines == 0) {
            board[click[0]][click[1]] = 'B';
            for (int[] dir : dirs) {
                int nr = click[0] + dir[0];
                int nc = click[1] + dir[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'E') {
                    dfs(board, new int[] { nr, nc });
                }

            }
        } else {
            board[click[0]][click[1]] = (char) (countMines + '0');
        }

    }

    private int countMines(char[][] board, int[] click) {
        int count = 0;
        for (int[] dir : dirs) {
            int nr = click[0] + dir[0];
            int nc = click[1] + dir[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'M') {
                count++;
            }
        }
        return count;
    }

}

// In this problem, adding initial click position in the queue and starting bfs,
// polling the current cell and counting the number
// of mines in its neighbors, if 0 then, adding all it's neighbors (if 'E') in
// queue and changing their value blindly to 'B'. If
// not 0, then simply changing the cell value to the number of mines in its
// vicinity.

// Time Complexity : O(m*n)
// Space Complexity : O(m*n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
// BFS
class Solution {
    int m, n;
    int[][] dirs;

    public char[][] updateBoard(char[][] board, int[] click) {
        // Base case
        if (board == null || board.length == 0) {
            return board;
        }
        // If the starting position is only Mine then change to X and return board
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        m = board.length;
        n = board[0].length;
        // Dirs array for looking at it's 8 neighbors U D L R UR UL LL LR
        dirs = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 }, { 1, 1 } };
        // Declare queue for bfs
        Queue<int[]> q = new LinkedList<>();
        // Add the initial click position to the queue
        q.add(new int[] { click[0], click[1] });
        // Change it's cell value to 'B' blindly without checkinf if there is a mine in
        // neighbor or not
        board[click[0]][click[1]] = 'B';
        // Start bfs
        while (!q.isEmpty()) {
            // Poll the current cell
            int[] curr = q.poll();
            // Now check the number of mines in it's neighbors
            int countMines = countMines(board, curr);
            // If the count is 0, simply add all it's neighbors in queue if there value is
            // 'E' and also change the value to 'B'
            if (countMines == 0) {
                for (int[] dir : dirs) {
                    int nr = curr[0] + dir[0];
                    int nc = curr[1] + dir[1];
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'E') {
                        board[nr][nc] = 'B';
                        q.add(new int[] { nr, nc });
                    }
                }
            }
            // Else change the value to number of mines
            else {
                board[curr[0]][curr[1]] = (char) (countMines + '0');
            }
        }
        return board;
    }

    private int countMines(char[][] board, int[] click) {
        int count = 0;
        for (int[] dir : dirs) {
            int nr = click[0] + dir[0];
            int nc = click[1] + dir[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'M') {
                count++;
            }
        }
        return count;
    }

}