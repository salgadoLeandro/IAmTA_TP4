import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gamification {
    private static final double mult = 0.08;
    
    private Map<String,Utilizador> utilizadores;
    
    public Gamification() {
        this.utilizadores = new HashMap<> ();
    }
    
    //Função que calcula a listagem do ranking de utilizadores
    public List<Utilizador> Ranking() {
        List<Utilizador> rankingUsers = new ArrayList<>();

        utilizadores.entrySet().stream().forEach((kp) -> {
            rankingUsers.add(kp.getValue());
        });

        rankingUsers.sort((Utilizador u1, Utilizador u2) -> ((Integer)u2.getPontos()).compareTo(u1.getPontos()));

        return rankingUsers;
    }

    public static int stepsToPoints(int passos){
        return passos/2;
    }
    
    public static int getUserLevel(int pontos){
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
    
    public void updateUserPoints() {
        Utilizador u;
        for(Map.Entry<String, Utilizador> kp : utilizadores.entrySet()){
            u = kp.getValue();
            u.setPontos(stepsToPoints(u.getTotalSteps()));
        }
    }

    public void updateUserAchievements(){
        int multiplier = 0, isteps = 10000, points = 0, steps = 0;
        Utilizador u;
        for(Map.Entry<String, Utilizador> kp : utilizadores.entrySet()){
            u = kp.getValue();
            for(Integer i : u.getPassos()){
                steps = multiply(isteps, multiplier);
                points += multiply(stepsToPoints(isteps), multiplier);
                if (i >= steps){
                    ++multiplier;
                } else if (multiplier > 0) {
                    --multiplier;
                }
            }
            u.updatePoints(points);
            points = multiplier = steps = 0;
        }
    }
    
    private int multiply(int value, int multiplier){
        return (int)(value * (1.0 + (multiplier * mult)));
    }
}
