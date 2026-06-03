package application;

import api.DefineTaskDto;
import domain.DefinedTask;

public class Mapper {

    public DefineTaskDto converDefineTaskToDefinDTO(DefinedTask definedTask){
        DefineTaskDto defineTaskDto= new DefineTaskDto();
        defineTaskDto.setId(definedTask.getId());
        defineTaskDto.setTaskName(definedTask.getTaskName());
        defineTaskDto.setEndDate(definedTask.getEndDate());
        defineTaskDto.setStartDate(definedTask.getStartDate());
        defineTaskDto.setPeriodDay(definedTask.getPeriodDay());
        return defineTaskDto;
    }

    public DefinedTask converDefinDTOToDefineTask(DefineTaskDto dto){
        DefinedTask defineTask= new DefinedTask();
        defineTask.setId(dto.getId());
        defineTask.setTaskName(dto.getTaskName());
        defineTask.setEndDate(dto.getEndDate());
        defineTask.setStartDate(dto.getStartDate());
        defineTask.setPeriodDay(dto.getPeriodDay());
        return defineTask;
    }
}
