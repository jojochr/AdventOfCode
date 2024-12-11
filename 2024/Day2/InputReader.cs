using System.Collections.Immutable;
using System.Text;

namespace Day2 {
    public static class InputReader {
        public static ImmutableList<ImmutableArray<ushort>> GetDay2Input() {
            using FileStream fileStream = new(Path.Combine(Directory.GetCurrentDirectory(), "Day2-Input.txt"), FileMode.Open, FileAccess.ReadWrite);
            using StreamReader streamReader = new StreamReader(fileStream, Encoding.UTF8, detectEncodingFromByteOrderMarks: true);

            if(streamReader is null) {
                throw new Exception("Failed to open input file");
            }

            List<ImmutableArray<ushort>> codes = [];
            while(streamReader.EndOfStream == false) {
                string inputLine = streamReader.ReadLine() ?? string.Empty;

                // Split on Spaces and remove all empty entries or else we'll get empty strings where the spaces were
                string[] inputsElements = inputLine.Split(separator: ' ', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries);

                List<ushort> partialCode = [];
                foreach(string inputElement in inputsElements) {
                    if(ushort.TryParse(inputElement, out ushort validCode)) {
                        partialCode.Add(validCode);
                    }
                }

                codes.Add([..partialCode]);
            }

            return codes.ToImmutableList();
        }
    }
}
