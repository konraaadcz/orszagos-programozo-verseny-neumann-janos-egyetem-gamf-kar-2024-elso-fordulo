import java.nio.file.*;
import java.util.*;

public class ElsoFeladat {

    public static void main(String[] args) throws Exception {

        ArrayList<String> szamok = new ArrayList<>();

        for (String sor : Files.readAllLines(Paths.get("elso-feladat/src/main/resources/szamok.txt"))) {
            szamok.add(sor);
        }


        int relativDarab = 0;
        long keresettSzam = 1310438493;


        for (String szam : szamok) {

            long ertek = Long.parseLong(szam);

            if (lnko(ertek, keresettSzam) == 1) {
                relativDarab++;
            }
        }


        String keresettAnagramma = "2354211341";

        int[] keresettDarab = new int[10];

        for (char karakter : keresettAnagramma.toCharArray()) {
            keresettDarab[karakter - '0']++;
        }


        HashSet<String> anagrammak = new HashSet<>();


        for (String szam : szamok) {

            if (szam.equals(keresettAnagramma)) {
                continue;
            }

            int[] szamDarab = new int[10];

            for (char karakter : szam.toCharArray()) {
                szamDarab[karakter - '0']++;
            }


            if (Arrays.equals(keresettDarab, szamDarab)) {
                anagrammak.add(szam);
            }
        }


        int anagrammaDarab = anagrammak.size();


        int[] ketjegyuDarab = new int[100];


        for (String szam : szamok) {

            for (int i = 0; i < szam.length() - 1; i++) {

                int ketjegyu = Integer.parseInt(szam.substring(i, i + 2));

                ketjegyuDarab[ketjegyu]++;
            }
        }


        int legtobb = 0;
        int leggyakoribb = 0;


        for (int i = 10; i < 100; i++) {

            if (ketjegyuDarab[i] > legtobb) {

                legtobb = ketjegyuDarab[i];
                leggyakoribb = i;
            }
        }


        System.out.println(relativDarab);
        System.out.println(anagrammaDarab);
        System.out.println(leggyakoribb);
    }


    public static long lnko(long elso, long masodik) {

        while (masodik != 0) {

            long maradek = elso % masodik;
            elso = masodik;
            masodik = maradek;
        }

        return elso;
    }
}