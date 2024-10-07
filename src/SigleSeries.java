public class SigleSeries {
    public int[] repetitions;
    public double[] weightInKg;
    private String exerciseName;
    private String categoryName;
    public SigleSeries(String exerciseName, String categoryName) {
        this.repetitions = new int[4];
        this.weightInKg = new double[4];
        this.exerciseName = exerciseName;
        this.categoryName=categoryName;
    }
    public String getCategoryName() {return categoryName;}
    public String getExerciseName(){return exerciseName;}
}
