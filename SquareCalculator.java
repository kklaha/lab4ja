import java.util.ArrayList;
import java.util.List;

public class Main{
public static void main(String[] args) {
    String[] fileNames = {
            "C:/Users/Admin/IdeaProjects/ind/src/data1.txt",
            "C:/Users/Admin/IdeaProjects/ind/src/data2.txt",
            "C:/Users/Admin/IdeaProjects/ind/src/data3.txt"
    };

    List<FileSquareProcessor> processors = new ArrayList<>();

    System.out.println("Запуск обработки " + fileNames.length + " файлов...");

    for (String fileName : fileNames) {
        FileSquareProcessor  processor = new FileSquareProcessor (fileName);
        processor.start();
        processors.add(processor);
    }

    for (FileSquareProcessor processor : processors) {
        try {
            processor.join();
        } catch (InterruptedException e) {
            System.err.println("Ошибка при ожидании завершения потока: " + e.getMessage());
        }
    }

    System.out.println("Обработка всех файлов завершена!");
}
}
