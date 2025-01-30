package src;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Diseases {
    int iddiseases;
    String name;
    String description;
    String discovery_date;

    public Diseases(int iddiseases, String name, String description, String discovery_date) {
        this.iddiseases = iddiseases;
        this.name = name;
        this.description = description;
        this.discovery_date = discovery_date;
    }

    public int getIddiseases() {
        return iddiseases;
    }

    public void setIddiseases(int iddiseases) {
        this.iddiseases = iddiseases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscovery_date() {
        return discovery_date;
    }

    public void setDiscovery_date(String discovery_date) {
        this.discovery_date = discovery_date;
    }

    @Override
    public String toString() {
        return "Diseases{" +
                "discovery_date='" + discovery_date + '\'' +
                '}';
    }
}


/*public class SimpleDateFormatDemo {
    public static void main(String[] args) {
        Date date_of_today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = format.format(date_of_today);
        System.out.println(stringDate);
    }
}*/
