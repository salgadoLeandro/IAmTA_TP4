
import java.util.*;

public class Utilizador {
    private String username;
    private int id;
    private int pontos;
    private String level;
    private List<Integer> passosDiarios;
    
    public Utilizador(String username, int id) {
        this.username = username;
        this.id = id;
        this.pontos = 0;
        this.level = "beginner";
        this.passosDiarios = new ArrayList<>();
    }
    
    public Utilizador(Utilizador u) {
        this.username = u.getUsername();
        this.id = u.getId();
        this.pontos = u.getPontos();
        this.level = u.getLevel();
        this.passosDiarios = new ArrayList<>();
        for(Integer i : u.getPassos()) { this.passosDiarios.add(i); }
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
    
    public final void addSteps(int passos){
        passosDiarios.add(passos);
    }
    
    public final List<Integer> getPassos(){
        List<Integer> ret = new ArrayList<>();
        for(Integer i : passosDiarios) { ret.add(i); }
        return ret;
    }
    
    public final int getTotalSteps(){
        int ret = 0;
        for(Integer i : passosDiarios){ ret += i; }
        return ret;
    }
    
    public final void updatePoints(int points){
        this.pontos += points;
    }
}
