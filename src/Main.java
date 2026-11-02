import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

//Реализовать копирование текстового файла с заданным именем в текстовый файл по указанному пути.
//
//Организовать последовательное копирование двух файлов. Замерить примерное время выполнения кода.
//
//Организовать параллельное копирование двух файлов. Замерить примерное время выполнения кода.
class File22 implements Runnable {
    public String start;     // откуда читаем
    public String new_file;  // куда записываем

    public File22(String start, String new_file) {
        this.start = start;
        this.new_file = new_file;
    }
    public void run() {
        List<String> array = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(start), StandardCharsets.UTF_16)) {//перевод так как у меня файла в юникоде были
            while (sc.hasNextLine()) {
                array.add(sc.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {//важный для операций с файлами так как действия с файлами для компилятора опасны
            Files.write(Path.of(new_file), array, StandardCharsets.UTF_16);//перевод из-за того что изначальный файл был в юникоде у меня//Path.of-заставляет работать как с объектом а не строкой обычной
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        String file1 = "C:\\Users\\Привет\\OneDrive\\Рабочий стол\\данные\\заказ.txt";
        String file2 = "C:\\Users\\Привет\\OneDrive\\Рабочий стол\\данные\\Tovar2.txt";

        String copy1 = "C:\\Users\\Привет\\OneDrive\\Рабочий стол\\данные\\заказ_copy.txt";
        String copy2 = "C:\\Users\\Привет\\OneDrive\\Рабочий стол\\данные\\Tovar2_copy.txt";

        long starttime = System.currentTimeMillis();
        new File22(file1, copy1).run();
        new File22(file2, copy2).run();

        long endtime= System.currentTimeMillis();//таймер
        System.out.println("время последовательно" + (endtime - starttime));

        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new File22(file1, copy1));
        Thread t2 = new Thread(new File22(file2, copy2));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long endPar = System.currentTimeMillis();//тип такой так как значения времени огромные
        System.out.println("время параллельно: " + (endPar - start));
    }
}