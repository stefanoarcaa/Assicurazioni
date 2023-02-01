import java.io.*;
import java.text.*;
import java.util.*;

public class Automobile extends Persona implements Serializable {
    Persona p;
    private String targa;
    private String codFiscProp;
    private Date dataImm;
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public Automobile(Persona p, String targa, Date dataImm)
    {
        super(p.getCodFiscale(), p.getNome(), p.getCognome(), p.getDataNascita());
        this.p = p;
        this.targa = targa;
        this.codFiscProp = p.getCodFiscale();
        this.dataImm = dataImm;
    }

    public String getTarga()
    {
        return targa;
    }

    public void setTarga(String targa)
    {
        this.targa = targa;
    }

    public String getCodFiscProp()
    {
        return codFiscProp;
    }

    public void setCodFiscProp(String codFiscProp)
    {
        this.codFiscProp = codFiscProp;
    }

    public Date getDataImm()
    {
        return dataImm;
    }

    public void setDataImm(Date dataImm)
    {
        this.dataImm = dataImm;
    }

    public boolean equals(Automobile a) {
        if (this == a) return true;
        if (a == null || a.getTarga().equals(this.getTarga())) return false;
        return Objects.equals(targa, a.targa) ;
    }

    @Override
    public String toString() {
        return "Automobile di " + p.getNome()  + " " + p.getCognome() +
                "\ntarga = " + targa +
                " | codFiscProp = " + codFiscProp +
                " | dataImm = " + df.format(dataImm) + "\nPROPRIETARIO/A DELL'AUTO:\n" + p.toString();
    }

    public Persona getP()
    {
        return p;
    }

    public static ArrayList<Automobile> LeggiAutomobili (File filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("File " + filename  + " esistente\n");
            TestIncidenti.Automobili = (ArrayList<Automobile>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("File \"" + filename + "\" non trovato, creazione del nuovo file\n");
            try {
                filename.createNewFile();
                System.out.println("File \"" + filename  + "\" creato correttamente\n");
                FileOutputStream fos = new FileOutputStream(filename, true);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                if(TestIncidenti.Persone.size()!=0)
                {
                    System.out.println("Come inizializzare il nuovo file Automobili.dat?");
                    System.out.println("1 -> Importando automaticamente le auto dal file Automobili.txt gia esistente");
                    System.out.println("2 -> Inserendo le automobili (e di conseguenza le rispettive polizze) in seguito");
                    System.out.print("Azione da eseguire: ");
                    Scanner sc = new Scanner(System.in);
                    int i = sc.nextInt();
                    switch(i)
                    {
                        case 1:
                            FileReader fr = new FileReader("Automobili.txt");
                            BufferedReader br = new BufferedReader(fr);
                            String line;
                            while ((line = br.readLine()) != null)
                            {
                                String[] s = line.split(" ");
                                System.out.println("la stringa è: " + line);
                                for (Persona p : TestIncidenti.Persone) {
                                    if (p.getCodFiscale().equals(s[0])) {
                                        Automobile a = new Automobile(p, s[1], df.parse(s[2]));
                                        TestIncidenti.Automobili.add(a);
                                    }
                                }
                            }
                            System.out.println("\nInserite nel database\n");
                            break;
                        case 2:
                            break;
                    }
                }
                oos.writeObject(TestIncidenti.Automobili);
                oos.flush();
                oos.close();
            } catch (IOException | ParseException ex)
            {
                System.out.println("File non trovato o errore nel parse");
            }
        } catch (ClassNotFoundException | IOException e)
        {
            System.out.println("Errore nel file");
        }
        return TestIncidenti.Automobili;
    }
}
