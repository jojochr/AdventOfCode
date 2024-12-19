using Day1;
using Shared;

Console.WriteLine("This is my Solution for Day 1 :)");
Console.WriteLine();

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
Console.ReadKey();
