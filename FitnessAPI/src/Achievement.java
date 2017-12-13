import org.joda.time.DateTime;

public class Achievement {
    private int pontos;
    private String name;
    private DateTime date;
        
    public Achievement(){
        this.pontos = 0;
        this.name = " ";
        this.date = null;
    }
    
    public Achievement(int pontos, String name, DateTime date) {
        this.pontos = pontos;
        this.name = name;
        this.date = date;
    }
    
    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
    
    public static int userExtraDayPoints(int passos){
            
            if(passos <= 500){
                return  ((int) 0.05 * passos);
            }else if(passos <= 1000){
                return ((int) 0.10 * passos);
            }else if(passos <= 2000){
                return ((int) 0.15 * passos);
            }else if(passos <= 8000){
                return ((int) 0.18 * passos);
            }else{
                return ((int) 0.25 * passos);
            }

    }
    
    public static String typeOFAchievement(int pontosExtra){
        
    }
    
}
