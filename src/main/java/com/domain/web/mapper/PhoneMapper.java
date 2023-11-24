package com.domain.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.domain.model.Phone;
import com.domain.web.vo.PhoneVo;

@Mapper
public interface PhoneMapper {

	public PhoneVo toVo(Phone phone);

	public Phone toEntity(PhoneVo vo);

	public List<PhoneVo> toVos(List<Phone> list);

	public List<Phone> toEntities(List<PhoneVo> list);

}