using System.Collections.Immutable;
using Day3;

Console.WriteLine("Start of Solution for Day 3:");

string input = InputReader.GetDay3Input();

const string C_METHOD_NAME = "mul";
const int C_MAX_NUMBER_LENGTH = 3;
long result = 0;

int index = 0;
while(index < input.Length) {
    if(input[index] == C_METHOD_NAME[0]) {
        result += TryDoMultiplicationFromHere(input, ref index, C_METHOD_NAME, C_MAX_NUMBER_LENGTH);
    }

    index++;
}

Console.WriteLine($"Result for Day 3: \"{result}\"");

Console.WriteLine();
Console.WriteLine("Starting part 2:");

const string C_ACTIVATION = @"do()";
const string C_DEACTIVATION = @"don't()";

bool activated = true;
result = 0;
index = 0;

while(index < input.Length) {
    // Check Activation
    if(input[index] == 'd') {
        bool? maybeActivation = CheckForActivation(input, ref index);
        if(maybeActivation is bool activation) {
            activated = activation;
        }
    }

    if(activated && input[index] == C_METHOD_NAME[0]) {
        result += TryDoMultiplicationFromHere(input, ref index, C_METHOD_NAME, C_MAX_NUMBER_LENGTH);
    }

    index++;
}

Console.WriteLine($"Result from Day 3 Part 2: \"{(result)}\"");
Console.ReadKey();

return;

static bool? CheckForActivation(string input, ref int index) {
    int startIndex = index;

    // Check fo do
    if(startIndex + C_ACTIVATION.Length - 1 >= input.Length) {
        index = input.Length - 1;
        return null;
    }

    bool foundDo = true;
    for(int i = 0; (i + startIndex) < (startIndex + C_ACTIVATION.Length); i++) {
        index = i + startIndex; // Set until where we already checked

        // If the characters don't match we have a mismatch
        if(input[i + startIndex] != C_ACTIVATION[i]) {
            foundDo = false;
            break;
        }
    }

    if(foundDo == true) {
        return true;
    }

    bool foundDont = true;
    for(int i = 0; (i + startIndex) < (startIndex + C_DEACTIVATION.Length); i++) {
        index = i + startIndex; // Set until where we already checked

        // If the characters don't match we have a mismatch
        if(input[i + startIndex] != C_DEACTIVATION[i]) {
            foundDont = false;
            break;
        }
    }

    if(foundDont == true) {
        return false;
    }

    // If we found neither
    return null;
}


static long TryDoMultiplicationFromHere(string input, ref int index, string methodName, int maxNumberLength) {
    if(false == CheckMethodName(input, ref index, methodName)) {
        return 0;
    }

    if(false == AssertNextCharacter(input, ref index, character: '(')) {
        return 0;
    }

    if(false == CheckNumberToMultiply(input, ref index, maxNumberLength, allowedDelimiters: [','], methodName, out int parameterOne)) {
        return 0;
    }

    if(false == AssertNextCharacter(input, ref index, character: ',')) {
        return 0;
    }

    if(false == CheckNumberToMultiply(input, ref index, maxNumberLength, allowedDelimiters: [')'], methodName, out int parameterTwo)) {
        return 0;
    }

    if(false == AssertNextCharacter(input, ref index, character: ')')) {
        return 0;
    }

    return parameterOne * parameterTwo;
}

static bool CheckMethodName(string input, ref int index, string methodNameToCheckFor) {
    int startIndex = index;

    if(startIndex + methodNameToCheckFor.Length - 1 >= input.Length) {
        index = input.Length - 1;
        return false;
    }

    for(int i = 0; (i + startIndex) < (startIndex + methodNameToCheckFor.Length); i++) {
        index = i + startIndex; // Set until where we already checked

        // If the characters don't match we have a mismatch
        if(input[i + startIndex] != methodNameToCheckFor[i]) {
            return false;
        }
    }

    // If we end up here we have a match
    return true;
}

static bool AssertNextCharacter(in string input, ref int index, in char character) {
    index++;
    if(index == input.Length) {
        return false;
    }
    return input[index] == character;
}

static bool CheckNumberToMultiply(string input, ref int index, int maxParameterLength, ImmutableArray<char> allowedDelimiters, string methodName, out int numberToMultiply) {
    index++;
    int startIndex = index;
    numberToMultiply = 0;

    // Check delimiting characters
    int delimiterIndex = -1; // Set index to the "not found" value from IndexOf()
    int searchSpace = Math.Min(maxParameterLength + 1, (input.Length - 1) - index);
    foreach(char delimiter in allowedDelimiters) {
        delimiterIndex = input.IndexOf(delimiter, startIndex, Math.Min(maxParameterLength + 1, (input.Length - 1) - index));

        if(delimiterIndex != -1) {
            break; // we found the delimiter and can continue
        }
    }

    // If comma was not found
    if(delimiterIndex == -1) {
        // I know that this is a bad solution, I just do this because I want to fix a bug without rewriting everything.
        // If I do this again, I will choose a different approach
        int indexOfNextMethod = input.IndexOf(methodName[0], startIndex);
        index = Math.Min(indexOfNextMethod - 1, startIndex + searchSpace - 1);

        return false;
    }

    index = delimiterIndex - 1; // The Scope after this method completes expects us to set the index at the last character of the number we returned

    if(int.TryParse(input.AsSpan(startIndex, delimiterIndex - startIndex), out numberToMultiply)) {
        return true;
    } else {
        numberToMultiply = 0;
        return false;
    }
}
