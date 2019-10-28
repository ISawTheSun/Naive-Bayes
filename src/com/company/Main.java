package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Main {

    static double Length;
    static List<Car> Cars; //zbior treningowy
    static List<Car> Test; //zbior testowy
    static Set<String> AtrrSet; //lista atrybutow decyzyjnych
    static double True = 0; //liczba poprawnie zaklasyfikowanych przypadkow


    public static void main(String[] args) {
        AtrrSet = new HashSet<>();
        Cars = getData(new File("car.data.txt"));
        Length = Cars.size();
        Test = getTestList(new File("car.data.test.txt"));

        System.out.println(getResult(Test.get(0)));


        for (int i = 0; i < Test.size(); i++) {
            System.out.println("Result: "+getResult(Test.get(i))+'\n');
        }
        System.out.println("__________________________");
        System.out.println("Accuracy: "+getAccuracy());
    }

    public static String getResult(Car test){
        double maxVal = 0;
        String result = "";

        for (String attr: AtrrSet) {
            double N = countAttrDecyzyjny(attr);
            double P = (N/Length);
            double E = 1;
            for (int i = 0; i < test.atrr.size(); i++) {
                double tmp = countAttributes(test.atrr.get(i), attr)/N;
                if(tmp == 0){
                    tmp = Normalize(N, test.atrr.get(i));
                }
                E *= tmp;
            }
            E *=P;
            System.out.println(attr + " - "+ E);
            if(maxVal < E){
                maxVal = E;
                result = attr;
            }
        }

        if(result.equals(test.atrybutDecyzyjny.trim()))
            True++;
        return result;
    }

    public static double getAccuracy(){
        System.out.println("True: "+True);
        return True/(double)Test.size();
    }

    public static int countAttrDecyzyjny(String atrybutDecyzyjny){
        int count = 0;
        for (int i = 0; i < Cars.size(); i++) {
            if (Cars.get(i).atrybutDecyzyjny.equals(atrybutDecyzyjny))
                count++;
        }
        return count;
    }

    public static int countAttributes(String atrybut, String atrybutDecyzyjny){
        int count = 0;
        for (int i = 0; i < Cars.size(); i++) {
            if(Cars.get(i).atrybutDecyzyjny.equals(atrybutDecyzyjny)){
                if(Cars.get(i).atrr.contains(atrybut))
                    count++;
            }
        }
        return count;
    }

    public static double Normalize(double N, String attribute){
        int index = 0;

        for (int i = 0; i < Cars.size(); i++) {
            for (int j = 0; j < Cars.get(i).atrr.size(); j++) {
                if(Cars.get(i).atrr.get(j).equals(attribute)){
                    index = j;
                    break;
                }
            }
        }

        Set<String> set = new HashSet<>();
        for (int i = 0; i < Cars.size(); i++) {
            set.add(Cars.get(i).atrr.get(index));
        }

        int d = set.size();


        double res = 1/(N + d);
        return res;
    }

    public static List<Car> getTestList(File file){
        List<Car> Test = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int f;
            while ((f = fis.read()) != -1)
                sb.append((char) f);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String line: sb.toString().split("\n")) {
            String [] tmp = line.split(",");
            List<String> atrybuty = new ArrayList<>();
            for (int i = 0; i < tmp.length-1; i++) {
                atrybuty.add(tmp[i]);
            }
            String atrybutDecyzyjny = tmp[atrybuty.size()];

            Test.add(new Car(atrybuty, atrybutDecyzyjny));

        }
        return Test;
    }

    public static List<Car> getData(File file){
        List<Car> data = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int f;
            while ((f = fis.read()) != -1)
                sb.append((char) f);
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> atrybuty = null;
        for (String line: sb.toString().split("\n")) {
            atrybuty = new ArrayList<>();
            String [] tmp = line.split(",");
            for (int i = 0; i < tmp.length-1; i++) {
                atrybuty.add(tmp[i]);
            }
            String atrybutDecyzyjny = tmp[atrybuty.size()];
            AtrrSet.add(atrybutDecyzyjny.trim());
            Car car = new Car(atrybuty, atrybutDecyzyjny.trim());
            data.add(car);
        }

        return data;
    }

}
