using System.Collections.Immutable;
using System.Text;

namespace Day5 {
    public static class InputReader {
        public static (ImmutableDictionary<ushort, ImmutableSortedSet<ushort>> Rules, ImmutableArray<ImmutableArray<ushort>> Updates) GetDay5Input() {
            string[] lines = File.ReadAllLines("Day5-Input.txt", Encoding.UTF8);

            if(lines is null || lines.Length == 0) {
                throw new Exception("Failed to open input file");
            }

            Dictionary<ushort, SortedSet<ushort>> rules = [];
            List<List<ushort>> updates = [];

            // I expect Lines to be either a rule OR an update
            foreach(string line in lines) {
                // Line has a pipe
                if(line.Contains('|')) {
                    ushort key = ushort.Parse(line.Substring(0, 2));
                    ushort value = ushort.Parse(line.Substring(3, 2));
                    if(false == rules.ContainsKey(key)) {
                        rules.Add(key, []);
                    }

                    rules[key].Add(value);
                }

                // Line has an update
                if(line.Contains(',')) {
                    updates.Add(line.Split(',', StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries).Select(ushort.Parse).ToList());
                }
            }

            ImmutableDictionary<ushort, ImmutableSortedSet<ushort>> immutableRules = rules.ToImmutableDictionary(e => e.Key, e => e.Value.ToImmutableSortedSet());
            ImmutableArray<ImmutableArray<ushort>> immutableUpdates = [..updates.Select(update => update.ToImmutableArray())];

            return (Rules: immutableRules, Updates: immutableUpdates);
        }
    }
}
