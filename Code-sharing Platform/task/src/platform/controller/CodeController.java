package platform.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class CodeController {

    @GetMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getAllCode() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = now.format(formatter);
        System.out.println(formatted);
        return "<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<span id=\"load_date\">" +
                formatted +
                "</span>" +
                "<pre id=\"code_snippet\">" +
                CodeRepository.getCode() +
                "</pre>" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String showLatest10Code(Model model) {
        List<Code> latest10Code = getLatest10Code();
        model.addAttribute("latests",latest10Code);
        return "latest";
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String showCodeN(Model model,@PathVariable int id) {
        Map<String, String> codeN = getCodeById(id);
        model.addAttribute("codeN",codeN);
        return "codeN";
    }

    @GetMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getCode() {
        Map<String, String> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = now.format(formatter);
        map.put("date", formatted);
        map.put("code", CodeRepository.getCode());

        return map;
    }

    @GetMapping(value = "/api/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getCodeById(@PathVariable int id) {
        Map<String, String> map = new LinkedHashMap<>();

        Code codeInId = CodeRepository.getCodeById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formatted = codeInId.getDate().format(formatter);
        map.put("code", codeInId.getCode());
        map.put("date", codeInId.getDate());
        return map;
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Code> getLatest10Code() {
        Map<Integer, Code> codeMap = CodeRepository.getCodeMap();
        List<Code> list = new ArrayList<>();
        int idNow = CodeRepository.getIdNext() - 1;
        int counter = 10;
        while (idNow != 0 && counter != 0) {
            Code tmp = codeMap.get(idNow);
            if (null != tmp) {
                list.add(codeMap.get(idNow));
                counter--;
            }
            idNow--;
        }
        return list;
    }


    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> post(@RequestBody Code newI) throws InterruptedException {
        Map<String, String> result = new ConcurrentHashMap<>();
        int id = -1;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringNow = now.format(formatter);
        newI.setDate(stringNow);
        id = CodeRepository.addCodeReturnId(newI);
        result.put("id", String.valueOf(id));
        return result;
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
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
