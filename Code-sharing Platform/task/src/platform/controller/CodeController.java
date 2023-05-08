package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import platform.api.CodeService;
import platform.entity.Code;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class CodeController {
    private CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> post(@RequestBody Code newI) {
        Map<String, String> result = new ConcurrentHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringNow = now.format(formatter);
        newI.setDate(stringNow);
        Code code = codeService.save(newI);
        Long id = code.getId();
        result.put("id", String.valueOf(id));
        return result;
    }

    @GetMapping(value = "/api/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getCodeById(@PathVariable int id) {
        Map<String, String> map = new LinkedHashMap<>();
        Code code = codeService.findById((long) id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formatted = codeInMemInId.getDate().format(formatter);
        map.put("code", code.getCode());
        map.put("date", code.getDate());
        return map;
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Code> getLatest10Code() {
        List<Code> codeList = codeService.findAll();
        List<Code> list = new ArrayList<>();
        codeList.sort((Code a, Code b) -> (Long.compare(b.getId(), a.getId())));
        int count = Math.min(10, codeList.size());
        for (int i = 0; i < count; i++) {
            Optional<Code> tmpCode = Optional.ofNullable(codeList.get(i));
            if (tmpCode.isPresent()) {
                list.add(tmpCode.get());
            }
        }
        return list;
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String showLatest10Code(Model model) {
        List<Code> latest10Code = getLatest10Code();
        model.addAttribute("latests", latest10Code);
        return "latest";
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String showCodeN(Model model, @PathVariable int id) {
        Map<String, String> codeN = getCodeById(id);
        model.addAttribute("codeN", codeN);
        return "codeN";
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