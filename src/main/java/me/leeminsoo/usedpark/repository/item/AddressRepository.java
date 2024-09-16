package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
