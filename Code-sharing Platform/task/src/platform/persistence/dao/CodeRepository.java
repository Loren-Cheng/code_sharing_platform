package platform.persistence.dao;

import platform.entity.Code;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeRepository {
    private static String code = "public static void ...";
    private static String date;
    private static int idNext = 1;
    private static Map<Integer, Code> codeMap = new ConcurrentHashMap<>();

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        CodeRepository.code = code;
    }

    public static synchronized int getIdNext() {
        return idNext;
    }

    public static void setIdNext(int idNext) {
        CodeRepository.idNext = idNext;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        CodeRepository.date = date;
    }


    public static synchronized int addCodeReturnId(Code code) {
        int id = idNext;
        Code addedCode = CodeRepository.codeMap.putIfAbsent(id, code);
        if (null != addedCode) {
        } else {
            idNext++;
        }
        return id;
    }

    public static Code getCodeById(int id) {
        return CodeRepository.codeMap.get(id);
    }

    public static synchronized Map<Integer, Code> getCodeMap() {
        return CodeRepository.codeMap;
    }
}
