using System.Collections.Immutable;
using System.Text;

namespace Day4;

public static class InputReader {
    public static ImmutableArray<string> GetDay4Input() {
        using FileStream fileStream = new(Path.Combine(Directory.GetCurrentDirectory(), "Day4-Input.txt"), FileMode.Open, FileAccess.ReadWrite);
        using StreamReader streamReader = new StreamReader(fileStream, Encoding.UTF8, detectEncodingFromByteOrderMarks: true);

        if(streamReader is null) {
            throw new Exception("Failed to open input file");
        }

        return streamReader.ReadToEnd().Split("\r\n").ToImmutableArray();
    }
}
