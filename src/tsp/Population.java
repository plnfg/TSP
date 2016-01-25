/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import java.util.Collections;
import java.util.Random;

public class Population {

    int[][] cityCost = {
        {0, 55, 34, 32, 54, 40, 36, 40, 37, 53},
        {64, 0, 54, 55, 73, 45, 71, 50, 53, 52},
        {51, 48, 0, 41, 40, 58, 55, 33, 35, 37},
        {47, 46, 55, 0, 49, 45, 56, 52, 57, 55},
        {50, 39, 43, 52, 0, 26, 40, 39, 38, 33},
        {60, 49, 48, 57, 58, 0, 48, 47, 48, 48},
        {51, 37, 44, 43, 38, 40, 0, 64, 48, 47},
        {58, 41, 53, 45, 47, 43, 74, 0, 43, 42},
        {53, 38, 40, 33, 36, 58, 35, 30, 0, 31},
        {60, 39, 40, 56, 41, 41, 45, 59, 19, 0}};

    private final float mutRate = 0.03f;
    final int POP_SIZE = 8;
    final int GeneLength = 10;

    Random rnd = new Random();
    Individual child1;
    Individual child2;

    Individual[] init_population() {
        int i, fitness;
        Individual[] POP = new Individual[POP_SIZE];
        for (i = 0; i < POP_SIZE; i++) {
            POP[i] = new Individual();
            POP[i].setGenes();
            fitness = fitnessCalc(POP[i]);
            POP[i].setFitness(fitness);
        }

        return POP;
    }

    Individual[] generatePop(Individual[] pop) {

        int i = 0;

        Individual[] NEW_POP = new Individual[POP_SIZE];
        Individual parent1 ;
        Individual parent2 ;
        Individual offspring1;
        Individual offspring2;

        while (i < (POP_SIZE)) {

            parent1 = Selection(3, pop);
            parent2 = Selection(3, pop);

            doCrossover(parent1, parent2);
            offspring1 = child1;
            offspring2 = child2;
            child1 = Mutuate(offspring1);
            child2 = Mutuate(offspring2);
        

            NEW_POP[i] = (child1);
            NEW_POP[i + 1] = (child2);

            child1 = null;
            child2 = null;
            i += 2;

        }

        return NEW_POP;

    }

   

    Individual Selection(int tour, Individual[] pop) {
        int i, j = 0;
        Individual best;

        best = null;

        while (j < tour) {
            i = rnd.nextInt(POP_SIZE);
            if (best != pop[i]) {
                if (best == null || (pop[i].getFitness() < best.getFitness())) {
                    best = pop[i];
                }
                j++;
            }
        }
        return best;

    }

    private void doCrossover(Individual p1, Individual p2) {
        int cutPoint1 = 0;
        int cutPoint2 = 0;

        while (true) {
            if (!(cutPoint1 == cutPoint2)) {
                if (cutPoint2 < cutPoint1) {
                    int temp;
                    temp = cutPoint1;
                    cutPoint1 = cutPoint2;
                    cutPoint2 = temp;
                }
                break;
            } else {
                cutPoint1 = rnd.nextInt(10);
                cutPoint2 = rnd.nextInt(10);
            }
        }

        child1 = Order1X(p1, p2, cutPoint1, cutPoint2);
        child2 = Order1X(p2, p1, cutPoint1, cutPoint2);
        child1.setFitness(fitnessCalc(child1));
        child2.setFitness(fitnessCalc(child2));

    }

    private Individual Order1X(Individual primaryParent, Individual secondaryParent, int startPoint, int endPoint) {
        int i, index, j;
        int currentGene;
        Individual newBorn = new Individual();

        for (i = 0; i < 10; i++) {
            if (i >= startPoint && i <= endPoint) {
                newBorn.Genes.add(i, primaryParent.Genes.get(i));
            } else {
                newBorn.Genes.add(i, 0);
            }
        }

        for (i = 0; i < 10; i++) {
            index = (endPoint + i) % 10;
            currentGene = secondaryParent.Genes.get(index);
            if (newBorn.Genes.get(index) == 0) {
                if (!(newBorn.Genes.contains(currentGene))) {
                    newBorn.Genes.set(index, currentGene);
                } else {
                    for (j = index; (newBorn.Genes.contains(currentGene)); j++) {
                        currentGene = secondaryParent.Genes.get(j % 10);

                    }
                    newBorn.Genes.set(index, currentGene);
                }
            }
        }

        return newBorn;
    }

    private Individual Mutuate(Individual child) {

        float prob = rnd.nextFloat();
        int Point1 = -1;
        int Point2 = -1;

        if (prob <= mutRate) {
            while (true) {
                if (!(Point1 == Point2)) {
                    break;
                } else {
                    Point1 = rnd.nextInt(10);
                    Point2 = rnd.nextInt(10);
                }
            }
            Collections.swap(child.Genes, Point1, Point2);
        }

        return child;

    }
    
    
     int fitnessCalc(Individual indv) {
        int i;
        int city1, city2;
        int sum = 0;
        for (i = 0; i < GeneLength - 1; i++) {
            city1 = indv.Genes.get(i) - 1;
            city2 = indv.Genes.get(i + 1) - 1;
            sum += cityCost[city1][city2];
        }

        return sum;
    }

    public Individual getFittest(Individual[] pop) {
        Individual fittest = pop[0];
        int i;
        for (i = 1; i < POP_SIZE; i++) {
            if (pop[i].getFitness() < fittest.getFitness()) {
                fittest = pop[i];
            }
        }

        return fittest;
    }

}
