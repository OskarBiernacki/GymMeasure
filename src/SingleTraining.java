import java.util.ArrayList;

public class SingleTraining{
    private int trainingNumber;
    private static int nextTrainingNumber=0;
    private ArrayList<SigleSeries> series = new ArrayList<>();
    public ArrayList<SigleSeries> getSeries() {
        return series;
    }
    SingleTraining(){
        this.trainingNumber=nextTrainingNumber++;
    }
    public int getTrainingNumber() {return trainingNumber;}
    public Object[] getSeriesOf(String exerciseName, String categoryName){
        Object[] output = new Object[8];
        boolean found = false;
        for(SigleSeries s : series){
            if(s.getExerciseName().compareTo(exerciseName)==0){
                found=true;
                for(int i=0;i<4;i++){
                    output[i*2]=s.weightInKg[i]==0?null:s.weightInKg[i];
                    output[i*2+1]=s.repetitions[i]==0?null:s.repetitions[i];
                }
            }
        }
        if(!found)series.add(new SigleSeries(exerciseName, categoryName));
        return output;
    }

    public void setSeriesOf(String exerciseName, Object[] seriesData){
        for(int i=0;i<series.size();i++)
            if(series.get(i).getExerciseName().compareTo(exerciseName)==0){
                for(int x=0;x<seriesData.length;x+=2){
                    if(seriesData[x]!=null)
                        series.get(i).weightInKg[x/2]=Double.parseDouble((String)seriesData[x]);
                    if(seriesData[x+1]!=null)
                        series.get(i).repetitions[x/2]=Integer.parseInt((String)seriesData[x+1]);
                }
            }
    }


}