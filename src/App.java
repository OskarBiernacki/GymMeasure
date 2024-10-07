public class App {
    public static void main(String[] args) throws Exception {
        ExerciseCategpry[] categories = {
            new ExerciseCategpry("Klata" , new String[]{
                "płaska",
                "pochyła (góra)",
                "pochyła (dół)",
                "2 hantle płaska",
                "1 hantel płaska",
                "maszyna klata",
                "motyl",
                "liny góra",
                "liny środek",
                "liny dół",
                "dipy na klate",
            } ),
            new ExerciseCategpry("Biceps" , new String[]{
                "sztanga z podparcia",
                "2 hantle na ławce",
                "walenie kufli",
                "sztanga stojąc",
                "wyciskanie lin przed sobą",
            } ),
            new ExerciseCategpry("Plecy" , new String[]{
                "podciąganie bez supportu",
                "podciąganie z suportem",
                "ściąganie drążka na wyciągu pionowym siedząc",
                "ściąganie drążka wyciągu poziomego siedząc",
                "ściąganie drążka przed siebie",
                "ściąganie lin z dwóch stron za siebie",
                "wyciskanie sztangi",
                "martwy ciąg",
            } ),
            new ExerciseCategpry("Triceps" , new String[]{
                "ciągnięcie jajec pod siebie przed sobą",
                "ciągnięcie jajec przed siebie z za pleców",
                "dipy na triceps",
                "dipy maszyna",
                "hantel zza głowy",
                "wyciskanie 1 hantlem leżąc na ławce",
                "maszyna na triceps",
            } ),
            new ExerciseCategpry("Barki" , new String[]{
                "podnoszenie sztangi przed siebie z odwróconym chwytem",
                "wznosy",
                "Wyciskanie z hantlami na siedząco",
                "podnoszenie hantla leżąc na boku 45°",
                "firery",
                "hantle leżąc na brzuchu do boków",
                "podnoszenie hantla leżąc na boku od boku do góry",
                "sztanga przed siebie stojąc z odwróconym chwytem",
            } ),
            new ExerciseCategpry("Nogi" , new String[]{
                "przysiady",
                "maszyna przysiady",
                "maszyna wyprost",
                "maszyna ugięcie",
                "maszyna łydka",
            } ),
        };
        
        TrainingHistory trainingHistory = new TrainingHistory();
        trainingHistory.addEmptyTraining();
        new GuiWindow(categories,trainingHistory).showWindow();
    }
}
