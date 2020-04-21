package com.bajingxiaozi.plugindemo.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class JavaUtils2 {

    public static void resign(File signDirectory, File file) throws Exception {
        if (!file.exists()) {
            throw new FileNotFoundException(file + " not exist");
        }
        if (!file.isFile()) {
            throw new FileNotFoundException(file + " not a file");
        }

        File tempFle = new File(file.getParentFile(), "temp.apk");
        tempFle.delete();
        // java -jar signapk/signapk.jar signapk/platform.x509.pem signapk/platform.pk8 1.apk XCThemeManager_signed.apk
        SystemUtils.execute("java", "-jar", new File(signDirectory, "signapk.jar").getAbsolutePath(), new File(signDirectory, "platform.x509.pem").getAbsolutePath(), new File(signDirectory, "platform.pk8").getAbsolutePath(), file.getAbsolutePath(), tempFle.getAbsolutePath());
        file.delete();
        FileUtils.moveFile(tempFle, file);
    }

}
