package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.DBFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DBFileRepository extends CrudRepository<DBFile, String> {

    DBFile findFirstByFileName(String fileName);
}
