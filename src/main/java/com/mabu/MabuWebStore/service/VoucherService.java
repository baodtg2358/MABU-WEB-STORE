package com.mabu.MabuWebStore.service;

import com.mabu.MabuWebStore.entity.Voucher;

import java.util.List;
public interface VoucherService {
    List<Voucher> findAllVoucher();
    
    Voucher findById(int id);

    Voucher findByVoucherCode(String code);

    Voucher createVoucher(Voucher voucher);

    Voucher updateVoucher(Voucher voucher);

    void deleteVoucher(Integer id);
}
