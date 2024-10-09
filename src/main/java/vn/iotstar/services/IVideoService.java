package vn.iotstar.services;

import java.util.List;
import vn.iotstar.entity.Video;

public interface IVideoService {
    void insert(Video video);
    void update(Video video);
    void delete(int id) throws Exception;
    Video findById(int id);
    List<Video> findAll();
    List<Video> findAll(int page, int pageSize);
    int count();
	
}
