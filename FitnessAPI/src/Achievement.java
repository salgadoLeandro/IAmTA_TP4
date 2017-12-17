import java.util.Date;

public class Achievement {
    private int pontos;
    private int multiplier;
    private Date date;
        
    public Achievement(){
        this.pontos = 0;
        this.date = null;
        this.multiplier = 0;
    }
    
    public Achievement(int pontos, Date date) {
        this.pontos = pontos;
        this.date = date;
        this.multiplier = 1;
    }
    
    public Achievement(Achievement a){
        this.pontos = a.getPontos();
        this.date = a.getDate();
        this.multiplier = a.getMultiplier();
    }
    
    public final int getPontos() {
        return pontos;
    }

    public final void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public final Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }
    
    private final int getMultiplier(){
        return multiplier;
    }
    
    @Override
    public Achievement clone(){
        return new Achievement(this);
    }
 
    
    public static Achievement nextAchievement(Achievement a){
        return null;
    }
}
