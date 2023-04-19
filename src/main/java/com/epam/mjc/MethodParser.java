package com.epam.mjc;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        Collection<String> stringCollection = List.of(" ", ",", "(", ")");

        String accessModifier = null;
        String returnType;
        String methodName;
        List<MethodSignature.Argument> argumentList = new ArrayList<>();

        StringSplitter stringSplitter = new StringSplitter();
        List<String> stringList = stringSplitter.splitByDelimiters(signatureString, stringCollection);

        switch (stringList.get(0)) {
            case "public":
                accessModifier = "public";
                break;
            case "private":
                accessModifier = "private";
                break;
            case "protected":
                accessModifier = "protected";
                break;
            default:
        }

        if (accessModifier == null) {
            returnType = stringList.get(0);
        } else {
            returnType = stringList.get(1);
        }

        if (accessModifier == null) {
            methodName = stringList.get(1);
        } else {
            methodName = stringList.get(2);
        }

        if (accessModifier == null) {
            for (int i = 2; i < stringList.size() - 1; i += 2) {
                argumentList.add(new MethodSignature.Argument(stringList.get(i), stringList.get(i + 1)));
            }
        } else {
            for (int i = 3; i < stringList.size() - 1; i += 2) {
                argumentList.add(new MethodSignature.Argument(stringList.get(i), stringList.get(i + 1)));
            }
        }

        MethodSignature methodSignature = new MethodSignature(methodName, argumentList);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);
        return methodSignature;
    }
}
