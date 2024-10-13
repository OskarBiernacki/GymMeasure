import java.util.ArrayList;

public class Profiles {
    static private ArrayList<Profile> allProfiles = new ArrayList<>();
    static private int selectedIndex=0;
    static void addProfile(Profile p){
        allProfiles.add(p);
    }

    static Profile getSelectedProfile(){
        if(allProfiles.size()==0 || selectedIndex >= allProfiles.size() || selectedIndex < 0)
            return  null;

        return allProfiles.get(selectedIndex);
    }

    static Profile selectProfile(String profileName){
        for(int i=0;i<allProfiles.size();i++)
            if(allProfiles.get(i).profileName.compareTo(profileName)==0)
                selectedIndex=i;
        return getSelectedProfile();
    }

    static String[] getProfilesNames(){
        String[] profilesNames = new String[allProfiles.size()];
        for(int i=0;i<allProfiles.size();i++)
            profilesNames[i]=allProfiles.get(i).profileName;
        return profilesNames;
    }

    public static void loadAllProviles(){
        allProfiles.add(new Profile("Oskar"));
        allProfiles.add(new Profile("Adrian"));
        allProfiles.add(new Profile("Michu"));
    }
}

class Profile{
    public String profileName;
    public TrainingHistory trainingHistory;
     
    public Profile(String name){
        this.profileName=name;
        this.trainingHistory=new TrainingHistory();
        trainingHistory.addEmptyTraining();
    }
}