using System.Collections.Immutable;
using Day4;

ImmutableArray<string> input = InputReader.GetDay4Input();

Console.WriteLine("Start of Solution for Day 4:");
Console.WriteLine($"Result from Day 4: \"{SolvePart1(input)}\"");

Console.WriteLine();
Console.WriteLine("Starting part 2:");
Console.WriteLine($"Result from Day 4 Part 2: \"{SolvePart2(input)}\"");
Console.ReadKey();


return;
// Methods from here on

static long SolvePart1(in ImmutableArray<string> input) {
    long result = 0;
    const string C_WORD_TO_SEARCH = @"XMAS";

    for(int rowIndexIndex = 0; rowIndexIndex < input.Length; rowIndexIndex++) {
        for(int columnIndex = 0; columnIndex < input[rowIndexIndex].Length; columnIndex++) {

            if(input[rowIndexIndex][columnIndex] == C_WORD_TO_SEARCH[0]) {
                result += CheckForWinsOnPosition(input, rowIndexIndex, columnIndex, Vector.GetAllDirections(), C_WORD_TO_SEARCH);
            }

        }
    }

    return result;
}

static ushort CheckForWinsOnPosition(in ImmutableArray<string> input, in int rowIndex, in int columnIndex, in Vector[] directionsToCheck, in string wordToSearch) {
    ushort winCount = 0;
    foreach(Vector vector in directionsToCheck) {
        if(true == WinForVector(input, rowIndex, columnIndex, vector, wordToSearch)) {
            winCount++;
        }
    }

    return winCount;
}

static long SolvePart2(in ImmutableArray<string> input) {
    long result = 0;
    const string C_WORD_TO_SEARCH = @"MAS";

    for(int rowIndexIndex = 0; rowIndexIndex < input.Length; rowIndexIndex++) {
        for(int columnIndex = 0; columnIndex < input[rowIndexIndex].Length; columnIndex++) {
            // Search for middle of the word this time
            if(input[rowIndexIndex][columnIndex] == C_WORD_TO_SEARCH[1]) {
                ushort winOnPosition = 0;
                foreach(Vector direction in Vector.GetDiagonalDirections()) {
                    if(true == WinForVector(input, rowIndexIndex + direction.Y, columnIndex + direction.X, direction.Invert(), C_WORD_TO_SEARCH)) {
                        winOnPosition++;
                    }
                }

                if(winOnPosition > 1) {
                    result++;
                }
            }
        }
    }

    return result;
}

static bool WinForVector(ImmutableArray<string> input, int rowIndex, int columnIndex, Vector vector, string wordToSearch) {
    for(int i = 0; i < wordToSearch.Length; i++) {
        // Out of bounds check
        if(rowIndex < 0 || rowIndex >= input.Length) {
            return false;
        }
        if(columnIndex < 0 || columnIndex >= input[rowIndex].Length) {
            return false;
        }

        // Check if we are stoll in the green
        if(input[rowIndex][columnIndex] == wordToSearch[i]) {
            // Apply vector
            rowIndex += vector.Y;
            columnIndex += vector.X;
            continue;
        } else {
            return false;
        }
    }

    return true;
}

internal readonly struct Vector(int x, int y) {
    public readonly int X = x;
    public readonly int Y = y;

    public static Vector[] GetAllDirections() => [
        new(0, 1),
        new(1, 1),
        new(1, 0),
        new(1, -1),
        new(0, -1),
        new(-1, -1),
        new(-1, 0),
        new(-1, 1),
    ];

    public static Vector[] GetDiagonalDirections() => [
        new(1, 1),
        new(1, -1),
        new(-1, -1),
        new(-1, 1),
    ];

    public Vector Invert() => new(-X, -Y);
}
