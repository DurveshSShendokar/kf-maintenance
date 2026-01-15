package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.Checklist;

public interface ChecklistServices {

	void addChecklist1(Checklist checklist);

	List<Checklist> getExistingList(String task, int machine_id, String frequency, String type);

	List<Checklist> getChecklistList();

	void deleteChecklist(int checklist_id);

	List<Checklist> getChecklistByMachineNFrequency(int machine_id, String freq);

	List<Checklist> listByMachineName(String machineName);

	List<Checklist> listByMachineEquipId(String equipId);

	List<Checklist> listByMachineEquipIdAndFrequency(String equipId, String frequency);

}
