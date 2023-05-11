package platform.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import platform.entity.Code;
import platform.persistence.dao.CodeRepository;
import platform.persistence.dto.CodeDTO;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service("Code")
public class CodeServiceImpl implements CodeService {

    private CodeRepository codeRepository;

    @Autowired
    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public List<Code> findAll() {
        return codeRepository.findAll();
    }

    @Override
    public Code findById(String theId) {
        Optional<Code> result = codeRepository.findById(theId);
        return result.orElseThrow(() -> new RuntimeException("Did not find Code id -" + theId));
    }

    @Override
    @Transactional
    public CodeDTO getCodeById(String id) {
        CodeDTO codeDto = new CodeDTO();
        Code code = findById(id);
        Duration duration = Duration.between(code.getDate(), LocalDateTime.now());
        long seconds = duration.getSeconds();
        int time;

        if (code.isTimeRestricted()) {
            time = (int) (code.getTime() - seconds);
            if (time <= -1) {
                deleteById(id);
            }
        } else {
            time = 0;
        }

        if ((code.isTimeRestricted() && time <= 0)
                || (code.isViewsRestricted() && code.getViews() <= 0)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (code.isViewsRestricted()) {
            code.setViews(code.getViews() - 1);
            if (code.getViews() <= -1) {
                deleteById(id);
            }
        } else {
            code.setViews(0);
        }
        save(code);
        code = findById(id);
        CodeService.toCodeDTO(code, codeDto);
        codeDto.setTime(time);
        return codeDto;
    }

    @Override
    public Map<String, String> createNewCode(Code newI) {
        Map<String, String> result = new ConcurrentHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        newI.setDate(now);
        Code code = save(newI);
        String id = code.getId();
        String stringNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        result.put("id", id);
        return result;
    }

    @Override
    public Code save(Code theCode) {
        LocalDateTime localDateTime = theCode.getDate();
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        theCode.setDate(localDateTime);
        return codeRepository.save(theCode);
    }

    @Override
    public void deleteById(String theId) {
        codeRepository.deleteById(theId);
    }

    @Override
    public String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public int remainingTime(Code code) {
        Duration duration = Duration.between(code.getDate(), LocalDateTime.now());
        long seconds = duration.getSeconds();
        int time;
        if (code.isTimeRestricted()) {
            time = (int) (code.getTime() - seconds);
        }else {
            time = 0;
        }
        return time;
    }
}
