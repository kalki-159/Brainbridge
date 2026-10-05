package com.brainbridge.service;

import com.brainbridge.entity.ConnectionRequest;
import com.brainbridge.entity.User;
import com.brainbridge.repository.ConnectionRequestRepository;
import com.brainbridge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {

    private final ConnectionRequestRepository connectionRepository;
    private final UserRepository userRepository;

    public ConnectionService(ConnectionRequestRepository connectionRepository,
                             UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    public ConnectionRequest sendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("You cannot connect with yourself.");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found."));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found."));

        if (connectionRepository.existsBySenderIdAndReceiverIdAndStatus(
                senderId, receiverId, ConnectionRequest.Status.PENDING)) {
            throw new IllegalArgumentException("A pending request already exists.");
        }

        if (connectionRepository.existsBySenderIdAndReceiverIdAndStatus(
                receiverId, senderId, ConnectionRequest.Status.PENDING)) {
            throw new IllegalArgumentException("This user already sent you a pending request.");
        }

        ConnectionRequest request = new ConnectionRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(ConnectionRequest.Status.PENDING);

        return connectionRepository.save(request);
    }

    public List<ConnectionRequest> getRequests(Long userId) {
        return connectionRepository.findBySenderIdOrReceiverIdOrderByIdDesc(userId, userId);
    }

    public ConnectionRequest accept(Long id, Long receiverId) {
        ConnectionRequest request = getRequest(id);

        if (!request.getReceiver().getId().equals(receiverId)) {
            throw new IllegalArgumentException("Only the receiver can accept this request.");
        }

        request.setStatus(ConnectionRequest.Status.ACCEPTED);
        return connectionRepository.save(request);
    }

    public ConnectionRequest reject(Long id, Long receiverId) {
        ConnectionRequest request = getRequest(id);

        if (!request.getReceiver().getId().equals(receiverId)) {
            throw new IllegalArgumentException("Only the receiver can reject this request.");
        }

        request.setStatus(ConnectionRequest.Status.REJECTED);
        return connectionRepository.save(request);
    }

    private ConnectionRequest getRequest(Long id) {
        return connectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Connection request not found."));
    }
}
