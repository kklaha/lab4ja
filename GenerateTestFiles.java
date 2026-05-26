import java.io.*;
import java.util.Random;

public class GenerateTestFiles {
    public static void main(String[] args) {
        String[] files = {"data1.txt", "data2.txt", "data3.txt", "data4.txt", "data5.txt"};
        Random random = new Random();
        
        for (String fileName : files) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                int numCount = 10 + random.nextInt(20); // 10-30 чисел в файле
                
                for (int i = 0; i < numCount; i++) {
                    double number = random.nextDouble() * 100 - 50; // Числа от -50 до 50
                    // Форматируем вывод
                    if (number == Math.floor(number)) {
                        writer.write(String.format("%.0f", number));
                    } else {
                        writer.write(String.format("%.2f", number));
                    }
                    
                    if (i < numCount - 1) {
                        writer.newLine();
                    }
                }
                System.out.println("Создан файл: " + fileName + " с " + numCount + " числами");
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла " + fileName + ": " + e.getMessage());
            }
        }
        
        System.out.println("\nВсе тестовые файлы созданы!");
    }
}
