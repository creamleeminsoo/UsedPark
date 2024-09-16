package me.leeminsoo.usedpark.service.item;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.item.Address;
import me.leeminsoo.usedpark.repository.item.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> getAddresses(){
        return addressRepository.findAll();
    }
}
