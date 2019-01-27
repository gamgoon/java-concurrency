package com.github.gamgoon.corejava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ex05 {
    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ConcurrentHashMap<String, Set<File>> resultMap = new ConcurrentHashMap<>();
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Path path = Paths.get("/Users/gamgoon/Downloads/javaimpatient1");
        List<Path> paths = Files.walk(path).collect(Collectors.toList());
        paths.parallelStream().forEach(p -> service.submit(new FileTask(resultMap, p)));

        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);

        resultMap.keySet().stream().forEach(k -> {
            Set<File> files = resultMap.get(k);
            System.out.println("word is " + k);
            files.stream().forEach(System.out::println);
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static class FileTask implements Runnable {
        private final ConcurrentHashMap<String, Set<File>> resultMap;
        private final Path path;

        public FileTask(ConcurrentHashMap<String, Set<File>> resultMap, Path path) {
            this.resultMap = resultMap;
            this.path = path;
        }

        @Override
        public void run() {
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(line -> {
                    Arrays.stream(line.split(" ")).forEach(word -> {
                        resultMap.merge(word, new HashSet<>(Arrays.asList(path.toFile())), (e, n) -> {
                            e.addAll(n);
                            return e;
                        });
                    });
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
