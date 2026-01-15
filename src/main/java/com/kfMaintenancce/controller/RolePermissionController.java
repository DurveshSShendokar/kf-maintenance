/**
 * Author: Avinash Darandale
 * Date: 20-11-2025
 */


package com.kfMaintenancce.controller;

import com.kfMaintenancce.dto.*;

import com.kfMaintenancce.model.*;
import com.kfMaintenancce.repo.*;
import com.kfMaintenancce.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/access")
public class RolePermissionController {

    @Autowired
    RolePermissionActionRepo rolePermissionActionRepo;

    @Autowired
    PermissionActionRepo permissionActionRepo;
    @Autowired
    AccessService accessService;
   
    @Autowired
    PermissionsRepo permissionRepo;
    @Autowired
    PermissionRequestRepo permissionRequestRepo;
    @Autowired
    RolePermissionRepo rolePermissionRepo;

    @Autowired
    UserDetailsRepo userInfoRepo;
    @Autowired
    RoleRepo roleRepo;
   
    @PostMapping({"/addPermissionRequest"})
    @ResponseBody
    public ResponceObj addPermissionRequest(@RequestBody PermissionRequestDTO permissionRequest) {
        ResponceObj responceDTO = new ResponceObj();
        try {

            Optional<Permissions> optional=permissionRepo.findById(permissionRequest.getPermissionsId());

            if(optional.isPresent()) {
                int i=1;
                for(PermissionAction permissionAction:permissionRequest.getActions()) {
                    Optional<PermissionAction> optionalAcion=permissionActionRepo.getPermissionActionBYPermissionIdAndActionName(permissionRequest.getPermissionsId(), permissionAction.getActionName());
                    System.out.println("Per By Id  "+optional.isPresent()+" "+permissionRequest.getPermissionsId()+" "+ permissionAction.getActionName());
                    System.out.println("PermissionAction "+optionalAcion.isPresent());

                    UserDetails userOp=userInfoRepo.getUserByIds(permissionRequest.getUserId());
                    PermissionRequest permissionRequestnew= new PermissionRequest();
                    permissionRequestnew.setPermissionAction(optionalAcion.get());
                    permissionRequestnew.setPermissions(optional.get());
                    permissionRequestnew.setRemark(permissionRequest.getRemark());
                    permissionRequestnew.setApproved(0);
                    permissionRequestnew.setUserInfo(userOp);
                    Optional<PermissionRequest> optionalReq=permissionRequestRepo.getPermisionRequestByPermisionIdUserANdAction(permissionRequestnew.getPermissions().getPermissionsId(),permissionRequestnew.getPermissionAction().getPermissionAsactionId(),permissionRequestnew.getUserInfo().getId());

                    if(optionalReq.isPresent()) {

                    }else {
                        i++;
                        permissionRequestRepo.save(permissionRequestnew);
                    }


                }
                if(i==permissionRequest.getActions().size()) {
                    responceDTO.setCode(200);
                    responceDTO.setMessage("Request Has Created");
                }else {
                    responceDTO.setCode(200);
                    responceDTO.setMessage("Request Has Created: Some action are already requested");
                }

            }
            return responceDTO;
        } catch (Exception e) {
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return responceDTO;
        }
    }


    @GetMapping({"/getAllPermissionRequests"})
    @ResponseBody
    public List<PermissionRequest> getAllPermissionRequests() {
        List<PermissionRequest> list = new ArrayList<PermissionRequest>();
        try {
            list = this.permissionRequestRepo.getAllPermissionRequests();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @PostMapping({"/updateRolePermission"})
    @ResponseBody
    public ResponseEntity<ResponceObj> saveRolePermission(@RequestBody SaveRolePermissionDTO rolePermissions) {
        ResponceObj responceDTO = new ResponceObj();
        try {
            Role role = this.accessService.getRoleById(rolePermissions.getRoleId());
            Permissions permissions = this.accessService.getPermissionsById(rolePermissions.getPermissionId());
            Optional<RolePermission> optional2 = this.accessService.getRolePermissionByRoleAndPermission(role.getRole_id(), permissions.getPermissionsId());
            if (rolePermissions.isSelected()) {
                if (optional2.isPresent()) {
                    RolePermission rolePer = optional2.get();
                    this.accessService.saveRolePermission(rolePer);
                } else {
                    RolePermission rolePer = new RolePermission();
                    rolePer.setPermissions(permissions);
                    rolePer.setRole(role);
                    this.accessService.saveRolePermission(rolePer);
                }
            } else {
                RolePermission rolePer = optional2.get();
                this.accessService.deleteRolePermission(rolePer);
            }
            responceDTO.setCode(200);
            responceDTO.setMessage("Permission Updated Successfully");
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping({"/updateRolePermissionAction"})
    @ResponseBody
    public ResponseEntity<ResponceObj> updateRolePermissionAction(@RequestBody SaveRolePermissionDTO rolePermissions) {
        ResponceObj responceDTO = new ResponceObj();
        try {
            Role role = this.accessService.getRoleById(rolePermissions.getRoleId());
            Permissions permissions = this.accessService.getPermissionsById(rolePermissions.getPermissionId());

            Optional<RolePermission> optional2 = this.accessService.getRolePermissionByRoleAndPermission(role.getRole_id(), permissions.getPermissionsId());


            Optional<PermissionAction> optional = this.accessService.getPermissionActionBYPermissionIdAndActionName(permissions.getPermissionsId(), rolePermissions.getActionName());
            Optional<RolePermissionAction> rolePerActionOp = this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(((PermissionAction)optional.get()).getPermissionAsactionId(), role.getRole_id());
            if (rolePermissions.isSelected()) {

                if (rolePerActionOp.isPresent()) {
                    RolePermissionAction rolePer =rolePerActionOp.get(); this.rolePermissionActionRepo.save(rolePer);
                } else {

                    RolePermissionAction rolePer = new RolePermissionAction();
                    rolePer.setPermissionsAction(optional.get()); rolePer.setRole(role);
                    this.rolePermissionActionRepo.save(rolePer);
                }

            } else {
                Optional<PermissionAction> optional1 = this.accessService.getPermissionActionBYPermissionIdAndActionName(permissions.getPermissionsId(), rolePermissions.getActionName());
                Optional<RolePermissionAction> rolePerActionOp1 = this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(((PermissionAction)optional.get()).getPermissionAsactionId(), role.getRole_id());
                rolePermissionActionRepo.delete(rolePerActionOp.get());
                RolePermission rolePer = optional2.get();
                //  this.accessService.deleteRolePermission(rolePer);
            }
            responceDTO.setCode(200);
            responceDTO.setMessage("Action Updated Successfully");
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }
    @GetMapping({"/getPermissionsAndActionByRole1"})
    @ResponseBody
    public PermissionsDTO getPermissionsAndActionByRole1(@RequestParam int roleId) {
        PermissionsDTO permissionsDTO = new PermissionsDTO();
        try {
            List<Permissions> machinemasterPermission = this.accessService.getPermissionsByCategory("MachineMaster");
            List<Permissions> emsmasterPermission = this.accessService.getPermissionsByCategory("EMSMaster");
            List<Permissions> assetmasterPermission = this.accessService.getPermissionsByCategory("AssetMaster");
            
            List<Permissions> breakdownPermission = this.accessService.getPermissionsByCategory("Breakdown");
            List<Permissions> maintenanceofmachinePermission = this.accessService.getPermissionsByCategory("MaintenanceOfMachine");
            List<Permissions> transactionPermission = this.accessService.getPermissionsByCategory("Transaction");
            
            List<Permissions> itreportPermission = this.accessService.getPermissionsByCategory("ITReport");     
            List<Permissions> emsreportPermission = this.accessService.getPermissionsByCategory("EmsReport");
            List<Permissions> reportPermission = this.accessService.getPermissionsByCategory("Report");
        
            List<Permissions> dashboardPermission = this.accessService.getPermissionsByCategory("Dashboard");
            List<Permissions> configurationPermission = this.accessService.getPermissionsByCategory("Configuration");

            
//
            for (Permissions permission : machinemasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                //System.out.printlnr("roleId " + roleId + " PERMo" + permission.getPermissionsId() + "  " + optional.isPresent());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                } else {
                    permission.setSelected(false);
                }
                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);
            }


//
            for (Permissions permission : emsmasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    // continue;
                } else {
                    permission.setSelected(false);
                }


                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);
            }
            
            
            //
            for (Permissions permission : assetmasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    // continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            //
            for (Permissions permission : breakdownPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //  continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);
            }
            
            
            //
            for (Permissions permission : maintenanceofmachinePermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
//1
            for (Permissions permission : transactionPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
          //2  
            for (Permissions permission : itreportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
            
            //
            for (Permissions permission : emsreportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
            //
            
            
            for (Permissions permission : reportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            //
            
            for (Permissions permission : dashboardPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            //
            
            for (Permissions permission : configurationPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            




            permissionsDTO.setMachinemasterPermission(machinemasterPermission);
            permissionsDTO.setEmsmasterPermission(emsmasterPermission);
            permissionsDTO.setAssetmasterPermission(assetmasterPermission);
            
            permissionsDTO.setBreakdownPermission(breakdownPermission);
            permissionsDTO.setMaintenanceofmachinePermission(maintenanceofmachinePermission);
            permissionsDTO.setTransactionPermission(transactionPermission);
            
            permissionsDTO.setItreportPermission(itreportPermission);
            permissionsDTO.setEmsreportPermission(emsreportPermission);
            permissionsDTO.setReportPermission(reportPermission);
            
            permissionsDTO.setDashboardPermission(dashboardPermission);
            permissionsDTO.setConfigurationPermission(configurationPermission);
            
            
            
            
            //   permissionsDTO.setUserPermissions(permissionsByUser);   
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionsDTO;
    }


    public static boolean isNamePresent(List<Permissions> people, String name) {
        for (Permissions person : people) {
            if (person.getPermissionsName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    @GetMapping({"/getPermissionsAndActionByRole"})
    @ResponseBody
    public PermissionsDTO getPermissionsAndActionByRole(@RequestParam int roleId,int userId) {
        PermissionsDTO permissionsDTO = new PermissionsDTO();
        try {
            List<Permissions> machinemasterPermission = new ArrayList<Permissions>();
            List<Permissions> emsmasterPermission = new ArrayList<Permissions>();
            List<Permissions> assetmasterPermission = new ArrayList<Permissions>();
            
            List<Permissions> breakdownPermission = new ArrayList<Permissions>();
            List<Permissions> maintenanceofmachinePermission = new ArrayList<Permissions>();
            List<Permissions> transactionPermission = new ArrayList<Permissions>();
            
            List<Permissions> itreportPermission = new ArrayList<Permissions>();
            List<Permissions> emsreportPermission = new ArrayList<Permissions>();
            List<Permissions> reportPermission = new ArrayList<Permissions>();
            
            List<Permissions> dashboardPermission = new ArrayList<Permissions>();
            List<Permissions> configurationPermission = new ArrayList<Permissions>();
          
   


            for (Permissions permission : this.accessService.getPermissionsByCategory("Machine Master")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    machinemasterPermission.add(permission);

                }

            }
            
         

            //
            
            for (Permissions permission : this.accessService.getPermissionsByCategory("EMS Master")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    emsmasterPermission.add(permission);

                }

            }
           
//

            for (Permissions permission : this.accessService.getPermissionsByCategory("Asset Master")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    assetmasterPermission.add(permission);

                }

            }
          

            
            for (Permissions permission : this.accessService.getPermissionsByCategory("Breakdown")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    breakdownPermission.add(permission);

                }

            }
          

            //
            for (Permissions permission : this.accessService.getPermissionsByCategory("Maintenance Of Machine")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    maintenanceofmachinePermission.add(permission);

                }

            }

          
            //
            for (Permissions permission : this.accessService.getPermissionsByCategory("Transaction")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    transactionPermission.add(permission);

                }

            }           

         

            //
            for (Permissions permission : this.accessService.getPermissionsByCategory("IT Report")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    itreportPermission.add(permission);

                }

            }
         
            //
            for (Permissions permission : this.accessService.getPermissionsByCategory("Ems Report")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    emsreportPermission.add(permission);

                }

            }

            
            //
            for (Permissions permission : this.accessService.getPermissionsByCategory("Report")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    reportPermission.add(permission);

                }

            }
            
            
            
            //
            for (Permissions permission : this.accessService.getPermissionsByCategory("Dashboard")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    dashboardPermission.add(permission);

                }

            }
            
            
            //
            for (Permissions permission : this.accessService.getPermissionsByCategory("Configuration")) {

                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                    Set<ActionDto> dtos = new HashSet<>();

                    for (PermissionAction action :permissionActions) {

                        List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());


                        if(permissionRequests.size()!=0) {
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            PermissionRequest permissionRequest=permissionRequests.get(0);
                            if(permissionRequest.getApproved()==1) {
                                actionDto.setSelected(true);
                            }else {
                                Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                                if(optionalAction.isPresent())
                                {
                                    actionDto.setSelected(true);
                                }
                                else {
                                    actionDto.setSelected(false);
                                }
                            }


                            permission.setActions(dtos);
                            permission.setActions(dtos);
                            dtos.add(actionDto);
                        }
                        else {
                            Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                            ActionDto actionDto = new ActionDto();
                            actionDto.setActionName(action.getActionName());
                            if(optionalAction.isPresent())
                            {
                                actionDto.setSelected(true);
                            }
                            else {
                                actionDto.setSelected(false); }
                            dtos.add(actionDto);
                        }
                        permission.setActions(dtos);
                    }

                    permission.setActions(dtos);
                    configurationPermission.add(permission);

                }

            }
         

            
            permissionsDTO.setMachinemasterPermission(machinemasterPermission);
            permissionsDTO.setEmsmasterPermission(emsmasterPermission);
            permissionsDTO.setAssetmasterPermission(assetmasterPermission);
            
            permissionsDTO.setBreakdownPermission(breakdownPermission);
            permissionsDTO.setMaintenanceofmachinePermission(maintenanceofmachinePermission);
            permissionsDTO.setTransactionPermission(transactionPermission);
            
            permissionsDTO.setItreportPermission(itreportPermission);
            permissionsDTO.setEmsreportPermission(emsreportPermission);
            permissionsDTO.setReportPermission(reportPermission);
            
            permissionsDTO.setEmsreportPermission(dashboardPermission);
            permissionsDTO.setReportPermission(configurationPermission);
            
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionsDTO;
    }
    @GetMapping({"/getPermissionsAndActionByRoleOld"})
    @ResponseBody
    public PermissionsDTO getPermissionsAndActionByRoleOld(@RequestParam int roleId,int userId) {
        PermissionsDTO permissionsDTO = new PermissionsDTO();
        try {
            System.out.println("ROLE "+roleId+" USER "+userId);
          

            
            List<Permissions> machinemasterPermission = this.accessService.getPermissionsByCategory("Machine Master");
            List<Permissions> emsmasterPermission = this.accessService.getPermissionsByCategory("EMS Master");
            List<Permissions> assetmasterPermission = this.accessService.getPermissionsByCategory("Asset Master");
            List<Permissions> breakdownPermission = this.accessService.getPermissionsByCategory("Breakdown");
            List<Permissions> maintenanceofmachinePermission = this.accessService.getPermissionsByCategory("Maintenance Of Machine");
            List<Permissions> transactionPermission = this.accessService.getPermissionsByCategory("Transaction");
            List<Permissions> itreportPermission = this.accessService.getPermissionsByCategory("IT Report");     
            List<Permissions> emsreportPermission = this.accessService.getPermissionsByCategory("Ems Report");
            List<Permissions> reportPermission = this.accessService.getPermissionsByCategory("Report");
            
            List<Permissions> dashboardPermission = this.accessService.getPermissionsByCategory("Dashboard");
            List<Permissions> configurationPermission = this.accessService.getPermissionsByCategory("Configuration");
        
           

//
            for (Permissions permission : machinemasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());

                if (optional.isPresent()) {
                    permission.setSelected(true);
                } else {
                    permission.setSelected(false);
                }
                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());
                //System.out.printlnr("roleId " + roleId + " PERMo" + permission.getPermissionsName() + "  " + optional.isPresent()+" Action "+permissionActions.size());
                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    //System.out.printlnr("Action  "+action.getActionName());
                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());

                    ////System.out.printlnr("permissionRequests  uid  "+userId+"  Per "+permission.getPermissionsId()+" Acr "+action.getPermissionAsactionId()+"  "+permissionRequests.size());
                    if(permissionRequests.size()!=0) {
                        System.out.println("REQ AC "+action.getActionName());
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);

                        dtos.add(actionDto);
                    }else {
                        //System.out.printlnr("PER AC "+action.getActionName());
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();


                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); }
                        dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }

                permission.setActions(dtos);
            }


//
            for (Permissions permission : emsmasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    // continue;
                } else {
                    permission.setSelected(false);
                }


                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }

                permission.setActions(dtos);
            }
            
            //
            for (Permissions permission : assetmasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    // continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);





            }
            
            //
            for (Permissions permission : breakdownPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //  continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);
            }
            
            
            //
            for (Permissions permission : maintenanceofmachinePermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);

            }
            
            
            //
            for (Permissions permission : transactionPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);

            }
            
            //
            for (Permissions permission : itreportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);

            }

            
            //
            for (Permissions permission : emsreportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);

            }

            
            
            //
            for (Permissions permission : reportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);

            }
            
            //
            for (Permissions permission : dashboardPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);

            }
            
            
            //
            for (Permissions permission : configurationPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                /*
                 * for (PermissionAction action :permissionActions) {
                 * Optional<RolePermissionAction> optionalAction
                 * =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(
                 * action.getPermissionAsactionId(), roleId); ActionDto actionDto = new
                 * ActionDto(); actionDto.setActionName(action.getActionName());
                 * if(optionalAction.isPresent()) { actionDto.setSelected(true); } else {
                 * actionDto.setSelected(false); } dtos.add(actionDto); }
                 */
                for (PermissionAction action :permissionActions) {

                    List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUserPermissionAndAction(userId,permission.getPermissionsId(),action.getActionName());
                    if(permissionRequests.size()!=0) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        actionDto.setSelected(true);
                        permission.setActions(dtos);
                        permission.setActions(dtos);
                    }else {
                        Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                        ActionDto actionDto = new ActionDto();
                        actionDto.setActionName(action.getActionName());
                        if(optionalAction.isPresent())
                        {
                            actionDto.setSelected(true);
                        }
                        else {
                            actionDto.setSelected(false); } dtos.add(actionDto);
                    }
                    permission.setActions(dtos);
                }
                permission.setActions(dtos);

            }

            

            permissionsDTO.setMachinemasterPermission(machinemasterPermission);
            permissionsDTO.setEmsmasterPermission(emsmasterPermission);
            permissionsDTO.setAssetmasterPermission(assetmasterPermission);
            
            permissionsDTO.setBreakdownPermission(breakdownPermission);
            permissionsDTO.setMaintenanceofmachinePermission(maintenanceofmachinePermission);
            permissionsDTO.setTransactionPermission(transactionPermission);
            
            permissionsDTO.setItreportPermission(itreportPermission);
            permissionsDTO.setEmsreportPermission(emsreportPermission);
            permissionsDTO.setReportPermission(reportPermission);
            
            permissionsDTO.setDashboardPermission(dashboardPermission);
            permissionsDTO.setConfigurationPermission(configurationPermission);


           
            //   permissionsDTO.setUserPermissions(permissionsByUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionsDTO;
    }
    @GetMapping({"/getPermissionsAndActionByRole2"})
    @ResponseBody
    public PermissionsDTO getPermissionsAndActionByRole2(@RequestParam int roleId ,int userId) {
        PermissionsDTO permissionsDTO = new PermissionsDTO();
        try {


            List<String> categories=accessService.getAllCateories();

            List<Permissions> machinemasterPermission = this.accessService.getPermissionsByCategory("Machine Master");
            List<Permissions> emsmasterPermission = this.accessService.getPermissionsByCategory("EMS Master");
            List<Permissions> assetmasterPermission = this.accessService.getPermissionsByCategory("Asset Master");
            List<Permissions> breakdownPermission = this.accessService.getPermissionsByCategory("Breakdown");
            List<Permissions> maintenanceofmachinePermission = this.accessService.getPermissionsByCategory("Maintenance Of Machine");
            List<Permissions> transactionPermission = this.accessService.getPermissionsByCategory("Transaction");
            List<Permissions> itreportPermission = this.accessService.getPermissionsByCategory("IT Report");     
            List<Permissions> emsreportPermission = this.accessService.getPermissionsByCategory("Ems Report");
            List<Permissions> reportPermission = this.accessService.getPermissionsByCategory("Report");
            List<Permissions> dashboardPermission = this.accessService.getPermissionsByCategory("Dashboard");
            List<Permissions> configurationPermission = this.accessService.getPermissionsByCategory("Configuration");
        
            


            for (Permissions permission : machinemasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                // System.out.println("roleId " + roleId + " PERMo" + permission.getPermissionsId() + "  " + optional.isPresent());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                } else {
                    permission.setSelected(false);
                }
                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);
            }



            for (Permissions permission : emsmasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                // System.out.println("roleId " + roleId + " PERMo" + permission.getPermissionsId() + "  " + optional.isPresent());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                } else {
                    permission.setSelected(false);
                }
                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);
            }



            for (Permissions permission : assetmasterPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    // continue;
                } else {
                    permission.setSelected(false);
                }


                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);
            }
            
            
            for (Permissions permission : breakdownPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    // continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);





            }
            for (Permissions permission : maintenanceofmachinePermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //  continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);
            }
            //
            for (Permissions permission : transactionPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
            
            
            //
            for (Permissions permission : itreportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
            //
            for (Permissions permission : emsreportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
            //
            for (Permissions permission : reportPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
            //
            for (Permissions permission : dashboardPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            //
            
            for (Permissions permission : configurationPermission) {
                Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permission.getPermissionsId());
                if (optional.isPresent()) {
                    permission.setSelected(true);
                    //continue;
                } else {
                    permission.setSelected(false);
                }

                List<PermissionAction> permissionActions = this.accessService.getPermissionActionBYPermissionId(permission.getPermissionsId());

                Set<ActionDto> dtos = new HashSet<>();
                for (PermissionAction action :permissionActions) {
                    Optional<RolePermissionAction> optionalAction =this.rolePermissionActionRepo.getRolePermissionActionByRoleAndPermission(action.getPermissionAsactionId(), roleId);
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(action.getActionName());
                    if(optionalAction.isPresent())
                    {
                        actionDto.setSelected(true);
                    }
                    else {
                        actionDto.setSelected(false); } dtos.add(actionDto);
                }

                permission.setActions(dtos);

            }
            
            
            
            
            
            
            
            
            // int userId=1;
            List<PermissionRequest> permissionRequests= permissionRequestRepo.getApprovedPermissionRequestByUser(userId);

            List<Permissions> permissionsByUser= new ArrayList<Permissions>();
            Set<Permissions> permissions= new HashSet<Permissions>();
            for(PermissionRequest permissionRequest:permissionRequests) {
                permissions.add(permissionRequest.getPermissions());

            }
            for(Permissions permissions2:permissions) {

                List<PermissionRequest> permissionRequestsByPermision= permissionRequestRepo.getApprovedPermissionRequestByUserAndPermission(userId,permissions2.getPermissionsId());
                Set<ActionDto> dtos = new HashSet<>();

                for(PermissionRequest request:permissionRequestsByPermision) {
                    ActionDto actionDto = new ActionDto();
                    actionDto.setActionName(request.getPermissionAction().getActionName());
                    actionDto.setSelected(true);
                    dtos.add(actionDto);

                }
                permissions2.setActions(dtos);

                if(permissions2.getCategory().equalsIgnoreCase("Machine Master")) {
                	machinemasterPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("EMS Master")) {
                	emsmasterPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Asset Master")) {
                	assetmasterPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Breakdown")) {
                	breakdownPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Maintenance Of Machine")) {
                	maintenanceofmachinePermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Transaction")) {
                	transactionPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("IT Report")) {
                	itreportPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Ems Report")) {
                	emsreportPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Report")) {
                	reportPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Dashboard")) {
                	dashboardPermission.add(permissions2);
                }
                if(permissions2.getCategory().equalsIgnoreCase("Configuration")) {
                	configurationPermission.add(permissions2);
                }
                //permissionsByUser.add(permissions2);
            }

         


            permissionsDTO.setMachinemasterPermission(machinemasterPermission);
            permissionsDTO.setEmsmasterPermission(emsmasterPermission);
            permissionsDTO.setAssetmasterPermission(assetmasterPermission);
            
            permissionsDTO.setBreakdownPermission(breakdownPermission);
            permissionsDTO.setMaintenanceofmachinePermission(maintenanceofmachinePermission);
            permissionsDTO.setTransactionPermission(transactionPermission);
            
            permissionsDTO.setItreportPermission(itreportPermission);
            permissionsDTO.setEmsreportPermission(emsreportPermission);
            permissionsDTO.setReportPermission(reportPermission);
            
            permissionsDTO.setDashboardPermission(dashboardPermission);
            permissionsDTO.setConfigurationPermission(configurationPermission);





          //  permissionsDTO.setUploadPermission(uploadPermission);
            // permissionsDTO.setUserPermissions(permissionsByUser);   setStockingPermission stockingPermission



        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionsDTO;
    }

    @GetMapping({"/getPermissionAction"})
    @ResponseBody
    public List<PermissionAction> getPermissionAction() {
        List<PermissionAction> list = new ArrayList<>();
        try {
            list = this.permissionActionRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @PostMapping({"/addPermissionAction"})
    @ResponseBody
    public ResponseEntity<ResponceObj> addPermissionAction(@RequestBody PermissionAction permissionAction) {
        ResponceObj responceDTO = new ResponceObj();
        try {
            List<PermissionAction> list=permissionActionRepo.getPermissionActionBYPermissionIdAndActionName1(permissionAction.getPermissions().getPermissionsId(), permissionAction.getActionName());

            if(list.size()!=0) {
                responceDTO.setCode(500);
                responceDTO.setMessage("Permission Action aleady exits");
            }else {
                this.permissionActionRepo.save(permissionAction);
                responceDTO.setCode(200);
                responceDTO.setMessage("Permission Action Added Success");
            }

            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping({"/getRolesPermission"})
    @ResponseBody
    public List<RolePermissionDto> getRolesPermission() {
        List<RolePermissionDto> list = new ArrayList<>();
        try {
            List<Role> roles = this.roleRepo.findAll();
            List<Permissions> permissions = this.accessService.getAllPermission();
            // //System.out.printlnr("PER " + permissions.size());
            for (Role role : roles) {
                RolePermissionDto rolePermissionDto = new RolePermissionDto();
                rolePermissionDto.setRole(role);
                List<PermissionDTO> dtos = new ArrayList<>();
                for (Permissions permission : permissions) {
                    PermissionDTO permissionDTO = new PermissionDTO();
                    permissionDTO.setPermissionName(permission.getPermissionsName());
                    permissionDTO.setCategory(permission.getCategory());
                    Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(role.getRole_id(), permission.getPermissionsId());
                    if (optional.isPresent()) {
                        permissionDTO.setPermissionAvailable(true);
                    } else {
                        permissionDTO.setPermissionAvailable(false);
                    }
                    permissionDTO.setEditTab(false);
                    dtos.add(permissionDTO);
                }
                rolePermissionDto.setPermissions(dtos);
                list.add(rolePermissionDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping({"/getPermissionsByRole"})
    @ResponseBody
    public List<Permissions> getPermissionsByRole(@RequestParam int roleId) {
        List<Permissions> list = new ArrayList<>();
        try {
            List<RolePermission> rolepermissions = this.accessService.getPermissionsByRole(roleId);
            for (RolePermission rolePermission : rolepermissions)
                list.add(rolePermission.getPermissions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping({"/getDashboardPermissionsByRole"})
    @ResponseBody
    public DashboardPermissionsDTO getDashboardPermissionsByRole(@RequestParam int roleId) {
        DashboardPermissionsDTO dashboardPermissionsDTO = new DashboardPermissionsDTO();
        try {
            List<RolePermission> rolepermissions = this.accessService.getPermissionsByRoleAndCatrgory(roleId, "Dashboard");
            for (RolePermission rolePermission : rolepermissions) {
                if (rolePermission.getPermissions().getPermissionsName().equalsIgnoreCase("Overview"))
                    dashboardPermissionsDTO.setOverview(true);
                if (rolePermission.getPermissions().getPermissionsName().equalsIgnoreCase("System"))
                    dashboardPermissionsDTO.setSystem(true);
                if (rolePermission.getPermissions().getPermissionsName().equalsIgnoreCase("Install"))
                    dashboardPermissionsDTO.setInstall(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dashboardPermissionsDTO;
    }

    @PostMapping({"/saveRolePermission"})
    @ResponseBody
    public ResponseEntity<ResponceObj> saveRolePermission(@RequestBody RolePermissionDto rolePermissions) {
        ResponceObj responceDTO = new ResponceObj();
        try {
            Role role = rolePermissions.getRole();
            for (PermissionDTO permissions : rolePermissions.getPermissions()) {
                Optional<Permissions> optional = this.accessService.getPermissionsByName(permissions.getPermissionName());
                Optional<RolePermission> optional2 = this.accessService.getRolePermissionByRoleAndPermission(role.getRole_id(), ((Permissions)optional.get()).getPermissionsId());
                if (permissions.isPermissionAvailable()) {
                    if (optional2.isEmpty()) {
                        RolePermission rolepermission = new RolePermission();
                        rolepermission.setPermissions(optional.get());
                        rolepermission.setRole(role);
                        this.accessService.saveRolePermission(rolepermission);
                        for (int i = 1; i <= 5; i++) {
                            PermissionAction action = new PermissionAction();
                            action.setPermissions(rolepermission.getPermissions());
                            if (i == 1) {
                                //System.out.printlnr("Add Permision");
                                action.setActionName("Add");
                                action.setAvailable(false);
                                this.accessService.savePermissionAction(action);
                            }
                            if (i == 2) {
                                //System.out.printlnr("Edit Permision");
                                //System.out.printlnr("Add Permision");
                                action.setActionName("Add");
                                action.setAvailable(false);
                                this.accessService.savePermissionAction(action);
                            }
                            if (i == 3) {
                                //System.out.printlnr("View Permision");
                                action.setActionName("View");
                                action.setAvailable(false);
                                this.accessService.savePermissionAction(action);
                            }
                            if (i == 4) {
                                //System.out.printlnr("Delete Permision");
                                action.setActionName("Delete");
                                action.setAvailable(false);
                                this.accessService.savePermissionAction(action);
                            }
                            if (i == 5) {
                                //System.out.printlnr("Upload Permision");
                                action.setActionName("Upload");
                                action.setAvailable(false);
                                this.accessService.savePermissionAction(action);
                            }
                        }
                    }
                    continue;
                }
                if (optional2.isPresent())
                    this.accessService.deleteRolePermission(optional2.get());
            }
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping({"/addPermission"})
    @ResponseBody
    public ResponseEntity<ResponceObj> addPermission(@RequestBody Permissions permissions) {
        ResponceObj responceDTO = new ResponceObj();
        permissions.setPermissionsName(permissions.getPermissionsName().trim());
        Optional<Permissions> optional = this.accessService.getPermissionsByName(permissions.getPermissionsName());
        try {
            //    Optional<Permissions> optional = this.accessService.getPermissionsByNameAndCategory(permissions.getCategory(), permissions.getPermissionsName());
            if(permissions.getPermissionsId()==0) {
                if(optional.isPresent()) {
                    responceDTO.setCode(500);
                    responceDTO.setMessage("Permission Name already Exits");
                    //System.out.printlnr("permission EXITS.........................");
                }else {
                    this.accessService.addPermission(permissions);
                    responceDTO.setCode(200);
                    responceDTO.setMessage("Permission  Added Successfully");
                    //System.out.printlnr("permission Added.........................");
                }
            }else {
                if (optional.isPresent() && optional.get().getPermissionsId() != permissions.getPermissionsId()) {
                    responceDTO.setCode(500);
                    responceDTO.setMessage("Permission Name already Exits");
                    //System.out.printlnr("permission EXITS.........................");
                } else {
                    this.accessService.addPermission(permissions);
                    responceDTO.setCode(200);
                    responceDTO.setMessage("Permission  Updated Successfully");
                    //System.out.printlnr("permission Updated.........................");
                }
            }
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping({"/deletePermission"})
    @ResponseBody
    public ResponseEntity<ResponceObj> deletePermission(@RequestBody Permissions permissions) {
        ResponceObj responceDTO = new ResponceObj();
        try {
            Optional<Permissions> optional = null;
            //System.out.printlnr("Saving Permission " + permissions.getPermissionsName());
            if (optional.isPresent()) {
                responceDTO.setCode(500);
                responceDTO.setMessage("Permissions Are used someever so can not be deleted");
            } else {
                permissions.setActive(1);
                this.accessService.deletePermission(permissions);
                responceDTO.setCode(200);
                responceDTO.setMessage("Permissions Deleted Successfully");
            }
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping({"/changeStatusPermission"})
    @ResponseBody
    public ResponseEntity<ResponceObj> changeStatusPermission(@RequestBody Permissions permissions) {
        ResponceObj responceDTO = new ResponceObj();
        try {
            if (permissions.getActive() == 1) {
                permissions.setActive(0);
            } else {
                permissions.setActive(1);
            }
            this.accessService.addPermission(permissions);
            responceDTO.setCode(200);
            responceDTO.setMessage("Status Changed Successfully");
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }


    @GetMapping({"/getPermissionsByPagination/{page_no}/{item_per_page}"})
    @ResponseBody
    public List<Permissions> getPermissionsByPagination(@PathVariable int page_no, @PathVariable int item_per_page) {
        List<Permissions> list = new ArrayList<>();
        try {
            list = this.accessService.getPermissionsByPagination(page_no, item_per_page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping({"/getPermissionsByPaginationAndSerach"})
    @ResponseBody
    public List<Permissions> getPermissionsByPaginationAndSerach(@RequestParam int page_no, @RequestParam int item_per_page, @RequestParam String search) {
        List<Permissions> list = new ArrayList<>();
        try {
            list = this.accessService.getPermissionsByPaginationAndSerach(page_no, item_per_page, search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping({"/getAllPermissions"})
    @ResponseBody
    public List<Permissions> getAllPermissions() {
        List<Permissions> list = new ArrayList<>();
        try {
            list = this.accessService.getAllPermissions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping({"/getPermissionsCount"})
    @ResponseBody
    public int getPermissionsCount() {
        int count = 0;
        try {
            count = this.accessService.getPermissionsCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @GetMapping({"/getCategoryWisePermissionsCount"})
    @ResponseBody
    public CategoryPermissionCountDto getCategoryWisePermissionsCount() {
        CategoryPermissionCountDto categoryPermissionCountDto = new CategoryPermissionCountDto();
        int count = 0;
        try {
            int mastercount = this.accessService.getPermissionsCountByCategory("Master");
            int transactioncount = this.accessService.getPermissionsCountByCategory("Transaction");
            int reportcount = this.accessService.getPermissionsCountByCategory("Report");
            int dashbaoedcount = this.accessService.getPermissionsCountByCategory("Dashboard");
            categoryPermissionCountDto.setDashbaoedcount(dashbaoedcount);
            categoryPermissionCountDto.setReportcount(reportcount);
            categoryPermissionCountDto.setTransactioncount(transactioncount);
            categoryPermissionCountDto.setMastercount(mastercount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryPermissionCountDto;
    }

    @GetMapping({"/getCategoryWiseTotalPermisions"})
    @ResponseBody
    public CategoryPermissionCountDto getCategoryWiseTotalPermisions() {
        CategoryPermissionCountDto categoryPermissionCountDto = new CategoryPermissionCountDto();
        int count = 0;
        try {
            int mastercount = this.accessService.getPermissionsMasterCategory("Master");
            int transactioncount = this.accessService.getPermissionsMasterCategory("Transaction");
            int reportcount = this.accessService.getPermissionsMasterCategory("Report");
            int dashbaoedcount = this.accessService.getPermissionsMasterCategory("Dashboard");
            categoryPermissionCountDto.setDashbaoedcount(dashbaoedcount);
            categoryPermissionCountDto.setReportcount(reportcount);
            categoryPermissionCountDto.setTransactioncount(transactioncount);
            categoryPermissionCountDto.setMastercount(mastercount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryPermissionCountDto;
    }

    @GetMapping({"/getPermissionsCountBySearch/{search}"})
    @ResponseBody
    public int getPermissionsCountBySearch(@PathVariable String search) {
        int count = 0;
        try {
            count = this.accessService.getPermissionsCountBySearch(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @GetMapping({"/checkPermissionName"})
    @ResponseBody
    public ResponceObj checkPermissionName(@RequestParam String permissionName) {
        ResponceObj responceObj = new ResponceObj();
        try {
            Optional<Permissions> optional = this.accessService.getPermissionsByName(permissionName);
            if (optional.isPresent()) {
                responceObj.setCode(500);
                responceObj.setMessage("Permission name already Exit");
            } else {
                responceObj.setCode(200);
                responceObj.setMessage("Permission name is valid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responceObj;
    }

    @GetMapping({"/checkRoleName"})
    @ResponseBody
    public ResponceObj checkRoleName(@RequestParam String roleName) {
        ResponceObj responceObj = new ResponceObj();
        try {
            Optional<Role> optional = this.accessService.getRoleByName(roleName);
            if (optional.isPresent()) {
                responceObj.setCode(500);
                responceObj.setMessage("Role name already Exit");
            } else {
                responceObj.setCode(200);
                responceObj.setMessage("Role name is valid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responceObj;
    }

    @PostMapping({"/addRole"})
    @ResponseBody
    public ResponseEntity<ResponceObj> addRole(@RequestBody Role role) {
        ResponceObj responceDTO = new ResponceObj();
        role.setRoleName(role.getRoleName().trim());
        Optional<Role> optional=accessService.getRoleByName(role.getRoleName());
        try {
            if(role.getRole_id()==0) {
                if(optional.isPresent()) {
                    //System.out.printlnr("Exits....................");
                    responceDTO.setCode(500);
                    responceDTO.setMessage("Role Name is already Exits");
                }else {
                    System.out.println("Added....................");
                    this.accessService.addRole(role);
                    responceDTO.setCode(200);
                    responceDTO.setMessage("Role  is added Successfully");
                }
            }else {
                if (optional.isPresent() && optional.get().getRole_id() != role.getRole_id()){
                    //System.out.printlnr("Exits....................");
                    responceDTO.setCode(500);
                    responceDTO.setMessage("Role Name is already Exits");
                }else{
                    //System.out.printlnr("updated....................");
                    this.accessService.addRole(role);
                    responceDTO.setCode(200);
                    responceDTO.setMessage("Role  is updated Successfully");
                }
            }

            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping({"/getPermisssionActionByRoleAndPermission"})
    @ResponseBody
    public List<PermissionAction>  getPermisssionActionByRoleAndPermission(@RequestParam int roleId, @RequestParam int permissionsId) {
        List<PermissionAction>  permissionActions = new ArrayList<PermissionAction> ();
        try {
            Optional<RolePermission> optional = this.accessService.getRolePermissionByRoleAndPermission(roleId, permissionsId);
            permissionActions=permissionActionRepo.getPermissionActionBYPermissionId(permissionsId) ;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionActions;
    }

    @PostMapping({"/updatePermissionAction"})
    @ResponseBody
    public ResponseEntity<ResponceObj> updatePermissionAction(@RequestBody PermissionAction permissionAction) {
        ResponceObj responceDTO = new ResponceObj();
        try {
            this.accessService.savePermissionAction(permissionAction);
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            responceDTO.setCode(500);
            responceDTO.setMessage(e.getMessage());
            return new ResponseEntity(responceDTO, HttpStatus.ACCEPTED);
        }
    }
}
