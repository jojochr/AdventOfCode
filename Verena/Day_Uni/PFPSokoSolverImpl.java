package Blatt_9;

public class PFPSokoSolverImpl implements PFPSokoSolver{
    /*
    Hinweise / Ideen / ANregungen zur aktuellen PFP-Abgabe:
    - Implementierung der eigentlichen Sokoban-Logik (z.B. Prüfung, in welche Richtung sich der Spieler bewegen kann)
    -> schaut euch die PFPSokoTools an, da gibt es mehrere Methoden am Ende, die einem die Arbeit abnehmen:
      - canMove() -> es reicht, nur auf kleingeschriebene Chars zu prüfen (u, d, r, l)
      - shiftBoxes() -> arbeitet inplace auf den übergebenen Boxes und gibt nur dann true zurück, wenn eine Box bewegt wurde
      - getPlayerX(), getPlayerY() -> berechnet die neue Playerposition anhand des übergebenen Chars
    - ich habe die Suche zunächst rekursiv sequentiell implementiert; muss man aber nicht machen, da es parallel mit Threads ohne Rekursion geht
    - z.B. könnte man Arbeiterthreads haben, die sich ihre APs aus einer Queue holen; für jedes AP werden folgende Schritte im Arbeiter ausgeführt:
      -> prüfen, ob der im AP enthaltene Levelzustand eine Lösung des Levels darstellt (falls  ja, Signal zur Beendigung senden)
      -> sonst: mögliche nächste Schritte des Spielers herausfinden, für jeden Schritt den neuen Levelstatus erstellen und diesen als AP verpackt in die Queue stecken
      -> wiederholen
    - habe mir zum Merken des Levelzustands eine eigene Klasse definiert (kennt die Spielerposition, die Boxenpositionen und die bisherigen "Bewegungen" = Schritte)
    - um Mehrfachverarbeitungen von bereits besuchten Levelzuständen zu vermeiden -> diese z.B. in einer ConcurrentHashMap speichern
    (um Platz zu sparen: statt eines "Levelzustands"objekts als Key lieber ein Bitset verwenden)

    - Idee für das Bitset: die Boxenpositionsmatrix (2D) auf ein 1D-Array abbilden und damit das Bitset füllen, dann noch die Positionen des Spielers kodiert anhängen (Integer auf Bitset abbilden)

    grafisches Spiel: siehe Befehl auf der Angabe (Achtung: falls die Klassen in einem Modul stecken: java <modulname>.PFPSokoTools ...) -> GUI-Fenster öffnet sich -> mittels Pfeiltasten lässt sich Spieler bewegen
     */

    /**
     * Solves a Sokoban level.
     *
     * @param lvl to be solved
     * @param numberOfThreads Number of additional threads to use.
     * @param timeout in seconds. Return null, if no solution inside the time limit is found.
     * @return a solution String with the chars
     * 			l (player left), L (box pushed, player left),
     * 			r (player right), R (box pushed, player right),
     * 			u (player up), U (box pushed, player up),
     * 			d (player down), D (box pushed, player down),
     */
    @Override
    public String solve(PFPSokoLvl lvl, int numberOfThreads, int timeout) {


        //Erstmal Lösung

        return "";
    }

    /**
    Löst das Problem indem alle Kombinationen bis zu einer bestimmten Tiefe durchprobiert werden
     */
    private String depthFirstSeach(PFPSokoLvl level, int numberOfThreads, int timeout, int d_max_initial) {

        for(int l = 0; l < d_max_initial; l++){
            for(int r = 0; r < d_max_initial; r++){
                for(int )
            }


        }




        return "";
    }
}
