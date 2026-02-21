package org.example.dombyra.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.query.sqm.produce.function.FunctionArgumentException;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {
    public static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
    public static final Pattern IMAGE_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)");
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String FILE_NAME_FORMAT = "%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern){
        final String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        return extension.matches("jpe?g|png|gif|bmp");
    }

    public static void assertAllowed(MultipartFile file, String pattern){
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE){
            throw new IllegalArgumentException("Max file is 2MB");
        }
        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName,pattern)){
            throw new IllegalArgumentException("Only jpg, png, gif, bmp files are allowed");
        }
    }
    public static String getFileName(final String name){
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT,name,date);
    }
}
