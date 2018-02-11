package ru.otus.lessons.home03;

import java.lang.management.ManagementFactory;
import java.util.Scanner;

public class CheckStand {
    private Scanner scanner;
    private boolean process = true;
    private int numberOfIteration = 6;

    public void launch() {
        initialize();
        mainLoop();
        close();
    }

    private void printHelp() {
        System.out.println(
                "\n*==================================================" +
                        "\n*  Введите:" +
                        "\n*  1 - для проверки размера Object" +
                        "\n*  2 - для проверки размера пустой строки" +
                        "\n*  3 - для проверки размера пустого массива" +
                        "\n*  4 - для наблюдения за изменением размера динамического массива" +
                        "\n*  q - выход" +
                        "\n*==================================================");
    }

    private void initialize() {
        scanner = new Scanner(System.in);
    }

    private void mainLoop() {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        while (process) {
            printHelp();
            System.out.println("\nСделайте выбор:");
            String input = scanner.nextLine();
            switch (input.toLowerCase().trim()) {
                case "1":
                    simpleObject();
                    break;
                case "2":
                    emptyString();
                    break;
                case "3":
                    emptyArray();
                    break;
                case "4":
                    dynamicArray();
                    break;
                case "q":
                    stop();
                    break;
                default:
                    wrongInput();
            }
        }
    }

    private long getMem() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private void tryCleanMemory() {
        for (int i = 0; i < 5; ++i) {
            System.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    private void simpleObject() {
        System.out.println("check Object size");

        int size = 20_000_000;

        for (int k = 0; k < numberOfIteration; k++) {
            System.out.println("\nЦикл " + (k + 1));
            tryCleanMemory();
            long mem = getMem();

            Object[] array = new Object[size];
            System.out.println("Создан массив для " + array.length + " элементов");

            long mem2 = getMem();
//        System.out.println("" + (mem2 - mem) / size);

            for (int i = 0; i < size; i++) {
                array[i] = new Object();
                if (i % 1_000_000 == 0) {
                    System.out.print(".");
                }
            }
            System.out.println("\rПоложили в массив " + size + " элементов.");
            long mem3 = getMem();
            System.out.println("Память под один простой объект примерно " + (mem3 - mem2) / size);
        }
    }

    private void emptyString() {
        System.out.println("check empty String size");

        int size = 20_000_000;

        for (int k = 0; k < numberOfIteration; k++) {
            System.out.println("\nЦикл " + (k + 1));
            tryCleanMemory();

            long mem = getMem();

            String[] array = new String[size];
            System.out.println("Создали массив и зарезервировали в нем " + array.length + " элементов");

            long mem2 = getMem();
//        System.out.println("Память под объект примерно " + (mem2 - mem) / size);

            for (int i = 0; i < size; i++) {
                array[i] = new String(new char[0]);
                if (i % 1_000_000 == 0) {
                    System.out.print(".");
                }
            }
            System.out.println("\rСоздано " + size + " пустых строк.");
            long mem3 = getMem();
            System.out.println("Размер пустой строки примерно " + (mem3 - mem2) / size);
        }
    }

    private void emptyArray() {
        System.out.println("check empty Array size");

        int size = 20_000_000;
        for (int k = 0; k < numberOfIteration; k++) {
            System.out.println("\nЦикл " + (k + 1));
            tryCleanMemory();

            long mem = getMem();

            int[][] intArray = new int[size][1];
            for (int i = 0; i < size; i++) {
                intArray[i] = new int[0];
                if (i % 1_000_000 == 0) {
                    System.out.print(".");
                }
            }

            long mem2 = getMem();
            System.out.println("\rЗанимаемая память " + (mem2 - mem) / size);
        }
    }

    private void dynamicArray() {
        System.out.println("check dynamic Array size");

        int testSize = 20_000;
        int size = -1;
        do {
            System.out.println("Введите количество элементов массива (больше 0): ");
            String quantity = scanner.nextLine();
            try {
                size = Integer.parseInt(quantity.trim());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность введенных данных");
            }
        } while (size < 1);

        for (int k = 0; k < numberOfIteration; k++) {
            System.out.println("\nЦикл " + (k + 1));
            tryCleanMemory();

            long mem = getMem();

            int[][] intArray = new int[testSize][1];
            for (int i = 0; i < testSize; i++) {
                intArray[i] = new int[size];
                if (i % 1_000_000 == 0) {
                    System.out.print(".");
                }
            }

            long mem2 = getMem();
            System.out.println("\rВ массиве " + size + " элементов");
            System.out.println("Память под массив " + (mem2 - mem)/testSize);
        }
    }

    private void stop() {
        process = false;
    }

    private void wrongInput() {
        System.out.println("check input");
    }

    private void close() {
        scanner.close();
    }
}
