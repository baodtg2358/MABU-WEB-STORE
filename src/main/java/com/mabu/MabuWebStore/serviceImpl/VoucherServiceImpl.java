package com.mabu.MabuWebStore.serviceImpl;

import java.util.List;


import com.mabu.MabuWebStore.entity.Voucher;
import com.mabu.MabuWebStore.repository.VoucherRepository;
import com.mabu.MabuWebStore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> findAllVoucher(){
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findByVoucherCode(String code) {
        return voucherRepository.findByVoucherCode(code).isPresent()?voucherRepository.findByVoucherCode(code).get():null;
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.saveAndFlush(voucher);
    }

    @Override
    public void deleteVoucher(Integer id) {
        voucherRepository.deleteById(id);
    }

	@Override
	public Voucher findById(int id) {
		// TODO Auto-generated method stub
		return voucherRepository.findById(id).isPresent()?voucherRepository.findById(id).get():null;
	}
}
