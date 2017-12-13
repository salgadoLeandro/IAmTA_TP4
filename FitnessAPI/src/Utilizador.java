
import java.util.HashMap;
import java.util.Map;

public class Utilizador {
    private String username;
    private int id;
    private int pontos;
    private String level;
    private Map<String,Achievement> achievements;
    
    public Utilizador(String username,int id) {
        this.username = username;
        this.id = id;
        this.pontos = 0;
        this.level = "begginer";
        this.achievements = new HashMap<> ();
    }
    
    public Utilizador(String username, int id, int pontos, String level, Map<String, Achievement> achievements) {
        this.username = username;
        this.id = id;
        this.pontos = pontos;
        this.level = level;
        this.achievements = achievements;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Map<String, Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Map<String, Achievement> achievements) {
        this.achievements = achievements;
    }
}