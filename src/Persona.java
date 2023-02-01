
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Persona implements Comparable<Persona>, Serializable {
    private String codFiscale;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public Persona(String codFiscale, String nome, String cognome, Date dataNascita)
    {
        this.codFiscale = codFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    public String getCodFiscale()
    {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale)
    {
        this.codFiscale = codFiscale;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCognome()
    {
        return cognome;
    }

    public void setCognome(String cognome)
    {
        this.cognome = cognome;
    }

    public Date getDataNascita()
    {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita)
    {
        this.dataNascita = dataNascita;
    }


    public boolean equals(Persona p)
    {
        if (this == p) return true;
        if (p == null || p.getCodFiscale().equals(this.getCodFiscale()))  return false;
        return Objects.equals(codFiscale, p.codFiscale);
    }

    @Override
    public int compareTo(Persona o)
    {
        return this.cognome.compareTo(o.cognome);
    }

    @Override
    public String toString() {
        return "codFiscale = " + codFiscale +
                " | nome = " + nome +
                " | cognome = " + cognome +
                " | dataNascita = " + df.format(dataNascita);
    }
    public static ArrayList<Persona> LeggiPersone(File filename) {

        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("\nFile " + filename  + " esistente\n");
            TestIncidenti.Persone = (ArrayList<Persona>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nFile \"" + filename + "\" non trovato, creazione del nuovo file\n");
            try {
                filename.createNewFile();
                System.out.println("File \"" + filename  + "\" creato correttamente\n");
                FileOutputStream fos = new FileOutputStream(filename, true);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                System.out.println("Come inizializzare il nuovo file Persone.dat?");
                System.out.println("1 -> Importando automaticamente i nomi dal file Persone.txt gia esistente");
                System.out.println("2 -> Inserendo le persone in seguito");
                System.out.print("Azione da eseguire: ");
                Scanner sc = new Scanner(System.in);
                int i = sc.nextInt();
                switch(i)
                {
                    case 1:
                        FileReader fr = new FileReader("Persone.txt");
                        BufferedReader br = new BufferedReader(fr);
                        String firstline;
                        while ((firstline = br.readLine()) != null) {
                            System.out.println("la stringa è: " + firstline);
                            String[] p = firstline.split(", ");
                            Persona e1 = new Persona(p[0], p[1], p[2], df.parse(p[3]));
                            TestIncidenti.Persone.add(e1);
                        }
                        System.out.println("\nInserite nel database\n");
                        break;
                    case 2:
                        break;
                }
                Collections.sort(TestIncidenti.Persone);
                oos.writeObject(TestIncidenti.Persone);
                oos.flush();
                oos.close();
            } catch (IOException | ParseException r) {
                System.out.println("Persone.txt non esiste");
            }
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Errore nel file");
        }
        return TestIncidenti.Persone;
    }
}
