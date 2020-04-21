package com.bajingxiaozi.plugindemo.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemUtils {

    public static List<String> execute(List<String> parameters) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(parameters).redirectErrorStream(true);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            List<String> messages = new ArrayList<>();
            while (true) {
                String message = reader.readLine();
                if (message == null) {
                    break;
                }

                messages.add(message);
            }

            final int exitValue = process.waitFor();

            return messages;
        }
    }

    public static List<String> execute(String... parameters) throws Exception {
        return execute(Arrays.asList(parameters));
    }

}
