package api;

import java.util.List;

public interface MaintenanceAPI {
    DefineTaskDto createTask(DefineTaskDto task);
    DefineTaskDto updateTask(int id, DefineTaskDto task);
    public DefineTaskDto getTaskById(int id);
    public List<DefineTaskDto> getAllTasks();
    public void deleteTask(int id);
}
