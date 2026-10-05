package com.brainbridge.repository;

import com.brainbridge.entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    List<ConnectionRequest> findBySenderIdOrReceiverIdOrderByIdDesc(Long senderId, Long receiverId);

    Optional<ConnectionRequest> findBySenderIdAndReceiverIdAndStatus(
            Long senderId,
            Long receiverId,
            ConnectionRequest.Status status
    );

    boolean existsBySenderIdAndReceiverIdAndStatus(
            Long senderId,
            Long receiverId,
            ConnectionRequest.Status status
    );
}
