import java.util.Date;

public class Achievement {
    private int pontos;
    private String name;
    private Date date;
        
    public Achievement(){
        this.pontos = 0;
        this.name = " ";
        this.date = null;
    }
    
    public Achievement(int pontos, String name, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
