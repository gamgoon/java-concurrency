package com.github.gamgoon.corejava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ex01 {
    /**
     * 병렬 스트림으로 디렉토리에서 주어진 단어가 포함된 파일을 모두 찾아라.
     * 그중 첫 번째 파일만 찾으려면 어떻게 해야하는가? 파일이 실제로 동시에 검색되는가?
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("/Users/gamgoon/Downloads/javaimpatient1");
        String keyword = "Demo";
        List<Path> paths = Files.walk(path).collect(Collectors.toList());
//        paths.stream().filter(p -> p.getFileName().toString().contains(keyword)).forEach(System.out::println);
        paths.parallelStream().filter(p -> p.getFileName().toString().contains(keyword)).forEach(System.out::println);
        Optional<Path> first = paths.parallelStream()
//                .peek(p -> {
//                    System.out.printf("%d %s: file is %s\n", System.currentTimeMillis(), Thread.currentThread().getName(), p);
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                })
                .filter(p -> p.getFileName().toString().contains(keyword))
                .findFirst()
//                .findAny()
                ;

        System.out.println("First = " + first.orElse(path));
    }
}
