import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Incidente extends Polizza{
    Polizza p;
    private int codice;
    private String codPolizza;
    private Date dataInc;
    private double importo;
    private String copertura;
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public String toString() {
        return "Incidente:\n" +
                "codice incidente = " + codice +
                " | targa dell'auto = " + p.getA().getTarga() +
                " | codice polizza = " + p.getCodicePolizza() +
                " | premio assicurativo = " + p.getPremioAss() +
                " | data incidente = " + df.format(dataInc) +
                " | importo = " + importo +
                " | copertura = " + copertura;
    }

    public Incidente(Polizza p, int codice, Date dataInc, double importo, String copertura)
    {
        super(p.getP(), p.getA(), p.getCodicePolizza(), p.getDataIniz(), p.getDataFine(), p.getPremioAss());
        this.p = p;
        this.codice = codice;
        this.codPolizza = p.getCodicePolizza();
        this.dataInc = dataInc;
        this.importo = importo;
        this.copertura = copertura;
    }

    public boolean equals(Incidente i) {
        if (this == i) return true;
        if (i == null || i.getCodice()!=this.getCodice()) return false;
        return codice == i.codice;
    }

    public Polizza getPol()
    {
        return p;
    }

    public void setP(Polizza p) {
        this.p = p;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getCodPolizza() {
        return codPolizza;
    }

    public void setCodPolizza(String codPolizza) {
        this.codPolizza = codPolizza;
    }

    public Date getDataInc() {
        return dataInc;
    }

    public void setDataInc(Date dataInc) {
        this.dataInc = dataInc;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }
    public String getCopertura() {
        return copertura;
    }

    public void setCopertura(String copertura) {
        this.copertura = copertura;
    }

    public static void LeggiIncidenti(File filename)
    {
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("File Esistente\n");
            TestIncidenti.Incidenti = (ArrayList<Incidente>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e)
        {
            try {
                filename.createNewFile();
            } catch (IOException ex)
            {
                System.out.println("Errore caricamento 1");
            }
        } catch (IOException | ClassNotFoundException ex)
        {
            System.out.println("Il file Incidenti.dat è vuoto");
        }
    }
}
