package platform.persistence.dao;

import platform.entity.CodeInMem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeInMemRepository {
    private static String code = "public static void ...";
    private static String date;
    private static int idNext = 1;
    private static Map<Integer, CodeInMem> codeMap = new ConcurrentHashMap<>();

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        CodeInMemRepository.code = code;
    }

    public static synchronized int getIdNext() {
        return idNext;
    }

    public static void setIdNext(int idNext) {
        CodeInMemRepository.idNext = idNext;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        CodeInMemRepository.date = date;
    }


    public static synchronized int addCodeReturnId(CodeInMem codeInMem) {
        int id = idNext;
        CodeInMem addedCodeInMem = CodeInMemRepository.codeMap.putIfAbsent(id, codeInMem);
        if (null != addedCodeInMem) {
        } else {
            idNext++;
        }
        return id;
    }

    public static CodeInMem getCodeById(int id) {
        return CodeInMemRepository.codeMap.get(id);
    }

    public static synchronized Map<Integer, CodeInMem> getCodeMap() {
        return CodeInMemRepository.codeMap;
    }
}
