import java.util.Arrays;

public class ExerciseCategpry {
    public String exerciseCategoryName;
    public String[] exercisesNames;
    public ExerciseCategpry(String exerciseCategoryName, String[] exercisesNames) {
        this.exerciseCategoryName = exerciseCategoryName;
        this.exercisesNames = exercisesNames;
        System.out.println(exerciseCategoryName + " = "+Arrays.toString(exercisesNames));
    }
}
