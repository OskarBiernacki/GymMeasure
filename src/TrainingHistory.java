import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.json.simple.JSONObject;

public class TrainingHistory {
    private ArrayList<SingleTraining> trainingsHistory;
    private int selectedHistoryIndex=0;

    TrainingHistory(){
        trainingsHistory = new ArrayList<>();
    }

    public void addEmptyTraining(){
        this.trainingsHistory.add(new SingleTraining());
    }

    public void nextTraining(){
        if(selectedHistoryIndex+1>=trainingsHistory.size())
            addEmptyTraining();
        this.selectedHistoryIndex++;
    }
    public void previousTraining(){
        if(this.selectedHistoryIndex-1 >= 0)
            this.selectedHistoryIndex--;
    }
    public int getHistoryIndex(){return this.selectedHistoryIndex;}

    public SingleTraining getCurrentTraining(){
        return trainingsHistory.get(selectedHistoryIndex);
    }

    @SuppressWarnings("unchecked")
    public JSONObject exportToJson(){
        JSONObject trainingHistroyJson = new JSONObject();
        try{
            for(int i=0;i<trainingsHistory.size();i++){
                JSONObject trainingJson = new JSONObject();
                for(SigleSeries ss : trainingsHistory.get(i).getSeries()){
                    JSONObject seriesJson = new JSONObject();
                    seriesJson.put("category", ss.getCategoryName());
                    seriesJson.put("repetitions", Arrays.toString(ss.repetitions));
                    seriesJson.put("weightInKg", Arrays.toString(ss.weightInKg));
                    trainingJson.put(ss.getExerciseName(), seriesJson);
                }

                trainingHistroyJson.put(i, trainingJson);
            }
        }catch(Exception ex){ex.printStackTrace();}
        System.out.println("\u001B["+(new Random().nextInt(7)+30)+"m" +trainingHistroyJson.toJSONString() + "\u001B[0m");
        return trainingHistroyJson;
    }
}
