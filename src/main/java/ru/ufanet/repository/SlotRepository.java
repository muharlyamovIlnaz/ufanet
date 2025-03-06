package ru.ufanet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ufanet.models.SlotEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<SlotEntity, Long> {

    int countByDataAndTime(LocalDate data, LocalTime time);

    @Query(value = """
            SELECT time, COUNT(id) FROM pool.timetable 
            WHERE data = :data GROUP BY time
            """, nativeQuery = true)
    List<Object[]> getBusySlotsCount(@Param("data") LocalDate data);

    @Query("SELECT s.clientId FROM SlotEntity s WHERE s.data = :data AND s.time = :time")
    List<Long> getClientsIdByDataAndTime(@Param("data") LocalDate data, @Param("time") LocalTime time);


    @Query("SELECT s.clientId FROM SlotEntity s WHERE s.data = :data")
    List<Long> getClientIdByData(@Param("data") LocalDate data);

}
