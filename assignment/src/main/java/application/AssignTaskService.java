package application;

import api.DefineTaskDto;
import api.EquipmentAPI;
import api.EquipmentDTO;
import api.MaintenanceAPI;
import domain.AssignTask;
import domain.AssignedTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignTaskService {

    private EquipmentAPI equipmentAPI;
    private MaintenanceAPI maintenanceAPI;
    private AssignedTaskRepository assginTaskRepository;

    @Autowired
    public AssignTaskService(EquipmentAPI equipmentAPI, MaintenanceAPI maintenanceAPI, AssignedTaskRepository assginTaskRepository) {
        this.equipmentAPI = equipmentAPI;
        this.maintenanceAPI = maintenanceAPI;
        this.assginTaskRepository = assginTaskRepository;
    }

    public AssignTask createAssignTask(AssignTask assignTask) {

        return assginTaskRepository.save(assignTask);
    }

    public void AssignTaskToEquipment(int defineTaskId, int equipId){

        DefineTaskDto definedTask= maintenanceAPI.getTaskById(defineTaskId);
        LocalDate nextExecutionDate= definedTask.getStartDate();
        if(nextExecutionDate.isBefore(LocalDate.now())){
            nextExecutionDate= LocalDate.now().plusDays(1);
        }

        EquipmentDTO equipDto= equipmentAPI.getEquipByID(equipId);
        AssignTask assignTask= new AssignTask();
        assignTask.setDefinedTaskId(definedTask.getId());
        assignTask.setEquipId(equipDto.getId());
        assignTask.setNextExecutionDate(nextExecutionDate);
        assginTaskRepository.save(assignTask);
    }

    public AssignTask updateAssignTask(int id, AssignTask assignTask) {
        AssignTask existing = assginTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AssignTask not found with id: " + id));

        existing.setEquipId(assignTask.getEquipId());
        existing.setDefinedTaskId(assignTask.getDefinedTaskId());
        existing.setNextExecutionDate(assignTask.getNextExecutionDate());
        existing.setActive(assignTask.getActive());
        existing.setStatus(assignTask.getStatus());

        return assginTaskRepository.save(existing);
    }

    public AssignTask getById(int id) {
        return assginTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AssignTask not found with id: " + id));
    }

    public List<AssignTask> getAll() {
        return assginTaskRepository.findAll();
    }

    public void delete(int id) {
        assginTaskRepository.deleteById(id);
    }

    public List<AssignTask> getActiveTasks() {
        return assginTaskRepository.findAll()
                .stream()
                .filter(t -> t.getActive() != null && t.getActive() == 1)
                .collect(Collectors.toList());
    }
}