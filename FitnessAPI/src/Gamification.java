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

            for(Map.Entry<String,Utilizador> kp: users.entrySet()){
                rankingUsers.add(kp.getValue());
            }
            
            rankingUsers.sort((Utilizador u1, Utilizador u2) -> ((Integer)u1.getPontos()).compareTo(u2.getPontos()));
            
            return rankingUsers;

        }
        
        public int pointsOfDay(int passos){
            int points = 0;
            
            //Calcular numero de pontos;

            return points;
            
        }
        
        
        
        public boolean getUser(String user){
            return utilizadores.containsKey(user);
        }
        
        
        public void insertUsers(String username,int passos){
            Utilizador ut;
            int id = utilizadores.size()+1;
            ut = new Utilizador(username,id,pointsOfDay(passos));
            
            utilizadores.put(username, ut);
        }
        
        private int getUserLevel(int pontos){
            int lvl=0;
            
            return lvl;
        }
        
        //Niveis do World of Wrcraft
        public void updateUserLevel(){
            int nivel = 0;
            
            for(Map.Entry<String,Utilizador> kp: utilizadores.entrySet()){
                nivel = getUserLevel(kp.getValue().getPontos());
                if(nivel <= 20){
                    kp.getValue().setLevel("Novice");
                }else if(nivel <= 40){
                    kp.getValue().setLevel("Apprentice");
                }else if(nivel <= 80){
                    kp.getValue().setLevel("Journeyman");
                }else if(nivel <= 160){
                    kp.getValue().setLevel("Master");
                }else if(nivel <= 320){
                    kp.getValue().setLevel("Grand Master");
                }else if(nivel <= 800){
                    kp.getValue().setLevel("Sanic");
                }
                
            }
        }
        
        public void didUserGetAchievenents(String username,int passos,Date dt){
            Achievement a = new Achievement();
            a.setPontos(Achievement.userExtraDayPoints(passos));
            a.setName(Achievement.typeOFAchievement(Achievement.userExtraDayPoints(passos)));
            a.setDate(dt);
            utilizadores.get(username).insertAchievement(a);
        }
    
}
