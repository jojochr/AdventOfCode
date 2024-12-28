import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/*
Disk Map:
- Abwechselnd File und Free Space
- Ziffer gibt die Länge eines Eintrags an
- Initiale Stelle des Files ist seine ID
- 12345 = 0..111....22222
*/
public class Day_9_2 {
    /// Model für einen Block von Dateien der gleichen ID
    public static class Block {
        /// ID dieses FileBlocks
        public int id;
        /// Anzahl der Dateien bzw. Whitespaces
        public int size;

        /// Default constructor
        public Block(int id, int size) {
            this.id = id;
            this.size = size;
        }

        /// Erzeugt die Darstellung dieses Blocks als String
        public String toString() {
            String output = "";
            for (int i = 0; i < size; i++) {
                output += id == -1 ? "." : id;
            }
            return output;
        }
    }

    /// Liest den Input für diese Aufgabe aus der Quelldatei ein
    public static String readInput() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Git\\AdventOfCode\\Verena\\Day_9\\Day_9\\src\\day_9_input.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    /// Konvertiert einen String zeichenweise in ein Block-Array
    public static ArrayList<Block> toBlockList(String input) {
        char[] charArray = input.replaceAll("[\n\r]", "").toCharArray();
        ArrayList<Block> blocks = new ArrayList<>();
        for (int i = 0; i < charArray.length; i++) {
            int id = i % 2 == 0 ? i / 2 : -1; // Gerade id = file, ungerade = free
            blocks.add(new Block(id, Character.getNumericValue(charArray[i])));
        }
        return blocks;
    }

    /// Komprimiert die Blöcke, indem die Files so weit wie möglich nach rechts verschoben werden
    public static void compressFiles(ArrayList<Block> input) {
        //Für jeden Block
        int rightIndex = input.size() - 1;
        while(rightIndex > 0){
            var blockToMove = input.get(rightIndex);
            //Whitespace muss nicht verschoben werden
            if(blockToMove.id == -1){
                rightIndex --;
                continue;
            }
            for(int leftIndex = 0; leftIndex < rightIndex; leftIndex ++){
                var spaceToFill = input.get(leftIndex);
                //In ein File kann nicht geschrieben werden
                if(spaceToFill.id >= 0){
                    continue;
                }

                //Check, ob der block in den aktuellen Platz passt
                if(blockToMove.size > spaceToFill.size){
                    continue;
                }
                //Der Block passt -> Block verschieben
                int restCapacity = spaceToFill.size - blockToMove.size;
                spaceToFill.id = blockToMove.id;
                spaceToFill.size = blockToMove.size;
                blockToMove.id = -1;
                //Block mit Restkapazität einhängen wenn nötig
                if(restCapacity > 0){
                    input.add(leftIndex + 1, new Block(-1, restCapacity));
                }
                //System.out.println("Moved block " + spaceToFill.toString() + " from index " + rightIndex +" to "+leftIndex);
                break;
            }
            System.out.println("Working from index: " + rightIndex + " ...");
            rightIndex --;
        }
    }

    /// Berechnet die Checksumme
    public static void calcCheckSum(ArrayList<Block> blocks) {
        int counter = 0;
        long checksum = 0;
        for (var block : blocks) {
            for (int i = 0; i < block.size; i++) {
                if(block.id > 0){
                    checksum += (long) counter * block.id;
                }
                counter++;
            }
        }
        System.out.println("Checksum: " + checksum);
    }


    public static void main(String[] args) {

        String input = null;
        try {
            input = readInput();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var blocks = toBlockList(input);
        String testOut = "";
        for (var block : blocks) {
            testOut += block.toString();
        }
        //System.out.println("Blocks vor dem Komprimieren: " + testOut);
        compressFiles(blocks);
        testOut = "";
        for (var block : blocks) {
            testOut += block.toString();
        }
        System.out.println("Blocks nach dem Komprimieren: " + testOut);

        calcCheckSum(blocks);
    }
}
