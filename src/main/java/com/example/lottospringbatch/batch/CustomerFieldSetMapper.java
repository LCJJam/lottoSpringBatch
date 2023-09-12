package com.example.lottospringbatch.batch;

import com.example.lottospringbatch.entity.LottoEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CustomerFieldSetMapper implements FieldSetMapper<LottoEntity> {
    @Override
    public LottoEntity mapFieldSet(FieldSet fs) {

        if (fs == null) {
            return null;
        }

        LottoEntity customer = new LottoEntity();
        customer.setBallNum1(Integer.parseInt(fs.readString(0)));
        customer.setBallNum2(Integer.parseInt(fs.readString(1)));
        customer.setBallNum3(Integer.parseInt(fs.readString(2)));
        customer.setBallNum4(Integer.parseInt(fs.readString(3)));
        customer.setBallNum5(Integer.parseInt(fs.readString(4)));
        customer.setBallNum6(Integer.parseInt(fs.readString(5)));

        return customer;
    }
}
