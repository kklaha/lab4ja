public class SquareCalculator {
    
    public static void main(String[] args) {
        // Массив имен входных файлов
        String[] inputFiles = {
            "data1.txt",
            "data2.txt",
            "data3.txt",
            "data4.txt",
            "data5.txt"
        };
        
        int n = inputFiles.length;
        
        System.out.println("=== Программа вычисления квадратов чисел из файлов ===");
        System.out.println("Количество файлов для обработки: " + n);
        System.out.println("Создаем " + n + " потоков для параллельной обработки...\n");
        
        long startTime = System.currentTimeMillis();
        
        // Создание процессоров и запуск потоков
        List<FileProcessor> processors = new ArrayList<>();
        
        for (String fileName : inputFiles) {
            FileProcessor processor = new FileProcessor(fileName);
            processors.add(processor);
            processor.start();  // Запускаем поток через метод start() в классе FileProcessor
        }
        
        // Ожидание завершения всех потоков
        System.out.println("Ожидание завершения всех потоков...\n");
        for (FileProcessor processor : processors) {
            try {
                processor.join();
            } catch (InterruptedException e) {
                System.err.println("Ошибка при ожидании завершения потока: " + e.getMessage());
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("\n=========================================");
        System.out.println("Все потоки завершили работу!");
        System.out.println("Общее время выполнения: " + (endTime - startTime) + " мс");
        System.out.println("=========================================");
    }
}
