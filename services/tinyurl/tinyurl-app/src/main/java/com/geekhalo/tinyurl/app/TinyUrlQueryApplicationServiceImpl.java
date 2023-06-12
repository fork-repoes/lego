package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.codec.NumberCodec;
import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlQueryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyUrlQueryApplicationServiceImpl implements TinyUrlQueryApplicationService{

    @Autowired
    private TinyUrlQueryRepository tinyUrlQueryRepository;

    @Autowired
    private NumberCodec numberCodec;

    @Override
    public Optional<TinyUrl> findById(Long id) {
        if (id == null){
            return Optional.empty();
        }
        return tinyUrlQueryRepository.findById(id);
    }

    @Override
    public Optional<TinyUrl> findByCode(String code) {
        if (StringUtils.isEmpty(code)){
            return Optional.empty();
        }

        Long number = this.numberCodec.decode(code);
        return this.findById(number);
    }
}
