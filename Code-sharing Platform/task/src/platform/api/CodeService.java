package platform.api;

import org.modelmapper.ModelMapper;
import platform.entity.Code;
import platform.persistence.dto.CodeDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public interface CodeService {
    List<Code> findAll();


    Code findById(String theId);

    CodeDTO getCodeById(String id);

    Map<String, String> createNewCode(Code code);

    Code save(Code theCode);

    void deleteById(String theId);

    String uuid();

    static CodeDTO toCodeDTO(Code code, CodeDTO codeDTO) {
        ModelMapper modelMapper = new ModelMapper();
        LocalDateTime localDateTime = code.getDate();
        String slocalDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        modelMapper.map(code,codeDTO);
        codeDTO.setDate(slocalDateTime);
        System.out.println(slocalDateTime);
        return codeDTO;
    }

    int remainingTime(Code code);



}
