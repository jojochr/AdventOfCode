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
public class Day_9_1 {
    /// Model für einen Block von Dateien der gleichen ID
    public static class Block {
        /// ID dieses FileBlocks
        public final int id;
        /// Anzahl der Dateien bzw. Whitespaces
        public int size;
        /// Default constructor
        public Block(int id, int size){
            this.id = id;
            this.size = size;
        }

        /// Erzeugt die Darstellung dieses Blocks als String
        public String toString(){
            String output = "";
            for(int i = 0; i < size; i++){
                output += id == -1 ? "." : id;
            }
            return output;
        }
    }

    /// Liest den Input für diese Aufgabe aus der Quelldatei ein
    public static String readInput() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Git\\AdventOfCode\\Verena\\Day_9\\Day_9\\src\\day_9_input.txt"));
        try{
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
        finally {
            br.close();
        }
    }

    /// Konvertiert einen String zeichenweise in ein Block-Array
    public static Block[] toBlockArray(String input){
        char[] charArray = input.replaceAll("[\n\r]", "").toCharArray();
        Block[] blocks = new Block[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            int id = i % 2 == 0 ? i / 2 : -1; // Gerade id = file, ungerade = free
            blocks[i] = new Block(id, Character.getNumericValue(charArray[i]));
        }
        return blocks;
    }

    /// Komprimiert die Blöcke, sodass sich zwischen den Files kein Whitespace mehr befindet
    public static Block[] compressFiles(Block[] input){
        ArrayList<Block> comprimizedBlocks = new ArrayList<>(input.length);
        int writeIndex = 0;
        int readIndex = input.length - 1;

        while(writeIndex < readIndex){
            //Gerader Schreibindex -> File übernehmen
            if(writeIndex % 2 == 0){
                comprimizedBlocks.add(input[writeIndex]);
                writeIndex ++;
            }
            //Leerraum mit File vom Ende füllen
            else{
                var capacity = input[writeIndex].size;
                int transferredFiles = 0;
                //Solange, bis der Whitespace mit Files vom Ende gefüllt wurde
                while(transferredFiles < capacity){

                    //Sonderfall: Letzter Block
                    if(readIndex <= writeIndex)
                        break;

                    var blockToTakeFrom = input[readIndex];
                    int filesToTransfer = capacity - transferredFiles;

                    //Den gesamten Block übernehmen und Schreibindex auf den nächsten Block setzen
                    if(blockToTakeFrom.size <= filesToTransfer){
                        transferredFiles += blockToTakeFrom.size;
                        comprimizedBlocks.add(blockToTakeFrom);
                        readIndex -= 2;
                    }
                    //Der Block ist größer als die Anzahl der zu übernehmenden Files => Teilblock abspalten
                    else {
                        //Anzahl der übertragenen Files vom Restblock abziehen
                        blockToTakeFrom.size -= filesToTransfer;
                        transferredFiles += filesToTransfer;
                        comprimizedBlocks.add(new Block(blockToTakeFrom.id, filesToTransfer));
                    }
                }
                //Whitespace wurde gefüllt
                writeIndex ++;
            }
        }
        //Den letzten Block anhängen
        comprimizedBlocks.add(input[readIndex]);

        return comprimizedBlocks.toArray(new Block[0]);
    }

    /// Berechnet die Checksumme
    public static void calcCheckSum(Block[] blocks){
        int counter = 0;
        long checksum = 0;
        for (var block : blocks){
            for (int i = 0; i < block.size; i++ ){
                checksum += (long) counter * block.id;
                counter ++;
            }
        }
        System.out.println("Checksum: "+ checksum);
    }


    public static void main(String[] args){

        String input = null;
        try {
            input = readInput();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var blocks = toBlockArray(input);
        var comprimizedBlocks = compressFiles(blocks);
        calcCheckSum(comprimizedBlocks);
    }
}
