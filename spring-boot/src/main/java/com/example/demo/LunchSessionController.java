package com.example.demo;

import com.example.dto.JoinSessionRequest;
import com.example.dto.LocationInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin
@RestController
public class LunchSessionController {
    private final Set sessionSet;
    private final Map selectedRestaurant;
    private final Map joinedSessionMap;
    private final Map<String, ConcurrentHashMap<String, String>> lunchLocationMap;

    @Autowired
    public LunchSessionController(@Qualifier("sessionSet") Set sessionMap, @Qualifier("lunchLocationMap") Map lunchLocationMap,
                                  @Qualifier("selectedRestaurant") Map selectedRestaurant, @Qualifier("joinedSessionMap") Map joinedSessionMap) {
        this.sessionSet = sessionMap;
        this.lunchLocationMap = lunchLocationMap;
        this.selectedRestaurant = selectedRestaurant;
        this.joinedSessionMap = joinedSessionMap;
    }

    @PostMapping("/initiate-session")
    public ResponseEntity<String> InitiateSession() {
        String sessionId = UUID.randomUUID().toString().concat(String.valueOf(System.currentTimeMillis()));
        this.sessionSet.add(sessionId);
        this.lunchLocationMap.put(sessionId, new ConcurrentHashMap<>());
        return new ResponseEntity<>(sessionId, HttpStatus.CREATED);
    }

    @PostMapping(value = "/join-session", consumes = {"application/json"})
    public ResponseEntity<Object> joinSession(@RequestBody JoinSessionRequest joinSessionRequest) {
        if (!this.sessionSet.contains(joinSessionRequest.sessionId())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        this.joinedSessionMap.put(joinSessionRequest.teamMemberName(), joinSessionRequest.sessionId());
        return new ResponseEntity<>(lunchLocationMap.get(joinSessionRequest.sessionId()), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/add-location", consumes = {"application/json"})
    public ResponseEntity<Map> addLocation(@RequestBody LocationInfoRequest locationInfo) {
        if (!this.sessionSet.contains(locationInfo.sessionId()) ||  this.joinedSessionMap.get(locationInfo.teamMemberName())==null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        Map SessionIdMap = this.lunchLocationMap.get(locationInfo.sessionId());
        SessionIdMap.put(locationInfo.teamMemberName(), locationInfo.locationName());
        return new ResponseEntity<>(SessionIdMap, HttpStatus.ACCEPTED);
    }

    @PostMapping("/end-session")
    public ResponseEntity<String> endSession(String sessionId) {
        this.sessionSet.remove(sessionId);
        Object[] mapKeys = lunchLocationMap.get(sessionId).keySet().toArray();
        Object key = mapKeys[new Random().nextInt(mapKeys.length)];
        String location = lunchLocationMap.get(sessionId).get(key);
        this.selectedRestaurant.put(sessionId, location);
        this.lunchLocationMap.remove(sessionId);
        return new ResponseEntity<>(location, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/list-all-restaurant")
    public ResponseEntity<Object> listAllRestaurant(String name, String sessionId) {
        if (this.joinedSessionMap.get(name) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(this.selectedRestaurant.get(sessionId), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/list-finalize-restaurant")
    public ResponseEntity<Object> listFinalizeRestaurant(String name, String sessionId) {
        if (this.joinedSessionMap.get(name) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(this.selectedRestaurant.get(sessionId), HttpStatus.ACCEPTED);
    }
}
