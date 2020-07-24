package ru.ase.ims.enomanager.service;

//      tried to realize parsing similar DefaultXMLReader file
//      for adding to Ematrix one more tag "methods"

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaParser {
    public static List<String> parseMethods(Source file) {

        String code = "";
//      Source file conversion to String to parse with regex

        List<String> methods = new ArrayList<>();

        Pattern pattern = Pattern.compile("public\\s((?!class\b|=|CLASSNAME).)*\\)");
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            methods.add(matcher.group());
        }
        return methods;
    }
}
