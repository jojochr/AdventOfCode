using System;
using System.CodeDom;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Security.Cryptography;
using System.Xml.Schema;

namespace ConsoleApp1 {
    internal class Garden {

        internal class Region {
            /// <summary>Zeichen mit dem diese Region im Garten repräsentiert wird</summary>
            public char symbol { get; private set; }

            /// <summary>Umfang dieser Region</summary>
            public int perimeter { get; set; }

            /// <summary>Flächeninhalt dieser Region</summary>
            public int area { get; set; }

            /// <summary>Anzahl nicht zusammenhängender Seitenflächen</summary>
            public int sides { get; set; }

            /// <summary>Beinhaltet alle Koordinaten dieses Region</summary>
            public List<(int, int)> positions { get; set; }

            /// <summary>Default constructor</summary>
            /// <param name="symbol"></param>
            public Region(char symbol) {
                this.symbol = symbol;
            }
        }

        private char[][] _Data;
        /// <summary>
        /// Inputdaten für diesen Garten
        /// </summary>
        public char[][] Data {
            get => _Data;
            set => _Data = value;
        }

        /// <summary>Default constructor</summary>
        /// <param name="data"></param>
        public Garden(char[][] data) {
            this._Data = data;
        }

        /// <summary>Zu untersuchende Richtungen</summary>
        private int[][] directions = new int[][]
        {
            //Rechts
            new int[] { 0, 1 },
            //Unten
            new int[] { 1, 0 },
            //Links
            new int[] { 0, -1 },
            //Oben
            new int[] { -1, 0 }
        };

        /// <summary>Prüft, ob sich die übergebenen Koordinaten auf dem Array befinden</summary>
        public bool checkCoordinates(int row, int col) => row >= 0 && row < Data.Length && col >= 0 && col < Data[row].Length;

        /// <summary>Ermittelt die einzelnen Regionen im Garten</summary>
        public List<Region> getRegions() {
            var regions = new List<Region>();
            //Felder die bereits Teil einer Region sind
            var visited = new HashSet<(int, int)>();
            //Zu untersuchende Felder der aktuellen Iteration
            var queue = new Queue<(int, int)>();

            //Für jeden Punkt auf der Karte
            for(int row = 0; row < _Data.Length; row++) {
                for(int col = 0; col < _Data[row].Length; col++) {

                    //Wenn dieser Punkt bereits Teil einer Region ist
                    if(visited.Contains((row, col))) {
                        continue;
                    }

                    Region currentRegion = new Region(Data[row][col]) {
                        area = 1
                        ,
                        positions = new List<(int, int)>() {
                            (row, col)
                        }
                    };

                    //Breitensuche vom aktuellen Punkt aus beginnen
                    visited.Add((row, col));
                    queue.Enqueue((row, col));
                    while(queue.Count > 0) {
                        var (currentRow, currentCol) = queue.Dequeue();
                        //Alle Nachbarn des aktuellen Punktes auf Gleichheit prüfen
                        foreach(var direction in directions) {
                            var newRow = currentRow + direction[0];
                            var newCol = currentCol + direction[1];

                            if(!checkCoordinates(newRow, newCol)) {
                                //Ungültiger Arrayzugriff -> Zaun
                                currentRegion.perimeter++;
                                continue;
                            }

                            if(Data[newRow][newCol] != currentRegion.symbol) {
                                //Andere Pflanze
                                currentRegion.perimeter++;
                                continue;
                            }

                            if(visited.Contains((newRow, newCol))) {
                                //Gleiche Pflanze aber Feld bereits untersucht
                                continue;
                            }

                            //Gleiche Pflanze und Teil dieser Fläche
                            currentRegion.area++;
                            currentRegion.positions.Add((newRow, newCol));
                            visited.Add((newRow, newCol));
                            queue.Enqueue((newRow, newCol));
                        }
                    }
                    currentRegion.sides = getSides(currentRegion);
                    regions.Add(currentRegion);
                }
            }
            return regions;
        }

        //Prüft, ob zwei koordinaten benachbart sind
        bool isNeighbour((int, int) first, (int,int) second) {

            //Übereinander
            if(Math.Abs(first.Item1 - second.Item1) == 1 && first.Item2 == second.Item2)
                return true;
            //Nebeneinander
            if(Math.Abs(first.Item2 - second.Item2) == 1 && first.Item1 == second.Item1)
                return true;
            //Weder noch
            return false;

        }

        /// <summary>Berechnet die Anzahl nicht zusammenhängender Umfangsstücke</summary>
        /// <returns></returns>
        public int getSides(Region region) {
            //Minimum
            int sides = 4;
            foreach ((int, int) position in region.positions) {
               //Anzahl der Nachbarn ermitteln
               int directneighbours = region.positions.Where(x => isNeighbour(x, position)).Count();
                Console.WriteLine("Anzahl direkter Nachbarn: " + directneighbours);     
            }
            return sides;

        }

    }
}
