using Day1;
using Shared;

Console.WriteLine("This is my Solution for Day 1 :)");

(int[] leftArray, int[] rightArray) = InputReader.GetDay1Input();

// Sort both arrays asynchronously
Task.WaitAll(Task.Run(leftArray.BubbleSortThis), Task.Run(rightArray.BubbleSortThis));

if(leftArray.Length != rightArray.Length) {
    throw new Exception("Lists did not have the same length as defined in Task");
}

// Evaluate the differences
long differenceBetweenLists = 0;
for(int i = 0; i < leftArray.Length; i++) {
    differenceBetweenLists += Math.Abs(leftArray[i] - rightArray[i]);
}

Console.WriteLine($"Result for Day 1: \"{differenceBetweenLists}\"");
Console.WriteLine();

Console.WriteLine("Starting Part 2:");

Dictionary<int, long> counterResults = [];

foreach(int leftNumber in leftArray) {
    counterResults.Add(leftNumber, 0);
}

foreach(int rightNumber in rightArray) {
    if(counterResults.ContainsKey(rightNumber)) {
        counterResults[rightNumber]++;
    }
}

long similarityScore = 0;
foreach(KeyValuePair<int, long> result in counterResults) {
    if(result.Key <= 0 || result.Value <= 0) {
        continue;
    }

    similarityScore += result.Key * result.Value;
}
Console.WriteLine($"Result for Part 2 of Day 1 is: \"{similarityScore}\"");
Console.ReadKey();
