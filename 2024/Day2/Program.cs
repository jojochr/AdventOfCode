using System.Collections.Immutable;
using Day2;

Console.WriteLine("Start of Solution for Day2:");

const int MIN_STATUS_CODE_DIFFERENCE = 1;
const int MAX_STATUS_CODE_DIFFERENCE = 3;
ImmutableList<ImmutableArray<ushort>> rawReactorCodes = InputReader.GetDay2Input();

int safeReports = rawReactorCodes.Count(statusValues => true == StatusCodeChecker.CheckStatusCode(statusValues, MIN_STATUS_CODE_DIFFERENCE, MAX_STATUS_CODE_DIFFERENCE));

Console.WriteLine($"Result for Day 2: \"{safeReports}\"");

Console.WriteLine();
Console.WriteLine("Starting Part 2:");

int safeReportsWithErrorTolerance =
    rawReactorCodes.Count(statusValues => true == StatusCodeChecker.CheckStatusCodeTolerateOneError(statusValues, MIN_STATUS_CODE_DIFFERENCE, MAX_STATUS_CODE_DIFFERENCE));

Console.WriteLine($"Result for Part 2 of Day 2: \"{safeReportsWithErrorTolerance}\"");
Console.ReadKey();
