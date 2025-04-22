package cz.czechitas.kockamyssyr;

import dev.czechitas.java1.kockamyssyr.api.*;

import java.awt.*;
import java.util.Random;

/**
 * Hlaví třída pro hru Kočka–myš–sýr.
 */
public class HlavniProgram {
  private final Random random = new Random();

  private final int VELIKOST_PRVKU = 50;
  private final int SIRKA_OKNA = 1000 - VELIKOST_PRVKU;
  private final int VYSKA_OKNA = 600 - VELIKOST_PRVKU;

  private Cat tom;
  private Mouse jerry;

  /**
   * Spouštěcí metoda celé aplikace.
   *
   * @param args
   */
  public static void main(String[] args) {
    new HlavniProgram().run();
  }

  /**
   * Hlavní metoda obsahující výkonný kód.
   */
  public void run() {
    tom = vytvorKocku();
//        tom.setBrain(new KeyboardBrain(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D));

    jerry = vytvorMys();
    jerry.setBrain(new KeyboardBrain());

    vytvorVeci(4);
    chytMys();
  }

  public void chytMys() {
    //0, 0 je levý horní roh
    otocKockuSmeremNahoru();
    // začátek lovu
    while (jerry.isAlive() && tom.isAlive()) {
      int x = jerry.getX();
      System.out.printf("JerryX: %d", x).println();
      if (tom.getX() > x) { // kočka je víc vpravo než myš
        tom.turnLeft();
        while (tom.getX() > x) {
          if (tom.isPossibleToMoveForward()) {
            tom.moveForward();
          } else {
            tom.turnLeft();
            tom.moveForward(50);
            tom.turnRight();
            tom.moveForward(50);
            tom.turnRight();
            tom.moveForward(50);
            tom.turnLeft();
          }
        }
      } else if (tom.getX() < x) { // kočka je vlevo od myši
        tom.turnRight();
        while (tom.getX() < x) {
          if (tom.isPossibleToMoveForward()) {
            tom.moveForward();
          } else {
            tom.turnRight();
            tom.moveForward(50);
            tom.turnLeft();
            tom.moveForward(50);
            tom.turnLeft();
            tom.moveForward(50);
            tom.turnRight();
          }
        }
      }

      otocKockuSmeremNahoru();
      int y = jerry.getY();
      if (tom.getY() > y) { // kočka je víc nahoře než myš
        while (tom.getY() > y) {
          if (tom.isPossibleToMoveForward()) {
            tom.moveForward();
          } else {
            tom.turnLeft();
            tom.moveForward(50);
            tom.turnRight();
            tom.moveForward(50);
            tom.turnRight();
            tom.moveForward(50);
            tom.turnLeft();
          }
        }
      } else if (tom.getY() < y) { // kočka je níž než myš
        tom.turnLeft();
        tom.turnLeft();
        while (tom.getY() < y) {
          if (tom.isPossibleToMoveForward()) {
            tom.moveForward();
          } else {
            tom.turnRight();
            tom.moveForward(50);
            tom.turnLeft();
            tom.moveForward(50);
            tom.turnLeft();
            tom.moveForward(50);
            tom.turnRight();
          }
        }
      }
    }

    otocKockuSmeremNahoru();

    int y = jerry.getY();
    System.out.printf("JerryY: %d", y).println();
    if (tom.getY() > y) { //kocka je niz nez mys
      while (tom.getY() > y) {
        tom.moveForward();
      }
    } else if (tom.getY() < y) { //kocka je vys nez mys
      tom.turnLeft();
      tom.turnLeft();
      while (tom.getY() < y) {
        tom.moveForward();
      }
    }
  }

  private void otocKockuSmeremNahoru() {
    if (tom.getOrientation() == PlayerOrientation.UP) {
    } else if (tom.getOrientation() == PlayerOrientation.DOWN) {
      tom.turnLeft(); // 2x turnLeft otoci kocku o 180 stupnu
      tom.turnLeft();
    } else if (tom.getOrientation() == PlayerOrientation.LEFT) {
      tom.turnRight();
    } else if (tom.getOrientation() == PlayerOrientation.RIGHT) {
      tom.turnLeft();
    }
  }

  public void vytvorVeci(int pocetStromu) {
    for (int i = 0; i < pocetStromu; i++) {
      vytvorStrom();
    }
    vytvorSyr();
    vytvorJitrnici();
  }

  public Tree vytvorStrom() {
    return new Tree(vytvorNahodnyBod());
  }

  public Cat vytvorKocku() {
    return new Cat(vytvorNahodnyBod());
  }

  public Mouse vytvorMys() {
    return new Mouse(vytvorNahodnyBod());
  }

  public Cheese vytvorSyr() {
    return new Cheese(vytvorNahodnyBod());
  }

  public Meat vytvorJitrnici() {
    return new Meat(vytvorNahodnyBod());
  }

  private Point vytvorNahodnyBod() {
    return new Point(random.nextInt(SIRKA_OKNA), random.nextInt(VYSKA_OKNA));
  }

}
