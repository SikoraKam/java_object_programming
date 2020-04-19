public class PoliceUnit {
    int id;
    int id_gl;
    String nazwa;
    String rodzaj;
    String kod;
    String miasto;
    String ulica;
    int kier;
    String tel;
    String fax;
    String internet;
    String mail;

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append(id + "," + id_gl + "," + nazwa + "," + rodzaj + "," + kod + "," + miasto +"," + ulica + "," + kier + "," + tel + "," + fax
                + "," +internet + "," + mail +"\n");
        return buf.toString();
    }

}
