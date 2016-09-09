# mainarizumu

Mainarizumu is a puzzle similar to Sudoku, played on an NxN grid with three types of constraints:

1) A row or column must contain the numbers from 1-N, with no repeats.
2) If two cells have an X < Y constraint, the value of cell X must be lower than the value of cell Y.
3) If two cells have a difference constraint, such as X (2) Y, the difference between the two cells must be exactly that value.

This is scratch code (no input serialization, very limited comments, has to be recompiled whenever you change constraints) but
it was sufficient to help find an optimal puzzle solution to the board:

<pre>
 A B C D E F
A
B 1 2 3 4 5
C
D
E > > 1 < <
F
<pre>
