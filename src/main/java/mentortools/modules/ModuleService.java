package mentortools.modules;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ModuleService {

    private ModuleRepository repository;

    private ModelMapper mapper;

    public List<ModuleDto> listModules() {
        return repository.findAll().stream()
                .map(m -> mapper.map(m, ModuleDto.class))
                .toList();
    }

    public ModuleDto findModuleById(long id) {
        return mapper.map(getModule(id),ModuleDto.class);
    }

    public Module getModule(long id) {
        return repository.findById(id).orElseThrow(() -> new ModuleNotFoundException(id));
    }

    public ModuleDto createModule(CreateModuleCommand command) {
        Module module = new Module(command.getName(), command.getUrl());
        repository.save(module);
        return mapper.map(module,ModuleDto.class);
    }

    @Transactional
    public ModuleDto updateModule(long id, UpdateModuleCommand command) {
        Module module = getModule(id);
        String name = command.getName();
        if(name != null && !name.isBlank()){
            module.setName(name);
        }
        String url = command.getUrl();
        if(url != null && !url.isBlank()){
            module.setUrl(url);
        }
        return mapper.map(module,ModuleDto.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
