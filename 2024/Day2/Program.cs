using System.Collections.Immutable;
using System.Diagnostics;
using Day2;

Console.WriteLine("Start of Solution for Day2:");

int safeReports = 0;
const int MIN_STATUS_CODE_DIFFERENCE = 1;
const int MAX_STATUS_CODE_DIFFERENCE = 3;
ImmutableList<ImmutableArray<ushort>> rawReactorCodes = InputReader.GetDay2Input();
Stopwatch watch = new();

foreach(ImmutableArray<ushort> statusValues in rawReactorCodes) {
    if(true == StatusCodeChecker.CheckStatusCode(statusValues, MIN_STATUS_CODE_DIFFERENCE, MAX_STATUS_CODE_DIFFERENCE)) {
        safeReports++;
    }
}

Console.WriteLine($"Result for Day 1: \"{safeReports}\"");

// Todo: Do Day 2 Part 2 :)
// Console.WriteLine();
// Console.WriteLine("Starting Part 2:");
// Console.WriteLine($"Result for Part 2 of Day 1 is: \"{similarityScore}\"");

Console.ReadKey();
