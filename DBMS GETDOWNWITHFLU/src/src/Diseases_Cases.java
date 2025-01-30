package src;

public class Diseases_Cases {
    int iddiseases_cases;
    int iddiseases;
    int idcountries;
    int cases;
    int deaths;
    int recoverings;
    String report_date;

    public Diseases_Cases(int iddiseases_cases, int iddiseases, int idcountries, int cases, int deaths, int recoverings, String report_date) {
        this.iddiseases_cases = iddiseases_cases;
        this.iddiseases = iddiseases;
        this.idcountries = idcountries;
        this.cases = cases;
        this.deaths = deaths;
        this.recoverings = recoverings;
        this.report_date = report_date;
    }

    public int getIddiseases_cases() {
        return iddiseases_cases;
    }

    public void setIddiseases_cases(int iddiseases_cases) {
        this.iddiseases_cases = iddiseases_cases;
    }

    public int getIddiseases() {
        return iddiseases;
    }

    public void setIddiseases(int iddiseases) {
        this.iddiseases = iddiseases;
    }

    public int getIdcountries() {
        return idcountries;
    }

    public void setIdcountries(int idcountries) {
        this.idcountries = idcountries;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecoverings() {
        return recoverings;
    }

    public void setRecoverings(int recoverings) {
        this.recoverings = recoverings;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }
}
