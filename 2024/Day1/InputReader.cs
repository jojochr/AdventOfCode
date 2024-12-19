using System.Text;

namespace Day1 {
    public static class InputReader {
        public static (int[] leftNumbersRaw, int[] rightNubmersRaw) GetDay1Input() {
            int[] leftNumbersRaw = new int[1_000];
            int[] rightNumbersRaw = new int[1_000];

            using FileStream fileStream = new(Path.Combine(Directory.GetCurrentDirectory(), "Day1-Input.txt"), FileMode.Open, FileAccess.ReadWrite);
            using StreamReader streamReader = new StreamReader(fileStream, Encoding.UTF8, detectEncodingFromByteOrderMarks: true);

            if(streamReader is null) {
                throw new Exception("Failed to open input file");
            }

            int index = 0;
            while(streamReader.EndOfStream == false) {
                string inputLine = streamReader.ReadLine() ?? string.Empty;

                // Split on Spaces and remove all empty entries or else we'll get empty strings where the spaces were
                string[] inputsElements = inputLine.Split(separator: ' ', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries);

                if(inputsElements.Length != 2) {
                    throw new Exception($"Got more or less elements than expected on one line. Number of Elements: \"{inputsElements.Length}\"");
                }

                leftNumbersRaw[index] = int.Parse(inputsElements[0]);
                rightNumbersRaw[index] = int.Parse(inputsElements[1]);

                index++;

                if(index == 1_000) {
                    break;
                }
            }

            return (leftNumbersRaw, rightNumbersRaw);
        }
    }
}
