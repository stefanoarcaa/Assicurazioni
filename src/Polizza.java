
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Polizza extends Automobile {
    Automobile a;
    Persona p;
    private String codicePolizza;
    private String targaAss;
    private String codFiscInt;
    private Date dataIniz;
    private Date dataFine;
    private double premioAss;
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public Polizza(Persona p, Automobile a, String codicePolizza, Date dataIniz, Date dataFine, double premioAss)
    {
        super(a.getP(), a.getTarga(), a.getDataImm());
        this.a = a;
        this.p = p;
        this.codicePolizza = codicePolizza;
        this.targaAss = a.getTarga();
        this.codFiscInt = p.getCodFiscale();
        this.dataIniz = dataIniz;
        this.dataFine = dataFine;
        this.premioAss = premioAss;
    }



    public boolean equals(Polizza p) {
        if (this == p) return true;
        if (p == null || !p.getCodicePolizza().equals(this.getCodicePolizza())) return false;
        return Objects.equals(codicePolizza, p.codicePolizza);
    }


    public Persona getIntPol()
    {
        return p;
    }

    public void setP(Persona p)
    {
        this.p = p;
    }

    public Automobile getA()
    {
        return a;
    }

    public void setA(Automobile a) {
        this.a = a;
    }

    public String getCodicePolizza() {
        return codicePolizza;
    }

    public void setCodicePolizza(String codicePolizza) {
        this.codicePolizza = codicePolizza;
    }

    public String getTargaAss() {
        return targaAss;
    }

    public void setTargaAss(String targaAss) {
        this.targaAss = targaAss;
    }

    public String getCodFiscInt() {
        return codFiscInt;
    }

    public void setCodFiscInt(String codFiscInt) {
        this.codFiscInt = codFiscInt;
    }


    public void setDataIniz(Date dataIniz) {
        this.dataIniz = dataIniz;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public double getPremioAss() {
        return premioAss;
    }

    public void setPremioAss(double premioAss) {
        this.premioAss = premioAss;
    }

    public Date getDataIniz()
    {
        return dataIniz;
    }

    @Override
    public String toString()
    {
        if(a.getP().getCodFiscale().equals(codFiscInt))
        {
            return "POLIZZA:\n" +
                    "codicePolizza = " + codicePolizza +
                    " | dataIniz = " + df.format(dataIniz) +
                    " | dataFine = " + df.format(dataFine) +
                    " | premioAss = " + premioAss +
                    "\nAUTO RELATIVA A QUESTA POLIZZA: \nAutomobile di " + p.getNome()  + " " + p.getCognome() +
                    "\ntarga = " + a.getTarga() +
                    " | codFiscProp = " + a.getCodFiscProp() +
                    " | dataImm = " + df.format(a.getDataImm()) +
                    "\nINTESTATARIO DELLA POLIZZA E PROPRIETARIO DELL'AUTO:\n" + p.toString();
        }
        else
        {
            return "POLIZZA:\n" +
                    "codicePolizza = " + codicePolizza +
                    " | dataIniz = " + df.format(dataIniz) +
                    " | dataFine = " + df.format(dataFine) +
                    " | premioAss = " + premioAss +
                    "\nAUTO RELATIVA A QUESTA POLIZZA:\n" + a.toString() +
                    "\nINTESTATARIO DELLA POLIZZA: \n" + p.toString();
        }
    }

    public static double AggiornaTotPremi(int anno)
    {
        double totpremiass = 0;
        double totprezzoinc = 0;
        for(Polizza pz : TestIncidenti.Polizze)
        {
            Calendar c = Calendar.getInstance();
            c.setTime(pz.getDataIniz());
            if(c.get(Calendar.YEAR)==anno)
            {
                totpremiass += pz.getPremioAss();
            }
        }
        for(Incidente inc : TestIncidenti.Incidenti)
        {
            if(inc.getCopertura().equals("si"))
            {
                Calendar c = Calendar.getInstance();
                c.setTime(inc.getDataInc());
                if(c.get(Calendar.YEAR)==anno)
                {
                    totprezzoinc += inc.getImporto();
                }
            }
        }
        return totpremiass-totprezzoinc;
    }
    public static ArrayList<Polizza> LeggiPolizza(File filename)
    {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("File " + filename  + " esistente\n");
            TestIncidenti.Polizze = (ArrayList<Polizza>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("File \"" + filename + "\" non trovato, creazione del nuovo file\n");
            try {
                filename.createNewFile();
                System.out.println("File \"" + filename  + "\" creato correttamente\n");
                FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                if(TestIncidenti.Automobili.size()!=0)
                {
                    System.out.println("Inizializzo il file Polizze.dat importando i dati da Polizze.txt");
                    FileReader fr = new FileReader("Polizze.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] s = line.split(" ");
                        System.out.println("la stringa è: " + line);
                        for (Persona p : TestIncidenti.Persone) {
                            if (p.getCodFiscale().equals(s[0])) {
                                for (Automobile a : TestIncidenti.Automobili) {
                                    if (a.getTarga().equals(s[1])) {
                                        Polizza pz = new Polizza(p, a, s[2], df.parse(s[3]), df.parse(s[4]), Double.parseDouble(s[5]));
                                        TestIncidenti.Polizze.add(pz);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("\nInserite nel database\n");
                }
                oos.writeObject(TestIncidenti.Polizze);
                oos.flush();
                oos.close();
            } catch (IOException | ParseException ex) {
                System.out.println("File non trovato o errore nel parse");
            }
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Errore nel file");
        }
        return TestIncidenti.Polizze;
    }
}
