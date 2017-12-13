
import org.joda.time.DateTime;

public class Achievement {
    private int pontos;
    private String name;
    private DateTime date;
        
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
    
}
