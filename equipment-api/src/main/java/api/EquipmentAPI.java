package api;

public interface EquipmentAPI {
    EquipmentDTO getEquipByID(int id);
    EquipmentDTO saveEquip(EquipmentDTO equipModel);
}
