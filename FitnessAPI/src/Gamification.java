import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gamification {
    private Map<String,Utilizador> utilizadores;
    
    public Gamification() {
        this.utilizadores = new HashMap<> ();
    }
    
    //Função que calcula a listagem do ranking de utilizadores
    public List<Utilizador> Ranking(Map<String,Utilizador> users) {
        List<Utilizador> rankingUsers = new ArrayList<>();

        users.entrySet().stream().forEach((kp) -> {
            rankingUsers.add(kp.getValue());
        });

        rankingUsers.sort((Utilizador u1, Utilizador u2) -> ((Integer)u1.getPontos()).compareTo(u2.getPontos()));

        return rankingUsers;
    }

    public int pointsOfDay(int passos){
        return passos/2;
    }
    
    private int getUserLevel(int pontos){
        return pontos/2500;
    }

    public boolean getUser(String user){
        return utilizadores.containsKey(user);
    }

    public void insertUser(String username, List<Integer> passos){
        Utilizador ut;
        int id = utilizadores.size()+1;
        ut = new Utilizador(username,id);
        for(Integer step : passos){
            ut.addSteps(step);
        }

        utilizadores.put(username, ut);
    }

    public void updateUserLevel(){
        int nivel = 0;

        for(Map.Entry<String,Utilizador> kp: utilizadores.entrySet()){
            nivel = getUserLevel(kp.getValue().getPontos());
            if(nivel <= 20){
                kp.getValue().setLevel("Novice");
            }else if(nivel <= 50){
                kp.getValue().setLevel("Apprentice");
            }else if(nivel <= 100){
                kp.getValue().setLevel("Journeyman");
            }else if(nivel <= 210){
                kp.getValue().setLevel("Master");
            }else if(nivel <= 420){
                kp.getValue().setLevel("Grand Master");
            }else if(nivel <= 2000){
                kp.getValue().setLevel("Sanic");
            }

        }
    }

    public void didUserGetAchievenents(String username, int passos, Date dt){
        Achievement a = new Achievement();
        a.setPontos(0);
        a.setName("");
        a.setDate(dt);
        utilizadores.get(username).insertAchievement(a);
    }
}
