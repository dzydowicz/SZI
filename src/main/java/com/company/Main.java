package com.company;

import com.company.LogicLayer.Table;
import com.company.TensorFlow.LabelImage;
import com.company.ViewLayer.MainFrame;

import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {

        LabelImage labelImage = new LabelImage();

//        for(int i = 1; i <= 100; i++){
//            labelImage.labelFood("resources/food_images/pizza/pizza ("+i+").jpg");
//            System.out.println("pizza("+i+")");
//        }
//        Genetic genetic = new Genetic();
//        genetic.start();
        MainFrame mainWindow = new MainFrame();
    }
}
