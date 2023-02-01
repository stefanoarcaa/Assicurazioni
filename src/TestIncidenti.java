import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestIncidenti {
    public static ArrayList<Persona> Persone = new ArrayList<>();
    public static ArrayList<Automobile> Automobili = new ArrayList<>();
    public static ArrayList<Polizza> Polizze = new ArrayList<>();
    public static ArrayList<Incidente> Incidenti = new ArrayList<>();
    public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public static Iterator<Polizza> itP;
    public static Iterator<Automobile> itA;

    public static Persona ControlloCodice(Scanner sc, int c) {
        while (true) {
            if (c == 1) {
                System.out.print("Inserire codice fiscale del proprietario dell'auto: ");
            } else {
                System.out.print("Inserire codice fiscale dell'intestatario della polizza: ");
            }
            String codice = sc.next().toUpperCase();
            for (Persona a : Persone) {
                if (a.getCodFiscale().equals(codice)) {
                    return a;
                }
            }
            System.out.println("Codice fiscale inesistente");
            System.out.println("Azioni disponibili:");
            System.out.println("1 -> Aggiungere persona");
            System.out.println("2 -> Reinserire codice fiscale");
            System.out.println("3 -> Annulla inserimento");
            System.out.print("Azione da eseguire: ");
            int i = sc.nextInt();
            switch (i) {
                case 1:
                    try {
                        System.out.println("Codice fiscale: " + codice);
                        System.out.print("Inserire nome: ");
                        String nome = sc.next();
                        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
                        System.out.print("Inserire cognome: ");
                        String cognome = sc.next();
                        cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1);
                        System.out.print("Inserire data di nascita: ");
                        String dataNascita = sc.next();
                        return new Persona(codice, nome, cognome, df.parse(dataNascita));
                    } catch (ParseException e) {
                        System.out.println("Errore caricamento");
                    }
                    break;
                case 2:
                    break;
                case 3:
                    return null;
            }
        }
    }

    public static void AggiungiPersona(Scanner sc) {
        try {
            System.out.print("Inserire codice fiscale: ");
            String codFiscale = sc.next().toUpperCase();
            System.out.print("Inserire nome: ");
            String nome = sc.next();
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
            System.out.print("Inserire cognome: ");
            String cognome = sc.next();
            cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1);
            System.out.print("Inserire data di nascita: ");
            String dataNascita = sc.next();
            Persone.add(new Persona(codFiscale, nome, cognome, df.parse(dataNascita)));
            Collections.sort(Persone);
            System.out.println("Persona aggiunta con successo al database");
        } catch (ParseException e) {
            System.out.println("Errore caricamento");
        }
    }

    public static ArrayList<Persona> RimuoviPersona(Scanner sc) {
        boolean b1 = true;
        while (b1) {
            boolean b = true;
            System.out.print("Inserisci codice fiscale della persona da rimuovere: ");
            String codfisc = sc.next().toUpperCase();
            for (Persona a : Persone) {
                if (a.getCodFiscale().equals(codfisc)) {
                    b = false;
                    System.out.println("La persona in questione è: " + a + "\n");
                    boolean b2 = true;
                    boolean b3 = true;
                    for (Polizza pz : Polizze) {
                        if (pz.getA().getP().equals(a)) {
                            b2 = false;
                        }
                        if (pz.getIntPol().equals(a)) {
                            b3 = false;
                        }
                    }
                    if (!b2) {
                        System.out.println("Codice associato a queste auto:\n");
                        for (Automobile au : Automobili) {
                            if (au.getP().equals(a)) {
                                System.out.println(au + "\n");
                            }
                        }
                    } else {
                        System.out.println("\nCodice non associato ad alcuna auto");
                    }
                    if (!b3) {
                        System.out.println("Codice associato a queste polizze:\n");
                        for (Polizza pz : Polizze) {
                            if (pz.getIntPol().equals(a)) {
                                System.out.println(pz + "\n");
                            }
                        }
                    } else {
                        System.out.println("Codice non associato ad alcuna polizza ");
                    }
                    if (!b3 || !b2) {
                        System.out.println("1 -> Reinserire codice fiscale");
                        System.out.println("2 -> Rimuovi persona e corrispettivi auto e polizza");
                        System.out.println("3 -> Annulla operazione");
                        System.out.print("Azione da eseguire: ");
                        int i = sc.nextInt();
                        switch (i) {
                            case 1:
                                break;
                            case 2:
                                b1 = false;
                                itP = Polizze.iterator();
                                while (itP.hasNext()) {
                                    Polizza pz = itP.next();
                                    if (pz.getIntPol().equals(a)) {
                                        itA = Automobili.iterator();
                                        while (itA.hasNext()) {
                                            Automobile au = itA.next();
                                            if (pz.getA().equals(au)) {
                                                itA.remove();
                                                break;
                                            }
                                        }
                                        itP.remove();
                                    }
                                }
                                itA = Automobili.iterator();
                                while (itA.hasNext()) {
                                    Automobile au = itA.next();
                                    if (au.getP().equals(a)) {
                                        itP = Polizze.iterator();
                                        while (itP.hasNext()) {
                                            Polizza pz = itP.next();
                                            if (pz.getA().equals(au)) {
                                                itP.remove();
                                                break;
                                            }
                                        }
                                        itA.remove();
                                    }
                                }
                                Persone.remove(a);
                                break;
                            case 3:
                                b1 = false;
                                break;
                        }
                    } else {
                        Persone.remove(a);
                        b1 = false;
                    }
                    break;
                }
            }
            if (b) {
                System.out.println("Questo codice fiscale non è presente nel database.");
                System.out.println("1 -> Reinserire codice fiscale");
                System.out.println("2 -> Annulla operazione");
                System.out.print("Azione da eseguire: ");
                int i = sc.nextInt();
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        b1 = false;
                        break;
                }
            }
        }
        return Persone;
    }

    public static void AggiungiAutoPolizza(Scanner sc) {
        int c = 1;
        Persona p = ControlloCodice(sc, c);
        if (p != null) {
            try {
                Persone.add(p);
                System.out.print("Inserire targa dell'auto: ");
                String targa = sc.next().toUpperCase();
                System.out.print("Inserire data immatricolazione: ");
                String dataImm = sc.next();
                Automobile a = new Automobile(p, targa, df.parse(dataImm));
                c = 2;
                Persona p1 = ControlloCodice(sc, c);
                if (p1 != null) {
                    Automobili.add(a);
                    System.out.print("Inserire codice polizza: ");
                    String codicePolizza = sc.next().toUpperCase();
                    System.out.print("Inserire data inizio polizza: ");
                    String dataInizio = sc.next();
                    System.out.print("Inserire data fine polizza: ");
                    String dataFine = sc.next();
                    System.out.print("Inserire premio assicurativo: ");
                    String premioAss = sc.next();
                    Polizze.add(new Polizza(p1, a, codicePolizza, df.parse(dataInizio), df.parse(dataFine), Double.parseDouble(premioAss)));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void RegistraIncidente(Scanner sc) {
        boolean b2 = true;
        while (b2) {
            System.out.print("Inserire codice della polizza dell'incidente: ");
            String s = sc.next().toUpperCase();
            boolean b = true;
            for (Polizza pz : Polizze) {
                b2 = false;
                if (pz.getCodicePolizza().equals(s)) {
                    b = false;
                    System.out.print("Inserire il codice dell'incidente: ");
                    String codinc = sc.next();
                    System.out.print("Inserire la data dell'incidente: ");
                    String data = sc.next();
                    String copertura;
                    try {
                        if (df.parse(data).before(pz.getDataFine()) && df.parse(data).after(pz.getDataIniz())) {
                            System.out.println("L'incidente è coperto dall'assicurazione");
                            copertura = "si";
                        } else {
                            System.out.println("L'incidente non è coperto dall'assicurazione");
                            copertura = "no";
                        }
                        System.out.print("Inserire l'importo dell'incidente: ");
                        String importo = sc.next();
                        Incidenti.add(new Incidente(pz, Integer.parseInt(codinc), df.parse(data), Double.parseDouble(importo), copertura));
                        if (copertura.equals("si")) {
                            pz.setPremioAss(pz.getPremioAss() + Double.parseDouble(importo) / 5);
                        }
                        break;
                    } catch (ParseException e) {
                        System.out.println("Errore nel parse");
                    }
                }
            }
            if (b) {
                System.out.println("Numero polizza inesistente\nAzioni disponibili: ");
                System.out.println("1 -> Reinserire codice polizza");
                System.out.println("2 -> Annulla operazione");
                System.out.print("Azione da eseguire: ");
                int i = sc.nextInt();
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        b2 = false;
                        break;
                }
            }
        }
    }

    public static void EstendiPolizza(Scanner sc) {
        boolean b2 = true;
        while (b2) {
            System.out.print("Inserire numero polizza da estendere: ");
            String s = sc.next();
            boolean b = true;
            for (Polizza pz : Polizze) {
                if (pz.getCodicePolizza().equals(s)) {
                    b2 = false;
                    b = false;
                    System.out.print("Polizza esistente, inserire il numero di anni per la quale si vuole estendere: ");
                    int i = sc.nextInt();
                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(pz.getDataFine());
                    c2.add(Calendar.YEAR, i);
                    pz.setDataFine(c2.getTime());
                    break;
                }
            }
            if (b) {
                System.out.println("Numero polizza inesistente\nAzioni disponibili: ");
                System.out.println("1 -> Reinserire codice polizza");
                System.out.println("2 -> Annulla operazione");
                System.out.print("Azione da eseguire: ");
                int i = sc.nextInt();
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        b2 = false;
                        break;
                }
            }
        }
    }

    public static void RimuoviAutoPolizza(Scanner sc) {
        boolean b2 = true;
        while (b2) {
            boolean b = true;
            System.out.println("Inserisci targa dell'auto da rimuovere");
            String s = sc.next();
            for (Automobile a : Automobili) {
                b = false;
                if (a.getTarga().equals(s)) {
                    for (Polizza pz : Polizze) {
                        if (pz.getA().equals(a)) {
                            Automobili.remove(a);
                            Polizze.remove(pz);
                            System.out.println("rimozione effettuata");
                            break;
                        }
                    }
                    break;
                }
            }
            if (b) {
                System.out.println("Targa auto inesistente\nAzioni disponibili: ");
                System.out.println("1 -> Reinserire targa");
                System.out.println("2 -> Annulla operazione");
                System.out.print("Azione da eseguire: ");
                int i = sc.nextInt();
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        b2 = false;
                        break;
                }
            }
        }
    }

    public static void ControlloAutoPolizza(Scanner sc) {
        boolean b2 = true;
        while (b2) {
            System.out.print("Inserire codice fiscale da controllare: ");
            String s = sc.next().toUpperCase();
            boolean b1 = true;
            for (Persona p : Persone) {
                if (p.getCodFiscale().equals(s)) {
                    b2 = false;
                    b1 = false;
                    System.out.println("Codice fiscale esistente. Cosa si vuole controllare?");
                    System.out.println("1 -> Auto intestate");
                    System.out.println("2 -> Polizze intestate");
                    System.out.println("3 -> Auto e polizze intestate");
                    System.out.print("Azione da eseguire: ");
                    int i = sc.nextInt();
                    switch (i) {
                        case 1:
                            boolean b = true;
                            System.out.println("AUTO INTESTATE:\n");
                            for (Automobile a : Automobili) {
                                if (a.getP().equals(p)) {
                                    System.out.println(a);
                                    b = false;
                                }
                            }
                            if (b) {
                                System.out.println("Non esistono auto intestate a questo codice fiscale nel nostro database");
                            }
                            break;
                        case 2:
                            b = true;
                            System.out.println("POLIZZE INTESTATE:\n");
                            for (Polizza pz : Polizze) {
                                if (pz.getIntPol().equals(p)) {
                                    System.out.println(pz);
                                    b = false;
                                }
                            }
                            if (b) {
                                System.out.println("Non esistono polizze intestate a questo codice fiscale nel nostro database");
                            }
                            break;
                        case 3:
                            b = true;
                            System.out.println("AUTO E POLIZZE INTESTATE:\n");
                            for (Automobile a : Automobili) {
                                if (a.getP().equals(p)) {
                                    System.out.println(a);
                                    b = false;
                                    break;
                                }
                            }
                            for (Polizza pz : Polizze) {
                                if (pz.getIntPol().equals(p)) {
                                    System.out.println(pz);
                                    b = false;
                                    break;
                                }
                            }
                            if (b) {
                                System.out.println("Non esistono auto o polizze intestate a questo codice fiscale nel nostro database");
                            }
                            break;
                    }
                }
            }
            if (b1) {
                System.out.println("Il codice fiscale è inesistente");
                System.out.println("Azioni disponibili:");
                System.out.println("1 -> Reinserire codice fiscale");
                System.out.println("2 -> Annulla inserimento");
                int i = sc.nextInt();
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        b2 = false;
                        break;
                }
            }
        }
    }

    public static void ControlloIncidenti(Scanner sc) {
        System.out.println("Definire la modalità di ricerca: ");
        System.out.println("1 -> Tramite codice fiscale dell'intestatario della polizza");
        System.out.println("2 -> Tramite numero polizza");
        System.out.println("3 -> Tramite targa dell'auto");
        int i = sc.nextInt();
        switch (i) {
            case 1:
                boolean b2 = true;
                while (b2) {
                    System.out.print("Inserire codice fiscale dell'intestatario della polizza: ");
                    String s = sc.next().toUpperCase();
                    boolean b = true;
                    for (Persona p : Persone) {
                        if (p.getCodFiscale().equals(s)) {
                            b2 = false;
                            b = false;
                            boolean b1 = true;
                            for (Incidente inc : Incidenti) {
                                if (inc.getPol().getP().equals(p)) {
                                    b1 = false;
                                    System.out.println(inc);
                                }
                            }
                            if (b1) {
                                System.out.println("Non esistono incidenti riferiti a questo codice fiscale");
                            }
                        }
                    }
                    if (b) {
                        System.out.println("Codice fiscale inesistente");
                        System.out.println("Azioni disponibili:");
                        System.out.println("1 -> Reinserire codice fiscale");
                        System.out.println("2 -> Annulla inserimento");
                        int i1 = sc.nextInt();
                        switch (i1) {
                            case 1:
                                break;
                            case 2:
                                b2 = false;
                                break;
                        }
                    }
                }
                break;
            case 2:
                b2 = true;
                while (b2) {
                    System.out.print("Inserire numero polizza: ");
                    String s = sc.next().toUpperCase();
                    boolean b = true;
                    for (Polizza pz : Polizze) {
                        if (pz.getCodicePolizza().equals(s)) {
                            b2 = false;
                            b = false;
                            boolean b1 = true;
                            for (Incidente inc : Incidenti) {
                                if (inc.getPol().equals(pz)) {
                                    b1 = false;
                                    System.out.println(inc);
                                }
                            }
                            if (b1) {
                                System.out.println("Non ci sono incidenti relativi a questa polizza");
                            }
                        }
                    }
                    if (b) {
                        System.out.println("Codice polizza inesistente");
                        System.out.println("Azioni disponibili:");
                        System.out.println("1 -> Reinserire codice polizza");
                        System.out.println("2 -> Annulla inserimento");
                        int i1 = sc.nextInt();
                        switch (i1) {
                            case 1:
                                break;
                            case 2:
                                b2 = false;
                                break;
                        }
                    }
                }
                break;
            case 3:
                b2 = true;
                while (b2) {
                    System.out.print("Inserire targa: ");
                    String s = sc.next().toUpperCase();
                    boolean b = true;
                    for (Automobile a : Automobili) {
                        if (a.getTarga().equals(s)) {
                            b2 = false;
                            b = false;
                            boolean b1 = true;
                            for (Incidente inc : Incidenti) {
                                if (inc.getPol().getA().equals(a)) {
                                    b1 = false;
                                    System.out.println(inc);
                                }
                            }
                            if (b1) {
                                System.out.println("Non ci sono incidenti relativi a questa targa");
                            }
                        }
                    }
                    if (b) {
                        System.out.println("Targa inesistente");
                        System.out.println("Azioni disponibili:");
                        System.out.println("1 -> Reinserire targa");
                        System.out.println("2 -> Annulla inserimento");
                        int i1 = sc.nextInt();
                        switch (i1) {
                            case 1:
                                break;
                            case 2:
                                b2 = false;
                                break;
                        }
                    }
                }
                break;
        }
    }

    public static <T> void SalvaFile(ArrayList<T> array, File filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(array);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.out.println("File binario non esistente");
        }
    }

    public static void main(String[] args) {
        File filebinpers = new File("Persone.dat");
        File filebinauto = new File("Automobili.dat");
        File filebinpol = new File("Polizze.dat");
        File filebininc = new File("Incidenti.dat");
        filebinpers.delete();
        filebinauto.delete();
        filebinpol.delete();
        filebininc.delete();
        Persone = Persona.LeggiPersone(filebinpers);
        Automobili = Automobile.LeggiAutomobili(filebinauto);
        Polizze = Polizza.LeggiPolizza(filebinpol);
        Incidente.LeggiIncidenti(filebininc);
        boolean b = true;
        do {
            System.out.println("\nPAGINA PRINCIPALE");
            System.out.println("Azioni disponibili: ");
            System.out.println("1 -> Aggiungi persona");
            System.out.println("2 -> Aggiungi automobile e rispettiva polizza");
            System.out.println("3 -> Registra incidente");
            System.out.println("4 -> Estendi polizza");
            System.out.println("5 -> Rimuovi persona");
            System.out.println("6 -> Rimuovi automobile e rispettiva polizza");
            System.out.println("7 -> Calcola guadagno annuale");
            System.out.println("8 -> Controlla auto e polizze intestate a una persona");
            System.out.println("9 -> Controlla incidenti relativi a una polizza, a una persona o a un'automobile");
            System.out.println("10 -> Visualizza le persone nel database");
            System.out.println("11 -> Visualizza le automobili nel database");
            System.out.println("12 -> Visualizza le polizze nel database");
            System.out.print("Azione da eseguire: ");
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            switch (i) {
                case 1:
                    AggiungiPersona(sc);
                    break;
                case 2:
                    AggiungiAutoPolizza(sc);
                    break;
                case 3:
                    RegistraIncidente(sc);
                    for (Incidente inc : Incidenti) {
                        System.out.println(inc);
                    }
                    break;
                case 4:
                    EstendiPolizza(sc);
                    break;
                case 5:
                    Persone = RimuoviPersona(sc);
                    break;
                case 6:
                    RimuoviAutoPolizza(sc);
                    break;
                case 7:
                    System.out.print("Inserire anno: ");
                    String s = sc.next();
                    double tot = Polizza.AggiornaTotPremi(Integer.parseInt(s));
                    System.out.print("Guadagno anno " + s + ": " + tot + "€");
                    break;
                case 8:
                    ControlloAutoPolizza(sc);
                    break;
                case 9:
                    ControlloIncidenti(sc);
                    break;
                case 10:
                    System.out.println();
                    for (Persona p : Persone) {
                        System.out.println(p);
                    }
                    break;
                case 11:
                    System.out.println();
                    for (Automobile a : Automobili) {
                        System.out.println(a + "\n");
                    }
                    break;
                case 12:
                    System.out.println();
                    for (Polizza pz : Polizze) {
                        System.out.println(pz + "\n");
                    }
                    break;
                default:
                    System.out.println("Numero inserito non valido");
                    break;
            }
            System.out.println("\nVuoi effettuare qualche altra operazione? ");
            while (true) {
                String s1 = sc.next();
                if (s1.equals("no")) {
                    b = false;
                    break;
                } else {
                    if (s1.equals("si")) {
                        break;
                    } else {
                        System.out.print("ERRORE\nInserire \"si\" o \"no\": ");
                    }
                }
            }
        } while (b);
        SalvaFile(Persone, filebinpers);
        SalvaFile(Automobili, filebinauto);
        SalvaFile(Polizze, filebinpol);
        SalvaFile(Incidenti, filebininc);
        try {
            FileWriter fw = new FileWriter("Incidenti.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (Incidente inc : Incidenti) {
                pw.println(inc);
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            System.out.println("Errore nella scrittura di Incidenti.txt");
        }
        System.out.println("Programma concluso con successo.");
    }
}
