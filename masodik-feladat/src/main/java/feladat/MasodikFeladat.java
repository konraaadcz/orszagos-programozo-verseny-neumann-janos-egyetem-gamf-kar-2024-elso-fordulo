import java.nio.file.*;
import java.util.*;

public class MasodikFeladat {

    public static void main(String[] args) throws Exception {


        ArrayList<String[]> telepulesek = new ArrayList<>();

        for (String sor : Files.readAllLines(Paths.get("masodik-feladat/src/main/resources/telepules.txt"))) {

            telepulesek.add(sor.split("\\s+"));
        }



        HashMap<String, String> megyek = new HashMap<>();

        for (String sor : Files.readAllLines(Paths.get("masodik-feladat/src/main/resources/megyek.txt"))) {

            String[] adat = sor.split("\\s+");

            megyek.put(adat[0], adat[1]);
        }




      

        HashMap<String, Integer> megyeLakos = new HashMap<>();


        for (String[] telepules : telepulesek) {

            String kod = telepules[1];

            int lakos = Integer.parseInt(telepules[5]);


            if (megyeLakos.containsKey(kod)) {

                megyeLakos.put(kod, megyeLakos.get(kod) + lakos);

            } else {

                megyeLakos.put(kod, lakos);
            }
        }


        ArrayList<Integer> lakosLista = new ArrayList<>(megyeLakos.values());

        Collections.sort(lakosLista);


        int masodikLegkisebb = lakosLista.get(1);



        for (String kod : megyeLakos.keySet()) {

            if (megyeLakos.get(kod) == masodikLegkisebb) {

                System.out.println(megyek.get(kod) + "-" + masodikLegkisebb);
            }
        }




     


        double legnagyobbSzelesseg = -100;

        String legeszakibb = "";


        for (String[] telepules : telepulesek) {

            double szelesseg = Double.parseDouble(telepules[2]);


            if (szelesseg > legnagyobbSzelesseg) {

                legnagyobbSzelesseg = szelesseg;

                legeszakibb = telepules[6];
            }
        }


        System.out.println(legeszakibb);





       


        double kecsLat = 0;
        double kecsLon = 0;

        double szegedLat = 0;
        double szegedLon = 0;



        for (String[] telepules : telepulesek) {


            if (telepules[6].equals("Kecskemet")) {

                kecsLat = Double.parseDouble(telepules[2]);
                kecsLon = Double.parseDouble(telepules[3]);
            }


            if (telepules[6].equals("Szeged")) {

                szegedLat = Double.parseDouble(telepules[2]);
                szegedLon = Double.parseDouble(telepules[3]);
            }
        }



        int darab = 0;



        for (String[] telepules : telepulesek) {


            double lat = Double.parseDouble(telepules[2]);

            double lon = Double.parseDouble(telepules[3]);



            double kecsTav = tavolsag(lat, lon, kecsLat, kecsLon);

            double szegedTav = tavolsag(lat, lon, szegedLat, szegedLon);



            if (kecsTav <= 50 && szegedTav <= 50) {

                darab++;
            }
        }


        System.out.println(darab);






       


        ArrayList<String[]> negyvenhetes = new ArrayList<>();


        for (String[] telepules : telepulesek) {

            double szelesseg = Double.parseDouble(telepules[2]);


            if (szelesseg >= 47.3 && szelesseg <= 47.4) {

                negyvenhetes.add(telepules);
            }
        }



        Collections.sort(negyvenhetes, (a, b) ->

                Double.compare(
                        Double.parseDouble(a[3]),
                        Double.parseDouble(b[3])
                )
        );



        double legnagyobbKulonbseg = 0;

        String eredmeny = "";



        for (int i = 0; i < negyvenhetes.size() - 1; i++) {


            double terulet1 = Double.parseDouble(negyvenhetes.get(i)[4]);

            double terulet2 = Double.parseDouble(negyvenhetes.get(i + 1)[4]);


            double kulonbseg = Math.abs(terulet1 - terulet2);



            if (kulonbseg > legnagyobbKulonbseg) {

                legnagyobbKulonbseg = kulonbseg;


                eredmeny =
                        negyvenhetes.get(i)[6]
                        + "-"
                        + negyvenhetes.get(i + 1)[6]
                        + "-"
                        + String.format("%.2f", kulonbseg);
            }
        }


        System.out.println(eredmeny);






        

        ArrayList<String[]> budas = new ArrayList<>();


        for (String[] telepules : telepulesek) {


            if (telepules[6].toLowerCase().contains("buda")) {

                budas.add(telepules);
            }
        }



        Collections.sort(budas, (a, b) ->

                Double.compare(
                        Double.parseDouble(a[3]),
                        Double.parseDouble(b[3])
                )
        );



        System.out.println(budas.get(2)[6]);






        


        int szamlalo = 0;



        for (String[] telepules : telepulesek) {


            String nev = telepules[6].toLowerCase();



            int a = nev.indexOf("a");

            int e = nev.indexOf("e", a + 1);

            int t = nev.indexOf("t", e + 1);



            if (a != -1 && e != -1 && t != -1) {

                szamlalo++;
            }
        }



        System.out.println(szamlalo);

    }




    public static double tavolsag(double lat1, double lon1, double lat2, double lon2) {


        double foldSugara = 6371;


        double dLat = Math.toRadians(lat2 - lat1);

        double dLon = Math.toRadians(lon2 - lon1);



        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2)
                +
                Math.cos(Math.toRadians(lat1))
                *
                Math.cos(Math.toRadians(lat2))
                *
                Math.sin(dLon / 2)
                *
                Math.sin(dLon / 2);



        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


        return foldSugara * c;
    }
}