package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.api.CodeService;
import platform.entity.Code;
import platform.persistence.dto.CodeDTO;

import java.util.*;


@Controller
public class CodeController {
    private CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> createNewCode(@RequestBody Code newI) {
        newI.setTimeRestricted(newI.getTime() != 0);
        newI.setViewsRestricted(newI.getViews() != 0);
        Map<String, String> map = codeService.createNewCode(newI);
        return map;
    }

    @GetMapping(value = "/api/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CodeDTO getCodeById(@PathVariable String id) {
        return codeService.getCodeById(id);
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CodeDTO> getLatest10Code() {
        List<Code> codeList = codeService.findAll();
        List<CodeDTO> list = new ArrayList<>();
        codeList.sort(Comparator.comparing(Code::getDate).reversed());

        List<Code> codeListWithoutRestriction = new ArrayList<>();
        for (Code code : codeList
        ) {
            int time = codeService.remainingTime(code);
            if ((code.isTimeRestricted())
                    || (code.isViewsRestricted())) {
            } else {
                codeListWithoutRestriction.add(code);
            }
        }
        int count = Math.min(10, codeListWithoutRestriction.size());
        for (int i = 0; i < count; i++) {
            Optional<Code> tmpCode = Optional.ofNullable(codeListWithoutRestriction.get(i));
            if (tmpCode.isPresent()) {
                CodeDTO codeDTO = new CodeDTO();
                CodeService.toCodeDTO(tmpCode.get(), codeDTO);
                list.add(codeDTO);
            }
        }
        return list;
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String showLatest10Code(Model model) {
        List<CodeDTO> latest10Code = getLatest10Code();
        model.addAttribute("latests", latest10Code);
        return "latest";
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String showCodeN(Model model, @PathVariable String id) {
        try {
            Code code = codeService.findById(id);
            if ((code.isTimeRestricted() && code.getTime() <= 0)
                    || (code.isViewsRestricted()) && code.getViews() <= 0) {
                new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            CodeDTO codeN = getCodeById(id);
            model.addAttribute("codeN", codeN);
            if (code.isTimeRestricted() && code.isViewsRestricted()) {
                return "codeN";
            } else if (code.isTimeRestricted()) {
                return "codeNWithTimeR";
            } else if (code.isViewsRestricted()) {
                return "codeNWithViewsR";
            } else {
                return "codeNWithoutR";
            }


        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String enterCode() {

        return "<html>\n" +
                "<head>\n" +
                "    <title>Create</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<textarea id=\"code_snippet\"> " +
                "// write your code here" +
                "</textarea>" +
                "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>" +
                "</body>\n" +
                "</html>";
    }
}