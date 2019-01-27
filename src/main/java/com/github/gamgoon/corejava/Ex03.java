package com.github.gamgoon.corejava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ex03 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String keyword = "newInputStream";
        Path path = Paths.get("/Users/gamgoon/Downloads/javaimpatient1");

        List<Path> paths = Files.walk(path).collect(Collectors.toList());
        List<Callable<Boolean>> tasks = new ArrayList<>();
        paths.stream()
                .filter(p -> !Files.isDirectory(p))
                .filter(p -> !p.getFileName().toString().equals(".DS_Store"))
                .forEach(p -> tasks.add(new FileSearch(p, keyword)));
        ExecutorService service = Executors.newFixedThreadPool(10);
        try {
            service.invokeAny(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);

//        paths.parallelStream()
////                .forEach(System.out::println);
//
//                .filter(p -> {
//                    if (p.getFileName().toString().equals(".DS_Store")) {
//                        return false;
//                    }
//                    if (Files.isDirectory(p)) {
//                        System.out.printf("%s is directory\n", p.toAbsolutePath());
//                        return false;
//                    }
//                    System.out.printf("%s\n", p.toAbsolutePath());
//                    try (Stream<String> lines = Files.lines(p)) {
//                        long count = lines.filter(line -> line.contains(keyword)).count();
//                        if (count > 0) {
//                            System.out.printf("%s has the keyword\n", p.toAbsolutePath());
//                            return true;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return false;
//                }).findFirst();
    }

    public static class FileSearch implements Callable<Boolean> {
        private final Path path;
        private final String keyword;

        public FileSearch(Path path, String keyword) {
            this.path = path;
            this.keyword = keyword;
        }

        @Override
        public Boolean call() throws Exception {
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("%s: file name interruped!\n",
                        Thread.currentThread().getName(), path.toAbsolutePath());
                throw new Exception("interruped!");
            }
            try (Stream<String> lines = Files.lines(path)) {
                System.out.printf("%s: path is %s\n",
                        Thread.currentThread().getName(), path.toAbsolutePath());
                Optional<String> result = lines.filter(line -> line.contains(keyword)).findAny();
                if (result.isPresent()) {
                    System.out.printf("%s: file name is %s, contents is %s\n",
                            Thread.currentThread().getName(), path.toAbsolutePath(), result.get());
                    return Boolean.TRUE;
                }
            } catch (IOException e) {
                System.out.printf("%s: file %s error, isInterruped? %s \n"
                        , Thread.currentThread().getName()
                        , path.toAbsolutePath()
                        , Thread.currentThread().isInterrupted());
            }
            throw new Exception("not found");
        }
    }
}
