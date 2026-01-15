package com.kfMaintenancce.service;

import com.kfMaintenancce.model.MaintSpareStock;
import com.kfMaintenancce.repo.MaintSpareStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MaintSpareStockServiceImpl implements MaintSpareStockServices{

    @Autowired
    private MaintSpareStockRepo maintSpareStockRepository;

    public List<MaintSpareStock> getAllSpareStocks() {
        return maintSpareStockRepository.findAll();
    }

  

    public MaintSpareStock saveSpareStock(MaintSpareStock spareStock) {
        return maintSpareStockRepository.save(spareStock);
    }

//    public MaintSpareStock updateSpareStock(int id, MaintSpareStock updatedStock) {
//        return maintSpareStockRepository.findById(id).map(stock -> {
//            stock.setMaintSpare(updatedStock.getMaintSpare());
//            stock.setQuantity(updatedStock.getQuantity());
//            stock.setUnitPrice(updatedStock.getUnitPrice());
//            stock.setTotalPrice(updatedStock.getTotalPrice());
//            return maintSpareStockRepository.save(stock);
//        }).orElseThrow(() -> new RuntimeException("Spare Stock not found with id " + id));
//    }

    public void deleteSpareStock(int id) {
        maintSpareStockRepository.deleteById(id);
    }
}
