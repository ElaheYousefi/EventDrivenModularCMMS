package ir.dadeandish.application;

import ir.dadeandish.api.*;
import ir.dadeandish.domain.AssignTask;
import ir.dadeandish.domain.AssignedTaskRepository;
import ir.dadeandish.event.ReadyAssignTasksEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class AssignTaskService implements AssignTaskAPI {

    @PostConstruct
    public void init() {
        System.out.println("AssignTaskService CREATED");
    }
    private EquipmentAPI equipmentAPI;
//    private MaintenanceAPI maintenanceAPI;
    private AssignedTaskRepository assginTaskRepository;
    private Mapper mapper;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public AssignTaskService(EquipmentAPI equipmentAPI, AssignedTaskRepository assginTaskRepository, Mapper mapper, ApplicationEventPublisher eventPublisher) {
        this.equipmentAPI = equipmentAPI;
        this.assginTaskRepository = assginTaskRepository;
        this.mapper= mapper;
        this.eventPublisher= eventPublisher;
    }

//    public AssignTask createAssignTask(AssignTask assignTask) {
//
//        return assginTaskRepository.save(assignTask);
//    }

//    public void AssignTaskToEquipment(int defineTaskId, int equipId){
//
//        DefineTaskDto definedTask= maintenanceAPI.getDefineTaskById(defineTaskId);
//        LocalDate nextExecutionDate= definedTask.getStartDate();
//        if(nextExecutionDate.isBefore(LocalDate.now())){
//            nextExecutionDate= LocalDate.now().plusDays(1);
//        }
//
//        EquipmentDTO equipDto= equipmentAPI.getEquipByID(equipId);
//        AssignTask assignTask= new AssignTask();
//        assignTask.setDefinedTaskId(definedTask.getId());
//        assignTask.setEquipId(equipDto.getId());
//        assignTask.setNextExecutionDate(nextExecutionDate);
//        assginTaskRepository.save(assignTask);
//    }

//    public AssignTask updateAssignTask(int id, AssignTask assignTask) {
//        AssignTask existing = assginTaskRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("AssignTask not found with id: " + id));
//
//        existing.setEquipId(assignTask.getEquipId());
//        existing.setDefinedTaskId(assignTask.getDefinedTaskId());
//        existing.setNextExecutionDate(assignTask.getNextExecutionDate());
//        existing.setActive(assignTask.getActive());
//        existing.setStatus(assignTask.getStatus());
//
//        return assginTaskRepository.save(existing);
//    }

    public AssignTaskDTO getById(int id) {
         AssignTask assignTask= assginTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AssignTask not found with id: " + id));
         return mapper.convertAssignTaskToDTO(assignTask);
    }

//    public List<AssignTask> getAll() {
//        return assginTaskRepository.findAll();
//    }

//    public void delete(int id) {
//        assginTaskRepository.deleteById(id);
//    }

//    public List<AssignTask> getActiveTasks() {
//        return assginTaskRepository.findAll()
//                .stream()
//                .filter(t -> t.getActive() != null && t.getActive() == 1)
//                .collect(Collectors.toList());
//    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup(){
        getReadyTask();
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.HOURS)
    public void scheduledRun() {
        getReadyTask();
    }

    @Scheduled(fixedDelay= 5000)  //each 200 seconds
    public void getReadyTask(){
        System.out.println("try to publish tasks are ready");
         List<AssignTaskDTO> assignTaskList= assginTaskRepository.findReadyTasks();
         eventPublisher.publishEvent(new ReadyAssignTasksEvent(assignTaskList));
    }

    public void proceedSchedule(int taskId){
        AssignTask assignTask= assginTaskRepository.findById(taskId)
                .orElseThrow(()-> new RuntimeException("there is not this assign task"));
        //DefineTaskDto defineTaskDto= maintenanceAPI.getDefineTaskById(assignTask.getDefinedTaskId());
        LocalDate nextExecutionDate= LocalDate.now()
                .plusDays(10);
        //.plusDays(defineTaskDto.getPeriodDay()+ 1);
        assginTaskRepository.updateAssignTaskNextExecutionDate(taskId, nextExecutionDate);
    }
}