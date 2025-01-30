package src;

public class Reports {
    int idreport;
    int iduser;
    int iddiseases;
    int idcountries;
    String comment;
    String report_date;

    public Reports(int idreport, int iduser, int iddiseases, int idcountries, String comment, String report_date) {
        this.idreport = idreport;
        this.iduser = iduser;
        this.iddiseases = iddiseases;
        this.idcountries = idcountries;
        this.comment = comment;
        this.report_date = report_date;
    }

    public int getIdreport() {
        return idreport;
    }

    public void setIdreport(int idreport) {
        this.idreport = idreport;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }
}
