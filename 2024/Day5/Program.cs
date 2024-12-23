using System.Collections.Immutable;
using Day5;

(ImmutableDictionary<ushort, ImmutableSortedSet<ushort>> rules, ImmutableArray<ImmutableArray<ushort>> updates) = InputReader.GetDay5Input();
List<ImmutableArray<ushort>> incorrectUpdates = [];

Console.WriteLine("Start of Solution for Day 5:");

long result = 0;

foreach(ImmutableArray<ushort> updateSequence in updates) {
    bool updateSequenceIsOkay = true;

    // We assume every update is a chain longer than 1
    for(int updateIndex = 0; updateIndex < updateSequence.Length; updateIndex++) {
        ushort updateValue = updateSequence[updateIndex];

        foreach(ushort pageToLookupInRules in updateSequence.AsSpan(Range.StartAt(updateIndex + 1))) {
            // If the pageToLookupInRules does not exist in the Rules for the current page
            if(rules[updateValue].Any(pageThatShouldComeAfter => pageToLookupInRules == pageThatShouldComeAfter) == false) {
                updateSequenceIsOkay = false;
                incorrectUpdates.Add(updateSequence);
                break;
            }
        }

        if(updateSequenceIsOkay == false) {
            break;
        }
    }

    if(updateSequenceIsOkay == true) {
        result += updateSequence.GetMiddleItem();
    }
}

Console.WriteLine($"Result from Day 5: \"{result}\"");

Console.WriteLine();
Console.WriteLine("Starting part 2:");
result = 0;

foreach(ImmutableArray<ushort> updateSequence in incorrectUpdates) {
    // Initialize appearanceCounter
    Dictionary<ushort, ushort> appearanceCounter = [];
    foreach(ushort pageToPrint in updateSequence) {
        appearanceCounter.Add(pageToPrint, 0);
    }

    foreach(KeyValuePair<ushort, ImmutableSortedSet<ushort>> rule in rules.Where(rule => updateSequence.Contains(rule.Key))) {
        foreach(ushort ruleAffectedPage in rule.Value.Where(ruleContainedPage => updateSequence.Contains(ruleContainedPage))) {
            appearanceCounter[ruleAffectedPage]++;
        }
    }

    ushort[] correctedUpdateSequence = new ushort[updateSequence.Length];

    foreach(KeyValuePair<ushort, ushort> page in appearanceCounter) {
        correctedUpdateSequence[correctedUpdateSequence.Length - page.Value - 1] = page.Key;
    }

    result += correctedUpdateSequence.GetMiddleItem();
}

Console.WriteLine($"Result from Day 5 Part 2: \"{result}\"");
Console.ReadKey();


return;

internal static class ExtensionMethods {
    public static ushort GetMiddleItem(this ImmutableArray<ushort> pages) => pages[pages.Length / 2];
    public static ushort GetMiddleItem(this ushort[] pages) => pages[pages.Length / 2];
}
