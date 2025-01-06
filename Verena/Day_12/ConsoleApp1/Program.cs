using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    internal class Program
    {
        static char[][] readFileInput(string filePath) {
            const Int32 BufferSize = 128;
            List<char[]> lines = new List<char[]>();

            using(var fileStream = File.OpenRead(filePath))
            using(var streamReader = new StreamReader(fileStream, Encoding.UTF8, true, BufferSize)) {
                string line;
                while((line = streamReader.ReadLine()) != null) {
                    var test = line.ToCharArray();
                    // Process line 
                    lines.Add(test);
                }
            }
            return lines.ToArray();
        }


        static void Main(string[] args)
        {
            var inputMatrix = readFileInput(@"C:\Git\AdventOfCode\Verena\Day_12\ConsoleApp1\day_12_input.txt");
            Garden garden = new Garden(inputMatrix);
            var regions = garden.getRegions();
            long checkSum = 0;

            //Checksumme berechnen
            foreach(var region in regions) {
                checkSum += region.perimeter * region.area;
            }
            Console.WriteLine("Checksumme: " + checkSum);
            Console.ReadKey();
        }
    }
}
