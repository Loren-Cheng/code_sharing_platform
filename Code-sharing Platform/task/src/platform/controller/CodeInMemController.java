package platform.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import platform.entity.CodeInMem;
import platform.persistence.dao.CodeInMemRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class CodeInMemController {

    @GetMapping(value = "/codem", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getAllCode() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = now.format(formatter);
        System.out.println(formatted);
        return "<html>\n" +
                "<head>\n" +
                "    <title>CodeInMem</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<span id=\"load_date\">" +
                formatted +
                "</span>" +
                "<pre id=\"code_snippet\">" +
                CodeInMemRepository.getCode() +
                "</pre>" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping(value = "/codem/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String showLatest10Code(Model model) {
        List<CodeInMem> latest10CodeInMem = getLatest10Code();
        model.addAttribute("latests", latest10CodeInMem);
        return "latestInMem";
    }

    @GetMapping(value = "/codem/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String showCodeN(Model model,@PathVariable int id) {
        Map<String, String> codeNInMem = getCodeById(id);
        model.addAttribute("codeNInMem",codeNInMem);
        return "codeNInMem";
    }

    @GetMapping(value = "/api/codem", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getCode() {
        Map<String, String> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = now.format(formatter);
        map.put("date", formatted);
        map.put("code", CodeInMemRepository.getCode());

        return map;
    }

    @GetMapping(value = "/api/codem/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getCodeById(@PathVariable int id) {
        Map<String, String> map = new LinkedHashMap<>();

        CodeInMem codeInMemInId = CodeInMemRepository.getCodeById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formatted = codeInMemInId.getDate().format(formatter);
        map.put("code", codeInMemInId.getCode());
        map.put("date", codeInMemInId.getDate());
        return map;
    }

    @GetMapping(value = "/api/codem/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CodeInMem> getLatest10Code() {
        Map<Integer, CodeInMem> codeMap = CodeInMemRepository.getCodeMap();
        List<CodeInMem> list = new ArrayList<>();
        int idNow = CodeInMemRepository.getIdNext() - 1;
        int counter = 10;
        while (idNow != 0 && counter != 0) {
            CodeInMem tmp = codeMap.get(idNow);
            if (null != tmp) {
                list.add(codeMap.get(idNow));
                counter--;
            }
            idNow--;
        }
        return list;
    }


    @PostMapping(value = "/api/codem/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> post(@RequestBody CodeInMem newI) {
        Map<String, String> result = new ConcurrentHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringNow = now.format(formatter);
        newI.setDate(stringNow);
        int id = CodeInMemRepository.addCodeReturnId(newI);
        result.put("id", String.valueOf(id));
        return result;
    }

    @GetMapping(value = "/codem/new", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String enterCode() {

        return "<html>\n" +
                "<head>\n" +
                "    <title>Create</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<textarea id=\"code_snippet\"> +" +
                "// write your code here" +
                "</textarea>" +
                "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>" +
                "</body>\n" +
                "</html>";
    }


}
