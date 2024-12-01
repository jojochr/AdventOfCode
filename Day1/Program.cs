using Day1;

Console.WriteLine("Hello, World!");
Console.WriteLine("This is my Solution for Day 1 :)");

(int[] leftArray, int[] rightArray) = InputReader.GetDay1Input();

Task.WaitAll(Task.Run(leftArray.BubbleSortThis), Task.Run(rightArray.BubbleSortThis));

if(leftArray.Length != rightArray.Length) {
    throw new Exception("Lists did not have the same length as defined in Task");
}

long differenceBetweenLists = 0;

for(int i = 0; i < leftArray.Length; i++) {
    differenceBetweenLists += Math.Abs(leftArray[i] - rightArray[i]);
}

Console.WriteLine($"Result for the Task of Day 1 = \"{differenceBetweenLists}\"");
Console.ReadKey();
