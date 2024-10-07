import java.util.ArrayList;

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
        if(selectedHistoryIndex+1<trainingsHistory.size())
            this.selectedHistoryIndex++;
    }
    public void previousTraining(){
        if(this.selectedHistoryIndex-1 > 0)
            this.selectedHistoryIndex--;
    }
    public int getHistoryIndex(){return this.selectedHistoryIndex;}

    public SingleTraining getCurrentTraining(){
        return trainingsHistory.get(selectedHistoryIndex);
    }
}
