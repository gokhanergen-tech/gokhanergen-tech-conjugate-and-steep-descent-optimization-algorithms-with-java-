package tr.guncetek.odev;

import tr.guncetek.odev.core.algorithms.EslenikGradient;
import tr.guncetek.odev.core.consts.Direction;
import tr.guncetek.odev.core.entities.Model;
import tr.guncetek.odev.core.interfaces.DerivationProcess;
import tr.guncetek.odev.core.algorithms.DikAlgorithm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    private static DerivationProcess derivationProcess=(Float[] params)->{
        float x1=params[0];
        float x2=params[1];

        Float[] gradientParams=new Float[2];

        gradientParams[0]=2*x1-2*x2;
        gradientParams[1]=2*x2-2*x1;

        return gradientParams;
    };
    public static synchronized void dik(){
        Scanner scanner=new Scanner(System.in);

        try {
            //başlangıç paramterler
            Float[] parameters={Float.valueOf(2),Float.valueOf(1)};

            //modelin oluşturulması
            Model model=new Model(parameters,0.0001f,1);

            DikAlgorithm dikAlgorithm=new DikAlgorithm(model,derivationProcess);
            System.out.print("Gradient number => ");
            dikAlgorithm.getModel().setGradientNumber(scanner.nextFloat());
            Files.deleteIfExists(Path.of("files/values_new.txt"));

            Path path=Files.createFile(Path.of("files/values_new.txt"));
            OutputStream stream= Files.newOutputStream(path);
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(stream));
            int i=0;
            while(true) {

                bufferedWriter.write(String.format("%-50s %s",Arrays.toString(dikAlgorithm.getModel().getPoint()),
                        "value "+dikAlgorithm.calculateTheValueOfTheParameters()));

                Float[] gradient=dikAlgorithm.calculateGradient();

                float magnitude=dikAlgorithm.calculateMagnitude(gradient);
                System.out.println(String.format("%d iterasyon magnitude %.6f",i,magnitude));
                if(dikAlgorithm.isLowerEqualEpsilon(gradient))
                    break;
                Float[] directedGradient=dikAlgorithm.direction(Direction.NEGATIVE);
                dikAlgorithm.getModel()
                        .setPoint(dikAlgorithm
                                .calculateNewParameters(directedGradient,dikAlgorithm
                                        .getModel()
                                        .getPoint()));

                 bufferedWriter.newLine();
                 i++;

            }
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    public static synchronized void eslenik(){
        Scanner scanner=new Scanner(System.in);

        Float[] params=new Float[]{Float.valueOf(2),Float.valueOf(1)};
        Model model=new Model(params,0.0001f,0.0002f);
        try {
            EslenikGradient eslenikGradient=new EslenikGradient(model,derivationProcess);
            Float[] directedGradient=eslenikGradient.direction(Direction.NEGATIVE);
            if(eslenikGradient.isLowerEqualEpsilon(eslenikGradient.calculateGradient()))
                System.exit(0);

            Float[] oldGradient=eslenikGradient.calculateGradient();

            Float[] newParams=eslenikGradient.calculateNewParameters(directedGradient,eslenikGradient.getModel().getPoint());
            eslenikGradient.getModel().setPoint(newParams);

            System.out.print("Gradient number => ");
            eslenikGradient.getModel().setGradientNumber(scanner.nextFloat());
            int t=1;
            while(true){

                if(eslenikGradient.isLowerEqualEpsilon(eslenikGradient.calculateGradient())){
                    System.out.println((t)+" iterasyonda bulundu");
                    break;
                }else{
                   Float[] newDirectedGredient=eslenikGradient
                           .newDirectionWithTeta(eslenikGradient.newDirectionWithTeta(oldGradient));


                   Float[] paramsCycle=eslenikGradient.calculateNewParameters(newDirectedGredient,eslenikGradient.getModel().getPoint());
                   eslenikGradient.getModel().setPoint(paramsCycle);

                }
                t++;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static void start(){

        System.out.println("Hoş geldiniz....");
        System.out.println("exit - çıkış yap");
        System.out.println("start-dik - algoritmayı başlat");
        System.out.println("start-eslenik - algoritmayı başlat");
        Scanner scanner=new Scanner(System.in);

        while (true){
            String instruction="";

            System.out.print("İşlem => ");
            instruction=scanner.nextLine();
            switch (instruction){
                case "exit":
                    System.gc();
                    System.exit(0);
                case "start-dik":
                    dik();
                    break;
                case "start-eslenik":
                    eslenik();
                    break;


            }
        }

    }
    public static void main(String[] args) {
        start();
    }
}
