
import java.util.*;

public class Utilizador {
    private String username;
    private int id;
    private int pontos;
    private String level;
    private List<Integer> passosDiarios;
    private Map<String, Achievement> achievements;
    
    public Utilizador(String username, int id) {
        this.username = username;
        this.id = id;
        this.pontos = 0;
        this.level = "beginner";
        this.achievements = new HashMap<>();
        this.passosDiarios = new ArrayList<>();
    }
    
    public Utilizador(Utilizador u) {
        this.username = u.getUsername();
        this.id = u.getId();
        this.pontos = u.getPontos();
        this.level = u.getLevel();
        this.achievements = new HashMap<>();
        this.setAchievements(u.getAchievements());
        this.passosDiarios = new ArrayList<>();
        
    }
    
    public final String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final int getPontos() {
        return pontos;
    }

    public final void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public final String getLevel() {
        return level;
    }

    public final void setLevel(String level) {
        this.level = level;
    }

    public final Map<String, Achievement> getAchievements() {
        return achievements;
    }

    public final void setAchievements(Map<String, Achievement> achievements) {
        achievements.entrySet().stream().forEach((kp) -> {
            this.achievements.put(kp.getKey(),kp.getValue());
        });
    }
    
    public final void insertAchievement(Achievement a){
        this.achievements.put(a.getName(), a);
        this.pontos += a.getPontos();
    }
    
    public final void addSteps(int passos){
        passosDiarios.add(passos);
    }
}
