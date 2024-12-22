using System.Text;

namespace Day3;

public static class InputReader {
    public static string GetDay3Input() {
        using FileStream fileStream = new(Path.Combine(Directory.GetCurrentDirectory(), "Day3-Input.txt"), FileMode.Open, FileAccess.ReadWrite);
        using StreamReader streamReader = new StreamReader(fileStream, Encoding.UTF8, detectEncodingFromByteOrderMarks: true);

        if(streamReader is null) {
            throw new Exception("Failed to open input file");
        }

        return streamReader.ReadToEnd();
    }
}
