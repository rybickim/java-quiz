package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.DBFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends CrudRepository<DBFile, String> {
}
