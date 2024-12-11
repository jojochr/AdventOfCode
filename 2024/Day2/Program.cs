using System.Collections.Immutable;
using Day2;

Console.WriteLine("Start of Solution for Day2:");

const int MIN_STATUS_CODE_DIFFERENCE = 1;
const int MAX_STATUS_CODE_DIFFERENCE = 3;
ImmutableList<ImmutableArray<ushort>> rawReactorCodes = InputReader.GetDay2Input();

int safeReports = rawReactorCodes.Count(statusValues => true == StatusCodeChecker.CheckStatusCode(statusValues, MIN_STATUS_CODE_DIFFERENCE, MAX_STATUS_CODE_DIFFERENCE));

Console.WriteLine($"Result for Day 2: \"{safeReports}\"");

// Todo: Do Day 2 Part 2 :)
// Console.WriteLine();
// Console.WriteLine("Starting Part 2:");
// Console.WriteLine($"Result for Part 2 of Day 1 is: \"{similarityScore}\"");

Console.ReadKey();
