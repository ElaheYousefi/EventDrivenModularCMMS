package ir.dadeandish.application;

import api.EquipmentAPI;
import api.EquipmentDTO;
import ir.dadeandish.EquipmentMapper;
import ir.dadeandish.domain.EquipModel;
import ir.dadeandish.domain.EquipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipService implements EquipmentAPI {

    EquipRepository equipRepository;
    EquipmentMapper mapper;

    @Autowired
    public EquipService(EquipRepository equipRepository, EquipmentMapper equipmentMapper) {
        this.mapper= equipmentMapper;
        this.equipRepository = equipRepository;
    }

    public EquipmentDTO saveEquip(EquipmentDTO dto){
        validate(dto);
        EquipModel model = mapper.toEntity(dto);
        model = equipRepository.save(model);
        return mapper.toDTO(model);
    }

    public EquipmentDTO getEquipByID(int id){
        EquipModel equipModel= equipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment with this id didn't find"));
        return convertEquipModelToDTO(equipModel);
    }

    private EquipmentDTO convertEquipModelToDTO(EquipModel model){
        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        return dto;
    }

    private EquipModel convertEquipdtoToEquipModel(EquipmentDTO equipmentDTO){
        EquipModel equipModel= new EquipModel();
        equipModel.setName(equipmentDTO.getName());
        equipModel.setId(equipmentDTO.getId());
        return equipModel;
    }
    private void validate(EquipmentDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
    }
}
