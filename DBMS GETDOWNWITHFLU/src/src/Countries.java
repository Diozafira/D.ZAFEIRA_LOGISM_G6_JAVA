package src;

public class Countries {
    int idcountries;
    String country_name;
    String continent;
    long population;

    public Countries(int idcountries, String country_name, String continent, long population) {
        this.idcountries = idcountries;
        this.country_name = country_name;
        this.continent = continent;
        this.population = population;
    }

    public int getIdcountries() {
        return idcountries;
    }

    public void setIdcountries(int idcountries) {
        this.idcountries = idcountries;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }


}
